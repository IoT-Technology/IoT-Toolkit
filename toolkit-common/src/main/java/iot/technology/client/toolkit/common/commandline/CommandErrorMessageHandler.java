package iot.technology.client.toolkit.common.commandline;

import picocli.CommandLine;

import java.io.PrintWriter;

/**
 * @author mushuwei
 */
public class CommandErrorMessageHandler  extends CommonErrorMessageHandler
        implements CommandLine.IParameterExceptionHandler {

    @Override
    public int handleParseException(CommandLine.ParameterException ex, String[] args) throws Exception {
        final int exitCode = super.handleParseException(ex, args);
        final CommandLine cmd = ex.getCommandLine();
        final PrintWriter writer = cmd.getErr();
        final CommandLine.Model.CommandSpec spec = cmd.getCommandSpec();
        writer.printf("Try '%s --help' for more information.%n", spec.qualifiedName());
        return exitCode;
    }
}
