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
package iot.technology.client.toolkit.mqtt.service.impl;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.codec.mqtt.MqttVersion;
import io.netty.util.concurrent.Future;
import iot.technology.client.toolkit.common.constants.*;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.FileUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.mqtt.config.MqttSettings;
import iot.technology.client.toolkit.mqtt.config.MqttSettingsInfo;
import iot.technology.client.toolkit.mqtt.service.MqttClientConfig;
import iot.technology.client.toolkit.mqtt.service.MqttClientService;
import iot.technology.client.toolkit.mqtt.service.domain.MqttConfigSettingsDomain;
import iot.technology.client.toolkit.mqtt.service.domain.MqttConnectResult;
import iot.technology.client.toolkit.mqtt.service.handler.MqttPubMessageHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static iot.technology.client.toolkit.common.constants.SystemConfigConst.MQTT_SETTINGS_FILE_NAME;

/**
 * @author mushuwei
 */
public class MqttBizService {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	public Boolean notExistOrContents() {
		if (!FileUtils.isExist(MQTT_SETTINGS_FILE_NAME)) {
			return true;
		}
		List<String> content;
		try {
			Path path = Paths.get(MQTT_SETTINGS_FILE_NAME);
			content = Files.lines(path).collect(Collectors.toList());
		} catch (IOException e) {
			return true;
		}
		return content.isEmpty();
	}

	/**
	 * @param code   node code
	 * @param data   user input data
	 * @param domain mqtt settings domain
	 */
	public void mqttNewConfigAfterLogic(String code, String data, MqttConfigSettingsDomain domain, boolean init) {
		if ((code.equals(MqttSettingsCodeEnum.LASTWILLANDTESTAMENT.getCode())
				&& data.toUpperCase().equals(ConfirmCodeEnum.NO.getValue()))
				|| code.equals(MqttSettingsCodeEnum.LAST_WILL_PAYLOAD.getCode())) {
			MqttClientConfig config = domain.convertMqttClientConfig();
			MqttClientService mqttClientService = connectBroker(config);
			domain.setClient(mqttClientService);
			if (init) {
				String settingsJson = domain.convertMqttSettingsJsonString();
				FileUtils.writeDataToFile(SystemConfigConst.MQTT_SETTINGS_FILE_NAME, settingsJson);
			}
		}
		if (code.equals(MqttSettingsCodeEnum.PUBLISH_MESSAGE.getCode())) {
			PubData pubData = PubData.validate(data);
			mqttPubLogic(pubData, domain.getClient());
		}
	}

	public void mqttSelectOneConfigLogic(MqttConfigSettingsDomain domain) {
		if (Objects.nonNull(domain.getClient()) && domain.getClient().isConnected()) {
			return;
		}
		MqttSettings settings = JsonUtils.jsonToObject(domain.getSelectConfig(), MqttSettings.class);
		MqttClientConfig config = this.convertMqttSettingsToClientConfig(Objects.requireNonNull(settings));
		MqttClientService mqttClientService = connectBroker(config);
		domain.setClient(mqttClientService);
		//update usages of mqtt configs
		updateAllMqttConfigsByUsage(settings);

	}

	private void updateAllMqttConfigsByUsage(MqttSettings waitUpdateSettings) {
		List<String> configList = this.getMqttConfigList();
		List<MqttSettings> settingsList = JsonUtils.decodeJsonToList(configList, MqttSettings.class)
				.stream().filter(cl -> !cl.getName().equals(waitUpdateSettings.getName())).collect(Collectors.toList());
		waitUpdateSettings.setUsage(waitUpdateSettings.getUsage() + 1);
		settingsList.add(waitUpdateSettings);
		settingsList.sort(Comparator.comparingInt(MqttSettings::getUsage).reversed());
		List<String> updateResults = settingsList.stream().map(JsonUtils::object2Json).collect(Collectors.toList());
		FileUtils.updateAllFileContents(SystemConfigConst.MQTT_SETTINGS_FILE_NAME, updateResults);

	}

