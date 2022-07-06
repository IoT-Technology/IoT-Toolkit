package iot.technology.client.toolkit.mqtt.service;

/**
 * @author mushuwei
 */
public interface MqttClientCallback {

	/**
	 * This method is called when the connection to the server is lost.
	 *
	 * @param cause the reason behind the loss of connection.
	 */
	void connectionLost(Throwable cause);

	/**
	 * This method is called when the connection to the server is recovered.
	 */
	void onSuccessfulReconnect();
}
