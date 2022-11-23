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
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.NodeTypeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.constants.SystemConfigConst;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.FileUtils;
import iot.technology.client.toolkit.common.utils.ObjectUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.mqtt.config.MqttSettings;
import iot.technology.client.toolkit.mqtt.service.MqttSettingsRuleChainProcessor;
import iot.technology.client.toolkit.mqtt.service.domain.MqttPubNewConfigDomain;
import iot.technology.client.toolkit.mqtt.service.impl.MqttBizService;
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
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
			MqttSettingsRuleChainProcessor ruleChain = new MqttSettingsRuleChainProcessor();
			Map<String, String> processor = ruleChain.getPublishNewConfigProcessor();
			String code = ruleChain.getRootPublishNewConfigNode();

			MqttPubNewConfigDomain domain = new MqttPubNewConfigDomain();
			NodeContext context = new NodeContext();
			context.setType(NodeTypeEnum.MQTT_PUBLISH.getType());
			while (true) {
				String data = null;
				try {
					String node = processor.get(code);
					TkNode tkNode = ObjectUtils.initComponent(node);
					if (tkNode == null) {
						break;
					}
					tkNode.prePrompt(context);
					data = reader.readLine(tkNode.nodePrompt());
					context.setData(data);
					tkNode.check(context);
					if (!StringUtils.isBlank(tkNode.getValue(context))) {
						System.out.format(ColorUtils.blackFaint(bundle.getString("call.prompt") + tkNode.getValue(context)) + "%n");
					}
					ObjectUtils.setValue(domain, code, tkNode.getValue(context));
					bizService.mqttBizLogic(code, data, domain, true);
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
		List<MqttSettings> settingsList = FileUtils.getDataFromFile(SystemConfigConst.MQTT_SETTINGS_FILE_NAME).stream()
				.map(data -> {
					MqttSettings settings = null;
					ObjectMapper mapper = new ObjectMapper();
					try {
						settings = mapper.readValue(data, MqttSettings.class);
					} catch (JsonProcessingException e) {
						System.out.println("settings json convert mqttSettings failed! ");
					}
					return settings;
				}).sorted(Comparator.comparingInt((MqttSettings mqttSettings) -> mqttSettings != null ? mqttSettings.getUsage() : 1)
						.reversed())
				.collect(Collectors.toList());

		Map<String, MqttSettings> mqttSettingsMap = new HashMap<>();
		AtomicInteger startIndex = new AtomicInteger();
		settingsList.forEach(list -> {
			mqttSettingsMap.put("" + startIndex, list);
			startIndex.addAndGet(1);
		});

		/**
		 * 1、0~9: select mqtt configs that was set earlier
		 * 2、add a new mqtt settings
		 */
		return ExitCodeEnum.NOTEND.getValue();
	}
}
