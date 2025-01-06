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
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.utils.StringUtils;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.CoAP;
import picocli.CommandLine;

import java.net.URI;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "discover",
		aliases = "disc",
		requiredOptionMarker = '*',
		description = "@|fg(red),bold ${bundle:coap.disc.description}|@",
		synopsisHeading = "%n@|bold ${bundle:general.usage}|@%n",
		commandListHeading = "%n@|bold ${bundle:general.commands}|@%n",
		optionListHeading = "%n@|bold ${bundle:general.option}|@%n",
		descriptionHeading = "%n",
		footerHeading = "%nCopyright (c) 2019-2025, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei"
)
public class CoapDiscoverCommand extends AbstractCoapContext implements Callable<Integer> {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	private final CoapClientService coapClientService = new CoapClientService();

	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "${bundle:general.help.description}")
	boolean usageHelpRequested;

	@CommandLine.Parameters(
			index = "0",
			description = "${bundle:coap.uri.description}")
	private URI uri;

	/* ********************************** Identity Section ******************************** */
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


	public Integer call() throws Exception {
		String scheme = validateUri(uri);
		String protocol = CoAP.getProtocolForScheme(scheme);
		CoapClient coapClient = CoapClientService.getCoapClient(uri, protocol, psk.identity, psk.sharekey);

		Set<WebLink> webLinks = coapClient.discover();
		String availableResources = coapClientService.getAvailableResources(webLinks);
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("==================== %s ====================", bundle.getString("coap.available.resources")))
				.append(StringUtils.lineSeparator());
		sb.append(availableResources).append(StringUtils.lineSeparator());
		System.out.println(sb);
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
