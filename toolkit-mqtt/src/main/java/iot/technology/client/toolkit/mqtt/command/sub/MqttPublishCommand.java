/*
 * Copyright © 2019-2022 The Toolkit Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package iot.technology.client.toolkit.mqtt.command.sub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.util.concurrent.Future;
import iot.technology.client.toolkit.common.constants.*;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.ObjectUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.mqtt.config.MqttSettings;
import iot.technology.client.toolkit.mqtt.service.MqttClientConfig;
import iot.technology.client.toolkit.mqtt.service.MqttClientService;
import iot.technology.client.toolkit.mqtt.service.MqttSettingsRuleChainProcessor;
import iot.technology.client.toolkit.mqtt.service.domain.MqttConnectResult;
import iot.technology.client.toolkit.mqtt.service.domain.MqttPubNewConfigDomain;
import iot.technology.client.toolkit.mqtt.service.handler.MqttPubMessageHandler;
import iot.technology.client.toolkit.mqtt.service.impl.MqttBizService;
import iot.technology.client.toolkit.mqtt.service.impl.MqttClientServiceImpl;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import picocli.CommandLine;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "publish",
		aliases = "pub",
		requiredOptionMarker = '*',
		description = "${bundle:mqtt.pub.description}",
		optionListHeading = "%n${bundle:general.option}:%n",
		sortOptions = false,
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei"
)
public class MqttPublishCommand implements Callable<Integer> {

	private static final Charset UTF8 = StandardCharsets.UTF_8;

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);


	@Override
	public Integer call() throws Exception {
		Terminal terminal = TerminalBuilder.builder()
				.system(true)
				.build();
		LineReader reader = LineReaderBuilder.builder()
				.terminal(terminal)
				.parser(new DefaultParser())
				.build();

		MqttBizService bizService = new MqttBizService();
		if (bizService.notExistOrContents()) {
			//TODO add a new mqtt settings
			MqttSettingsRuleChainProcessor ruleChain = new MqttSettingsRuleChainProcessor();
			Map<String, String> processor = ruleChain.getPublishNewConfigProcessor();
			String code = ruleChain.getRootPublishNewConfigNode();

			MqttPubNewConfigDomain domain = new MqttPubNewConfigDomain();
			while (true) {
				String data = null;
				try {
					String node = processor.get(code);
					TkNode tkNode = ObjectUtils.initComponent(node);
					if (tkNode == null) {
						break;
					}
					tkNode.prePrompt();
					data = reader.readLine(tkNode.nodePrompt());
					tkNode.check(data);
					if (!StringUtils.isBlank(tkNode.getValue(data))) {
						System.out.format(ColorUtils.blackFaint(bundle.getString("call.prompt") + tkNode.getValue(data)) + "%n");
					}
					ObjectUtils.setValue(domain, code, tkNode.getValue(data));
					mqttBizLogic(code, data, domain);

					NodeContext context = new NodeContext();
					context.setData(data);
					context.setType(NodeTypeEnum.MQTT_PUBLISH.getType());
					code = tkNode.nextNode(context);
					if (code.equals("end")) {
						break;
					}
				} catch (UserInterruptException e) {
					return ExitCodeEnum.ERROR.getValue();
				} catch (EndOfFileException e) {
					return ExitCodeEnum.ERROR.getValue();
				}
			}

		}
		/**
		 * 1、0~9: select mqtt configs that was set earlier
		 * 2、add a new mqtt settings
		 */
		return ExitCodeEnum.NOTEND.getValue();
	}


	/**
	 * @param code   node code
	 * @param data   user input data
	 * @param domain mqtt settings domain
	 */
	public void mqttBizLogic(String code, String data, MqttPubNewConfigDomain domain) {
		if ((code.equals(MqttSettingsCodeEnum.LASTWILLANDTESTAMENT.getCode())
				&& data.toUpperCase().equals(ConfirmCodeEnum.NO.getValue()))
				|| code.equals(MqttSettingsCodeEnum.LAST_WILL_PAYLOAD.getCode())) {
			MqttClientService mqttClientService = connectBroker(domain);
			domain.setClient(mqttClientService);
			String settingsJson = convertMqttSettings(domain);
			System.out.println(settingsJson);
		}
		if (code.equals(MqttSettingsCodeEnum.PUBLISH_MESSAGE.getCode())) {
			PubData pubData = PubData.validate(data);
			mqttPubLogic(pubData, domain.getClient());
		}
	}

	public String convertMqttSettings(MqttPubNewConfigDomain domain) {
		MqttSettings settings = new MqttSettings();
		settings.setName(domain.getSettingsName() + "@" + domain.getHost() + ":" + domain.getPort());
		MqttSettings.MqttSettingInfo info = new MqttSettings.MqttSettingInfo();
		info.setVersion(domain.getMqttVersion());
		info.setClientId(domain.getClientId());
		info.setHost(domain.getHost());
		info.setPort(domain.getPort());
		info.setUsername(domain.getUsername());
		info.setPassword(domain.getPassword());
		info.setSsl(domain.getSsl());
		info.setCertType(domain.getCertType());
		info.setCa(domain.getCa());
		info.setClientCert(domain.getClientCert());
		info.setClientKey(domain.getClientKey());
		info.setAdvanced(domain.getAdvanced());
		info.setConnectTimeout(domain.getConnectTimeout());
		info.setKeepAlive(domain.getKeepAlive());
		info.setCleanSession(domain.getCleanSession());
		info.setAutoReconnect(domain.getAutoReconnect());
		info.setLastWillAndTestament(domain.getLastWillAndTestament());
		info.setLastWillQoS(domain.getLastWillQoS());
		info.setLastWillTopic(domain.getLastWillTopic());
		info.setLastWillRetain(domain.getLastWillRetain());
		info.setLastWillPayload(domain.getLastWillPayload());
		settings.setInfo(info);
		String json = "";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			json = objectMapper.writeValueAsString(settings);
		} catch (JsonProcessingException e) {
			System.out.println("config convert mqtt settings jsonString failed!");
		}
		return json;
	}

	private MqttClientService connectBroker(MqttPubNewConfigDomain domain) {
		MqttClientConfig config = domain.convertMqttClientConfig();
		MqttClientService mqttClientService = new MqttClientServiceImpl(config, new MqttPubMessageHandler());
		Future<MqttConnectResult> connectFuture = mqttClientService.connect(domain.getHost(), Integer.parseInt(domain.getPort()));
		MqttConnectResult result;
		try {
			result = connectFuture.get(config.getTimeoutSeconds(), TimeUnit.SECONDS);
			System.out.format("%s%s %s %s%s" + "%n",
					bundle.getString("mqtt.clientId"),
					domain.getClientId(),
					bundle.getString("mqtt.connect.broker"),
					domain.getHost() + ":" + domain.getPort(),
					String.format(EmojiEnum.smileEmoji));
		} catch (TimeoutException | InterruptedException | ExecutionException ex) {
			connectFuture.cancel(true);
			mqttClientService.disconnect();
			String hostPort = domain.getHost() + ":" + domain.getPort();
			throw new RuntimeException(
					String.format("%s %s %s.",
							bundle.getString("mqtt.failed.connect"),
							hostPort,
							String.format(EmojiEnum.pensiveEmoji)));
		}
		if (!result.isSuccess()) {
			connectFuture.cancel(true);
			mqttClientService.disconnect();
			String hostPort = domain.getHost() + ":" + domain.getPort();
			throw new RuntimeException(
					String.format("%s %s. %s %s", bundle.getString("mqtt.failed.connect"),
							hostPort, bundle.getString("mqtt.result.code"), result.getReturnCode()));
		}
		return mqttClientService;
	}

	private void mqttPubLogic(PubData data, MqttClientService client) {
		MqttQoS qosLevel = MqttQoS.valueOf(data.getQos());
		client.publish(data.getTopic(), Unpooled.wrappedBuffer(data.getPayload().getBytes(UTF8)), qosLevel, false);
		System.out.format(data.getPayload() + bundle.getString("publishMessage.success") + String.format(EmojiEnum.successEmoji) + "%n");
	}
}
