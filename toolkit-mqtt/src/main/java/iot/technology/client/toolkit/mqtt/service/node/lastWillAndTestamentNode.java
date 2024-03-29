package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.*;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class lastWillAndTestamentNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean check(NodeContext context) {
		String data = context.getData();
		if (StringUtils.isBlank(data)) {
			context.setData("N");
			context.setCheck(true);
			return true;
		}
		if (!data.toUpperCase().equals(ConfirmCodeEnum.YES.getValue())
				&& !data.toUpperCase().equals(ConfirmCodeEnum.NO.getValue())) {
			System.out.format(ColorUtils.redError(bundle.getString("param.confirm.error")));
			context.setCheck(false);
			return false;
		}
		context.setCheck(true);
		return true;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.LASTWILLANDTESTAMENT.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		if (!context.isCheck()) {
			return MqttSettingsCodeEnum.LASTWILLANDTESTAMENT.getCode();
		}
		if (context.getData().toUpperCase().equals(ConfirmCodeEnum.YES.getValue())) {
			return MqttSettingsCodeEnum.LAST_WILL_TOPIC.getCode();
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
		System.out.println(ColorUtils.greenItalic(bundle.getString(MqttSettingsCodeEnum.LASTWILLANDTESTAMENT.getCode() + GlobalConstants.prePrompt)));
	}
}
