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

import iot.technology.client.toolkit.app.config.LogLevelConfig;
import iot.technology.client.toolkit.app.settings.ConfigCommand;
import iot.technology.client.toolkit.app.settings.lang.LangService;
import iot.technology.client.toolkit.coap.command.CoapCommand;
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.HelpVersionGroup;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.exception.ExceptionMessageHandler;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
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
		StringBuilder sb = new StringBuilder();
		sb.append(bundle.getString("general.description")).append(StringUtils.lineSeparator());
		sb.append(String.format("%s %s", bundle.getString("general.usage"), "toolkit [--version] [--help] <command> [<args>]"))
				.append(StringUtils.lineSeparator());
		sb.append(StringUtils.lineSeparator());

		sb.append(bundle.getString("general.common.commands.desc")).append(StringUtils.lineSeparator());
		sb.append(StringUtils.lineSeparator());

		// toolkit config simple description
		sb.append(String.format("%s %s", bundle.getString("config.description"),
						"(" + bundle.getString("general.reference") + ColorUtils.colorBold("toolkit config -h", "red") + ")"))
				.append(StringUtils.lineSeparator());
		sb.append(String.format("%s %s", "locale        ",
						bundle.getString("config.lang.header") + " " + bundle.getString("config.locale")))
				.append(StringUtils.lineSeparator());
		sb.append(StringUtils.lineSeparator());

		// toolkit coap simple description
		sb.append(String.format("%s %s", bundle.getString("coap.description"),
						"(" + bundle.getString("general.reference") + ColorUtils.colorBold("toolkit coap -h", "red") + ")"))
				.append(StringUtils.lineSeparator());
		sb.append(String.format("%s %s", "disc:          ", bundle.getString("coap.disc.description")))
				.append(StringUtils.lineSeparator());
		sb.append(String.format("%s %s", "get:           ", bundle.getString("coap.get.description")))
				.append(StringUtils.lineSeparator());
		sb.append(String.format("%s %s", "post:          ", bundle.getString("coap.post.description")))
				.append(StringUtils.lineSeparator());
		sb.append(String.format("%s %s", "put            ", bundle.getString("coap.put.description")))
				.append(StringUtils.lineSeparator());
		sb.append(String.format("%s %s", "delete:        ", bundle.getString("coap.del.description"))
				.equals(StringUtils.lineSeparator()));
		sb.append(StringUtils.lineSeparator());

		// toolkit mqtt simple description
		sb.append(String.format("%s %s", bundle.getString("mqtt.description"),
						"(" + bundle.getString("general.reference") + ColorUtils.colorBold("toolkit mqtt -h", "red") + ")"))
				.append(StringUtils.lineSeparator());
		sb.append(String.format("%s %s", "publish:       ", bundle.getString("mqtt.pub.description")))
				.append(StringUtils.lineSeparator());
		sb.append(String.format("%s %s", "subscribe:     ", bundle.getString("mqtt.sub.description")))
				.append(StringUtils.lineSeparator());
		sb.append(StringUtils.lineSeparator());

		// toolkit nb simple description
		sb.append(String.format("%s %s", bundle.getString("nb.description"),
						"(" + bundle.getString("general.reference") + ColorUtils.colorBold("toolkit nb -h", "red") + ")"))
				.append(StringUtils.lineSeparator());
		sb.append(String.format("%s %s", "call:          ", bundle.getString("nb.call.desc")))
				.append(StringUtils.lineSeparator());
		sb.append(String.format("%s %s", "set:           ", bundle.getString("nb.desc.desc")))
				.append(StringUtils.lineSeparator());
		sb.append(StringUtils.lineSeparator());

		sb.append(bundle.getString("general.main.page.help"));
		System.out.println(sb);
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
