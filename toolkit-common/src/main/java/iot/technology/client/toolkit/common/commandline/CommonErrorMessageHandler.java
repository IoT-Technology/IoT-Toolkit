package iot.technology.client.toolkit.common.commandline;

import picocli.CommandLine;

import java.io.PrintWriter;

/**
 * @author mushuwei
 */
public abstract class CommonErrorMessageHandler implements CommandLine.IParameterExceptionHandler {

    @Override
    public int handleParseException(CommandLine.ParameterException ex, String[] args) throws Exception {
        printErrorMessage(ex);

        return generateExitCode(ex);
    }

    private void printErrorMessage(final CommandLine.ParameterException parameterException) {
        final PrintWriter writer = parameterException.getCommandLine().getErr();

        if (parameterException.getCause() != null) {
            writer.println(parameterException.getMessage()
                    .replace(parameterException.getCause().toString(), parameterException.getCause().getMessage()));
        } else {
            writer.println(parameterException.getMessage());
        }
    }

    private int generateExitCode(final CommandLine.ParameterException parameterException) {
        final CommandLine cmd = parameterException.getCommandLine();
        final CommandLine.Model.CommandSpec spec = cmd.getCommandSpec();
        return cmd.getExitCodeExceptionMapper() != null ?
                cmd.getExitCodeExceptionMapper().getExitCode(parameterException) :
                spec.exitCodeOnInvalidInput();
    }
}
