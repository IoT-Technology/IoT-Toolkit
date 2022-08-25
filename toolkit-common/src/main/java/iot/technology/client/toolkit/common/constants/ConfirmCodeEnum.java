package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public enum ConfirmCodeEnum {

	YES("Y", "YES"),

	NO("N", "NO");
	
	private String value;

	private String desc;

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	ConfirmCodeEnum(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}
}
