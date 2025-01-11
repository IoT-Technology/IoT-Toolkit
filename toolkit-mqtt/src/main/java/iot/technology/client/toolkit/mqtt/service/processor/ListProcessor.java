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

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import com.github.freva.asciitable.OverflowBehaviour;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.constants.SystemConfigConst;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.FileUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.mqtt.config.MqttSettings;
import iot.technology.client.toolkit.mqtt.service.domain.MqttSettingsListDomain;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mushuwei
 */
public class ListProcessor implements TkProcessor {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean supports(ProcessContext context) {
		return Objects.requireNonNull(context.getData()).equals("list");
	}

	@Override
	public void handle(ProcessContext context) {
		List<String> configStringList = FileUtils.getDataFromFile(SystemConfigConst.MQTT_SETTINGS_FILE_NAME);
		if (!configStringList.isEmpty()) {
			AtomicInteger cal = new AtomicInteger();
			List<MqttSettingsListDomain> settingsListDomains = new ArrayList<>();
			configStringList.stream().map(cs -> JsonUtils.jsonToObject(cs, MqttSettings.class))
					.filter(Objects::nonNull)
					.forEach(ms -> {
						MqttSettingsListDomain msld = new MqttSettingsListDomain();
						msld.setSerial(cal + "");
						msld.setClientId(ms.getInfo().getClientId());
						msld.setHost(ms.getInfo().getHost());
						msld.setPort(ms.getInfo().getPort());
						msld.setPassword(ms.getInfo().getPassword());
						msld.setUsername(ms.getInfo().getUsername());
						settingsListDomains.add(msld);
						cal.getAndIncrement();
					});
			String tableInfo = AsciiTable.getTable(AsciiTable.BASIC_ASCII_NO_OUTSIDE_BORDER, settingsListDomains, Arrays.asList(
					new Column().header(bundle.getString("mqtt.serial.table.desc"))
							.headerAlign(HorizontalAlign.CENTER)
							.maxWidth(8, OverflowBehaviour.ELLIPSIS_RIGHT)
							.dataAlign(HorizontalAlign.LEFT)
							.with(MqttSettingsListDomain::getSerial),
					new Column().header(bundle.getString("mqtt.clientId.table.desc"))
							.headerAlign(HorizontalAlign.CENTER)
							.dataAlign(HorizontalAlign.LEFT)
							.maxWidth(30, OverflowBehaviour.ELLIPSIS_RIGHT)
							.with(MqttSettingsListDomain::getClientId),
					new Column().header(bundle.getString("mqtt.host.table.desc"))
							.headerAlign(HorizontalAlign.CENTER)
							.dataAlign(HorizontalAlign.LEFT)
							.maxWidth(20, OverflowBehaviour.ELLIPSIS_RIGHT)
							.with(MqttSettingsListDomain::getHost),
					new Column()
							.header(bundle.getString("mqtt.port.table.desc"))
							.headerAlign(HorizontalAlign.CENTER)
							.dataAlign(HorizontalAlign.LEFT)
							.maxWidth(10, OverflowBehaviour.ELLIPSIS_RIGHT)
							.with(MqttSettingsListDomain::getPort),
					new Column().header(bundle.getString("mqtt.username.table.desc"))
							.headerAlign(HorizontalAlign.CENTER)
							.dataAlign(HorizontalAlign.LEFT)
							.maxWidth(30, OverflowBehaviour.ELLIPSIS_RIGHT)
							.with(MqttSettingsListDomain::getUsername),
					new Column().header(bundle.getString("mqtt.password.table.desc"))
							.headerAlign(HorizontalAlign.CENTER)
							.dataAlign(HorizontalAlign.LEFT)
							.maxWidth(30, OverflowBehaviour.ELLIPSIS_RIGHT)
							.with(MqttSettingsListDomain::getPassword)
			));
			System.out.println(tableInfo);
		}

	}
}
