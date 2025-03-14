/*
 * Copyright © 2019-2025 The Toolkit Authors
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
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Response;
import picocli.CommandLine;

import java.net.URI;
import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "get",
		requiredOptionMarker = '*',
		description = "@|fg(red),bold ${bundle:coap.get.description}|@",
		synopsisHeading = "%n@|bold ${bundle:general.usage}|@%n",
		commandListHeading = "%n@|bold ${bundle:general.commands}|@%n",
		optionListHeading = "%n@|bold ${bundle:general.option}|@%n",
		descriptionHeading = "%n",
		footerHeading = "%nCopyright (c) 2019-2025, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei"
)
public class CoapGetCommand extends AbstractCoapContext implements Callable<Integer> {

	public static final String COAP_TEXT_PLAIN = "" + MediaTypeRegistry.TEXT_PLAIN;

	private final CoapClientService coapClientService = new CoapClientService();

	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "${bundle:general.help.description}")
	boolean usageHelpRequested;

	@CommandLine.Parameters(
			index = "0",
			description = "${bundle:coap.uri.description}")
	private URI uri;

	@CommandLine.Option(
			names = {"-a", "--accept"},
			required = false,
			description = "${bundle:coap.accept.description}",
			defaultValue = COAP_TEXT_PLAIN)
	private String accept;

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
						"${coap.psk.identity.desc}" })
		public String identity;

		@CommandLine.Option(required = true,
				names = { "-p", "--psk-key" },
				description = { //
						"${coap.psk.sharekey.desc}" })
		public String sharekey;
	}


	@Override
	public Integer call() throws Exception {
		String scheme = validateUri(uri);
		String protocol = CoAP.getProtocolForScheme(scheme);

		Response response = coapClientService.getResponse(uri, protocol, psk.identity, psk.sharekey, accept);

		StringBuffer result = new StringBuffer();
		String requestInfo = coapClientService.requestInfo("get", uri.toString());
		String responseStr = coapClientService.prettyPrint(response, requestInfo);
		result.append(responseStr);
		System.out.println(result);
		return ExitCodeEnum.SUCCESS.getValue();
	}
	
}
