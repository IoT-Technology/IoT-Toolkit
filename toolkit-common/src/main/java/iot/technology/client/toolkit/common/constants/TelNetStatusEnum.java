package iot.technology.client.toolkit.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mushuwei
 */
public enum TelNetStatusEnum {

	ONLINE(1, "在线"),
	OFFLINE(2, "离线");

	private final Integer type;

	private final String desc;

	public static final Map<Integer, TelNetStatusEnum> cache = new HashMap();

	static {
		for (TelNetStatusEnum type : TelNetStatusEnum.values()) {
			cache.put(type.type, type);
		}
	}

	public static String type(Integer key) {
		TelNetStatusEnum netStatusEnum = cache.get(key);
		if (netStatusEnum != null) {
			return netStatusEnum.getDesc();
		}
		return "";
	}

	TelNetStatusEnum(Integer type, String desc) {
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
