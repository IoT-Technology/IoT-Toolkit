package iot.technology.client.toolkit.mqtt.service;


import io.netty.util.concurrent.Future;

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
}
