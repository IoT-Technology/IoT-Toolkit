package iot.technology.client.toolkit.nb.service;

import iot.technology.client.toolkit.common.constants.NbSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.FileUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.nb.service.processor.TelecomBizService;
import iot.technology.client.toolkit.nb.service.telecom.TelecomProductService;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.product.TelQueryProductResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class NbBizService {

	private final TelecomBizService telecomBizService = new TelecomBizService();
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

	public boolean nbProcessorAfterLogic(String code, NodeContext context, NbConfigSettingsDomain domain) {
		if (code.equals(NbSettingsCodeEnum.NB_TEL_API_KEY.getCode())) {
			TelecomConfigDomain telecomConfigDomain = domain.convertTelecomConfig();
			TelQueryProductResponse queryProductResponse = productService.queryProduct(telecomConfigDomain);
			if (Objects.nonNull(queryProductResponse) && queryProductResponse.isSuccess()) {
				System.out.format(JsonUtils.object2Json(queryProductResponse));
				saveTelSettings(domain);
				telecomBizService.call(telecomConfigDomain);
				return true;
			}
		}
		return false;
	}

	private void saveTelSettings(NbConfigSettingsDomain domain) {

	}

	public void printValueToConsole(String code, NodeContext context) {
		System.out.format(ColorUtils.blackFaint(bundle.getString("call.prompt") + context.getData()) + "%n");
	}
}
