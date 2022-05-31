package iot.technology.client.toolkit.mqtt.command;

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
		header = "@|fg(Cyan),bold mqtt client toolkit|@",
		description = "this is a sub command to toolkit which deal with mqtt protocol",
		optionListHeading = "%nOptions are:%n",
		mixinStandardHelpOptions = true,
		subcommands = {
				MqttPublishCommand.class,
				MqttSubscribeCommand.class
		},
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by James mu")
public class MqttCommand implements Callable<Integer> {

	@CommandLine.Option(
			names = {"-m", "--message"},
			required = true,
			description = "a message to send")
	String message;


	@Override
	public Integer call() {
		System.out.println("[mqtt] MQTT message: Message: " + message);
		return 0;
	}
}
