package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public enum MqttBizEnum {

	PUB("pub", "publish"),

	SUB("sub", "subscribe");

	private String value;

	private String desc;

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	MqttBizEnum(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public static MqttBizEnum getBizEnum(String value) {
		for (MqttBizEnum elem : MqttBizEnum.values()) {
			if (elem.getValue().equals(value)) {
				return elem;
			}
		}
		return null;
	}
}
