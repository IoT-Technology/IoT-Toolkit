package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.CertTypeEnum;
import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class CertTypeNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void check(String data) {
		if (StringUtils.isBlank(data)) {
			throw new IllegalArgumentException(bundle.getString("param.error"));
		}
		if (!data.equals(CertTypeEnum.CA_SIGNED_SERVER.getValue())
				&& !data.equals(CertTypeEnum.SELF_SIGNED.getValue())) {
			throw new IllegalArgumentException(bundle.getString("mqtt.cert.error"));
		}
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.CERT_TYPE.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(String data) {
		if (data.equals(CertTypeEnum.CA_SIGNED_SERVER.getValue())) {
			return MqttSettingsCodeEnum.ADVANCED.getCode();
		}
		return MqttSettingsCodeEnum.CA.getCode();
	}

	@Override
	public String getValue(String data) {
		return data;
	}

	@Override
	public void prePrompt() {
		System.out.format(ColorUtils.greenItalic("(1) CA signed Server") + "%n");
		System.out.format(ColorUtils.greenItalic("(2) Self signed * ") + "%n");
	}
}
