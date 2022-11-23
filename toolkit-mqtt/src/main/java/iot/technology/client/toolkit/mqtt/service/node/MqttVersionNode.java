package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.MqttVersionEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class MqttVersionNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void check(NodeContext context) {
		String data = context.getData();
		if (StringUtils.isBlank(data)
				|| data.equals(MqttVersionEnum.MQTT_3_1.getCode())
				|| data.equals(MqttVersionEnum.MQTT_3_1_1.getCode())) {
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
	public String nextNode(NodeContext context) {
		return MqttSettingsCodeEnum.CLIENT_ID.getCode();
	}


	@Override
	public String getValue(NodeContext context) {
		String data = context.getData();
		if (StringUtils.isBlank(data)) {
			return MqttVersionEnum.MQTT_3_1_1.getValue();
		}
		if (data.equals(MqttVersionEnum.MQTT_3_1.getCode())) {
			return MqttVersionEnum.MQTT_3_1.getValue();
		}
		return MqttVersionEnum.MQTT_3_1_1.getValue();
	}

	@Override
	public void prePrompt(NodeContext context) {
		System.out.format(ColorUtils.greenItalic("(1) 3.1") + "%n");
		System.out.format(ColorUtils.greenItalic("(2) 3.1.1 * ") + "%n");
	}
}
