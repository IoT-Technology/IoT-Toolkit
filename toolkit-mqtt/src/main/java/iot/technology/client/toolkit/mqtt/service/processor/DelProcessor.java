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
public class DelProcessor implements TkProcessor {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean supports(ProcessContext context) {
		return Objects.requireNonNull(context.getData()).contains("del");
	}

	@Override
	public void handle(ProcessContext context) {
		do {
			if (!context.getData().contains("del") || !context.getData().contains(" ")) {
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
			if (serialNumber > mqttSettings.size() || serialNumber < 0) {
				System.out.format(ColorUtils.redError(bundle.getString("param.error")));
				break;
			}
			MqttSettings mqttSettingsInfo = mqttSettings.get(serialNumber);
			mqttSettings.removeIf(ms -> ms.getName().equals(mqttSettingsInfo.getName()));
			List<String> updateResults = mqttSettings.stream().map(JsonUtils::object2Json).collect(Collectors.toList());
			FileUtils.updateAllFileContents(SystemConfigConst.MQTT_SETTINGS_FILE_NAME, updateResults);
		} while (false);
	}
}
