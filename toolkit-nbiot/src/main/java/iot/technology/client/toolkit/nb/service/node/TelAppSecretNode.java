package iot.technology.client.toolkit.nb.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.NbSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.NbBizService;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class TelAppSecretNode implements TkNode {

	private final NbBizService bizService = new NbBizService();
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
		return bundle.getString(NbSettingsCodeEnum.NB_TEL_APP_SECRET.getCode() + GlobalConstants.promptSuffix)
				+ GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		if (!context.isCheck()) {
			return NbSettingsCodeEnum.NB_TEL_APP_SECRET.getCode();
		}
		return NbSettingsCodeEnum.NB_TEL_PRODUCT_ID.getCode();
	}

	@Override
	public String getValue(NodeContext context) {
		return context.getData();
	}
}
