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
package iot.technology.client.toolkit.mqtt.service.domain;

import io.netty.handler.codec.mqtt.MqttPublishMessage;

/**
 * @author mushuwei
 */
public final class MqttIncomingQos2Publish {

	private final MqttPublishMessage incomingPublish;

	public MqttIncomingQos2Publish(MqttPublishMessage incomingPublish) {
		this.incomingPublish = incomingPublish;
	}

	public MqttPublishMessage getIncomingPublish() {
		return incomingPublish;
	}
}
