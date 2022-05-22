package iot.technology.client.toolkit.coap.command;

import iot.technology.client.toolkit.coap.command.sub.CoapDeleteCommand;
import iot.technology.client.toolkit.coap.command.sub.CoapGetCommand;
import iot.technology.client.toolkit.coap.command.sub.CoapPostCommand;
import iot.technology.client.toolkit.coap.command.sub.CoapPutCommand;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "coap",
		version = "0.0.1",
		requiredOptionMarker = '*',
		header = "toolkit coap client",
		description = "this is a sub command to toolkit which deal with coap protocol",
		optionListHeading = "%nOptions are:%n",
		mixinStandardHelpOptions = true,
		subcommands = {
				CoapGetCommand.class,
				CoapPostCommand.class,
				CoapPutCommand.class,
				CoapDeleteCommand.class
		},
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by James mu")
public class CoapCommand implements Callable<Integer> {

	@CommandLine.Option(
			names = {"-m", "--message"},
			required = true,
			description = "a message to send")
	String message;


	@Override
	public Integer call() {
		System.out.println("[coap] CoAP message: Message: " + message);
		return 0;
	}
}
