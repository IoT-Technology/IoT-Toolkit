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
package iot.technology.client.toolkit.mqtt.service.domain;

import io.netty.channel.EventLoop;
import io.netty.handler.codec.mqtt.MqttUnsubscribeMessage;
import io.netty.util.concurrent.Promise;
import iot.technology.client.toolkit.mqtt.service.PendingOperation;
import iot.technology.client.toolkit.mqtt.service.handler.RetransmissionHandler;

import java.util.function.Consumer;

/**
 * @author mushuwei
 */
public final class MqttPendingUnsubscription {

	private final Promise<Void> future;
	private final String topic;
	private final RetransmissionHandler<MqttUnsubscribeMessage> retransmissionHandler;

	public MqttPendingUnsubscription(Promise<Void> future,
									 String topic,
									 MqttUnsubscribeMessage unsubscribeMessage,
									 PendingOperation operation) {
		this.future = future;
		this.topic = topic;

		this.retransmissionHandler = new RetransmissionHandler<>(operation);
		this.retransmissionHandler.setOriginalMessage(unsubscribeMessage);

	}

	public Promise<Void> getFuture() {
		return future;
	}

	public String getTopic() {
		return topic;
	}

	public void startRetransmissionTimer(EventLoop eventLoop, Consumer<Object> sendPacket) {
		this.retransmissionHandler.setHandle((fixedHeader, originalMessage) ->
				sendPacket.accept(new MqttUnsubscribeMessage(fixedHeader, originalMessage.variableHeader(), originalMessage.payload())));
		this.retransmissionHandler.start(eventLoop);
	}

	public void onUnsubackReceived() {
		this.retransmissionHandler.stop();
	}

	public void onChannelClosed() {
		this.retransmissionHandler.stop();
	}
}
