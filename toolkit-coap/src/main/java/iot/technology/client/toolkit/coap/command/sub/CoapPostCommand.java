package iot.technology.client.toolkit.coap.command.sub;

import iot.technology.client.toolkit.coap.service.CoapClientService;
import iot.technology.client.toolkit.coap.service.CoapFactory;
import iot.technology.client.toolkit.coap.validator.CoapCommandParamValidator;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import picocli.CommandLine;

import java.net.URI;
import java.util.concurrent.Callable;

import static iot.technology.client.toolkit.coap.command.sub.CoapGetCommand.COAP_TEXT_PLAIN;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "post",
		version = "0.0.1",
		requiredOptionMarker = '*',
		description = "Create/Update data in CoAP Resource",
		optionListHeading = "%nOptions are:%n",
		mixinStandardHelpOptions = true,
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by James mu"
)
public class CoapPostCommand implements Callable<Integer> {

	private CoapClientService coapClientService;

	public CoapPostCommand() {
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
			names = {"-p", "--payload"},
			required = false,
			description = "POST message payload")
	private String payload;

	@CommandLine.Option(
			names = {"-f", "--format"},
			required = false,
			description = "payload content-type",
			defaultValue = COAP_TEXT_PLAIN)
	private String format;

	@CommandLine.Option(
			names = {"-a", "--accept"},
			required = false,
			description = "accepted response content-type",
			defaultValue = COAP_TEXT_PLAIN)
	private String accept;

	@Override
	public Integer call() throws Exception {
		CoapCommandParamValidator.validateUri(uri);
		String payloadContent = CoapCommandParamValidator.validatePayloadAndFile(payload);

		CoapClient coapClient = coapClientService.getCoapClient(uri);
		int formatCode = coapClientService.coapContentType(format);
		int acceptCode = coapClientService.coapContentType(accept);

		StringBuffer result = new StringBuffer();
		CoapResponse response = coapClient.post(payloadContent, formatCode, acceptCode);
		String requestInfo = coapClientService.requestInfo("post", uri.toString());
		String responseStr = coapClientService.prettyPrint(response, requestInfo);
		result.append(responseStr);
		System.out.println(result);
		return 0;
	}
}
