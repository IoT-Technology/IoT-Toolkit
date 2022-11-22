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
public class SettingsNameNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void prePrompt() {
	}

	@Override
	public void check(String data) {
		if (StringUtils.isBlank(data)) {
			System.out.format("%s", bundle.getString("mqtt.settings.name.error"));
		}

	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.SETTINGS_NAME.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		return MqttSettingsCodeEnum.MQTT_VERSION.getCode();
	}


	@Override
	public String getValue(String data) {
		return data;
	}
}
