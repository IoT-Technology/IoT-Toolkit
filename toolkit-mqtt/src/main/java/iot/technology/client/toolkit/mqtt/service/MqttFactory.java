package iot.technology.client.toolkit.mqtt.service;

import iot.technology.client.toolkit.mqtt.service.impl.MqttClientServiceImpl;

/**
 * @author mushuwei
 */
public class MqttFactory {

	public static MqttClientService getService(MqttClientConfig mqttClientConfig) {
		return new MqttClientServiceImpl(mqttClientConfig, null);
	}
}
