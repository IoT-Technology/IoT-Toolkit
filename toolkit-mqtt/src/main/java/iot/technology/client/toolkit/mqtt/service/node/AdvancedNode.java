package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.ConfirmCodeEnum;
import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class AdvancedNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void check(NodeContext context) {
		if (StringUtils.isBlank(context.getData())) {
			throw new IllegalArgumentException(bundle.getString("param.error"));
		}
		if (!context.getData().toUpperCase().equals(ConfirmCodeEnum.YES.getValue())
				&& !context.getData().toUpperCase().equals(ConfirmCodeEnum.NO.getValue())) {
			throw new IllegalArgumentException(bundle.getString("param.confirm.error"));
		}
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.ADVANCED.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		if (context.getData().toUpperCase().equals(ConfirmCodeEnum.YES.getValue())) {
			return MqttSettingsCodeEnum.CONNECT_TIMEOUT.getCode();
		}
		return MqttSettingsCodeEnum.LASTWILLANDTESTAMENT.getCode();
	}


	@Override
	public void prePrompt(NodeContext context) {
	}

	@Override
	public String getValue(NodeContext context) {
		return context.getData();
	}
}
