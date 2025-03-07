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
package iot.technology.client.toolkit.nb.service;

import iot.technology.client.toolkit.common.constants.ConfirmCodeEnum;
import iot.technology.client.toolkit.common.constants.NbSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.FileUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.nb.service.lwm2m.domain.Lwm2mConfigSetting;
import iot.technology.client.toolkit.nb.service.lwm2m.domain.Lwm2mConfigSettingsDomain;
import iot.technology.client.toolkit.nb.service.mobile.OneNetService;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.OneNetProductResponse;
import iot.technology.client.toolkit.nb.service.mobile.domain.settings.MobProjectSettings;
import iot.technology.client.toolkit.nb.service.processor.Lwm2mBizService;
import iot.technology.client.toolkit.nb.service.processor.MobileBizService;
import iot.technology.client.toolkit.nb.service.processor.TelecomBizService;
import iot.technology.client.toolkit.nb.service.telecom.TelecomProductService;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.product.TelQueryProductResponse;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.product.TelQueryProductResponseBody;
import iot.technology.client.toolkit.nb.service.telecom.domain.settings.TelProjectSettings;
import org.jline.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static iot.technology.client.toolkit.common.constants.SystemConfigConst.*;

/**
 * @author mushuwei
 */
public class NbBizService {

	private final TelecomBizService telecomBizService = new TelecomBizService();
	private final MobileBizService mobileBizService = new MobileBizService();
	private final TelecomProductService productService = new TelecomProductService();
	private final Lwm2mBizService lwm2mBizService = new Lwm2mBizService();
	private final OneNetService oneNetService = new OneNetService();
	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	public List<String> getNbSettingsFromFile(String fileName) {
		List<String> fileDatas = new ArrayList<>();
		if (FileUtils.notExistOrContents(fileName)) {
			return fileDatas;
		}
		fileDatas = FileUtils.getDataFromFile(fileName);
		return fileDatas;
	}

	public boolean nbProcessorAfterLogic(String code, NbConfigSettingsDomain domain, NodeContext context, Terminal terminal) {
		/**
		 * user fill in telecom type
		 * and select one old telecom settings
		 * enter telecom biz logic at once.
		 */
		if (code.equals(NbSettingsCodeEnum.NB_TELECOM_APP_CONFIG.getCode())
				&& context.isCheck()
				&& !domain.getNbTelecomAppConfig().equals("new")) {
			TelecomConfigDomain telecomConfigDomain = domain.convertTelecomConfig();
			return telecomBizService.call(telecomConfigDomain, terminal);
		}
		/**
		 * user fill in  telecom type
		 * and select new
		 * 1、save telecom settings
		 * 2、enter telecom biz logic
		 */
		if (code.equals(NbSettingsCodeEnum.NB_TEL_API_KEY.getCode())) {
			TelecomConfigDomain telecomConfigDomain = domain.convertTelecomConfig();
			TelQueryProductResponse queryProductResponse = productService.queryProduct(telecomConfigDomain);
			if (Objects.nonNull(queryProductResponse) && queryProductResponse.isSuccess()) {
				TelProjectSettings projectSettings = saveTelSettings(telecomConfigDomain, queryProductResponse);
				telecomConfigDomain.setProductName(projectSettings.getProductName());
				return telecomBizService.call(telecomConfigDomain, terminal);
			}
		}
		/**
		 * user fill in mobile type
		 * and select one old mobile settings
		 * enter mobile biz logic at once.
		 */
		if (code.equals(NbSettingsCodeEnum.NB_MOB_APP_CONFIG.getCode())
				&& context.isCheck()
				&& !domain.getMobAppConfig().equals("new")) {
			MobileConfigDomain mobileConfigDomain = domain.convertMobileConfig();
			return mobileBizService.call(mobileConfigDomain, terminal);

		}
		/**
		 * user fill in mobile type
		 * and select new
		 * 1、save mobile settings
		 * 2、enter mobile biz logic
		 */
		if (code.equals(NbSettingsCodeEnum.NB_MOB_ACCESS_KEY.getCode())) {
			MobileConfigDomain mobileConfigDomain = domain.convertMobileConfig();
			OneNetProductResponse response = oneNetService.getProductDetail(mobileConfigDomain);
			MobProjectSettings mobProjectSettings = new MobProjectSettings();
			mobProjectSettings.setProductId(mobileConfigDomain.getProductId());
			mobProjectSettings.setAccessKey(mobileConfigDomain.getAccessKey());
			if (Objects.nonNull(response.getData())) {
				mobProjectSettings.setProductName(response.getData().getName());
				mobileConfigDomain.setProductName(response.getData().getName());
			}
			String settingsJson = JsonUtils.object2Json(mobProjectSettings);
			FileUtils.writeDataToFile(SYS_NB_MOBILE_PRODUCT_FILE_NAME, settingsJson);
			return mobileBizService.call(mobileConfigDomain, terminal);
		}

		if (code.equals(NbSettingsCodeEnum.NB_LWM2M_CONFIG.getCode())
				&& context.isCheck()
				&& !domain.getLwm2mConfig().equals("new")) {
			//select one to connect server
			Lwm2mConfigSettingsDomain lwm2mConfigSettingsDomain = domain.convertLwm2mConfig();
			return lwm2mBizService.call(lwm2mConfigSettingsDomain, terminal);
		}

		if ((code.equals(NbSettingsCodeEnum.NB_LWM2M_DTLS.getCode())
				&& domain.getLwm2mDtls().equals(ConfirmCodeEnum.NO.getValue()))
				|| code.equals(NbSettingsCodeEnum.NB_LWM2M_CLIENT_PRIVATE_KEY.getCode())) {
			Lwm2mConfigSettingsDomain lwm2mConfigSettingsDomain = domain.convertLwm2mConfig();
			Boolean result = saveLwm2mSettings(domain);
			if (result) {
				return lwm2mBizService.call(lwm2mConfigSettingsDomain, terminal);
			}
		}
		return true;

	}

