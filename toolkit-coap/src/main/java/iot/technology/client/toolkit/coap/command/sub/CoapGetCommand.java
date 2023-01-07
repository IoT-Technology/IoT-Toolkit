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
		description = "${bundle:coap.get.description}",
		optionListHeading = "%n${bundle:general.option}:%n",
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei"
)
public class CoapGetCommand implements Callable<Integer> {

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
