package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.*;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class PublishMessageNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void prePrompt(NodeContext context) {
		System.out.format(ColorUtils.greenItalic(
				bundle.getString("publishMessage.pre.prompt") + " topic:qos message or topic message (qos default 0)") + "%n");
		System.out.format(
				ColorUtils.greenItalic(bundle.getString("publishMessage.pre.example") + " hello:0 hello world") + "%n");

	}

	@Override
	public boolean check(NodeContext context) {
		TopicAndQos bizDomain = new TopicAndQos();
		boolean validate = PubData.validate(context.getData(), bizDomain);
		context.setCheck(validate);
		return validate;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.PUBLISH_MESSAGE.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		if (!context.isCheck()) {
			return MqttSettingsCodeEnum.PUBLISH_MESSAGE.getCode();
		}
		return MqttSettingsCodeEnum.PUBLISH_MESSAGE.getCode();
	}


	@Override
	public String getValue(NodeContext context) {
		return context.getData();
	}
}
