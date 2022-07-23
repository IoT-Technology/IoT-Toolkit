package iot.technology.client.toolkit.mqtt.service.handler;

import io.netty.buffer.ByteBuf;
import picocli.CommandLine;

import java.nio.charset.StandardCharsets;

/**
 * @author mushuwei
 */
public class MqttPubMessageHandler implements MqttHandler {

	@Override
	public void onMessage(String topic, ByteBuf payload) {
		System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(Cyan),bold " +
				payload.toString(StandardCharsets.UTF_8) + "success send to" + topic + "|@") + "%n");
	}
}
