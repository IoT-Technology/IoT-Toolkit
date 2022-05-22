package iot.technology.client.toolkit.coap.command.sub;

import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "get",
		version = "0.0.1",
		requiredOptionMarker = '*',
		description = "performs a GET request",
		optionListHeading = "%nOptions are:%n",
		mixinStandardHelpOptions = true,
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by James mu"
)
public class CoapGetCommand implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		return null;
	}
}
