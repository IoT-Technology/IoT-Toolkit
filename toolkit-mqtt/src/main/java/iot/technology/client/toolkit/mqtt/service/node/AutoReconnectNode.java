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
public class AutoReconnectNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void check(NodeContext context) {
		if (!StringUtils.isBlank(context.getData())) {
			if (context.getData().toUpperCase().equals(ConfirmCodeEnum.YES.getValue())
					|| context.getData().toUpperCase().equals(ConfirmCodeEnum.NO.getValue())) {
				return;
			}
			throw new IllegalArgumentException(bundle.getString("param.confirm.error"));
		}
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.AUTO_RECONNECT.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		return MqttSettingsCodeEnum.LASTWILLANDTESTAMENT.getCode();
	}


	@Override
	public String getValue(NodeContext context) {
		return StringUtils.isBlank(context.getData()) ? ConfirmCodeEnum.YES.getValue() : context.getData();
	}

	@Override
	public void prePrompt(NodeContext context) {
	}
}
