package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public enum NbSettingsCodeEnum {

	NB_TYPE("nbType", "iot.technology.client.toolkit.nb.service.node.NbTypeNode"),

	NB_TELECOM_APP_CONFIG("nbTelecomAppConfig", "iot.technology.client.toolkit.nb.service.node.NbTelecomAppConfigNode"),

	NB_TEL_APP_SECRET("nbTelAppSecret", "iot.technology.client.toolkit.nb.service.node.TelAppSecretNode"),

	NB_TEL_APP_KEY("nbTelAppKey", "iot.technology.client.toolkit.nb.service.node.TelAppKeyNode"),

	NB_TEL_PRODUCT_ID("nbTelProductId", "iot.technology.client.toolkit.nb.service.node.TelProductIdNode"),

	NB_TEL_API_KEY("nbTelApiKey", "iot.technology.client.toolkit.nb.service.node.TelApiKeyNode"),

	NB_MOBILE_APP_CONFIG("nbMobileAppConfig", "iot.technology.client.toolkit.nb.service.node.NbTelecomAppConfigNode"),

	NB_MOBILE_PRODUCT_CONFIG("nbMobileProductConfig", "iot.technology.client.toolkit.nb.service.node.NbTelecomProductConfigNode");

	private String code;

	private String clazzName;

	public String getCode() {
		return code;
	}

	public String getClazzName() {
		return clazzName;
	}

	NbSettingsCodeEnum(String code, String clazzName) {
		this.code = code;
		this.clazzName = clazzName;
	}
}
