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

import iot.technology.client.toolkit.common.constants.SystemConfigConst;
import iot.technology.client.toolkit.common.utils.FileUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.mqtt.config.MqttSettings;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author mushuwei
 */
public class MqttSettingService {

    public void updateAllMqttConfigsByUsage(MqttSettings waitUpdateSettings, Integer used) {
        List<String> configList = FileUtils.getDataFromFile(SystemConfigConst.MQTT_SETTINGS_FILE_NAME);
        List<MqttSettings> settingsList = JsonUtils.decodeJsonToList(configList, MqttSettings.class)
                .stream().filter(cl -> !cl.getName().equals(waitUpdateSettings.getName())).collect(Collectors.toList());
        //this client is connected
        waitUpdateSettings.setUsage(used);
        if (waitUpdateSettings.getSettingTime() == null) {
            waitUpdateSettings.setSettingTime(System.currentTimeMillis());
        }
        settingsList.add(waitUpdateSettings);
        settingsList.forEach(st -> {
            if (st.getSettingTime() == null) {
                st.setSettingTime(System.currentTimeMillis());
            }
        });
        settingsList.sort(Comparator.comparingLong(MqttSettings::getSettingTime).reversed());
        List<String> updateResults = settingsList.stream().map(JsonUtils::object2Json).collect(Collectors.toList());
        FileUtils.updateAllFileContents(SystemConfigConst.MQTT_SETTINGS_FILE_NAME, updateResults);

    }
}
