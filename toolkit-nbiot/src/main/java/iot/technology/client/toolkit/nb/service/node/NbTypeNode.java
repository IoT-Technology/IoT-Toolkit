package iot.technology.client.toolkit.nb.service.node;

import iot.technology.client.toolkit.common.constants.*;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.NbBizService;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class NbTypeNode implements TkNode {

	private final NbBizService bizService = new NbBizService();
	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void prePrompt(NodeContext context) {
		if (bundle.getLocale().equals(Locale.CHINESE)) {
			System.out.format(ColorUtils.greenItalic("(1) 电信AEP * ") + "%n");
			System.out.format(ColorUtils.greenItalic("(2) 移动OneNET") + "%n");
			System.out.format(ColorUtils.greenItalic("(3) LwM2M") + "%n");
		} else {
			System.out.format(ColorUtils.greenItalic("(1) LwM2M * ") + "%n");
		}
	}

	@Override
	public boolean check(NodeContext context) {
		String data = context.getData();
		if (StringUtils.isBlank(data)
				|| data.equals(NBTypeEnum.TELECOM.getCode())
				|| data.equals(NBTypeEnum.MOBILE.getCode())
				|| data.equals(NBTypeEnum.LWM2M.getCode())) {
			context.setCheck(true);
			return true;
		}
		System.out.format(ColorUtils.redError(bundle.getString("param.error")));
		context.setCheck(false);
		return false;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(NbSettingsCodeEnum.NB_TYPE.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		if (!context.isCheck()) {
			return NbSettingsCodeEnum.NB_TYPE.getCode();
		}
		if (context.getType() != null && context.getType().equals("settings")) {
			return NbSettingsCodeEnum.NB_SETTINGS.getCode();
		}
		if (bundle.getLocale().equals(Locale.CHINESE)) {
			if (context.getData().equals(NBTypeEnum.TELECOM.getCode())) {
				List<String> nbSettings = bizService.getNbSettingsFromFile(SystemConfigConst.SYS_NB_TELECOM_PRODUCT_FILE_NAME);
				context.setPromptData(nbSettings);
				return NbSettingsCodeEnum.NB_TELECOM_APP_CONFIG.getCode();
			}
			if (context.getData().equals(NBTypeEnum.MOBILE.getCode())) {
				List<String> nbSettings = bizService.getNbSettingsFromFile(SystemConfigConst.SYS_NB_MOBILE_PRODUCT_FILE_NAME);
				context.setPromptData(nbSettings);
				return NbSettingsCodeEnum.NB_MOB_APP_CONFIG.getCode();
			}
			if (context.getData().equals(NBTypeEnum.LWM2M.getCode())) {
				List<String> nbSettings = bizService.getNbSettingsFromFile(SystemConfigConst.SYS_NB_LWM2M_SETTINGS_FILE_NAME);
				context.setPromptData(nbSettings);
				return NbSettingsCodeEnum.NB_LWM2M_CONFIG.getCode();
			}
			List<String> nbSettings = bizService.getNbSettingsFromFile(SystemConfigConst.SYS_NB_TELECOM_PRODUCT_FILE_NAME);
			context.setPromptData(nbSettings);
			return NbSettingsCodeEnum.NB_TELECOM_APP_CONFIG.getCode();
		}
		List<String> nbSettings = bizService.getNbSettingsFromFile(SystemConfigConst.SYS_NB_LWM2M_SETTINGS_FILE_NAME);
		context.setPromptData(nbSettings);
		return NbSettingsCodeEnum.NB_LWM2M_CONFIG.getCode();
	}

	@Override
	public String getValue(NodeContext context) {
		String data = context.getData();
		if (bundle.getLocale().equals(Locale.CHINESE)) {
			if (data.equals(NBTypeEnum.MOBILE.getCode())) {
				return NBTypeEnum.MOBILE.getValue();
			}
			if (data.equals(NBTypeEnum.TELECOM.getCode())) {
				return NBTypeEnum.TELECOM.getValue();
			}
			if (data.equals(NBTypeEnum.LWM2M.getCode())) {
				return NBTypeEnum.LWM2M.getValue();
			}
			return NBTypeEnum.TELECOM.getValue();
		}
		return NBTypeEnum.LWM2M.getValue();
	}
}
