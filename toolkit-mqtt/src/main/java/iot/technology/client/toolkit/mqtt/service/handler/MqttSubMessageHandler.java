package iot.technology.client.toolkit.mqtt.service.handler;

import io.netty.buffer.ByteBuf;

/**
 * @author mushuwei
 */
public class MqttSubMessageHandler implements MqttHandler {

	@Override
	public void onMessage(String topic, ByteBuf payload) {
		System.out.println("topic" + topic);
	}
}
