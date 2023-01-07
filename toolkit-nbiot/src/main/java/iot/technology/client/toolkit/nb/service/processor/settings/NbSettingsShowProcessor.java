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
package iot.technology.client.toolkit.nb.service.processor.settings;

import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.constants.SystemConfigConst;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.FileUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.common.utils.ObjectUtils;
import iot.technology.client.toolkit.nb.service.mobile.domain.settings.MobProjectSettings;
import iot.technology.client.toolkit.nb.service.processor.NbSettingsContext;
import iot.technology.client.toolkit.nb.service.telecom.domain.settings.TelProjectSettings;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class NbSettingsShowProcessor implements TkProcessor {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("show");
	}

	@Override
	public void handle(ProcessContext context) {
		NbSettingsContext nbSettingsContext = (NbSettingsContext) context;
		List<String> argus = List.of(context.getData().split(" "));
		if (argus.size() != 2) {
			System.out.format(ColorUtils.blackBold("argument:%s is illegal"), context.getData());
			System.out.format(" " + "%n");
			return;
		}
		if (!ObjectUtils.isInteger(argus.get(1))) {
			System.out.format(ColorUtils.redError(bundle.getString("param.error")));
			return;
		}
		Integer serial = Integer.valueOf(argus.get(1));
		if (nbSettingsContext.getNbType().equals(NBTypeEnum.TELECOM.getValue())) {
			List<String> configStringList = FileUtils.getDataFromFile(SystemConfigConst.SYS_NB_TELECOM_PRODUCT_FILE_NAME);
			TelProjectSettings telProjectSettings = JsonUtils.jsonToObject(configStringList.get(serial), TelProjectSettings.class);
			System.out.format("ProductName:  " + Objects.requireNonNull(telProjectSettings).getProductName() + "%n");
			System.out.format("AppKey:       " + telProjectSettings.getAppKey() + "%n");
			System.out.format("AppSecret:    " + telProjectSettings.getAppSecret() + "%n");
			System.out.format("ProductId:    " + telProjectSettings.getProductId() + "%n");
			System.out.format("MasterApiKey: " + telProjectSettings.getMasterApiKey() + "%n");
		}
		if (nbSettingsContext.getNbType().equals(NBTypeEnum.MOBILE.getValue())) {
			List<String> configStringList = FileUtils.getDataFromFile(SystemConfigConst.SYS_NB_MOBILE_PRODUCT_FILE_NAME);
			MobProjectSettings mobProjectSettings = JsonUtils.jsonToObject(configStringList.get(serial), MobProjectSettings.class);
			System.out.format("ProductName: " + mobProjectSettings.getProductName() + "%n");
			System.out.format("ProductId:   " + mobProjectSettings.getProductId() + "%n");
			System.out.format("AccessKey:   " + mobProjectSettings.getAccessKey() + "%n");
		}
	}
}
