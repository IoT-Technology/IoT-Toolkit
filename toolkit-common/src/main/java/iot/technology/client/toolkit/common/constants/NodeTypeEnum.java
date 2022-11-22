package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public enum NodeTypeEnum {

	MQTT_DEFAULT("mqtt_default"),

	MQTT_PUBLISH("mqtt_pub"),

	MQTT_SUBSCRIBE("mqtt_sub");

	private final String type;

	NodeTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
