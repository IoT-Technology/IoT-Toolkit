package iot.technology.client.toolkit.mqtt.service.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.MqttConnAckMessage;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.util.concurrent.Promise;
import iot.technology.client.toolkit.mqtt.service.domain.MqttConnectResult;
import iot.technology.client.toolkit.mqtt.service.impl.MqttClientServiceImpl;

/**
 * @author mushuwei
 */
public final class MqttChannelHandler extends SimpleChannelInboundHandler<MqttMessage> {

	private final MqttClientServiceImpl client;
	private final Promise<MqttConnectResult> connectFuture;

	public MqttChannelHandler(MqttClientServiceImpl client, Promise<MqttConnectResult> connectFuture) {
		this.client = client;
		this.connectFuture = connectFuture;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MqttMessage msg) throws Exception {
		switch (msg.fixedHeader().messageType()) {
			case CONNACK:

		}

	}

	private void handleConack(Channel channel, MqttConnAckMessage message) {
		switch (message.variableHeader().connectReturnCode()) {
			case CONNECTION_ACCEPTED:

		}
	}
}
