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
		description = "CoAP protocol description",
		optionListHeading = "%nOptions are:%n",
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by James mu"
)
public class CoapDescribeCommand implements Callable<Integer> {

	@CommandLine.Option(
			names = {"-h", "--help"},
			versionHelp = false,
			description = "Show this help message and exit.")
	private boolean help;

	@Override
	public Integer call() throws Exception {
		return 0;
	}
}
