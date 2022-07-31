package iot.technology.client.toolkit.mqtt.command.sub;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.util.concurrent.Future;
import iot.technology.client.toolkit.mqtt.service.MqttClientConfig;
import iot.technology.client.toolkit.mqtt.service.MqttClientService;
import iot.technology.client.toolkit.mqtt.service.domain.MqttConnectResult;
import iot.technology.client.toolkit.mqtt.service.handler.MqttSubMessageHandler;
import iot.technology.client.toolkit.mqtt.service.impl.MqttClientServiceImpl;
import picocli.CommandLine;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "subscribe",
		aliases = "sub",
		requiredOptionMarker = '*',
		description = "subscribe for updates from the broker",
		optionListHeading = "%nOptions are:%n",
		mixinStandardHelpOptions = true,
		sortOptions = false,
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by mushuwei",
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class
)
public class MqttSubscribeCommand implements Callable<Integer> {

	@CommandLine.Option(
			order = 0,
			names = {"-h", "--hostname"},
			required = true,
			description = "the broker host")
	String host;

	@CommandLine.Option(
			order = 1,
			names = {"-p", "--port"},
			required = true,
			description = "the broker port",
			defaultValue = "1883",
			showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
	Integer port;

	@CommandLine.Option(
			order = 2,
			names = {"-i", "--client-id"},
			description = "the client id"
	)
	String clientId;

	@CommandLine.Option(
			order = 3,
			names = {"-u", "--username"},
			description = "the username"
	)
	String username;

	@CommandLine.Option(
			order = 4,
			names = {"-P", "--password"},
			description = "the password"
	)
	String password;

	@CommandLine.Option(
			order = 5,
			names = {"-q", "--qos"},
			defaultValue = "0",
			showDefaultValue = CommandLine.Help.Visibility.ALWAYS,
			description = "the QoS of the message, 0/1/2")
	Integer qos;

	@CommandLine.Option(
			order = 6,
			required = true,
			names = {"-t", "--topic"},
			description = "the message topic")
	String topic;


	@CommandLine.Option(
			order = 7,
			names = {"-k", "--keepalive"},
			description = "send a ping every SEC seconds",
			defaultValue = "60",
			showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
	Integer keepalive;

	@Override
	public Integer call() throws Exception {
		MqttClientConfig config = new MqttClientConfig();
		config.setClientId(Objects.nonNull(clientId) ? clientId : config.getClientId());
		config.setUsername(Objects.nonNull(username) ? username : null);
		config.setPassword(Objects.nonNull(password) ? password : null);
		config.setTimeoutSeconds(keepalive);
		MqttSubMessageHandler handler = new MqttSubMessageHandler();
		MqttClientService mqttClientService = new MqttClientServiceImpl(config, handler);
		MqttQoS qosLevel = MqttQoS.valueOf(qos);
		Future<MqttConnectResult> connectFuture = mqttClientService.connect(host, port);
		MqttConnectResult result;
		try {
			result = connectFuture.get(config.getTimeoutSeconds(), TimeUnit.SECONDS);
		} catch (TimeoutException ex) {
			connectFuture.cancel(true);
			mqttClientService.disconnect();
			String hostPort = host + ":" + port;
			throw new RuntimeException(String.format("Failed to connect to MQTT broker at %s.", hostPort));
		}
		if (!result.isSuccess()) {
			connectFuture.cancel(true);
			mqttClientService.disconnect();
			String hostPort = host + ":" + port;
			throw new RuntimeException(
					String.format("Failed to connect to MQTT broker at %s. Result code is: %s", hostPort, result.getReturnCode()));
		}
		mqttClientService.on(topic, handler, qosLevel);
		return 0;
	}
}
