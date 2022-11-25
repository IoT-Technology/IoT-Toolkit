package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class KeepAliveNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean check(NodeContext context) {
		if (!StringUtils.isBlank(context.getData()) && !StringUtils.isNumeric(context.getData())) {
			System.out.format(ColorUtils.redError(bundle.getString("number.error")));
			return false;
		}
		return true;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.KEEP_ALIVE.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		return MqttSettingsCodeEnum.CLEAN_SESSION.getCode();
	}

	@Override
	public String getValue(NodeContext context) {
		return StringUtils.isBlank(context.getData()) ? "10" : context.getData();
	}

	@Override
	public void prePrompt(NodeContext context) {
	}
}
