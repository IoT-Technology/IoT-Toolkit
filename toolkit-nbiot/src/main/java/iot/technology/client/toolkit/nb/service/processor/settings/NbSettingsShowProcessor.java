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

import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.constants.SystemConfigConst;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.FileUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.common.utils.ObjectUtils;
import iot.technology.client.toolkit.nb.service.lwm2m.domain.Lwm2mConfigSetting;
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
		if (nbSettingsContext.getNbType().equals(NBTypeEnum.LWM2M.getValue())) {
			List<String> configStringList = FileUtils.getDataFromFile(SystemConfigConst.SYS_NB_LWM2M_SETTINGS_FILE_NAME);
			var lwm2mConfigSetting = JsonUtils.jsonToObject(configStringList.get(serial), Lwm2mConfigSetting.class);
			System.out.format("Endpoint:         " + lwm2mConfigSetting.getLwm2mEndpoint() + "%n");
			System.out.format("ServerAndPort:    " + lwm2mConfigSetting.getServerAndPort() + "%n");
			System.out.format("LifeTime:         " + lwm2mConfigSetting.getLwm2mLifeTime() + "%n");
			System.out.format("BootstrapServer:  " + lwm2mConfigSetting.getLwm2mBootstrapServer() + "%n");
			System.out.format("PskIdentity:      " + lwm2mConfigSetting.getLwm2mPskIdentity() + "%n");
			System.out.format("PskShareKey:      " + lwm2mConfigSetting.getLwm2mPskShareKey() + "%n");
			System.out.format("ClientPrivateKey: " + lwm2mConfigSetting.getLwm2mClientPrivateKey() + "%n");
			System.out.format("ClientPublicKey:  " + lwm2mConfigSetting.getLwm2mClientPublicKey() + "%n");
			System.out.format("ServerPublicKey:  " + lwm2mConfigSetting.getLwm2mServerPublicKey() + "%n");
			System.out.format("ClientCert:       " + lwm2mConfigSetting.getLwm2mClientCert() + "%n");
			System.out.format("ServerCert:       " + lwm2mConfigSetting.getLwm2mServerCert() + "%n");
			System.out.format("CertUsage:        " + lwm2mConfigSetting.getLwm2mCertUsage() + "%n");
		}
	}
}
