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
package iot.technology.client.toolkit.mqtt.service;

import iot.technology.client.toolkit.mqtt.service.domain.DisReason;

/**
 * @author mushuwei
 */
public interface MqttClientCallback {

	/**
	 * This method is called when the connection to the server is lost.
	 *
	 * @param cause the reason behind the loss of connection.
	 */
	void connectionLost(DisReason reason);

	/**
	 * This method is called when the connection to the server is recovered.
	 */
	void onSuccessfulReconnect();
}
