package iot.technology.client.toolkit.coap.command.sub;

import iot.technology.client.toolkit.coap.service.CoapClientService;
import iot.technology.client.toolkit.coap.service.CoapFactory;
import iot.technology.client.toolkit.coap.validator.CoapCommandParamValidator;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.WebLink;
import picocli.CommandLine;

import java.net.URI;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "discover",
		version = "0.0.1",
		requiredOptionMarker = '*',
		description = "list available resources",
		optionListHeading = "%nOptions are:%n",
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by James mu"
)
public class CoapDiscoverCommand implements Callable<Integer> {

	private CoapClientService coapClientService;

	public CoapDiscoverCommand() {
		coapClientService = CoapFactory.getService();
	}

	@CommandLine.Parameters(
			index = "0",
			description = "URI of the server to connect to")
	private URI uri;


	public Integer call() throws Exception {
		CoapCommandParamValidator.validateUri(uri);

		CoapClient coapClient = coapClientService.getCoapClient(uri);

		Set<WebLink> webLinks = coapClient.discover();
		String availableResources = coapClientService.getAvailableResources(webLinks);
		System.out.println(availableResources);
		return 0;
	}
}
