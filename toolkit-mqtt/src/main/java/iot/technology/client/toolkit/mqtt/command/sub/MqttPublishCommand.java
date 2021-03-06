package iot.technology.client.toolkit.mqtt.command.sub;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.util.concurrent.Future;
import iot.technology.client.toolkit.mqtt.service.MqttClientConfig;
import iot.technology.client.toolkit.mqtt.service.MqttClientService;
import iot.technology.client.toolkit.mqtt.service.domain.MqttConnectResult;
import iot.technology.client.toolkit.mqtt.service.handler.MqttPubMessageHandler;
import iot.technology.client.toolkit.mqtt.service.impl.MqttClientServiceImpl;
import picocli.CommandLine;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "publish",
		aliases = "pub",
		version = "0.0.1",
		requiredOptionMarker = '*',
		description = "publish a message to the broker",
		optionListHeading = "%nOptions are:%n",
		mixinStandardHelpOptions = true,
		sortOptions = false,
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by mushuwei"
)
public class MqttPublishCommand implements Callable<Integer> {

	private static final Charset UTF8 = StandardCharsets.UTF_8;

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
			names = {"-t", "--topic"},
			required = true,
			description = "the message topic")
	String topic;

	@CommandLine.Option(
			order = 7,
			required = true,
			names = {"-m", "--message"},
			description = "the message body")
	String message;

	@Override
	public Integer call() throws Exception {
		MqttClientConfig config = new MqttClientConfig();
		config.setClientId(Objects.nonNull(clientId) ? clientId : config.getClientId());
		config.setUsername(Objects.nonNull(username) ? username : null);
		config.setPassword(Objects.nonNull(password) ? password : null);
		MqttClientService mqttClientService = new MqttClientServiceImpl(config, new MqttPubMessageHandler());
		Future<MqttConnectResult> connectFuture = mqttClientService.connect(host, port);
		MqttQoS qosLevel = MqttQoS.valueOf(qos);
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
		mqttClientService.publish(topic, Unpooled.wrappedBuffer(message.getBytes(UTF8)), qosLevel, false);
		return 0;
	}
}
