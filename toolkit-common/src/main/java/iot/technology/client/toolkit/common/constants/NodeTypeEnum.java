/*
 * Copyright © 2019-2022 The Toolkit Authors
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

/**
 * @author mushuwei
 */
public enum NodeTypeEnum {

	MQTT_DEFAULT("mqtt_default"),

	MQTT_PUBLISH("mqtt_pub"),

	MQTT_SUBSCRIBE("mqtt_sub"),

	MQTT_SETTINGS("mqtt_settings");

	private final String type;

	NodeTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}