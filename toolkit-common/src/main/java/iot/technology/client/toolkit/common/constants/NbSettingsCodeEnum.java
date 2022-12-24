package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public enum NbSettingsCodeEnum {

	NB_TYPE("nbType", "iot.technology.client.toolkit.nb.service.node.NbTypeNode"),

	NB_SETTINGS("nbSettings", "iot.technology.client.toolkit.nb.service.node.NbSettingsNode"),

	NB_TELECOM_APP_CONFIG("nbTelecomAppConfig", "iot.technology.client.toolkit.nb.service.node.NbTelecomAppConfigNode"),

	NB_TEL_APP_SECRET("nbTelAppSecret", "iot.technology.client.toolkit.nb.service.node.TelAppSecretNode"),

	NB_TEL_APP_KEY("nbTelAppKey", "iot.technology.client.toolkit.nb.service.node.TelAppKeyNode"),

	NB_TEL_PRODUCT_ID("nbTelProductId", "iot.technology.client.toolkit.nb.service.node.TelProductIdNode"),

	NB_TEL_API_KEY("nbTelApiKey", "iot.technology.client.toolkit.nb.service.node.TelApiKeyNode"),

	NB_MOB_APP_CONFIG("mobAppConfig", "iot.technology.client.toolkit.nb.service.node.MobAppConfigNode"),

	NB_MOB_PRODUCT_NAME("mobProductName", "iot.technology.client.toolkit.nb.service.node.MobProductNameNode"),

	NB_MOB_PRODUCT_ID("mobProductId", "iot.technology.client.toolkit.nb.service.node.MobProductIdNode"),

	NB_MOB_ACCESS_KEY("mobAccessKey", "iot.technology.client.toolkit.nb.service.node.MobAccessKeyNode"),
	
	END("end", "");


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
