package iot.technology.client.toolkit.app.settings.lang;

import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "lang",
		requiredOptionMarker = '*',
		optionListHeading = "%n${bundle:general.option}:%n",
		header = "@|fg(blue),bold ${bundle:config.lang.header}|@",
		description = "@|fg(blue),italic ${bundle:config.lang.description}|@",
		mixinStandardHelpOptions = true,
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei",
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class
)
public class LanguageCommand implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
