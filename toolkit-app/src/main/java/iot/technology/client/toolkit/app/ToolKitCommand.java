package iot.technology.client.toolkit.app;

import iot.technology.client.toolkit.app.config.LogLevelConfig;
import iot.technology.client.toolkit.coap.command.CoapCommand;
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.exception.ExceptionMessageHandler;
import iot.technology.client.toolkit.common.utils.OsUtils;
import iot.technology.client.toolkit.mqtt.command.MqttCommand;
import org.fusesource.jansi.AnsiConsole;
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
				CoapCommand.class,
				MqttCommand.class
		},
		exitCodeOnExecutionException = 400,
		exitCodeOnSuccess = 200,
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class)
public class ToolKitCommand implements Callable<Integer> {


	public static void main(String[] args) {
		LogLevelConfig.setLogLevel();
		boolean isWin = OsUtils.isWindows();
		if (isWin) {
			AnsiConsole.systemInstall();
		}
		int exitStatus = new CommandLine(new ToolKitCommand())
				.setExecutionExceptionHandler(new ExceptionMessageHandler())
				.setCaseInsensitiveEnumValuesAllowed(true)
				.execute(args);
		if (exitStatus == ExitCodeEnum.NOTEND.getValue()) {
			return;
		}
		System.exit(exitStatus);
		if (isWin) {
			AnsiConsole.systemUninstall();
		}
	}

	public Integer call() {
		System.out.println("A handy toolkit for IoT developers and learners.");
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
