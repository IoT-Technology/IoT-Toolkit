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
package iot.technology.client.toolkit.mqtt.service.core;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableSet;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.mqtt.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import iot.technology.client.toolkit.mqtt.service.MqttClientCallback;
import iot.technology.client.toolkit.mqtt.service.MqttClientConfig;
import iot.technology.client.toolkit.mqtt.service.domain.*;
import iot.technology.client.toolkit.mqtt.service.handler.MqttChannelHandler;
import iot.technology.client.toolkit.mqtt.service.handler.MqttHandler;
import iot.technology.client.toolkit.mqtt.service.handler.MqttPingHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mushuwei
 */
public class MqttClientService {

	private final Set<String> serverSubscriptions = new HashSet<>();
	private final ConcurrentMap<Integer, MqttPendingUnsubscription> pendingServerUnsubscribes = new ConcurrentHashMap<>();
	private final ConcurrentMap<Integer, MqttIncomingQos2Publish> qos2PendingIncomingPublishes = new ConcurrentHashMap<>();
	private final ConcurrentMap<Integer, MqttPendingPublish> pendingPublishes = new ConcurrentHashMap<>();
	private final HashMultimap<String, MqttSubscription> subscriptions = HashMultimap.create();
	private final ConcurrentMap<Integer, MqttPendingSubscription> pendingSubscriptions = new ConcurrentHashMap<>();
	private final Set<String> pendingSubscribeTopics = new HashSet<>();
	private final HashMultimap<MqttHandler, MqttSubscription> handlerToSubscribtion = HashMultimap.create();
	private final AtomicInteger nextMessageId = new AtomicInteger(1);

	private final MqttClientConfig clientConfig;
	private final MqttHandler defaultHandler;

	private EventLoopGroup eventLoop;
	private volatile Channel channel;

	private volatile boolean disconnected = false;
	private volatile boolean reconnect = false;
	private MqttClientCallback callback;

	public MqttClientService(MqttHandler defaultHandler) {
		this.clientConfig = new MqttClientConfig();
		this.defaultHandler = defaultHandler;
	}

	public void setCallback(MqttClientCallback callback) {
		this.callback = callback;
	}

	/**
	 * Construct the MqttClientImpl with additional config.
	 * This config can also be changed using the {@link #getClientConfig()} function
	 *
	 * @param clientConfig The config object to use while looking for settings
	 */
	public MqttClientService(MqttClientConfig clientConfig, MqttHandler defaultHandler) {
		this.clientConfig = clientConfig;
		this.defaultHandler = defaultHandler;
	}

	public Future<MqttConnectResult> connect(String host) {
		return connect(host, 1883);
	}

	public Future<MqttConnectResult> connect(String host, int port) {
		return connect(host, port, false);
	}


	public boolean isConnected() {
		return !disconnected && channel != null && channel.isActive();
	}


	public EventLoopGroup getEventLoop() {
		return eventLoop;
	}


	public void setEventLoop(EventLoopGroup eventLoop) {
		this.eventLoop = eventLoop;
	}

	public void disconnect() {
		disconnected = true;
		if (this.channel != null) {
			MqttMessage message = new MqttMessage(new MqttFixedHeader(MqttMessageType.DISCONNECT, false, MqttQoS.AT_MOST_ONCE, false, 0));
			this.sendAndFlushPacket(message).addListener(future -> channel.close());
		}
	}


	public Future<Void> publish(String topic, ByteBuf payload, MqttQoS qos, boolean retain) {
		Promise<Void> future = new DefaultPromise<>(this.eventLoop.next());
		MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.PUBLISH, false, qos, retain, 0);
		MqttPublishVariableHeader variableHeader = new MqttPublishVariableHeader(topic, getNewMessageId().messageId());
		MqttPublishMessage message = new MqttPublishMessage(fixedHeader, variableHeader, payload);
		MqttPendingPublish pendingPublish = new MqttPendingPublish(variableHeader.packetId(), future,
				payload.retain(), message, qos, () -> !pendingPublishes.containsKey(variableHeader.packetId()));
		this.pendingPublishes.put(pendingPublish.getMessageId(), pendingPublish);
		ChannelFuture channelFuture = this.sendAndFlushPacket(message);

