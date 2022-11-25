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
public class UsernameNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean check(NodeContext context) {
		context.setCheck(true);
		return true;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.USERNAME.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		if (!context.isCheck()) {
			return MqttSettingsCodeEnum.USERNAME.getCode();
		}
		return MqttSettingsCodeEnum.PASSWORD.getCode();
	}


	@Override
	public String getValue(NodeContext context) {
		String value = "";
		if (!StringUtils.isBlank(context.getData())) {
			value = context.getData();
		}
		return value;
	}

	@Override
	public void prePrompt(NodeContext context) {
	}
}
