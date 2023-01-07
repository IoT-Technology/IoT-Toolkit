/*
 * Copyright © 2019-2023 The Toolkit Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
