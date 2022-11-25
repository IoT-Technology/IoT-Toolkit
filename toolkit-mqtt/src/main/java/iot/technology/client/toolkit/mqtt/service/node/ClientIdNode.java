package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.Random;
import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class ClientIdNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean check(NodeContext context) {
		return true;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.CLIENT_ID.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		return MqttSettingsCodeEnum.HOST.getCode();
	}


	@Override
	public String getValue(NodeContext context) {
		String id = "toolkit_mqtt_";
		String[] options = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".split("");
		for (int i = 0; i < 8; i++) {
			id += options[new Random().nextInt(options.length)];
		}
		return StringUtils.isBlank(context.getData()) ? id : context.getData();
	}

	@Override
	public void prePrompt(NodeContext context) {
	}
}
