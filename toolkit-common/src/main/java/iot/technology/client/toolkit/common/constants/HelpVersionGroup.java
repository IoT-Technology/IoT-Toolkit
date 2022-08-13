package iot.technology.client.toolkit.common.constants;

import picocli.CommandLine;

/**
 * @author mushuwei
 */
public class HelpVersionGroup {

	@CommandLine.Option(names = {"-V", "--version"}, versionHelp = true, description = "${bundle:general.version.description}")
	boolean versionInfoRequested;

	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "${bundle:general.help.description}")
	boolean usageHelpRequested;
}
