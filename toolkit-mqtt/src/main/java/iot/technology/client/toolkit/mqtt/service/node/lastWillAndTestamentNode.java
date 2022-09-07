package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.ConfirmCodeEnum;
import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class lastWillAndTestamentNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void check(String data) {
		if (StringUtils.isBlank(data)) {
			throw new IllegalArgumentException(bundle.getString("param.error"));
		}
		if (!data.toUpperCase().equals(ConfirmCodeEnum.YES.getValue())
				&& !data.toUpperCase().equals(ConfirmCodeEnum.NO.getValue())) {
			throw new IllegalArgumentException(bundle.getString("param.confirm.error"));
		}
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.LASTWILLANDTESTAMENT.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(String data) {
		if (data.toUpperCase().equals(ConfirmCodeEnum.YES.getValue())) {
			return MqttSettingsCodeEnum.LAST_WILL_TOPIC.getCode();
		}
		return MqttSettingsCodeEnum.MQTT_BIZ_TYPE.getCode();
	}

	@Override
	public String getValue(String data) {
		return data;
	}

	@Override
	public void prePrompt() {
	}
}