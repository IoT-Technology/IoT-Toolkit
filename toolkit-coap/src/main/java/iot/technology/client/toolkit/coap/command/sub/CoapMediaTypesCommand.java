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
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.HelpVersionGroup;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import picocli.CommandLine;

import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import static iot.technology.client.toolkit.coap.service.impl.CoapClientServiceImpl.green;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "media-types",
		aliases = "mt",
		requiredOptionMarker = '*',
		description = "${bundle:coap.media.types.description}",
		optionListHeading = "%n${bundle:general.option}:%n",
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei",
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class
)
public class CoapMediaTypesCommand implements Callable<Integer> {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	private CoapClientService coapClientService;

	public CoapMediaTypesCommand() {
		coapClientService = CoapFactory.getService();
	}

	@CommandLine.ArgGroup
	HelpVersionGroup helpVersionGroup;

	@Override
	public Integer call() throws Exception {
		MediaTypeRegistry mediaTypeRegistry = new MediaTypeRegistry();
		String supportedMediaTypes = coapClientService.getSupportedMediaTypes(mediaTypeRegistry);
		String header = String.format("==================== %s ====================", bundle.getString("coap.media.types"));
		System.out.format(green(header) + "%n");
		System.out.println(supportedMediaTypes);
		return ExitCodeEnum.SUCCESS.getValue();
	}

}
