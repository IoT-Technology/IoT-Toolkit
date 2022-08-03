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
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
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
		description = "Update data in CoAP Resource",
		optionListHeading = "%nOptions are:%n",
		mixinStandardHelpOptions = true,
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by mushuwei",
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class
)
public class CoapPutCommand implements Callable<Integer> {

	private CoapClientService coapClientService;

	public CoapPutCommand() {
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
			names = {"-p", "--payload"},
			required = false,
			description = "PUT message payload")
	private String payload;


	@CommandLine.Option(
			names = {"-f", "--format"},
			required = false,
			description = "payload content-type",
			defaultValue = COAP_TEXT_PLAIN)
	private String format;

	@Override
	public Integer call() throws Exception {
		CoapCommandParamValidator.validateUri(uri);
		String payloadContent = CoapCommandParamValidator.validatePayloadAndFile(payload);

		CoapClient coapClient = coapClientService.getCoapClient(uri);
		CoapResponse response = coapClient.put(payloadContent, coapClientService.coapContentType(format));

		String requestInfo = coapClientService.requestInfo("put", uri.toString());
		String responseStr = coapClientService.prettyPrint(response, requestInfo);
		System.out.println(responseStr);
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
