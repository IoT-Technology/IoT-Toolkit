package iot.technology.client.toolkit.nb.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.NbSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.nb.service.processor.settings.NbSettingsHelpProcessor;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class NbSettingsNode implements TkNode {

	public static ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void prePrompt(NodeContext context) {
	}

	@Override
	public boolean check(NodeContext context) {
		return true;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(NbSettingsCodeEnum.NB_SETTINGS.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		return NbSettingsCodeEnum.NB_SETTINGS.getCode();
	}

	@Override
	public String getValue(NodeContext context) {
		return context.getData();
	}
}
