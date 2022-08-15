package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public enum LangEnum {

	EN("en", "english"),

	ZH("zh", "chinese"),

	DE("de", "german");

	private String value;

	private String desc;


	LangEnum(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
}
