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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.util.concurrent.Future;
import iot.technology.client.toolkit.common.constants.*;
import iot.technology.client.toolkit.common.utils.FileUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.mqtt.config.MqttSettings;
import iot.technology.client.toolkit.mqtt.config.MqttSettingsInfo;
import iot.technology.client.toolkit.mqtt.service.MqttClientConfig;
import iot.technology.client.toolkit.mqtt.service.MqttClientService;
import iot.technology.client.toolkit.mqtt.service.domain.MqttConnectResult;
import iot.technology.client.toolkit.mqtt.service.domain.MqttPubNewConfigDomain;
import iot.technology.client.toolkit.mqtt.service.domain.MqttPubSelectConfigDomain;
import iot.technology.client.toolkit.mqtt.service.handler.MqttPubMessageHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	public void mqttNewConfigLogic(String code, String data, MqttPubNewConfigDomain domain, boolean init) {
		if ((code.equals(MqttSettingsCodeEnum.LASTWILLANDTESTAMENT.getCode())
				&& data.toUpperCase().equals(ConfirmCodeEnum.NO.getValue()))
				|| code.equals(MqttSettingsCodeEnum.LAST_WILL_PAYLOAD.getCode())) {
			MqttClientConfig config = domain.convertMqttClientConfig();
			MqttClientService mqttClientService = connectBroker(config);
			domain.setClient(mqttClientService);
			if (init) {
				String settingsJson = convertMqttSettings(domain);
				FileUtils.writeDataToFile(SystemConfigConst.MQTT_SETTINGS_FILE_NAME, settingsJson);
			}
		}
		if (code.equals(MqttSettingsCodeEnum.PUBLISH_MESSAGE.getCode())) {
			PubData pubData = PubData.validate(data);
			mqttPubLogic(pubData, domain.getClient());
		}
	}

	public void mqttPubSelectConfigPreLogic(MqttPubSelectConfigDomain domain) {
		if (Objects.nonNull(domain.getClient()) && domain.getClient().isConnected()) {
			return;
		}
		MqttSettings settings = JsonUtils.jsonToObject(domain.getSelectConfig(), MqttSettings.class);
		MqttClientConfig config = domain.convertMqttSettingsToClientConfig(Objects.requireNonNull(settings));
		MqttClientService mqttClientService = connectBroker(config);
		domain.setClient(mqttClientService);
	}

	public void mqttPubSelectConfigAfterLogic(String code, String data, MqttPubSelectConfigDomain domain, boolean init) {
		if ((code.equals(MqttSettingsCodeEnum.LASTWILLANDTESTAMENT.getCode()) && data.toUpperCase().equals(ConfirmCodeEnum.NO.getValue()))
				|| code.equals(MqttSettingsCodeEnum.LAST_WILL_PAYLOAD.getCode())) {
			MqttClientConfig config = domain.convertMqttClientConfig();
			MqttClientService mqttClientService = connectBroker(config);
			domain.setClient(mqttClientService);
			if (init) {
				String settingsJson = convertMqttSettings(domain);
				FileUtils.writeDataToFile(SystemConfigConst.MQTT_SETTINGS_FILE_NAME, settingsJson);
			}
		}
		if (code.equals(MqttSettingsCodeEnum.PUBLISH_MESSAGE.getCode())) {
			PubData pubData = PubData.validate(data);
			mqttPubLogic(pubData, domain.getClient());
		}
	}


	public List<String> getMqttConfigList() {
		return FileUtils.getDataFromFile(SystemConfigConst.MQTT_SETTINGS_FILE_NAME);
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
		System.out.format(data.getPayload() + bundle.getString("publishMessage.success") + String.format(EmojiEnum.successEmoji) + "%n");
	}

	private String convertMqttSettings(MqttPubNewConfigDomain domain) {
		MqttSettings settings = new MqttSettings();
		settings.setName(domain.getSettingsName() + "@" + domain.getHost() + ":" + domain.getPort());
		MqttSettingsInfo info = new MqttSettingsInfo();
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
		if (domain.getAdvanced().equals(ConfirmCodeEnum.YES.getValue())) {
			info.setKeepAlive(domain.getKeepAlive());
			info.setAutoReconnect(domain.getAutoReconnect());
			info.setConnectTimeout(domain.getConnectTimeout());
			info.setCleanSession(domain.getCleanSession());
		} else {
			info.setAutoReconnect(ConfirmCodeEnum.NO.getValue());
			info.setCleanSession(ConfirmCodeEnum.YES.getValue());
			info.setConnectTimeout("10");
			info.setKeepAlive("10");
		}
		if (domain.getLastWillAndTestament().equals(ConfirmCodeEnum.YES.getValue())) {
			info.setLastWillQoS(domain.getLastWillQoS());
			info.setLastWillTopic(domain.getLastWillTopic());
			info.setLastWillRetain(domain.getLastWillRetain());
			info.setLastWillPayload(domain.getLastWillPayload());
		}
		settings.setInfo(info);
		String json = "";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			json = objectMapper.writeValueAsString(settings);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return json;
	}

	private String convertMqttSettings(MqttPubSelectConfigDomain domain) {
		MqttSettings settings = new MqttSettings();
		settings.setName(domain.getSettingsName() + "@" + domain.getHost() + ":" + domain.getPort());
		MqttSettingsInfo info = new MqttSettingsInfo();
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
		if (domain.getAdvanced().equals(ConfirmCodeEnum.YES.getValue())) {
			info.setKeepAlive(domain.getKeepAlive());
			info.setAutoReconnect(domain.getAutoReconnect());
			info.setConnectTimeout(domain.getConnectTimeout());
			info.setCleanSession(domain.getCleanSession());
		} else {
			info.setAutoReconnect(ConfirmCodeEnum.NO.getValue());
			info.setCleanSession(ConfirmCodeEnum.YES.getValue());
			info.setConnectTimeout("10");
			info.setKeepAlive("10");
		}
		if (domain.getLastWillAndTestament().equals(ConfirmCodeEnum.YES.getValue())) {
			info.setLastWillQoS(domain.getLastWillQoS());
			info.setLastWillTopic(domain.getLastWillTopic());
			info.setLastWillRetain(domain.getLastWillRetain());
			info.setLastWillPayload(domain.getLastWillPayload());
		}
		settings.setInfo(info);
		String json = "";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			json = objectMapper.writeValueAsString(settings);
		} catch (JsonProcessingException e) {
			System.out.println("config convert mqtt settings jsonString failed!");
		}
		return json;
	}

}
