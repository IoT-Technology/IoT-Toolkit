package iot.technology.client.toolkit.mqtt.service.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.mqtt.*;
import io.netty.util.CharsetUtil;
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
				handleConack(ctx.channel(), (MqttConnAckMessage) msg);
				break;

		}

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);

		MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.CONNECT, false, MqttQoS.AT_MOST_ONCE, false, 0);
		MqttConnectVariableHeader variableHeader = new MqttConnectVariableHeader(
				this.client.getClientConfig().getProtocolVersion().protocolName(),  // Protocol Name
				this.client.getClientConfig().getProtocolVersion().protocolLevel(), // Protocol Level
				this.client.getClientConfig().getUsername() != null,                // Has Username
				this.client.getClientConfig().getPassword() != null,                // Has Password
				this.client.getClientConfig().getLastWill() != null                 // Will Retain
						&& this.client.getClientConfig().getLastWill().isRetain(),
				this.client.getClientConfig().getLastWill() != null                 // Will QOS
						? this.client.getClientConfig().getLastWill().getQos().value()
						: 0,
				this.client.getClientConfig().getLastWill() != null,                // Has Will
				this.client.getClientConfig().isCleanSession(),                     // Clean Session
				this.client.getClientConfig().getTimeoutSeconds()                   // Timeout
		);
		MqttConnectPayload payload = new MqttConnectPayload(
				this.client.getClientConfig().getClientId(),
				this.client.getClientConfig().getLastWill() != null ? this.client.getClientConfig().getLastWill().getTopic() : null,
				this.client.getClientConfig().getLastWill() != null ? this.client.getClientConfig().getLastWill().getMessage().getBytes(
						CharsetUtil.UTF_8) : null,
				this.client.getClientConfig().getUsername(),
				this.client.getClientConfig().getPassword() != null ?
						this.client.getClientConfig().getPassword().getBytes(CharsetUtil.UTF_8) : null
		);
		ctx.channel().writeAndFlush(new MqttConnectMessage(fixedHeader, variableHeader, payload));
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}


	private void handleConack(Channel channel, MqttConnAckMessage message) {
		switch (message.variableHeader().connectReturnCode()) {
			case CONNECTION_ACCEPTED:
				this.connectFuture.setSuccess(
						new MqttConnectResult(true, MqttConnectReturnCode.CONNECTION_ACCEPTED, channel.closeFuture()));

				this.client.getPendingSubscriptions().entrySet().stream().filter((e) -> !e.getValue().isSent()).forEach((e) -> {
					channel.write(e.getValue().getSubscribeMessage());
					e.getValue().setSent(true);
				});

				this.client.getPendingPublishes().forEach((id, publish) -> {
					if (publish.isSent()) {
						return;
					}
					channel.write(publish.getMessage());
					publish.setSent(true);
					if (publish.getQos() == MqttQoS.AT_MOST_ONCE) {
						publish.getFuture().setSuccess(null); //We don't get an ACK for QOS 0
						this.client.getPendingPublishes().remove(publish.getMessageId());
					}
				});
				channel.flush();
				if (this.client.isReconnect()) {
					this.client.onSuccessfulReconnect();
				}
				break;

			case CONNECTION_REFUSED_BAD_USER_NAME_OR_PASSWORD:
			case CONNECTION_REFUSED_IDENTIFIER_REJECTED:
			case CONNECTION_REFUSED_NOT_AUTHORIZED:
			case CONNECTION_REFUSED_SERVER_UNAVAILABLE:
			case CONNECTION_REFUSED_UNACCEPTABLE_PROTOCOL_VERSION:
				this.connectFuture.setSuccess(
						new MqttConnectResult(false, message.variableHeader().connectReturnCode(), channel.closeFuture()));
				channel.close();
				// Don't start reconnect logic here
				break;
		}
	}
}
