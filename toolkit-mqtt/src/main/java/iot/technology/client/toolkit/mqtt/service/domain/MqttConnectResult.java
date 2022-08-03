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

import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.mqtt.MqttConnectReturnCode;

/**
 * @author mushuwei
 */
public final class MqttConnectResult {

	private final boolean success;
	private final MqttConnectReturnCode returnCode;
	private final ChannelFuture closeFuture;

	public MqttConnectResult(boolean success, MqttConnectReturnCode returnCode, ChannelFuture closeFuture) {
		this.success = success;
		this.returnCode = returnCode;
		this.closeFuture = closeFuture;
	}

	public boolean isSuccess() {
		return success;
	}

	public MqttConnectReturnCode getReturnCode() {
		return returnCode;
	}

	public ChannelFuture getCloseFuture() {
		return closeFuture;
	}
}
