/*
 * Copyright © 2019-2022 The Toolkit Authors
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
import iot.technology.client.toolkit.coap.service.CoapFactory;
import iot.technology.client.toolkit.coap.validator.CoapCommandParamValidator;
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.WebLink;
import picocli.CommandLine;

import java.net.URI;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.Callable;

import static iot.technology.client.toolkit.coap.service.impl.CoapClientServiceImpl.green;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "discover",
		aliases = "disc",
		requiredOptionMarker = '*',
		description = "${bundle:coap.disc.description}",
		optionListHeading = "%n${bundle:general.option}:%n",
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei",
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class
)
public class CoapDiscoverCommand implements Callable<Integer> {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	private CoapClientService coapClientService;

	public CoapDiscoverCommand() {
		coapClientService = CoapFactory.getService();
	}

	@CommandLine.Parameters(
			index = "0",
			description = "${bundle:coap.uri.description}")
	private URI uri;


	public Integer call() throws Exception {
		CoapCommandParamValidator.validateUri(uri);

		CoapClient coapClient = coapClientService.getCoapClient(uri);

		Set<WebLink> webLinks = coapClient.discover();
		String availableResources = coapClientService.getAvailableResources(webLinks);
		String header = String.format("==================== %s ====================", bundle.getString("coap.available.resources"));
		System.out.format(green(header) + "%n");
		System.out.println(availableResources);
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
