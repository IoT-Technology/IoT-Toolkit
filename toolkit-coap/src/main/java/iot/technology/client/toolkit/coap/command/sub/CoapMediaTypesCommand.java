package iot.technology.client.toolkit.coap.command.sub;

import iot.technology.client.toolkit.common.utils.table.DefaultTable;
import iot.technology.client.toolkit.common.utils.table.DefaultTableFormatter;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "media-types",
		version = "0.0.1",
		requiredOptionMarker = '*',
		description = "List supported MIME types",
		optionListHeading = "%nOptions are:%n",
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by James mu"
)
public class CoapMediaTypesCommand implements Callable<Integer> {

	@CommandLine.Option(
			names = {"-h", "--help"},
			versionHelp = false,
			description = "Show this help message and exit.")
	private boolean help;
	
	@Override
	public Integer call() throws Exception {
		DefaultTable dt = new DefaultTable();
		dt.setTitle("Coap Supported Media Types");
		dt.setHeaders(new String[] {"Type Id", "Type Name"});
		MediaTypeRegistry.getAllMediaTypes().stream()
				.forEach(i -> {
					String[] mediaTypeArray = getRow("" + i, MediaTypeRegistry.toString(i));
					dt.addRow(mediaTypeArray);
				});
		DefaultTableFormatter dtf = new DefaultTableFormatter(120, 2);
		System.out.println(dtf.format(dt));
		return 0;
	}

	private String[] getRow(String mediaTypeId, String mediaTypeName) {
		return new String[] {mediaTypeId, mediaTypeName};
	}
}
