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
package iot.technology.client.toolkit.nb.command;

import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.nb.command.sub.NbCallCommand;
import iot.technology.client.toolkit.nb.command.sub.NbDescribeCommand;
import iot.technology.client.toolkit.nb.command.sub.NbSettingsCommand;
import picocli.CommandLine;

import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import static iot.technology.client.toolkit.common.utils.ColorUtils.colorItalic;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "nb",
		requiredOptionMarker = '*',
		header = "@|fg(Cyan),bold ${bundle:nb.header}|@",
		description = "@|fg(Cyan),italic ${bundle:nb.description}|@",
		optionListHeading = "%n${bundle:general.option}:%n",
		subcommands = {
				NbCallCommand.class,
				NbSettingsCommand.class,
				NbDescribeCommand.class
		},
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei")
public class NbCommand implements Callable<Integer> {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);
	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "${bundle:general.help.description}")
	boolean usageHelpRequested;

	@Override
	public Integer call() throws Exception {
		System.out.format("call:           " + colorItalic(bundle.getString("nb.call.desc"), "blue") + "%n");
		System.out.format("settings, set:  " + colorItalic(bundle.getString("nb.settings.desc"), "blue") + "%n");
		System.out.format("describe, desc: " + colorItalic(bundle.getString("nb.desc.desc"), "blue") + "%n");
		return ExitCodeEnum.SUCCESS.getValue();
	}


}
