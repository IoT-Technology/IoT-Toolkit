package iot.technology.client.toolkit.mqtt.service.domain;

import io.netty.handler.codec.mqtt.MqttPublishMessage;

/**
 * @author mushuwei
 */
public final class MqttIncomingQos2Publish {

	private final MqttPublishMessage incomingPublish;

	MqttIncomingQos2Publish(MqttPublishMessage incomingPublish) {
		this.incomingPublish = incomingPublish;
	}

	public MqttPublishMessage getIncomingPublish() {
		return incomingPublish;
	}
}
