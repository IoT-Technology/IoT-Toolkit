/*
 * Copyright © 2019-2025 The Toolkit Authors
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
package iot.technology.client.toolkit.mqtt.service.domain;

import io.netty.handler.codec.mqtt.MqttQoS;
import iot.technology.client.toolkit.mqtt.service.handler.MqttHandler;

import java.util.regex.Pattern;

/**
 * @author mushuwei
 */
public final class MqttSubscription {

	private final String topic;
	private final Pattern topicRegex;
	private final MqttHandler handler;

	private final boolean once;
	private boolean called;
	private MqttQoS mqttQoS;

	public MqttSubscription(String topic, MqttHandler handler, boolean once, MqttQoS mqttQoS) {
		if (topic == null) {
			throw new NullPointerException("topic");
		}
		if (handler == null) {
			throw new NullPointerException("handler");
		}
		this.topic = topic;
		this.handler = handler;
		this.once = once;
		this.mqttQoS = mqttQoS;
		this.topicRegex = Pattern.compile(topic.replace("+", "[^/]+").replace("#", ".+") + "$");
	}

	public MqttQoS getMqttQoS() {
		return mqttQoS;
	}

	public void setMqttQoS(MqttQoS mqttQoS) {
		this.mqttQoS = mqttQoS;
	}

	public String getTopic() {
		return topic;
	}

	public MqttHandler getHandler() {
		return handler;
	}

	public boolean isOnce() {
		return once;
	}

	public boolean isCalled() {
		return called;
	}

	public boolean matches(String topic) {
		return this.topicRegex.matcher(topic).matches();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		MqttSubscription that = (MqttSubscription) o;

		return once == that.once && topic.equals(that.topic) && handler.equals(that.handler);
	}

	@Override
	public int hashCode() {
		int result = topic.hashCode();
		result = 31 * result + handler.hashCode();
		result = 31 * result + (once ? 1 : 0);
		return result;
	}

	public void setCalled(boolean called) {
		this.called = called;
	}
}
