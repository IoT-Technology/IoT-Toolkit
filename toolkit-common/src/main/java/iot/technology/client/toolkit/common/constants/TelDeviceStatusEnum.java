package iot.technology.client.toolkit.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mushuwei
 */
public enum TelDeviceStatusEnum {

	REGISTER(0, "已注册"),
	ACTIVE(1, "已激活"),
	CANCEL(2, "已注销");

	private final Integer type;

	private final String desc;

	public static final Map<Integer, TelDeviceStatusEnum> cache = new HashMap();

	static {
		for (TelDeviceStatusEnum type : TelDeviceStatusEnum.values()) {
			cache.put(type.type, type);
		}
	}

	public static String type(Integer key) {
		TelDeviceStatusEnum deviceStatusEnum = cache.get(key);
		if (deviceStatusEnum != null) {
			return deviceStatusEnum.desc;
		}
		return null;
	}

	TelDeviceStatusEnum(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public Integer getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}
}
