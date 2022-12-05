package iot.technology.client.toolkit.nb.command;

import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "nb",
		requiredOptionMarker = '*',
		header = "@|fg(Cyan),bold ${bundle:nb.header}|@",
		description = "@|fg(Cyan),italic ${bundle:nb.description}|@",
		optionListHeading = "%n${bundle:general.option}:%n",
		subcommands = {
		},
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei")
public class NbCommand implements Callable<Integer> {

	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "${bundle:general.help.description}")
	boolean usageHelpRequested;

	@Override
	public Integer call() throws Exception {
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
