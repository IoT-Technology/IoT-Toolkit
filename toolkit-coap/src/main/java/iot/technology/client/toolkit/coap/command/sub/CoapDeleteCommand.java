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
import picocli.CommandLine;

import java.net.URI;
import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "delete",
		aliases = "del",
		requiredOptionMarker = '*',
		description = "@|fg(red),bold ${bundle:coap.del.description}|@",
		synopsisHeading = "%n@|bold ${bundle:general.usage}|@%n",
		commandListHeading = "%n@|bold ${bundle:general.commands}|@%n",
		optionListHeading = "%n@|bold ${bundle:general.option}|@%n",
		descriptionHeading = "%n",
		footerHeading = "%nCopyright (c) 2019-2023, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei"
)
public class CoapDeleteCommand implements Callable<Integer> {

	private final CoapClientService coapClientService = new CoapClientService();

	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "${bundle:general.help.description}")
	boolean usageHelpRequested;

	@CommandLine.Parameters(
			index = "0",
			description = "${bundle:coap.uri.description}")
	private URI uri;

	@Override
	public Integer call() throws Exception {
		CoapCommandParamValidator.validateUri(uri);

		CoapClient coapClient = coapClientService.getCoapClient(uri);
		CoapResponse response = coapClient.delete();

		StringBuffer result = new StringBuffer();
		String requestInfo = coapClientService.requestInfo("delete", uri.toString());
		String responseStr = coapClientService.prettyPrint(response, requestInfo);
		result.append(responseStr);
		System.out.println(result);
		return ExitCodeEnum.SUCCESS.getValue();
	}
	
}