		if (channelFuture != null) {
			channelFuture.addListener(result -> {
				pendingPublish.setSent(true);
				if (result.cause() != null) {
					pendingPublishes.remove(pendingPublish.getMessageId());
					future.setFailure(result.cause());
				} else {
					if (pendingPublish.isSent() && pendingPublish.getQos() == MqttQoS.AT_MOST_ONCE) {
						pendingPublishes.remove(pendingPublish.getMessageId());
						pendingPublish.getFuture().setSuccess(null);
					} else if (pendingPublish.isSent()) {
						pendingPublish.startPublishRetransmissionTimer(eventLoop.next(), this::sendAndFlushPacket);
					} else {
						pendingPublishes.remove(pendingPublish.getMessageId());
					}
				}
			});
		} else {
			pendingPublishes.remove(pendingPublish.getMessageId());
		}
		return future;
	}


	public Future<Void> on(String topic, MqttHandler handler) {
		return on(topic, handler, MqttQoS.AT_MOST_ONCE);
	}


	public Future<Void> on(String topic, MqttHandler handler, MqttQoS qos) {
		return createSubscription(topic, handler, false, qos);
	}

	/**
	 * Remove the subscription for the given topic and handler
	 * If you want to unsubscribe from all handlers known for this topic, use {@link #off(String)}
	 *
	 * @param topic   The topic to unsubscribe for
	 * @param handler The handler to unsubscribe
	 * @return A future which will be completed when the server acknowledges our unsubscribe request
	 */
	public Future<Void> off(String topic, MqttHandler handler) {
		Promise<Void> future = new DefaultPromise<>(this.eventLoop.next());
		for (MqttSubscription subscription : this.handlerToSubscribtion.get(handler)) {
			this.subscriptions.remove(topic, subscription);
		}
		this.handlerToSubscribtion.removeAll(handler);
		this.checkSubscribtions(topic, future);
		return future;
	}

	/**
	 * Remove all subscriptions for the given topic.
	 * If you want to specify which handler to unsubscribe, use {@link #off(String, MqttHandler)}
	 *
	 * @param topic The topic to unsubscribe for
	 * @return A future which will be completed when the server acknowledges our unsubscribe request
	 */
	public Future<Void> off(String topic) {
		Promise<Void> future = new DefaultPromise<>(this.eventLoop.next());
		ImmutableSet<MqttSubscription> subscriptions = ImmutableSet.copyOf(this.subscriptions.get(topic));
		for (MqttSubscription subscription : subscriptions) {
			for (MqttSubscription handSub : this.handlerToSubscribtion.get(subscription.getHandler())) {
				this.subscriptions.remove(topic, handSub);
			}
			this.handlerToSubscribtion.remove(subscription.getHandler(), subscription);
		}
		this.checkSubscribtions(topic, future);
		return future;
	}

	/**
	 * Retrieve the MqttClient configuration
	 *
	 * @return The {@link MqttClientConfig} instance we use
	 */
	public MqttClientConfig getClientConfig() {
		return clientConfig;
	}

	public boolean isReconnect() {
		return reconnect;
	}

	public void onSuccessfulReconnect() {
		if (callback != null) {
			callback.onSuccessfulReconnect();
		}
	}


	public ConcurrentMap<Integer, MqttPendingSubscription> getPendingSubscriptions() {
		return pendingSubscriptions;
	}

	public HashMultimap<String, MqttSubscription> getSubscriptions() {
		return subscriptions;
	}

	public Set<String> getPendingSubscribeTopics() {
		return pendingSubscribeTopics;
	}

	public HashMultimap<MqttHandler, MqttSubscription> getHandlerToSubscribtion() {
		return handlerToSubscribtion;
	}

	public Set<String> getServerSubscriptions() {
		return serverSubscriptions;
	}

	public ConcurrentMap<Integer, MqttPendingUnsubscription> getPendingServerUnsubscribes() {
		return pendingServerUnsubscribes;
	}

	public ConcurrentMap<Integer, MqttPendingPublish> getPendingPublishes() {
		return pendingPublishes;
	}

	public ConcurrentMap<Integer, MqttIncomingQos2Publish> getQos2PendingIncomingPublishes() {
		return qos2PendingIncomingPublishes;
	}

	/***
	 * PRIVATE API
	 */

	private void checkSubscribtions(String topic, Promise<Void> promise) {
		if (!(this.subscriptions.containsKey(topic) && this.subscriptions.get(topic).size() != 0) &&
				this.serverSubscriptions.contains(topic)) {
			MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.UNSUBSCRIBE, false, MqttQoS.AT_LEAST_ONCE, false, 0);
			MqttMessageIdVariableHeader variableHeader = getNewMessageId();
			MqttUnsubscribePayload payload = new MqttUnsubscribePayload(Collections.singletonList(topic));
			MqttUnsubscribeMessage message = new MqttUnsubscribeMessage(fixedHeader, variableHeader, payload);

			MqttPendingUnsubscription pendingUnsubscription = new MqttPendingUnsubscription(promise, topic, message,
					() -> !pendingServerUnsubscribes.containsKey(variableHeader.messageId()));
			this.pendingServerUnsubscribes.put(variableHeader.messageId(), pendingUnsubscription);
			pendingUnsubscription.startRetransmissionTimer(this.eventLoop.next(), this::sendAndFlushPacket);

			this.sendAndFlushPacket(message);
		} else {
			promise.setSuccess(null);
		}
	}


	public ChannelFuture sendAndFlushPacket(Object message) {
		if (this.channel == null) {
			return null;
		}
		if (this.channel.isActive()) {
			return this.channel.writeAndFlush(message);
		}
		return this.channel.newFailedFuture(new ChannelClosedException("Channel is closed!"));
	}

	private MqttMessageIdVariableHeader getNewMessageId() {
		int messageId;
		synchronized (this.nextMessageId) {
			this.nextMessageId.compareAndSet(0xffff, 1);
			messageId = this.nextMessageId.getAndIncrement();
		}
		return MqttMessageIdVariableHeader.from(messageId);
	}

	private Future<MqttConnectResult> connect(String host, int port, boolean reconnect) {
		if (this.eventLoop == null) {
			this.eventLoop = new NioEventLoopGroup();
		}
		Promise<MqttConnectResult> connectFuture = new DefaultPromise<>(this.eventLoop.next());
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(this.eventLoop);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.remoteAddress(host, port);
		bootstrap.handler(new MqttChannelInitializer(connectFuture, host, port, clientConfig.getSslContext()));
		ChannelFuture future = bootstrap.connect();

		future.addListener((ChannelFutureListener) f -> {
			if (f.isSuccess()) {
				this.channel = f.channel();
				this.channel.closeFuture().addListener((ChannelFutureListener) channelFuture -> {
					if (isConnected()) {
						return;
					}
					ChannelClosedException e = new ChannelClosedException("Channel is closed!");
					if (callback != null) {
						callback.connectionLost(e);
					}
					pendingSubscriptions.forEach((id, mqttPendingSubscription) -> mqttPendingSubscription.onChannelClosed());
					pendingSubscriptions.clear();
					serverSubscriptions.clear();
					subscriptions.clear();
					pendingServerUnsubscribes.forEach(
							(id, mqttPendingServerUnsubscribes) -> mqttPendingServerUnsubscribes.onChannelClosed());
					pendingServerUnsubscribes.clear();
					qos2PendingIncomingPublishes.clear();
					pendingPublishes.forEach((id, mqttPendingPublish) -> mqttPendingPublish.onChannelClosed());
					pendingPublishes.clear();
					pendingSubscribeTopics.clear();
					handlerToSubscribtion.clear();
					scheduleConnectIfRequired(host, port, true);
				});
			} else {
				scheduleConnectIfRequired(host, port, true);
			}
		});
		return connectFuture;
	}

	private Future<Void> createSubscription(String topic, MqttHandler handler, boolean once, MqttQoS qos) {
		if (this.pendingSubscribeTopics.contains(topic)) {
			Optional<Map.Entry<Integer, MqttPendingSubscription>> subscriptionEntry = this.pendingSubscriptions.entrySet().stream()
					.filter((e) -> e.getValue().getTopic().equals(topic)).findAny();
			if (subscriptionEntry.isPresent()) {
				subscriptionEntry.get().getValue().addHandler(handler, once);
				return subscriptionEntry.get().getValue().getFuture();
			}
		}
		if (this.serverSubscriptions.contains(topic)) {
			MqttSubscription subscription = new MqttSubscription(topic, handler, once, qos);
			this.subscriptions.put(topic, subscription);
			this.handlerToSubscribtion.put(handler, subscription);
			return this.channel.newSucceededFuture();
		}

		Promise<Void> future = new DefaultPromise<>(this.eventLoop.next());
		MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.SUBSCRIBE, false, MqttQoS.AT_LEAST_ONCE, false, 0);
		MqttTopicSubscription subscription = new MqttTopicSubscription(topic, qos);
		MqttMessageIdVariableHeader variableHeader = getNewMessageId();
		MqttSubscribePayload payload = new MqttSubscribePayload(Collections.singletonList(subscription));
		MqttSubscribeMessage message = new MqttSubscribeMessage(fixedHeader, variableHeader, payload);

		final MqttPendingSubscription pendingSubscription = new MqttPendingSubscription(future, topic, message,
				() -> !pendingSubscriptions.containsKey(variableHeader.messageId()));
		pendingSubscription.addHandler(handler, once);
		this.pendingSubscriptions.put(variableHeader.messageId(), pendingSubscription);
		this.pendingSubscribeTopics.add(topic);
		pendingSubscription.setSent(this.sendAndFlushPacket(message) != null); //If not sent, we will send it when the connection is opened

		pendingSubscription.startRetransmitTimer(this.eventLoop.next(), this::sendAndFlushPacket);

		return future;
	}

	private void scheduleConnectIfRequired(String host, int port, boolean reconnect) {
		if (clientConfig.isReconnect() && !disconnected) {
			if (reconnect) {
				this.reconnect = true;
			}
			eventLoop.schedule((Runnable) () -> connect(host, port, reconnect),
					clientConfig.getReconnectDelay(), TimeUnit.SECONDS);
		}
	}

	private class MqttChannelInitializer extends ChannelInitializer<SocketChannel> {

		private final Promise<MqttConnectResult> connectFuture;
		private final String host;
		private final int port;
		private final SslContext sslContext;

		public MqttChannelInitializer(Promise<MqttConnectResult> connectFuture,
									  String host,
									  int port,
									  SslContext context) {
			this.host = host;
			this.port = port;
			this.connectFuture = connectFuture;
			this.sslContext = context;

		}

		@Override
		protected void initChannel(SocketChannel ch) {
			if (sslContext != null) {
				ch.pipeline().addLast(sslContext.newHandler(ch.alloc(), host, port));
			}
			ch.pipeline().addLast("mqttDecoder", new MqttDecoder(clientConfig.getMaxBytesInMessage()));
			ch.pipeline().addLast("mqttEncoder", MqttEncoder.INSTANCE);
			ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(MqttClientService.this.clientConfig.getKeepAlive(),
					MqttClientService.this.clientConfig.getKeepAlive(), 0));
			ch.pipeline().addLast("mqttPingHandler", new MqttPingHandler(MqttClientService.this.clientConfig.getKeepAlive()));
			ch.pipeline().addLast("mqttHandler", new MqttChannelHandler(MqttClientService.this, connectFuture));
		}
	}


	public MqttHandler getDefaultHandler() {
		return defaultHandler;
	}
}
