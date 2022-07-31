package iot.technology.client.toolkit.mqtt.command.sub;

import iot.technology.client.toolkit.mqtt.service.MqttClientConfig;
import iot.technology.client.toolkit.mqtt.service.MqttClientService;
import iot.technology.client.toolkit.mqtt.service.impl.MqttClientServiceImpl;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "describe",
		aliases = "desc",
		requiredOptionMarker = '*',
		description = "introduction and description of MQTT protocol",
		optionListHeading = "%nOptions are:%n",
		mixinStandardHelpOptions = true,
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by mushuwei",
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class
)
public class MqttDescribeCommand implements Callable<Integer> {

	private final MqttClientService mqttClientService;

	public MqttDescribeCommand() {
		MqttClientConfig mqttClientConfig = new MqttClientConfig();
		this.mqttClientService = new MqttClientServiceImpl(mqttClientConfig, null);
	}

	@CommandLine.Option(
			names = {"-h", "--help"},
			versionHelp = false,
			description = "Show this help message and exit.")
	private boolean help;

	@Override
	public Integer call() {
		mqttClientService.getMqttDescription();
		return 0;
	}
}
