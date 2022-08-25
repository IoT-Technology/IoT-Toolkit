package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class ConnectTimeoutNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void check(String data) {
		if (!StringUtils.isBlank(data) && !StringUtils.isNumeric(data)) {
			throw new IllegalArgumentException(bundle.getString("number.error"));
		}
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.CONNECT_TIMEOUT.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(String data) {
		return MqttSettingsCodeEnum.KEEP_ALIVE.getCode();
	}

	@Override
	public String getValue(String data) {
		return Objects.nonNull(data) ? data : "10";
	}

	@Override
	public void prePrompt() {
	}
}
