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
import iot.technology.client.toolkit.common.utils.ColorUtils;
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
		aliases = "iotkit",
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
				MqttCommand.class,
				NbCommand.class
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

			CommandLine nbCmd = commandLine.getSubcommands().get("nb");
			boolean isChinese = bundle.getLocale().equals(Locale.CHINESE);
			nbCmd.getCommandSpec().usageMessage().hidden(!isChinese);

			int exitStatus = commandLine.execute("nb", "call");
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
		System.out.format("%s %s" + "%n",
				bundle.getString("general.usage"),
				"toolkit [--version] [--help] <command> [<args>]");
		System.out.format("" + "%n");
		System.out.format("%s" + "%n", bundle.getString("general.common.commands.desc"));
		System.out.format("" + "%n");
		System.out.format("%s %s" + "%n",
				bundle.getString("config.description"),
				"(" + bundle.getString("general.reference") + ColorUtils.redBold("toolkit config -h", "red") + ")");
		System.out.format("%s %s" + "%n", "locale        ",
				bundle.getString("config.lang.header") + " " + bundle.getString("config.locale"));

		System.out.format("" + "%n");
		System.out.format("%s %s" + "%n",
				bundle.getString("coap.description"),
				"(" + bundle.getString("general.reference") + ColorUtils.redBold("toolkit coap -h", "red") + ")");
		System.out.format("%s %s" + "%n",
				"disc          ", bundle.getString("coap.disc.description"));
		System.out.format("%s %s" + "%n",
				"get           ", bundle.getString("coap.get.description"));
		System.out.format("%s %s" + "%n",
				"post          ", bundle.getString("coap.post.description"));
		System.out.format("%s %s" + "%n",
				"put           ", bundle.getString("coap.put.description"));
		System.out.format("%s %s" + "%n",
				"delete        ", bundle.getString("coap.del.description"));

		System.out.format("" + "%n");
		System.out.format("%s %s" + "%n", bundle.getString("mqtt.description"),
				"(" + bundle.getString("general.reference") + ColorUtils.redBold("toolkit mqtt -h", "red") + ")");
		System.out.format("%s %s" + "%n",
				"publish       ", bundle.getString("mqtt.pub.description"));
		System.out.format("%s %s" + "%n",
				"subscribe     ", bundle.getString("mqtt.sub.description"));

		System.out.format("" + "%n");
		System.out.format("%s %s" + "%n", bundle.getString("nb.description"),
				"(" + bundle.getString("general.reference") + ColorUtils.redBold("toolkit nb -h", "red") + ")");
		System.out.format("%s %s" + "%n",
				"call          ", bundle.getString("nb.call.desc"));
		System.out.format("%s %s" + "%n",
				"set           ", bundle.getString("nb.desc.desc"));

		System.out.format("" + "%n");
		System.out.println(bundle.getString("general.main.page.help"));
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
