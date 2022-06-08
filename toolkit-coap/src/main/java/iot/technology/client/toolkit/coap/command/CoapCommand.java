package iot.technology.client.toolkit.coap.command;

import iot.technology.client.toolkit.coap.command.sub.*;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "coap",
		version = "0.0.1",
		requiredOptionMarker = '*',
		header = "@|fg(blue),bold CoAP client toolkit|@",
		description = "@|fg(blue),italic user-friendly CoAP protocol client toolkit|@",
		optionListHeading = "%nOptions are:%n",
		mixinStandardHelpOptions = true,
		subcommands = {
				CoapGetCommand.class,
				CoapPostCommand.class,
				CoapPutCommand.class,
				CoapDeleteCommand.class,
				CoapMediaTypesCommand.class,
				CoapDescribeCommand.class
		},
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by James mu")
public class CoapCommand implements Callable<Integer> {


	@Override
	public Integer call() {
		return 0;
	}
}
