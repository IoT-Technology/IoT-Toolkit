package iot.technology.client.toolkit.coap.command.sub;

import iot.technology.client.toolkit.coap.service.CoapClientService;
import iot.technology.client.toolkit.coap.service.CoapFactory;
import iot.technology.client.toolkit.coap.validator.CoapCommandParamValidator;
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
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
		requiredOptionMarker = '*',
		description = "Request data from CoAP Resource",
		optionListHeading = "%nOptions are:%n",
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by mushuwei",
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class
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

		StringBuffer result = new StringBuffer();
		String requestInfo = coapClientService.requestInfo("get", uri.toString());
		String responseStr = coapClientService.prettyPrint(response, requestInfo);
		result.append(responseStr);
		System.out.println(result);
		return ExitCodeEnum.SUCCESS.getValue();
	}
	
}
