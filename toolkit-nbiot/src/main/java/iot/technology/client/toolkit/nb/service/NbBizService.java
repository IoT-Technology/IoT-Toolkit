package iot.technology.client.toolkit.nb.service;

import iot.technology.client.toolkit.common.constants.NbSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.FileUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.settings.MobProjectSettings;
import iot.technology.client.toolkit.nb.service.processor.MobileBizService;
import iot.technology.client.toolkit.nb.service.processor.TelecomBizService;
import iot.technology.client.toolkit.nb.service.telecom.TelecomProductService;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.product.TelQueryProductResponse;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.product.TelQueryProductResponseBody;
import iot.technology.client.toolkit.nb.service.telecom.domain.settings.TelProjectSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static iot.technology.client.toolkit.common.constants.SystemConfigConst.SYS_NB_MOBILE_PRODUCT_FILE_NAME;
import static iot.technology.client.toolkit.common.constants.SystemConfigConst.SYS_NB_TELECOM_PRODUCT_FILE_NAME;

/**
 * @author mushuwei
 */
public class NbBizService {

	private final TelecomBizService telecomBizService = new TelecomBizService();
	private final MobileBizService mobileBizService = new MobileBizService();
	private final TelecomProductService productService = new TelecomProductService();
	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	public List<String> getNbSettingsFromFile(String fileName) {
		List<String> fileDatas = new ArrayList<>();
		if (FileUtils.notExistOrContents(fileName)) {
			return fileDatas;
		}
		fileDatas = FileUtils.getDataFromFile(fileName);
		return fileDatas;
	}

	public boolean nbProcessorAfterLogic(String code, NbConfigSettingsDomain domain, NodeContext context) {
		/**
		 * user fill in telecom type
		 * and select one old telecom settings
		 * enter telecom biz logic at once.
		 */
		if (code.equals(NbSettingsCodeEnum.NB_TELECOM_APP_CONFIG.getCode())
				&& context.isCheck()
				&& !domain.getNbTelecomAppConfig().equals("new")) {
			TelecomConfigDomain telecomConfigDomain = domain.convertTelecomConfig();
			return telecomBizService.call(telecomConfigDomain);
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
				return telecomBizService.call(telecomConfigDomain);
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
			return mobileBizService.call(mobileConfigDomain);

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
			return mobileBizService.call(mobileConfigDomain);
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

	public void printValueToConsole(String code, NodeContext context) {
		System.out.format(ColorUtils.blackFaint(bundle.getString("call.prompt") + context.getData()) + "%n");
	}
}
