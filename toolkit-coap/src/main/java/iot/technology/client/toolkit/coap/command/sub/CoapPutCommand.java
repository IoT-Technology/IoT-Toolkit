/*
 * Copyright © 2019-2023 The Toolkit Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package iot.technology.client.toolkit.coap.command.sub;

import iot.technology.client.toolkit.coap.service.CoapClientService;
import iot.technology.client.toolkit.coap.validator.CoapCommandParamValidator;
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import org.eclipse.californium.core.coap.CoAP;
import picocli.CommandLine;

import java.net.URI;
import java.util.concurrent.Callable;

import static iot.technology.client.toolkit.coap.command.sub.CoapGetCommand.COAP_TEXT_PLAIN;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "put",
		requiredOptionMarker = '*',
		description = "@|fg(red),bold ${bundle:coap.put.description}|@",
		synopsisHeading = "%n@|bold ${bundle:general.usage}|@%n",
		commandListHeading = "%n@|bold ${bundle:general.commands}|@%n",
		optionListHeading = "%n@|bold ${bundle:general.option}|@%n",
		descriptionHeading = "%n",
		footerHeading = "%nCopyright (c) 2019-2023, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei"
)
public class CoapPutCommand extends AbstractCoapContext implements Callable<Integer> {

	private final CoapClientService coapClientService = new CoapClientService();

	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "${bundle:general.help.description}")
	boolean usageHelpRequested;

	@CommandLine.Parameters(
			index = "0",
			description = "${bundle:coap.uri.description}")
	private URI uri;

	@CommandLine.Option(
			names = {"-p", "--payload"},
			required = false,
			description = "${bundle:coap.request.payload.description}")
	private String payload;


	@CommandLine.Option(
			names = {"-f", "--format"},
			required = false,
			description = "${bundle:coap.request.format.description}",
			defaultValue = COAP_TEXT_PLAIN)
	private String format;

	/* ********************************** Identity Section ******************************** */
	@CommandLine.ArgGroup(exclusive = false,
			heading = "%n@|bold,underline PSK Options|@ %n%n"//
					+ "@|italic " //
					+ "By default use non secure connection.%n"//
					+ "To use CoAP over DTLS with Pre-Shared Key, -i and -p options should be used together." //
					+ "|@%n%n")
	private PskSection psk = new PskSection();

	public static class PskSection {
		@CommandLine.Option(required = true,
				names = { "-i", "--psk-identity" },
				description = { //
						"Set the server PSK identity in ascii." })
		public String identity;

		@CommandLine.Option(required = true,
				names = { "-pk", "--psk-key" },
				description = { //
						"Set the server Pre-Shared-Key" })
		public String sharekey;
	}


	@Override
	public Integer call() throws Exception {
		var scheme = validateUri(uri);
		var protocol = CoAP.getProtocolForScheme(scheme);
		var payloadContent = CoapCommandParamValidator.validatePayloadAndFile(payload);
		var response = coapClientService.putPayload(uri, protocol, psk.identity, psk.sharekey, payloadContent, format);

		String requestInfo = coapClientService.requestInfo("put", uri.toString());
		String responseStr = coapClientService.prettyPrint(response, requestInfo);
		System.out.println(responseStr);
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
