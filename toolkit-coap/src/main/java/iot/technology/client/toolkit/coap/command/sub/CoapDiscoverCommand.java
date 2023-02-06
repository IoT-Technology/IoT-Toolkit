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
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.utils.StringUtils;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.WebLink;
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
		description = "${bundle:coap.disc.description}",
		optionListHeading = "%n${bundle:general.option}:%n",
		footerHeading = "%nCopyright (c) 2019-2023, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei"
)
public class CoapDiscoverCommand implements Callable<Integer> {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	private final CoapClientService coapClientService = new CoapClientService();

	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "${bundle:general.help.description}")
	boolean usageHelpRequested;

	@CommandLine.Parameters(
			index = "0",
			description = "${bundle:coap.uri.description}")
	private URI uri;


	public Integer call() throws Exception {
		CoapCommandParamValidator.validateUri(uri);

		CoapClient coapClient = coapClientService.getCoapClient(uri);

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
