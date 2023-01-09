package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.*;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class SubscribeMessageNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void prePrompt(NodeContext context) {
		System.out.format(ColorUtils.greenItalic(bundle.getString("subscribeMessage.pre.add.prompt") + "add topic:qos") + "%n");
		System.out.format(ColorUtils.greenItalic(bundle.getString("subscribeMessage.pre.del.prompt") + "del topic") + "%n");
	}

	@Override
	public boolean check(NodeContext context) {
		TopicAndQos bizDomain = new TopicAndQos();
		boolean validate = SubData.validate(context.getData(), bizDomain);
		context.setCheck(validate);
		return validate;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.SUBSCRIBE_MESSAGE.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		if (!context.isCheck()) {
			return MqttSettingsCodeEnum.SUBSCRIBE_MESSAGE.getCode();
		}
		return MqttSettingsCodeEnum.SUBSCRIBE_MESSAGE.getCode();
	}


	@Override
	public String getValue(NodeContext context) {
		return context.getData();
	}
}
