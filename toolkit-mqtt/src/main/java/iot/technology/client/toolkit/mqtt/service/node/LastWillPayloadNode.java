package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.NodeTypeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class LastWillPayloadNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean check(NodeContext context) {
		if (StringUtils.isBlank(context.getData())) {
			System.out.format(ColorUtils.redError(bundle.getString("param.error")));
			context.setCheck(false);
			return false;
		}
		context.setCheck(true);
		return true;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.LAST_WILL_PAYLOAD.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		if (!context.isCheck()) {
			return MqttSettingsCodeEnum.LAST_WILL_PAYLOAD.getCode();
		}
		return MqttSettingsCodeEnum.END.getCode();
//		if (context.getType().equals(NodeTypeEnum.MQTT_DEFAULT.getType())) {
//			return MqttSettingsCodeEnum.MQTT_BIZ_TYPE.getCode();
//		} else if (context.getType().equals(NodeTypeEnum.MQTT_PUBLISH.getType())) {
//			return MqttSettingsCodeEnum.PUBLISH_MESSAGE.getCode();
//		} else if (context.getType().equals(NodeTypeEnum.MQTT_SETTINGS.getType())) {
//			return MqttSettingsCodeEnum.END.getCode();
//		} else {
//			return MqttSettingsCodeEnum.SUBSCRIBE_MESSAGE.getCode();
//		}
	}


	@Override
	public String getValue(NodeContext context) {
		return context.getData();
	}

	@Override
	public void prePrompt(NodeContext context) {
	}
}
