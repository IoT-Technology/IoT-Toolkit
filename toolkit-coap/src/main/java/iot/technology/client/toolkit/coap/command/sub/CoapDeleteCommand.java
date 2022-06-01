package iot.technology.client.toolkit.coap.command.sub;

import iot.technology.client.toolkit.coap.service.CoapClientService;
import iot.technology.client.toolkit.coap.service.CoapFactory;
import iot.technology.client.toolkit.coap.validator.CoapCommandParamValidator;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import picocli.CommandLine;

import java.net.URI;
import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "delete",
		version = "0.0.1",
		requiredOptionMarker = '*',
		description = "Delete CoAP Resource",
		optionListHeading = "%nOptions are:%n",
		mixinStandardHelpOptions = true,
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by James mu"
)
public class CoapDeleteCommand implements Callable<Integer> {

	private CoapClientService coapClientService;

	public CoapDeleteCommand() {
		coapClientService = CoapFactory.getService();
	}

	@CommandLine.Option(
			names = {"-h", "--help"},
			versionHelp = false,
			description = "Show this help message and exit.")
	private boolean help;

	@CommandLine.Parameters(
			index = "0",
			description = "URI of the server to connect to")
	private URI uri;

	@Override
	public Integer call() throws Exception {
		CoapCommandParamValidator.validateUri(uri);

		CoapClient coapClient = coapClientService.getCoapClient(uri);
		CoapResponse response = coapClient.delete();

		StringBuffer result = new StringBuffer();
		String requestInfo = coapClientService.requestInfo("delete", uri.toString());
		String responseStr = coapClientService.prettyPrint(response, requestInfo);
		result.append(responseStr);
		System.out.println(result);
		return 0;
	}
}
