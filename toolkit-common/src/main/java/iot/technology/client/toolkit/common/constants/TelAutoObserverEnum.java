package iot.technology.client.toolkit.common.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mushuwei
 */
public enum TelAutoObserverEnum {
	SUBSCRIBE(0, "订阅"),
	UNSUBSCRIBE(1, "不订阅");

	private final Integer type;

	private final String desc;

	public static final Map<Integer, TelAutoObserverEnum> cache = new HashMap();

	static {
		for (TelAutoObserverEnum type : TelAutoObserverEnum.values()) {
			cache.put(type.type, type);
		}
	}

	public static String type(Integer key) {
		TelAutoObserverEnum autoObserverEnum = cache.get(key);
		if (autoObserverEnum != null) {
			return autoObserverEnum.desc;
		}
		return null;
	}

	TelAutoObserverEnum(Integer type, String desc) {
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
