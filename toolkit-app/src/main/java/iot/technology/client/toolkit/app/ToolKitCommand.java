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
package iot.technology.client.toolkit.app;

import iot.technology.client.toolkit.app.config.LogLevelConfig;
import iot.technology.client.toolkit.app.settings.ConfigCommand;
import iot.technology.client.toolkit.app.settings.lang.LangService;
import iot.technology.client.toolkit.coap.command.CoapCommand;
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.HelpVersionGroup;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.exception.ExceptionMessageHandler;
import iot.technology.client.toolkit.mqtt.command.MqttCommand;
import org.fusesource.jansi.AnsiConsole;
import picocli.AutoComplete;
import picocli.CommandLine;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "toolkit",
		header = "${bundle:general.header}",
		optionListHeading = "%n${bundle:general.option}:%n",
		requiredOptionMarker = '*',
		description = "@|fg(red),bold ${bundle:general.description}|@",
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei",
		subcommands = {
				AutoComplete.GenerateCompletion.class,
				ConfigCommand.class,
				CoapCommand.class,
				MqttCommand.class
		},
		exitCodeOnExecutionException = 400,
		exitCodeOnSuccess = 200,
		resourceBundle = "i18n.messages",
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class)
public class ToolKitCommand implements Callable<Integer> {

	@CommandLine.ArgGroup
	HelpVersionGroup helpVersionGroup;

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);


	public static void main(String[] args) {
		LogLevelConfig.setLogLevel();
		AnsiConsole.systemInstall();
		try {
			String locale = LangService.currentLocale();
			Locale.setDefault(new Locale(locale));
			ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);
			CommandLine commandLine = new CommandLine(new ToolKitCommand())
					.setResourceBundle(bundle)
					.setExecutionExceptionHandler(new ExceptionMessageHandler())
					.setAdjustLineBreaksForWideCJKCharacters(true)
					.setCaseInsensitiveEnumValuesAllowed(true);
			CommandLine generateCompletionCmd = commandLine.getSubcommands().get("generate-completion");
			generateCompletionCmd.getCommandSpec().usageMessage().hidden(true);

			int exitStatus = commandLine.execute(args);
			if (exitStatus == ExitCodeEnum.NOTEND.getValue()) {
				return;
			}
			System.exit(exitStatus);
		} finally {
			AnsiConsole.systemUninstall();
		}
	}

	public Integer call() {
		System.out.println(bundle.getString("general.description"));
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
