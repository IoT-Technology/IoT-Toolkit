package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class PasswordNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void check(NodeContext context) {

	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.PASSWORD.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		return MqttSettingsCodeEnum.SSL.getCode();
	}


	@Override
	public String getValue(NodeContext context) {
		String data = context.getData();
		String value = "";
		if (!StringUtils.isBlank(data)) {
			value = data;
		}
		return value;
	}

	@Override
	public void prePrompt(NodeContext context) {
	}
}
