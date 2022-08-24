package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.MqttVersionEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.TkNode;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class MqttVersionNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void check(String data) {
		if (Objects.isNull(data)
				|| data.equals(MqttVersionEnum.MQTT_3_1_1.getCode())
				|| data.equals(MqttVersionEnum.MQTT_5_0.getCode())) {
			return;
		}
		throw new IllegalArgumentException(bundle.getString("mqttVersion.version.error"));
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.MQTT_VERSION.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(String data) {
		return MqttSettingsCodeEnum.CLIENT_ID.getCode();
	}

	@Override
	public String getValue(String data) {
		if (Objects.isNull(data)) {
			return MqttVersionEnum.MQTT_3_1_1.getValue();
		}
		if (data.equals(MqttVersionEnum.MQTT_5_0.getCode())) {
			return MqttVersionEnum.MQTT_5_0.getValue();
		}
		return MqttVersionEnum.MQTT_3_1_1.getValue();
	}
}
