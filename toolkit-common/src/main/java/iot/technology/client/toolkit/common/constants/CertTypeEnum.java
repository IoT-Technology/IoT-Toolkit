package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public enum CertTypeEnum {

	CA_SIGNED_SERVER("1", "CA signed server"),

	SELF_SIGNED("2", "Self signed");

	private String value;

	private String desc;

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	CertTypeEnum(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public static CertTypeEnum getCertTypeEnum(String value) {
		for (CertTypeEnum elem : CertTypeEnum.values()) {
			if (elem.getValue().equals(value)) {
				return elem;
			}
		}
		return null;
	}
}
