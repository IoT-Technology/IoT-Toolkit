package iot.technology.client.toolkit.mqtt.service;


import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.util.concurrent.Future;
import iot.technology.client.toolkit.mqtt.service.domain.MqttConnectResult;

/**
 * @author mushuwei
 */
public interface MqttClientService {

	/**
	 * Connect to the specified hostname/ip. By default uses port 1883.
	 * If you want to change the port number, see {@link #connect(String, int)}
	 *
	 * @param host host The ip address or host to connect to
	 * @return A future which will be completed when the connection is opened and we received an CONNACK
	 */
	Future<MqttConnectResult> connect(String host);


	/**
	 * Connect to the specified hostname/ip using the specified port
	 *
	 * @param host The ip address or host to connect to
	 * @param port The tcp port to connect to
	 * @return A future which will be completed when the connection is opened and we received an CONNACK
	 */
	Future<MqttConnectResult> connect(String host, int port);


	/**
	 * @return boolean value indicating if channel is active
	 */
	boolean isConnected();


	/**
	 * Send disconnect and close channel
	 */
	void disconnect();


	/**
	 * Publish a message to the given payload, using the given qos and optional retain
	 *
	 * @param topic   The topic to publish to
	 * @param payload The payload to send
	 * @param qos     The qos to use while publishing
	 * @param retain  true if you want to retain the message on the server, false otherwise
	 * @return A future which will be completed when the message is delivered to the server
	 */
	Future<Void> publish(String topic, ByteBuf payload, MqttQoS qos, boolean retain);


	/**
	 * Retrieve the MqttClient configuration
	 *
	 * @return The {@link MqttClientConfig} instance we use
	 */
	MqttClientConfig getClientConfig();
}
