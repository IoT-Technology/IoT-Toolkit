package iot.technology.client.toolkit.mqtt.service.node;

import io.netty.handler.codec.mqtt.MqttQoS;
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
public class LastWillQoSNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean check(NodeContext context) {
		if (StringUtils.isBlank(context.getData()) || !StringUtils.isNumeric(context.getData())) {
			System.out.format(ColorUtils.redError(bundle.getString("param.error")));
			return false;
		}
		Integer qosValue = Integer.parseInt(context.getData());
		if (qosValue.equals(MqttQoS.AT_LEAST_ONCE.value())
				|| qosValue.equals(MqttQoS.AT_MOST_ONCE.value())
				|| qosValue.equals(MqttQoS.EXACTLY_ONCE.value())) {
			return true;
		}
		System.out.format(ColorUtils.redError(bundle.getString("mqtt.qos.error")));
		return false;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.LAST_WILL_QOS.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		return MqttSettingsCodeEnum.LAST_WILL_RETAIN.getCode();
	}


	@Override
	public String getValue(NodeContext context) {
		return context.getData();
	}

	@Override
	public void prePrompt(NodeContext context) {
		System.out.format(ColorUtils.greenItalic("(0) ") + bundle.getString("mqtt.qos0.prompt") + "%n");
		System.out.format(ColorUtils.greenItalic("(1) ") + bundle.getString("mqtt.qos1.prompt") + "%n");
		System.out.format(ColorUtils.greenItalic("(2) ") + bundle.getString("mqtt.qos2.prompt") + "%n");
	}
}
