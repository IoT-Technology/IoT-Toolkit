package iot.technology.client.toolkit.app;

import iot.technology.client.toolkit.coap.command.CoapCommand;
import iot.technology.client.toolkit.mqtt.command.MqttCommand;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(name = "toolkit", description = "iot client toolkit",
		version = "0.0.1", mixinStandardHelpOptions = true, subcommands = {CoapCommand.class, MqttCommand.class})
public class ToolKitCommand implements Callable<Integer> {

	@CommandLine.Option(names = {"-p", "--protocol"}, description = "choose the protocol you want to use")
	private String protocol;


	@Override

	public Integer call() throws Exception {
		System.out.println(protocol);
		return 0;
	}
}
