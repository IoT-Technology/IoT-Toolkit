package iot.technology.client.toolkit.common.settings;

import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.settings.lang.LanguageCommand;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "config",
		requiredOptionMarker = '*',
		optionListHeading = "%nOptions are:%n",
		header = "@|fg(blue),bold Toolkit general configuration|@",
		description = "@|fg(blue),italic Toolkit general configuration operation.|@",
		mixinStandardHelpOptions = true,
		subcommands = {
				LanguageCommand.class
		},
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by mushuwei",
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class
)
public class ConfigCommand implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