	public boolean nbSettingsProcessorAfterLogic(String code, NbConfigSettingsDomain domain) {
		/**
		 * user fill in  telecom type
		 * and select new
		 * 1、save telecom settings
		 * 2、enter telecom biz logic
		 */
		if (code.equals(NbSettingsCodeEnum.NB_TEL_API_KEY.getCode())) {
			TelecomConfigDomain telecomConfigDomain = domain.convertTelecomConfig();
			TelQueryProductResponse queryProductResponse = productService.queryProduct(telecomConfigDomain);
			if (Objects.nonNull(queryProductResponse) && queryProductResponse.isSuccess()) {
				TelProjectSettings projectSettings = saveTelSettings(telecomConfigDomain, queryProductResponse);
				telecomConfigDomain.setProductName(projectSettings.getProductName());
			}
		}
		/**
		 * user fill in mobile type
		 * and select new
		 * 1、save mobile settings
		 * 2、enter mobile biz logic
		 */
		if (code.equals(NbSettingsCodeEnum.NB_MOB_ACCESS_KEY.getCode())) {
			MobileConfigDomain mobileConfigDomain = domain.convertMobileConfig();
			MobProjectSettings mobProjectSettings = new MobProjectSettings();
			mobProjectSettings.setProductName(mobileConfigDomain.getProductName());
			mobProjectSettings.setProductId(mobileConfigDomain.getProductId());
			mobProjectSettings.setAccessKey(mobileConfigDomain.getAccessKey());
			String settingsJson = JsonUtils.object2Json(mobProjectSettings);
			FileUtils.writeDataToFile(SYS_NB_MOBILE_PRODUCT_FILE_NAME, settingsJson);
		}
		return false;
	}

	private TelProjectSettings saveTelSettings(TelecomConfigDomain telecomConfigDomain, TelQueryProductResponse queryProductResponse) {
		TelQueryProductResponseBody body = queryProductResponse.getResult();
		TelProjectSettings telProjectSettings = new TelProjectSettings();
		telProjectSettings.setProductName(body.getProductName());
		telProjectSettings.setAppKey(telecomConfigDomain.getAppKey());
		telProjectSettings.setAppSecret(telecomConfigDomain.getAppSecret());
		telProjectSettings.setProductId(telecomConfigDomain.getProductId());
		telProjectSettings.setMasterApiKey(telecomConfigDomain.getMasterKey());
		String settingsJson = JsonUtils.object2Json(telProjectSettings);
		FileUtils.writeDataToFile(SYS_NB_TELECOM_PRODUCT_FILE_NAME, settingsJson);
		return telProjectSettings;
	}

	private Boolean saveLwm2mSettings(NbConfigSettingsDomain domain) {
		Lwm2mConfigSetting lwm2mConfigSetting = domain.geneLwm2mConfigSettings();
		String settingsJson = JsonUtils.object2Json(lwm2mConfigSetting);
		FileUtils.writeDataToFile(SYS_NB_LWM2M_SETTINGS_FILE_NAME, settingsJson);
		return Boolean.TRUE;
	}

	public void printValueToConsole(String code, NodeContext context) {
		System.out.format(ColorUtils.blackFaint(bundle.getString("call.prompt") + context.getData()) + "%n");
	}
}
