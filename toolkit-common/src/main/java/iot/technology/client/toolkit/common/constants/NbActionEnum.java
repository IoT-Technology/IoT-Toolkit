package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public enum NbActionEnum {
	LIST("list"),

	ADD("add"),

	COMMAND("command"),

	DELETE("del"),

	SHOW("show"),

	LOG("log"),

	UPDATE("update")
	;

	NbActionEnum(String value) {
		this.value = value;
	}

	private String value;

	public String getValue() {
		return value;
	}
}
