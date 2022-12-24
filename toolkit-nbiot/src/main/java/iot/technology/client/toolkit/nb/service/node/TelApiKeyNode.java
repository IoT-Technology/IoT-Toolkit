package iot.technology.client.toolkit.nb.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.NbSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class TelApiKeyNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void prePrompt(NodeContext context) {

	}

	@Override
	public boolean check(NodeContext context) {
		if (StringUtils.isBlank(context.getData())) {
			System.out.format(ColorUtils.redError(bundle.getString("param.error")));
			context.setCheck(false);
			return false;
		}
		context.setCheck(true);
		return true;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(NbSettingsCodeEnum.NB_TEL_API_KEY.getCode() + GlobalConstants.promptSuffix)
				+ GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		if (!context.isCheck()) {
			return NbSettingsCodeEnum.NB_TEL_API_KEY.getCode();
		}
		if (context.getType() != null && context.getType().equals("settings")) {
			return NbSettingsCodeEnum.END.getCode();
		}
		return NbSettingsCodeEnum.NB_TYPE.getCode();
	}

	@Override
	public String getValue(NodeContext context) {
		return context.getData();
	}
}
