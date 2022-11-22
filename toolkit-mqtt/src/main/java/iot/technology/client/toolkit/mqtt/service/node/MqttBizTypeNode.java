package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttBizEnum;
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
public class MqttBizTypeNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void prePrompt() {
		System.out.format(ColorUtils.greenItalic("sub ") + bundle.getString("mqtt.sub.description") + "%n");
		System.out.format(ColorUtils.greenItalic("pub ") + bundle.getString("mqtt.pub.description") + "%n");

	}

	@Override
	public void check(String data) {
		if (StringUtils.isBlank(data)) {
			throw new IllegalArgumentException(bundle.getString("param.error"));
		}
		if (data.equals(MqttBizEnum.SUB.getValue())
				|| data.equals(MqttBizEnum.PUB.getValue())) {
			return;
		}
		throw new IllegalArgumentException(bundle.getString("param.error"));
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.MQTT_BIZ_TYPE.getCode() + GlobalConstants.promptSuffix)
				+ GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		if (context.getData().toLowerCase().equals(MqttBizEnum.SUB.getValue())) {
			return MqttSettingsCodeEnum.SUBSCRIBE_MESSAGE.getCode();
		}
		return MqttSettingsCodeEnum.PUBLISH_MESSAGE.getCode();
	}


	@Override
	public String getValue(String data) {
		MqttBizEnum mqttBizEnum = MqttBizEnum.getBizEnum(data);
		return mqttBizEnum.getDesc();
	}
}
