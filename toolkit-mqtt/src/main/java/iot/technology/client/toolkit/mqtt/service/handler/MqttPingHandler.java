package iot.technology.client.toolkit.mqtt.service.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author mushuwei
 */
public class MqttPingHandler extends ChannelInboundHandlerAdapter {

	private final int keepaliveSeconds;
	private ScheduledFuture<?> pingRespTimeout;

	public MqttPingHandler(int keepaliveSeconds) {
		this.keepaliveSeconds = keepaliveSeconds;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (!(msg instanceof MqttMessage)) {
			ctx.fireChannelRead(msg);
			return;
		}
		MqttMessage message = (MqttMessage) msg;
		if (message.fixedHeader().messageType() == MqttMessageType.PINGREQ) {
			this.handlePingReq(ctx.channel());
		} else if (message.fixedHeader().messageType() == MqttMessageType.PINGRESP) {
			this.handlePingResp();
		} else {
			ctx.fireChannelRead(ReferenceCountUtil.retain(msg));
		}
	}


	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		super.userEventTriggered(ctx, evt);

		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			switch (event.state()) {
				case READER_IDLE:
					break;
				case WRITER_IDLE:
					this.sendPingReq(ctx.channel());
					break;
			}
		}
	}

	private void sendPingReq(Channel channel) {
		MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.PINGREQ, false, MqttQoS.AT_MOST_ONCE, false, 0);
		channel.writeAndFlush(new MqttMessage(fixedHeader));

		if (this.pingRespTimeout != null) {
			this.pingRespTimeout = channel.eventLoop().schedule(() -> {
				MqttFixedHeader fixedHeader2 = new MqttFixedHeader(MqttMessageType.DISCONNECT, false, MqttQoS.AT_MOST_ONCE, false, 0);
				channel.writeAndFlush(new MqttMessage(fixedHeader2)).addListener(ChannelFutureListener.CLOSE);
				//TODO: what do when the connection is closed ?
			}, this.keepaliveSeconds, TimeUnit.SECONDS);
		}
	}

	private void handlePingReq(Channel channel) {
		MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.PINGRESP, false, MqttQoS.AT_MOST_ONCE, false, 0);
		channel.writeAndFlush(new MqttMessage(fixedHeader));
	}

	private void handlePingResp() {
		if (this.pingRespTimeout != null && !this.pingRespTimeout.isCancelled() && !this.pingRespTimeout.isDone()) {
			this.pingRespTimeout.cancel(true);
			this.pingRespTimeout = null;
		}
	}
}
