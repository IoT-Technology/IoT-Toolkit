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
package iot.technology.client.toolkit.app;

import iot.technology.client.toolkit.app.config.CommandLineConfig;
import iot.technology.client.toolkit.app.config.LogLevelConfig;
import iot.technology.client.toolkit.app.settings.ConfigCommand;
import iot.technology.client.toolkit.app.settings.info.MainInfo;
import iot.technology.client.toolkit.app.settings.lang.LangService;
import iot.technology.client.toolkit.coap.command.CoapCommand;
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.HelpVersionGroup;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.constants.VersionInfo;
import iot.technology.client.toolkit.common.exception.ExceptionMessageHandler;
import iot.technology.client.toolkit.mqtt.command.MqttCommand;
import iot.technology.client.toolkit.nb.command.NbCommand;
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
		header = "@|bold ${bundle:general.header}|@",
		description = "@|fg(red),bold ${bundle:general.description}|@%n",
		synopsisHeading = "%n@|bold ${bundle:general.usage}|@%n",
		optionListHeading = "%n@|bold ${bundle:general.option}|@%n",
		commandListHeading = "%n@|bold ${bundle:general.commands}|@%n",
		synopsisSubcommandLabel = "{ config | mqtt | coap | nb }",
		descriptionHeading = "%n",
		requiredOptionMarker = '*',
		footerHeading = "%nCopyright (c) 2019-2023, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei",
		subcommands = {
				AutoComplete.GenerateCompletion.class,
				ConfigCommand.class,
				CoapCommand.class,
				MqttCommand.class,
				NbCommand.class
		},
		exitCodeOnExecutionException = 400,
		exitCodeOnSuccess = 200,
		resourceBundle = "i18n.messages",
		versionProvider = VersionInfo.class)
public class ToolKitCommand implements Callable<Integer> {

	@CommandLine.ArgGroup
	HelpVersionGroup helpVersionGroup;


	public static void main(String[] args) {
		LogLevelConfig.setLogLevel();
		AnsiConsole.systemInstall();
		try {
			String locale = LangService.currentLocale();
			Locale.setDefault(new Locale(locale));
			ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);
			CommandLine commandLine = new CommandLine(new ToolKitCommand())
					.setResourceBundle(bundle)
					.setColorScheme(CommandLineConfig.getColorScheme())
					.setUsageHelpWidth(CommandLineConfig.getCliWidth())
					.setExecutionExceptionHandler(new ExceptionMessageHandler())
					.setAdjustLineBreaksForWideCJKCharacters(true)
					.setCaseInsensitiveEnumValuesAllowed(true);
			CommandLine generateCompletionCmd = commandLine.getSubcommands().get("generate-completion");
			generateCompletionCmd.getCommandSpec().usageMessage().hidden(true);

			CommandLine nbCmd = commandLine.getSubcommands().get("nb");
			boolean isChinese = bundle.getLocale().equals(Locale.CHINESE);
			nbCmd.getCommandSpec().usageMessage().hidden(!isChinese);

			if (args.length == 0) {
				MainInfo.printMainInfo();
			}
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
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
