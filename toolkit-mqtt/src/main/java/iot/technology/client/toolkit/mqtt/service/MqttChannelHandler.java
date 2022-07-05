package iot.technology.client.toolkit.mqtt.service;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.MqttConnAckMessage;
import io.netty.handler.codec.mqtt.MqttMessage;

/**
 * @author mushuwei
 */
public final class MqttChannelHandler extends SimpleChannelInboundHandler<MqttMessage> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MqttMessage msg) throws Exception {
		switch (msg.fixedHeader().messageType()) {
			case CONNACK:

		}

	}

	private void handleConack(Channel channel, MqttConnAckMessage message) {
		switch (message.variableHeader().connectReturnCode()) {

		}
	}
}
