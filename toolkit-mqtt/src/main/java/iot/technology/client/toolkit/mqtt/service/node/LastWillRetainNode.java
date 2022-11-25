package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.ConfirmCodeEnum;
import iot.technology.client.toolkit.common.constants.GlobalConstants;
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
public class LastWillRetainNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean check(NodeContext context) {
		String data = context.getData();
		if (!StringUtils.isBlank(data)) {
			if (data.toUpperCase().equals(ConfirmCodeEnum.YES.getValue())
					|| data.toUpperCase().equals(ConfirmCodeEnum.NO.getValue())) {
				context.setCheck(true);
				return true;
			}
		}
		System.out.format(ColorUtils.redError(bundle.getString("param.confirm.error")));
		context.setCheck(false);
		return false;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.LAST_WILL_RETAIN.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		if (!context.isCheck()) {
			return MqttSettingsCodeEnum.LAST_WILL_RETAIN.getCode();
		}
		return MqttSettingsCodeEnum.LAST_WILL_PAYLOAD.getCode();
	}

	@Override
	public String getValue(NodeContext context) {
		return StringUtils.isBlank(context.getData()) ? ConfirmCodeEnum.NO.getValue() : context.getData();
	}

	@Override
	public void prePrompt(NodeContext context) {
	}
}
