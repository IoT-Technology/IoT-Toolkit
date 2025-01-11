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

import io.netty.channel.EventLoop;
import io.netty.handler.codec.mqtt.MqttSubscribeMessage;
import io.netty.util.concurrent.Promise;
import iot.technology.client.toolkit.mqtt.service.PendingOperation;
import iot.technology.client.toolkit.mqtt.service.handler.MqttHandler;
import iot.technology.client.toolkit.mqtt.service.handler.RetransmissionHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * @author mushuwei
 */
public final class MqttPendingSubscription {

	private final Promise<Void> future;
	private final String topic;
	private final Set<MqttPendingHandler> handlers = new HashSet<>();
	private final MqttSubscribeMessage subscribeMessage;

	private final RetransmissionHandler<MqttSubscribeMessage> retransmissionHandler;

	private boolean sent = false;

	public MqttPendingSubscription(Promise<Void> future,
								   String topic,
								   MqttSubscribeMessage message,
								   PendingOperation operation) {
		this.future = future;
		this.topic = topic;
		this.subscribeMessage = message;

		this.retransmissionHandler = new RetransmissionHandler<>(operation);
		this.retransmissionHandler.setOriginalMessage(message);
	}

	public Promise<Void> getFuture() {
		return future;
	}

	public String getTopic() {
		return topic;
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public MqttSubscribeMessage getSubscribeMessage() {
		return subscribeMessage;
	}

	public void addHandler(MqttHandler handler, boolean once) {
		this.handlers.add(new MqttPendingHandler(handler, once));
	}

	public Set<MqttPendingHandler> getHandlers() {
		return handlers;
	}

	public void startRetransmitTimer(EventLoop eventLoop, Consumer<Object> sendPacket) {
		if (this.sent) { //If the packet is sent, we can start the retransmit timer
			this.retransmissionHandler.setHandle((fixedHeader, originalMessage) ->
					sendPacket.accept(new MqttSubscribeMessage(fixedHeader, originalMessage.variableHeader(), originalMessage.payload())));
			this.retransmissionHandler.start(eventLoop);
		}
	}

	public void onSubackReceived() {
		this.retransmissionHandler.stop();
	}

	public void onChannelClosed() {
		this.retransmissionHandler.stop();
	}

	public final class MqttPendingHandler {
		private final MqttHandler handler;
		private final boolean once;

		MqttPendingHandler(MqttHandler handler, boolean once) {
			this.handler = handler;
			this.once = once;
		}

		public MqttHandler getHandler() {
			return handler;
		}

		public boolean isOnce() {
			return once;
		}
	}
}
