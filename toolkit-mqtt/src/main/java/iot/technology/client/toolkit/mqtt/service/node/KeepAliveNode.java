package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class KeepAliveNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void check(String data) {
		if (!StringUtils.isBlank(data) && !StringUtils.isNumeric(data)) {
			throw new IllegalArgumentException(bundle.getString("number.error"));
		}
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.KEEP_ALIVE.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(String data) {
		return MqttSettingsCodeEnum.CLEAN_SESSION.getCode();
	}

	@Override
	public String getValue(String data) {
		return StringUtils.isBlank(data) ? "10" : data;
	}

	@Override
	public void prePrompt() {
	}
}
