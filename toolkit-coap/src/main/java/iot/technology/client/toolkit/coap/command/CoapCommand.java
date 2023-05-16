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
package iot.technology.client.toolkit.coap.command;

import iot.technology.client.toolkit.coap.command.sub.*;
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.utils.StringUtils;
import picocli.CommandLine;

import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import static iot.technology.client.toolkit.common.utils.ColorUtils.colorItalic;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "coap",
		requiredOptionMarker = '*',
		header = "@|bold ${bundle:coap.header}|@",
		description = "@|fg(red),bold ${bundle:coap.description}|@",
		synopsisHeading = "%n@|bold ${bundle:general.usage}|@%n",
		commandListHeading = "%n@|bold ${bundle:general.commands}|@%n",
		optionListHeading = "%n@|bold ${bundle:general.option}|@%n",
		descriptionHeading = "%n",
		subcommands = {
				CoapDescribeCommand.class,
				CoapMediaTypesCommand.class,
				CoapDiscoverCommand.class,
				CoapGetCommand.class,
				CoapPostCommand.class,
				CoapPutCommand.class,
				CoapDeleteCommand.class,
		},
		footerHeading = "%nCopyright (c) 2019-2023, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei")
public class CoapCommand implements Callable<Integer> {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "${bundle:general.help.description}")
	boolean usageHelpRequested;

	@CommandLine.Spec
	private CommandLine.Model.CommandSpec spec;

	@Override
	public Integer call() {
		System.out.println(spec.commandLine().getUsageMessage());
		return ExitCodeEnum.SUCCESS.getValue();
	}

}
