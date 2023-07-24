package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.CertTypeEnum;
import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class CertTypeNode implements TkNode {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean check(NodeContext context) {
		if (StringUtils.isBlank(context.getData())) {
			context.setData(CertTypeEnum.SELF_SIGNED.getValue());
			context.setCheck(true);
			return true;
		}
		if (context.getType().equals(CertTypeEnum.CA_SIGNED_SERVER.getValue())
				|| context.getData().equals(CertTypeEnum.SELF_SIGNED.getValue())) {
			context.setCheck(true);
			return true;
		}
		System.out.format(ColorUtils.redError(bundle.getString("mqtt.cert.error")));
		context.setCheck(false);
		return false;
	}

	@Override
	public String nodePrompt() {
		return bundle.getString(MqttSettingsCodeEnum.CERT_TYPE.getCode() + GlobalConstants.promptSuffix) +
				GlobalConstants.promptSeparator;
	}

	@Override
	public String nextNode(NodeContext context) {
		if (!context.isCheck()) {
			return MqttSettingsCodeEnum.CERT_TYPE.getCode();
		}
		if (context.getData().equals(CertTypeEnum.CA_SIGNED_SERVER.getValue())) {
			return MqttSettingsCodeEnum.ADVANCED.getCode();
		}
		return MqttSettingsCodeEnum.CA.getCode();
	}


	@Override
	public String getValue(NodeContext context) {
		CertTypeEnum certTypeEnum = CertTypeEnum.getCertTypeEnum(context.getData());
		return Objects.requireNonNull(certTypeEnum).getDesc();
	}

	@Override
	public void prePrompt(NodeContext context) {
		System.out.format(ColorUtils.greenItalic("(1) CA signed Server") + "%n");
		System.out.format(ColorUtils.greenItalic("(2) Self signed * ") + "%n");
	}
}
