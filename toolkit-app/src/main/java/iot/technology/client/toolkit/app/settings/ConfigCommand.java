package iot.technology.client.toolkit.app.settings;

import iot.technology.client.toolkit.app.settings.lang.LangService;
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.HelpVersionGroup;
import iot.technology.client.toolkit.common.constants.LangEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.ResourceBundle;
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
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei",
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class
)
public class ConfigCommand implements Callable<Integer> {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@CommandLine.ArgGroup
	HelpVersionGroup helpVersionGroup;

	@CommandLine.Option(
			names = {"-l", "--locale"},
			defaultValue = "en",
			required = true,
			showDefaultValue = CommandLine.Help.Visibility.ALWAYS,
			description = "${bundle:config.locale}")
	private String locale;

	@Override
	public Integer call() throws Exception {
		boolean isMatch = Arrays.stream(LangEnum.values()).anyMatch(ls -> ls.getValue().equals(locale));
		if (!isMatch) {
			throw new RuntimeException(bundle.getString("config.locale.miss"));
		}
		LangService.updateLocale(locale);
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
