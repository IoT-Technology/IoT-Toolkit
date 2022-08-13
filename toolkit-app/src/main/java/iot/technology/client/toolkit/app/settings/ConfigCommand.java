package iot.technology.client.toolkit.app.settings;

import iot.technology.client.toolkit.app.settings.lang.LanguageCommand;
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "config",
		requiredOptionMarker = '*',
		optionListHeading = "%n${bundle:general.option}:%n",
		header = "@|fg(blue),bold ${bundle:config.header}|@",
		description = "@|fg(blue),italic ${bundle:config.description}|@",
		mixinStandardHelpOptions = true,
		subcommands = {
				LanguageCommand.class
		},
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei",
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class
)
public class ConfigCommand implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