	public MqttClientConfig convertMqttSettingsToClientConfig(MqttSettings settings) {
		MqttClientConfig config = new MqttClientConfig();
		MqttSettingsInfo info = settings.getInfo();
		if (info.getVersion().equals(MqttVersionEnum.MQTT_3_1.getValue())) {
			config.setProtocolVersion(MqttVersion.MQTT_3_1);
		}
		if (info.getVersion().equals(MqttVersionEnum.MQTT_5_0.getValue())) {
			config.setProtocolVersion(MqttVersion.MQTT_5);
		}
		config.setProtocolVersion(MqttVersion.MQTT_3_1_1);
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

	public void mqttPubSelectConfigAfterLogic(String code, String data, MqttConfigSettingsDomain domain, boolean init) {
		do {
			if (code.equals(MqttSettingsCodeEnum.SELECT_CONFIG.getCode()) && !data.equals("new")) {
				this.mqttSelectOneConfigLogic(domain);
				break;
			}
			if (code.equals(MqttSettingsCodeEnum.PUBLISH_MESSAGE.getCode())) {
				PubData pubData = PubData.validate(data);
				mqttPubLogic(pubData, domain.getClient());
				break;
			}
			if ((code.equals(MqttSettingsCodeEnum.LASTWILLANDTESTAMENT.getCode()) &&
					data.toUpperCase().equals(ConfirmCodeEnum.NO.getValue()))
					|| code.equals(MqttSettingsCodeEnum.LAST_WILL_PAYLOAD.getCode())) {
				MqttClientConfig config = domain.convertMqttClientConfig();
				MqttClientService mqttClientService = connectBroker(config);
				domain.setClient(mqttClientService);
				if (init) {
					String settingsJson = domain.convertMqttSettingsJsonString();
					FileUtils.writeDataToFile(SystemConfigConst.MQTT_SETTINGS_FILE_NAME, settingsJson);
				}
			}

		} while (false);
	}


	public List<String> getMqttConfigList() {
		return FileUtils.getDataFromFile(SystemConfigConst.MQTT_SETTINGS_FILE_NAME);
	}

	public void printValueToConsole(String code, String data, NodeContext context) {
		if (StringUtils.isNotBlank(data) &&
				context.isCheck() &&
				code.equals(MqttSettingsCodeEnum.SELECT_CONFIG.getCode()) &&
				!data.equals("new")) {
			MqttSettings settings = JsonUtils.jsonToObject(data, MqttSettings.class);
			System.out.format(
					ColorUtils.blackFaint(bundle.getString("call.prompt") + Objects.requireNonNull(settings).getName()) + "%n");
		} else {
			System.out.format(ColorUtils.blackFaint(bundle.getString("call.prompt") + data) + "%n");
		}
	}


	private MqttClientService connectBroker(MqttClientConfig config) {
		MqttClientService mqttClientService = new MqttClientServiceImpl(config, new MqttPubMessageHandler());
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
			mqttClientService.disconnect();
			String hostPort = config.getHost() + ":" + config.getPort();
			throw new RuntimeException(
					String.format("%s %s %s.",
							bundle.getString("mqtt.failed.connect"),
							hostPort,
							String.format(EmojiEnum.pensiveEmoji)));
		}
		if (!result.isSuccess()) {
			connectFuture.cancel(true);
			mqttClientService.disconnect();
			String hostPort = config.getHost() + ":" + config.getPort();
			throw new RuntimeException(
					String.format("%s %s. %s %s", bundle.getString("mqtt.failed.connect"),
							hostPort, bundle.getString("mqtt.result.code"), result.getReturnCode()));
		}
		return mqttClientService;
	}

	private void mqttPubLogic(PubData data, MqttClientService client) {
		MqttQoS qosLevel = MqttQoS.valueOf(data.getQos());
		client.publish(data.getTopic(), Unpooled.wrappedBuffer(data.getPayload().getBytes(StandardCharsets.UTF_8)), qosLevel, false);
		System.out.format(
				data.getPayload() + " " + bundle.getString("publishMessage.success") + String.format(EmojiEnum.successEmoji) + "%n");
	}

}
