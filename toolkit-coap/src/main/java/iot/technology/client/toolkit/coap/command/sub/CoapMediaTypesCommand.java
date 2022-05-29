package iot.technology.client.toolkit.coap.command.sub;

import iot.technology.client.toolkit.coap.validator.CoapCommandParamValidator;
import iot.technology.client.toolkit.common.utils.TableConsoleUtil;
import iot.technology.client.toolkit.common.utils.table.Row;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import picocli.CommandLine;

import java.net.URI;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

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

	@CommandLine.Parameters(
			index = "0",
			description = "URI of the server to connect to")
	private URI uri;

	@Override
	public Integer call() throws Exception {
		CoapCommandParamValidator.validateUri(uri);

		List<Row> list = MediaTypeRegistry.getAllMediaTypes().stream()
				.map(i -> {
					Row row = new Row();
					row.getColumn().add("" + i);
					row.getColumn().add(MediaTypeRegistry.toString(i));
					return row;
				}).collect(Collectors.toList());
		TableConsoleUtil.printCoapMediaTypes(list);
		return 0;
	}
}
