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
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import picocli.CommandLine;

import java.util.ResourceBundle;
import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "media-types",
		aliases = "mt",
		requiredOptionMarker = '*',
		description = "@|fg(red),bold ${bundle:coap.media.types.description}|@",
		synopsisHeading = "%n@|bold ${bundle:general.usage}|@%n",
		commandListHeading = "%n@|bold ${bundle:general.commands}|@%n",
		optionListHeading = "%n@|bold ${bundle:general.option}|@%n",
		descriptionHeading = "%n",
		footerHeading = "%nCopyright (c) 2019-2023, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei"
)
public class CoapMediaTypesCommand implements Callable<Integer> {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	private final CoapClientService coapClientService = new CoapClientService();

	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "${bundle:general.help.description}")
	boolean usageHelpRequested;

	@Override
	public Integer call() throws Exception {

		String supportedMediaTypes = coapClientService.getSupportedMediaTypes();
		StringBuilder sb = new StringBuilder();
		String header = String.format("==================== %s ====================", bundle.getString("coap.media.types"));
		sb.append(ColorUtils.colorBold(header, "green")).append(StringUtils.lineSeparator());
		sb.append(supportedMediaTypes).append(StringUtils.lineSeparator());
		System.out.println(sb);
		return ExitCodeEnum.SUCCESS.getValue();
	}

}
