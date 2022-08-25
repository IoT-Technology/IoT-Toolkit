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
public class PortNode implements TkNode {
	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void check(String data) {
		if (StringUtils.isBlank(data)) {
			throw new IllegalArgumentException(bundle.getString("param.error"));
		}
		Integer port = 0;
		try {
			port = Integer.parseInt(data);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(bundle.getString("param.error"));
		}
		if (port < 0 || port > 65535) {
			throw new IllegalArgumentException(bundle.getString("mqtt.port.error"));
		}
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.PORT.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(String data) {
		return MqttSettingsCodeEnum.USERNAME.getCode();
	}

	@Override
	public String getValue(String data) {
		return data;
	}

	@Override
	public void prePrompt() {
	}
}
