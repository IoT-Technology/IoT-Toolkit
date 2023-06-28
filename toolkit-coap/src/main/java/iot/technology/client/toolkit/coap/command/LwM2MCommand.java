package iot.technology.client.toolkit.coap.command;

import iot.technology.client.toolkit.coap.command.sub.*;
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
        name = "lwm2m",
        requiredOptionMarker = '*',
        header = "@|bold ${bundle:lwm2m.header}|@",
        description = "@|fg(red),bold ${bundle:lwm2m.description}|@",
        synopsisHeading = "%n@|bold ${bundle:general.usage}|@%n",
        commandListHeading = "%n@|bold ${bundle:general.commands}|@%n",
        optionListHeading = "%n@|bold ${bundle:general.option}|@%n",
        descriptionHeading = "%n",
        subcommands = {

        },
        footerHeading = "%nCopyright (c) 2019-2023, ${bundle:general.copyright}",
        footer = "%nDeveloped by mushuwei")
public class LwM2MCommand implements Callable<Integer> {

    @CommandLine.Spec
    private CommandLine.Model.CommandSpec spec;

    @Override
    public Integer call() throws Exception {
        System.out.println(spec.commandLine().getUsageMessage());
        return ExitCodeEnum.SUCCESS.getValue();
    }
}
