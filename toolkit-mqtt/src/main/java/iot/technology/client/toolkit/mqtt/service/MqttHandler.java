package iot.technology.client.toolkit.mqtt.service;

import io.netty.buffer.ByteBuf;

/**
 * @author mushuwei
 */
public interface MqttHandler {

	void onMessage(String topic, ByteBuf payload);
}
