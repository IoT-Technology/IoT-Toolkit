package iot.technology.client.toolkit.coap.command.sub;

import iot.technology.client.toolkit.coap.service.CoapClientService;
import iot.technology.client.toolkit.coap.service.CoapFactory;
import iot.technology.client.toolkit.coap.validator.CoapCommandParamValidator;
import iot.technology.client.toolkit.common.utils.SysLog;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import picocli.CommandLine;

import java.net.URI;
import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "get",
		version = "0.0.1",
		requiredOptionMarker = '*',
		description = "Request data from CoAP Resource",
		optionListHeading = "%nOptions are:%n",
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by James mu"
)
public class CoapGetCommand implements Callable<Integer> {

	public static final String COAP_TEXT_PLAIN = "" + MediaTypeRegistry.TEXT_PLAIN;

	private CoapClientService coapClientService;

	public CoapGetCommand() {
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

	@CommandLine.Option(
			names = {"-a", "--accept"},
			required = false,
			description = "accepted response content-type",
			defaultValue = COAP_TEXT_PLAIN)
	private String accept;


	@Override
	public Integer call() throws Exception {
		CoapCommandParamValidator.validateUri(uri);
		
		CoapClient coapClient = coapClientService.getCoapClient(uri);
		int accept = coapClientService.coapContentType(this.accept);
		CoapResponse response = coapClient.get(accept);
		SysLog.info("Response: " + response.getResponseText());
		return 0;
	}
}
