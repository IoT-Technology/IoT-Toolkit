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
import iot.technology.client.toolkit.coap.command.CoapCommand;
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.exception.ExceptionMessageHandler;
import iot.technology.client.toolkit.common.settings.ConfigCommand;
import iot.technology.client.toolkit.mqtt.command.MqttCommand;
import org.fusesource.jansi.AnsiConsole;
import picocli.AutoComplete;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "toolkit",
		header = "IoT Client Toolkit CLI",
		optionListHeading = "%nOptions are:%n",
		requiredOptionMarker = '*',
		description = "A handy @|fg(red),bold toolkit|@ for IoT developers and learners.",
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by mushuwei",
		mixinStandardHelpOptions = true,
		subcommands = {
				AutoComplete.GenerateCompletion.class,
				ConfigCommand.class,
				CoapCommand.class,
				MqttCommand.class
		},
		exitCodeOnExecutionException = 400,
		exitCodeOnSuccess = 200,
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class)
public class ToolKitCommand implements Callable<Integer> {


	public static void main(String[] args) {
		LogLevelConfig.setLogLevel();
		AnsiConsole.systemInstall();
		try {
			CommandLine commandLine = new CommandLine(new ToolKitCommand());
			commandLine.setExecutionExceptionHandler(new ExceptionMessageHandler())
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
		System.out.println("A handy toolkit for IoT developers and learners.");
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
