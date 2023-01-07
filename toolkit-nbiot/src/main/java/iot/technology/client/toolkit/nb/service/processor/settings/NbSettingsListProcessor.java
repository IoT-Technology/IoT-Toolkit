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
package iot.technology.client.toolkit.nb.service.processor.settings;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.constants.SystemConfigConst;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.FileUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.nb.service.mobile.domain.settings.MobProjectSettings;
import iot.technology.client.toolkit.nb.service.processor.NbSettingsContext;
import iot.technology.client.toolkit.nb.service.telecom.domain.settings.TelProjectSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mushuwei
 */
public class NbSettingsListProcessor implements TkProcessor {

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("list");
	}

	@Override
	public void handle(ProcessContext context) {
		NbSettingsContext nbSettingsContext = (NbSettingsContext) context;
		if (nbSettingsContext.getNbType().equals(NBTypeEnum.TELECOM.getValue())) {
			List<String> configStringList = FileUtils.getDataFromFile(SystemConfigConst.SYS_NB_TELECOM_PRODUCT_FILE_NAME);
			if (!configStringList.isEmpty()) {
				AtomicInteger cal = new AtomicInteger();
				List<TelProjectSettings> telProjectSettings = new ArrayList<>();
				configStringList.stream()
						.map(s -> JsonUtils.jsonToObject(s, TelProjectSettings.class))
						.filter(Objects::nonNull)
						.forEach(s -> {
							s.setSerial(cal + "");
							telProjectSettings.add(s);
							cal.getAndIncrement();
						});
				String tableInfo = AsciiTable.getTable(AsciiTable.BASIC_ASCII_NO_OUTSIDE_BORDER, telProjectSettings, Arrays.asList(
						new Column().header("Serial").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
								TelProjectSettings::getSerial),
						new Column().header("AppKey").minWidth(10).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(TelProjectSettings::getAppKey),
						new Column().header("AppSecret").minWidth(10).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(TelProjectSettings::getAppSecret),
						new Column().header("ProductId").minWidth(10).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(TelProjectSettings::getProductId),
						new Column().header("MasterApiKey").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(TelProjectSettings::getMasterApiKey),
						new Column().header("ProductName").minWidth(10).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(TelProjectSettings::getProductName)
				));
				System.out.println(tableInfo);
			}
		}
		if (nbSettingsContext.getNbType().equals(NBTypeEnum.MOBILE.getValue())) {
			List<String> configStringList = FileUtils.getDataFromFile(SystemConfigConst.SYS_NB_MOBILE_PRODUCT_FILE_NAME);
			if (!configStringList.isEmpty()) {
				AtomicInteger cal = new AtomicInteger();
				List<MobProjectSettings> mobProjectSettings = new ArrayList<>();
				configStringList.stream()
						.map(s -> JsonUtils.jsonToObject(s, MobProjectSettings.class))
						.filter(Objects::nonNull)
						.forEach(s -> {
							s.setSerial(cal + "");
							mobProjectSettings.add(s);
							cal.getAndIncrement();
						});
				String tableInfo = AsciiTable.getTable(AsciiTable.BASIC_ASCII_NO_OUTSIDE_BORDER, mobProjectSettings, Arrays.asList(
						new Column().header("Serial").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
								MobProjectSettings::getSerial),
						new Column().header("ProductId").minWidth(10).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(s -> s.getProductId() + ""),
						new Column().header("accessKey").minWidth(10).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(MobProjectSettings::getAccessKey),
						new Column().header("ProductName").minWidth(10).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(MobProjectSettings::getProductName)
				));
				System.out.println(tableInfo);
			}
		}
	}
}
