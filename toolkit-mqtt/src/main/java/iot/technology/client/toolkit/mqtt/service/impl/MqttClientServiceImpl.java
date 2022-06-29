package iot.technology.client.toolkit.mqtt.service.impl;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import iot.technology.client.toolkit.mqtt.service.MqttClientConfig;
import iot.technology.client.toolkit.mqtt.service.MqttClientService;
import iot.technology.client.toolkit.mqtt.service.MqttConnectResult;

/**
 * @author mushuwei
 */
public class MqttClientServiceImpl implements MqttClientService {

	private final MqttClientConfig clientConfig;

	private EventLoopGroup eventLoop;
	private String host;
	private int port;

	public MqttClientServiceImpl() {
		this.clientConfig = new MqttClientConfig();
	}

	@Override
	public Future<MqttConnectResult> connect(String host) {
		return null;
	}

	@Override
	public Future<MqttConnectResult> connect(String host, int port) {
		return null;
	}

	private Future<MqttConnectResult> connect(String host, int port, boolean reconnect) {
		if (this.eventLoop == null) {
			this.eventLoop = new NioEventLoopGroup();
		}
		this.host = host;
		this.port = port;
		Promise<MqttConnectResult> connectFuture = new DefaultPromise<>(this.eventLoop.next());
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(this.eventLoop);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.remoteAddress(host, port);
		bootstrap.handler(new MqttChannelInitializer(connectFuture, host, port));
		ChannelFuture future = bootstrap.connect();

		return connectFuture;
	}

	private class MqttChannelInitializer extends ChannelInitializer<SocketChannel> {

		private final Promise<MqttConnectResult> connectFuture;
		private final String host;
		private final int port;

		public MqttChannelInitializer(Promise<MqttConnectResult> connectFuture,
									  String host,
									  int port) {
			this.connectFuture = connectFuture;
			this.host = host;
			this.port = port;
		}

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast("mqttDecoder", new MqttDecoder(clientConfig.getMaxBytesInMessage()));
			ch.pipeline().addLast("mqttEncoder", MqttEncoder.INSTANCE);
		}
	}
}
