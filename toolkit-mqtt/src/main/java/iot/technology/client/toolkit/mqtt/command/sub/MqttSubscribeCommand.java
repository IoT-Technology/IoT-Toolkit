package iot.technology.client.toolkit.mqtt.command.sub;

import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "subcribe",
		aliases = "sub",
		version = "0.0.1",
		requiredOptionMarker = '*',
		description = "publish a message to the broker",
		optionListHeading = "%nOptions are:%n",
		mixinStandardHelpOptions = true,
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by James mu"
)
public class MqttSubscribeCommand implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		return null;
	}
}
