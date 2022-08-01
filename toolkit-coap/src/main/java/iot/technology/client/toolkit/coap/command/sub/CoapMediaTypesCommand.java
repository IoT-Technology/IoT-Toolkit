package iot.technology.client.toolkit.coap.command.sub;

import iot.technology.client.toolkit.coap.service.CoapClientService;
import iot.technology.client.toolkit.coap.service.CoapFactory;
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import picocli.CommandLine;

import java.util.concurrent.Callable;

import static iot.technology.client.toolkit.coap.service.impl.CoapClientServiceImpl.green;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "media-types",
		aliases = "mt",
		requiredOptionMarker = '*',
		description = "List supported MIME types",
		optionListHeading = "%nOptions are:%n",
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by mushuwei",
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class
)
public class CoapMediaTypesCommand implements Callable<Integer> {

	private CoapClientService coapClientService;

	public CoapMediaTypesCommand() {
		coapClientService = CoapFactory.getService();
	}

	@CommandLine.Option(
			names = {"-h", "--help"},
			versionHelp = false,
			description = "Show this help message and exit.")
	private boolean help;

	@Override
	public Integer call() throws Exception {
		MediaTypeRegistry mediaTypeRegistry = new MediaTypeRegistry();
		String supportedMediaTypes = coapClientService.getSupportedMediaTypes(mediaTypeRegistry);
		System.out.format(green("==================== Coap Supported Media Types ====================") + "%n");
		System.out.println(supportedMediaTypes);
		return ExitCodeEnum.SUCCESS.getValue();
	}

}
