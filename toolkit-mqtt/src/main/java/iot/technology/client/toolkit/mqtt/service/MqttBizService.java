/*
 * Copyright © 2019-2023 The Toolkit Authors
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
package iot.technology.client.toolkit.mqtt.service;

import io.netty.handler.codec.mqtt.MqttVersion;
import io.netty.util.concurrent.Future;
import iot.technology.client.toolkit.common.constants.*;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.FileUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.mqtt.config.MqttSettings;
import iot.technology.client.toolkit.mqtt.config.MqttSettingsInfo;
import iot.technology.client.toolkit.mqtt.config.MqttShellModeDomain;
import iot.technology.client.toolkit.mqtt.service.core.MqttClientService;
import iot.technology.client.toolkit.mqtt.service.domain.MqttConfigSettingsDomain;
import iot.technology.client.toolkit.mqtt.service.domain.MqttConnectResult;
import iot.technology.client.toolkit.mqtt.service.handler.MqttPubMessageHandler;
import org.jline.terminal.Terminal;
import picocli.CommandLine;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author mushuwei
 */
public class MqttBizService {

	private final MqttSettingService mqttSettingService = new MqttSettingService();
	private final MqttShellModeService shellModeService = new MqttShellModeService();
	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	public void getMqttDescription() {
		if (bundle.getLocale().equals(Locale.CHINESE)) {
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(Cyan),bold " +
					"MQTT (消息队列遥测传输)" + "|@") + "%n");
			System.out.format("" + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|italic " +
					"MQTT是用于物联网(IoT)的OASIS标准消息传递协议" + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|italic " +
					"它被设计为一种非常轻量级的发布/订阅消息传输" + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|italic " +
					"这是远距离传输设备的理想选择且代码量很小," + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|italic " +
					"很小的网络带宽。MQTT如今被广泛应用于各种行业" + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|italic " +
					"如汽车、制造业、电信和石油天然气等。" + "|@") + "%n");
			System.out.format("" + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(Cyan),italic " + "官方地址: " + "https://mqtt.org/" + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(Cyan),italic " + "中文" + "MQTT 3.1/3.1.1" + "版本协议文档: "
					+ "https://iot.mushuwei.cn/#/mqtt3/" + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(Cyan),italic " + "中文" + "MQTT 5 " + "版本协议文档: "
					+ "https://iot.mushuwei.cn/#/mqtt5/" + "|@") + "%n");
		} else {
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(Cyan),bold " +
					"MQTT (Message Queuing Telemetry Transport)" + "|@") + "%n");
			System.out.format("" + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|italic " +
					"MQTT is an OASIS standard messaging protocol for the Internet of Things (IoT)." + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|italic " +
					"It is designed as an extremely lightweight publish/subscribe messaging transport" + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|italic " +
					"that is ideal for connecting remote devices with a small code footprint and" + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|italic " +
					"minimal network bandwidth. MQTT today is used in a wide variety of industries," + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|italic " +
					"such as automotive, manufacturing, telecommunications, oil and gas, etc." + "|@") + "%n");
			System.out.format("" + "%n");
			System.out.format(
					CommandLine.Help.Ansi.AUTO.string("@|fg(Cyan),italic " + "The Official address: " + "https://mqtt.org/" + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(Cyan),italic " + "The English MQTT 3.1/3.1.1 Specification: "
					+ "http://docs.oasis-open.org/mqtt/mqtt/v3.1.1/os/mqtt-v3.1.1-os.html" + "|@") + "%n");
			System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(Cyan),italic " + "The English MQTT 5 Specification: "
					+ "https://docs.oasis-open.org/mqtt/mqtt/v5.0/mqtt-v5.0.html" + "|@") + "%n");
		}

	}

	public MqttClientConfig convertMqttSettingsToClientConfig(MqttSettings settings) {
		MqttClientConfig config = new MqttClientConfig();
		MqttSettingsInfo info = settings.getInfo();
		if (info.getVersion().equals(MqttVersionEnum.MQTT_3_1.getValue())) {
			config.setProtocolVersion(MqttVersion.MQTT_3_1);
		} else if (info.getVersion().equals(MqttVersionEnum.MQTT_5_0.getValue())) {
			config.setProtocolVersion(MqttVersion.MQTT_5);
		} else {
			config.setProtocolVersion(MqttVersion.MQTT_3_1_1);
		}

		config.setClientId(info.getClientId());
		config.setHost(info.getHost());
		config.setPort(Integer.parseInt(info.getPort()));
		config.setUsername(info.getUsername());
		config.setPassword(info.getPassword());
		config.setReconnect(info.getAutoReconnect().equals(ConfirmCodeEnum.YES.getValue()));
		config.setCleanSession(info.getCleanSession().equals(ConfirmCodeEnum.YES.getValue()));
		config.setKeepAlive(Integer.parseInt(info.getKeepAlive()));
		config.setTimeoutSeconds(Integer.parseInt(info.getConnectTimeout()));
		return config;
	}

	public boolean mqttProcessorAfterLogic(String code, MqttConfigSettingsDomain domain, NodeContext context, Terminal terminal) {
		    // select a older setting
			if (code.equals(MqttSettingsCodeEnum.MQTT_APP_CONFIG.getCode()) &&
					context.isCheck() &&
					!context.getData().equals("new")) {
				// already connected
				if (Objects.nonNull(domain.getClient()) && domain.getClient().isConnected()) {
					return true;
				}
				//convert older setting to mqttClientConfig
				MqttSettings settings = JsonUtils.jsonToObject(domain.getMqttAppConfig(), MqttSettings.class);
				MqttClientConfig config = this.convertMqttSettingsToClientConfig(Objects.requireNonNull(settings));
				MqttClientService mqttClientService = connectBroker(config);
				domain.setClient(mqttClientService);
				// update this setting is used
				mqttSettingService.updateAllMqttConfigsByUsage(settings, 1);
				// enter into subcommand
				MqttShellModeDomain shellModeDomain = new MqttShellModeDomain();
				shellModeDomain.setSettings(settings);
				shellModeDomain.setClient(mqttClientService);
				return shellModeService.call(shellModeDomain, terminal);
			}
			// select new
			if ((code.equals(MqttSettingsCodeEnum.LASTWILLANDTESTAMENT.getCode()) && ((context.getData().toUpperCase().equals(ConfirmCodeEnum.NO.getValue()))))
					|| (code.equals(MqttSettingsCodeEnum.LAST_WILL_PAYLOAD.getCode()) && context.isCheck())
			) {
				MqttClientConfig config = domain.convertMqttClientConfig();
				MqttClientService mqttClientService = connectBroker(config);
				domain.setClient(mqttClientService);
				//write settings to file
				MqttSettings settings = domain.convertMqttSettingsJsonString();
				settings.setUsage(0);
				FileUtils.writeDataToFile(SystemConfigConst.MQTT_SETTINGS_FILE_NAME, JsonUtils.object2Json(settings));

				// add settings config, not enter into sub terminal
				if (context.getType().equals(NodeTypeEnum.MQTT_SETTINGS.getType())) {
					return true;
				}
				MqttShellModeDomain shellModeDomain = new MqttShellModeDomain();
				shellModeDomain.setClient(mqttClientService);
				shellModeDomain.setSettings(settings);
				return shellModeService.call(shellModeDomain, terminal);
			}
			return true;

	}


	private MqttClientService connectBroker(MqttClientConfig config) {
		MqttClientService mqttClientService = new MqttClientService(config, new MqttPubMessageHandler());
		Future<MqttConnectResult> connectFuture = mqttClientService.connect(config.getHost(), config.getPort());
		MqttConnectResult result;
		try {
			result = connectFuture.get(config.getTimeoutSeconds(), TimeUnit.SECONDS);
			System.out.format("%s%s %s %s%s" + "%n",
					bundle.getString("mqtt.clientId"),
					config.getClientId(),
					bundle.getString("mqtt.connect.broker"),
					config.getHost() + ":" + config.getPort(),
					String.format(EmojiEnum.smileEmoji));
		} catch (TimeoutException | InterruptedException | ExecutionException ex) {
			connectFuture.cancel(true);
			mqttClientService.disconnect(0);
			String hostPort = config.getHost() + ":" + config.getPort();
			throw new RuntimeException(
					String.format("%s %s %s.",
							bundle.getString("mqtt.failed.connect"),
							hostPort,
							String.format(EmojiEnum.pensiveEmoji)));
		}
		if (!result.isSuccess()) {
			connectFuture.cancel(true);
			mqttClientService.disconnect(1);
			String hostPort = config.getHost() + ":" + config.getPort();
			throw new RuntimeException(
					String.format("%s %s. %s %s", bundle.getString("mqtt.failed.connect"),
							hostPort, bundle.getString("mqtt.result.code"), result.getReturnCode()));
		}
		return mqttClientService;
	}



	public void printValueToConsole(NodeContext context) {
		System.out.format(ColorUtils.blackFaint(bundle.getString("call.prompt") + context.getData()) + "%n");
	}

}
