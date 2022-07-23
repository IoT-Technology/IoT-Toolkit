package iot.technology.client.toolkit.mqtt.command.sub;

import io.netty.util.concurrent.Future;
import iot.technology.client.toolkit.mqtt.service.MqttClientConfig;
import iot.technology.client.toolkit.mqtt.service.MqttClientService;
import iot.technology.client.toolkit.mqtt.service.domain.MqttConnectResult;
import iot.technology.client.toolkit.mqtt.service.handler.MqttSubMessageHandler;
import iot.technology.client.toolkit.mqtt.service.impl.MqttClientServiceImpl;
import picocli.CommandLine;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "subscribe",
		aliases = "sub",
		version = "0.0.1",
		requiredOptionMarker = '*',
		description = "subscribe for updates from the broker",
		optionListHeading = "%nOptions are:%n",
		mixinStandardHelpOptions = true,
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by mushuwei"
)
public class MqttSubscribeCommand implements Callable<Integer> {

	private final MqttClientConfig mqttClientConfig;
	private final MqttClientService mqttClientService;

	public MqttSubscribeCommand() {
		this.mqttClientConfig = new MqttClientConfig();
		this.mqttClientService = new MqttClientServiceImpl(mqttClientConfig, null);
	}

	@CommandLine.Parameters(
			index = "0",
			description = "the broker host")
	String uri;

	@CommandLine.Parameters(
			index = "1",
			description = "the broker host",
			defaultValue = "1883")
	Integer port;

	@CommandLine.Parameters(
			index = "2",
			description = "the message topic")
	String topic;

	@Override
	public Integer call() throws Exception {
		Future<MqttConnectResult> connectFuture = mqttClientService.connect(uri, port);
		MqttConnectResult result;
		try {
			result = connectFuture.get(mqttClientConfig.getTimeoutSeconds(), TimeUnit.SECONDS);
		} catch (TimeoutException ex) {
			connectFuture.cancel(true);
			mqttClientService.disconnect();
			String hostPort = uri + ":" + port;
			throw new RuntimeException(String.format("Failed to connect to MQTT broker at %s.", hostPort));
		}
		if (!result.isSuccess()) {
			connectFuture.cancel(true);
			mqttClientService.disconnect();
			String hostPort = uri + ":" + port;
			throw new RuntimeException(
					String.format("Failed to connect to MQTT broker at %s. Result code is: %s", hostPort, result.getReturnCode()));
		}
		mqttClientService.on(topic, new MqttSubMessageHandler());
		return 0;
	}
}
