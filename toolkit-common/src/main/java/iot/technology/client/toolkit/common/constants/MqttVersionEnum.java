package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public enum MqttVersionEnum {
	MQTT_3_1_1("1", "3.1.1"),

	MQTT_5_0("2", "5.0");

	private String code;

	private String value;

	MqttVersionEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}
}
