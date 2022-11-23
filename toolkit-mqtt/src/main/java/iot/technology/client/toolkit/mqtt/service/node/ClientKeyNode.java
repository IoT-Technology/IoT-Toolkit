package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ObjectUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class ClientKeyNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void check(NodeContext context) {
		if (StringUtils.isBlank(context.getData())) {
			throw new IllegalArgumentException(bundle.getString("param.error"));
		}
		if (!ObjectUtils.fileExists(context.getData())) {
			throw new IllegalArgumentException(bundle.getString("file.error"));
		}
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.CLIENT_KEY.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}


	@Override
	public String nextNode(NodeContext context) {
		return MqttSettingsCodeEnum.ADVANCED.getCode();
	}


	@Override
	public String getValue(NodeContext context) {
		return context.getData();
	}

	@Override
	public void prePrompt(NodeContext context) {

	}
}
