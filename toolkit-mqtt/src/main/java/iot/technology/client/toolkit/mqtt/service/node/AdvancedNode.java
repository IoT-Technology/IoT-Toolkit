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
public class AdvancedNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean check(NodeContext context) {
		if (StringUtils.isBlank(context.getData())) {
			context.setData("N");
			context.setCheck(true);
			return true;
		}
		if (!context.getData().toUpperCase().equals(ConfirmCodeEnum.YES.getValue())
				&& !context.getData().toUpperCase().equals(ConfirmCodeEnum.NO.getValue())) {
			System.out.format(ColorUtils.redError(bundle.getString("param.confirm.error")));
			context.setCheck(false);
			return false;
		}
		context.setCheck(true);
		return true;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.ADVANCED.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		if (!context.isCheck()) {
			return MqttSettingsCodeEnum.ADVANCED.getCode();
		}
		if (context.getData().toUpperCase().equals(ConfirmCodeEnum.YES.getValue())) {
			return MqttSettingsCodeEnum.CONNECT_TIMEOUT.getCode();
		}
		return MqttSettingsCodeEnum.LASTWILLANDTESTAMENT.getCode();
	}


	@Override
	public void prePrompt(NodeContext context) {
		System.out.println(ColorUtils.greenItalic(bundle.getString(MqttSettingsCodeEnum.ADVANCED.getCode() + GlobalConstants.prePrompt)));
	}

	@Override
	public String getValue(NodeContext context) {
		return context.getData();
	}
}
