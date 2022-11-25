package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.PubData;
import iot.technology.client.toolkit.common.constants.StorageConstants;
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
		System.out.format(ColorUtils.greenItalic(bundle.getString("publishMessage.pre.prompt") + "topic:qos=message") + "%n");
		System.out.format(ColorUtils.greenItalic(bundle.getString("publishMessage.pre.example") + "hello:0=hello world") + "%n");

	}

	@Override
	public boolean check(NodeContext context) {
		PubData.validate(context.getData());
		return true;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.PUBLISH_MESSAGE.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		return MqttSettingsCodeEnum.PUBLISH_MESSAGE.getCode();
	}


	@Override
	public String getValue(NodeContext context) {
		return context.getData();
	}
}
