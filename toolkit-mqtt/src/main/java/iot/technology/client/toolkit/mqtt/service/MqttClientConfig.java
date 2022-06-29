package iot.technology.client.toolkit.mqtt.service;

import java.util.Random;

/**
 * @author mushuwei
 */
public final class MqttClientConfig {

	private String clientId;
	private final String randomClientId;
	private int maxBytesInMessage = 8092;

	public MqttClientConfig() {
		Random random = new Random();
		String id = "toolkit/";
		String[] options = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".split("");
		for (int i = 0; i < 8; i++) {
			id += options[random.nextInt(options.length)];
		}
		this.clientId = id;
		this.randomClientId = id;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		if (clientId == null) {
			this.clientId = randomClientId;
		} else {
			this.clientId = clientId;
		}
	}

	public int getMaxBytesInMessage() {
		return maxBytesInMessage;
	}

	/**
	 * Sets the maximum number of bytes in the message for the {@link io.netty.handler.codec.mqtt.MqttDecoder}
	 * Default value is 8092 as specified by Netty. The absolute maximum size is 256MB as set by the MQTT spec.
	 *
	 * @param maxBytesInMessage
	 * @throws IllegalArgumentException if maxBytesInMessage is smaller than 1 or greater than 256_000_000.
	 */
	public void setMaxBytesInMessage(int maxBytesInMessage) {
		if (maxBytesInMessage <= 0 || maxBytesInMessage > 256_000_000) {
			throw new IllegalArgumentException("maxBytesInMessage must be > 0 or < 256_000_000");
		}
		this.maxBytesInMessage = maxBytesInMessage;
	}
}
