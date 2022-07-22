package iot.technology.client.toolkit.mqtt.command;

import iot.technology.client.toolkit.mqtt.command.sub.MqttConnectCommand;
import iot.technology.client.toolkit.mqtt.command.sub.MqttPublishCommand;
import iot.technology.client.toolkit.mqtt.command.sub.MqttSubscribeCommand;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "mqtt",
		version = "0.0.1",
		requiredOptionMarker = '*',
		header = "@|fg(Cyan),bold MQTT Client Toolkit|@",
		description = "this is a sub command to toolkit which deal with mqtt protocol",
		optionListHeading = "%nOptions are:%n",
		mixinStandardHelpOptions = true,
		subcommands = {
				MqttConnectCommand.class,
				MqttPublishCommand.class,
				MqttSubscribeCommand.class
		},
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by James mu")
public class MqttCommand implements Callable<Integer> {

	@Override
	public Integer call() {
		return 0;
	}
}
