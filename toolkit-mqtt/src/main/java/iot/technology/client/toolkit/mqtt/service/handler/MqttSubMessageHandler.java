package iot.technology.client.toolkit.mqtt.service.handler;

import io.netty.buffer.ByteBuf;
import picocli.CommandLine;

import java.nio.charset.StandardCharsets;

/**
 * @author mushuwei
 */
public class MqttSubMessageHandler implements MqttHandler {

	@Override
	public void onMessage(String topic, ByteBuf payload) {
		System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(Cyan),bold " +
				topic + " success subscribe message: " + payload.toString(StandardCharsets.UTF_8) + "|@") + "%n");
	}
}
