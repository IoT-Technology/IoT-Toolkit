package iot.technology.client.toolkit.common.settings.lang;

import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "lang",
		requiredOptionMarker = '*',
		optionListHeading = "%nOptions are:%n",
		header = "@|fg(blue),bold Toolkit language configuration|@",
		description = "@|fg(blue),italic Toolkit language configuration.|@",
		mixinStandardHelpOptions = true,
		footerHeading = "%nCopyright (c) 2019-2022, IoT Technology",
		footer = "%nDeveloped by mushuwei",
		resourceBundle = "iot.technology.client.toolkit.common.settings.lang.messages",
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class
)
public class LanguageCommand implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
