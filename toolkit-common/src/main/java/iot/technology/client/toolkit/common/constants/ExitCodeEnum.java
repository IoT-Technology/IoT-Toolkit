package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public enum ExitCodeEnum {

	SUCCESS(200, "SUCCESS"),

	NOTEND(201, "don't kill jvm"),

	ERROR(400, "ERROR");

	private int value;

	private String desc;

	ExitCodeEnum(int value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}
