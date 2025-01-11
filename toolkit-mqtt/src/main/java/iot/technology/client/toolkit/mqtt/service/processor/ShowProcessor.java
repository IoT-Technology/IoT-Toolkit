/*
 * Copyright © 2019-2025 The Toolkit Authors
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
package iot.technology.client.toolkit.mqtt.service.processor;

import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.constants.SystemConfigConst;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.*;
import iot.technology.client.toolkit.mqtt.config.MqttSettings;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * @author mushuwei
 */
public class ShowProcessor implements TkProcessor {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean supports(ProcessContext context) {
		return Objects.requireNonNull(context.getData()).contains("show");
	}

	@Override
	public void handle(ProcessContext context) {
		do {
			if (!context.getData().contains("show") || !context.getData().contains(" ")) {
				System.out.format(ColorUtils.redError(bundle.getString("param.error")));
				break;
			}
			int spaceIndex = context.getData().indexOf(" ");
			String serial = context.getData().substring(spaceIndex + 1).trim();
			if (StringUtils.isBlank(serial) || !ObjectUtils.isInteger(serial)) {
				System.out.format(ColorUtils.redError(bundle.getString("param.error")));
				break;
			}
			List<String> configStringList = FileUtils.getDataFromFile(SystemConfigConst.MQTT_SETTINGS_FILE_NAME);
			int serialNumber = Integer.parseInt(serial);
			if (configStringList.isEmpty()) {
				System.out.format(ColorUtils.redError(bundle.getString("param.error")));
				break;
			}
			List<MqttSettings> mqttSettings = configStringList.stream()
					.map(cs -> JsonUtils.jsonToObject(cs, MqttSettings.class))
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
			if (serialNumber > mqttSettings.size() - 1 || serialNumber < 0) {
				System.out.format(ColorUtils.redError(bundle.getString("param.error")));
				break;
			}
			MqttSettings mqttSettingsInfo = mqttSettings.get(serialNumber);
			System.out.format("ClientId: " + mqttSettingsInfo.getInfo().getClientId() + "%n");
			System.out.format("Version: " + mqttSettingsInfo.getInfo().getVersion() + "%n");
			System.out.format("Host: " + mqttSettingsInfo.getInfo().getHost() + "%n");
			System.out.format("Port: " + mqttSettingsInfo.getInfo().getPort() + "%n");
			System.out.format("Username: " + mqttSettingsInfo.getInfo().getUsername() + "%n");
			System.out.format("Password: " + mqttSettingsInfo.getInfo().getPassword() + "%n");
			System.out.format("ConnectTimeout: " + mqttSettingsInfo.getInfo().getConnectTimeout() + "%n");
			System.out.format("KeepAlive: " + mqttSettingsInfo.getInfo().getKeepAlive() + "%n");
			System.out.format("CleanSession: " + mqttSettingsInfo.getInfo().getCleanSession() + "%n");
			System.out.format("AutoReconnect: " + mqttSettingsInfo.getInfo().getAutoReconnect() + "%n");
		} while (false);

	}
}
