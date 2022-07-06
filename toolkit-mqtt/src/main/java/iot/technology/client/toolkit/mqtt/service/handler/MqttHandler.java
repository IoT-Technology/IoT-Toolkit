package iot.technology.client.toolkit.mqtt.service.handler;

import io.netty.buffer.ByteBuf;

/**
 * @author mushuwei
 */
public interface MqttHandler {

	void onMessage(String topic, ByteBuf payload);
}
