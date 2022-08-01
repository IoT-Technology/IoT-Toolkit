package iot.technology.client.toolkit.common.exception;

import picocli.CommandLine;

/**
 * @author mushuwei
 */
public class ExceptionMessageHandler implements CommandLine.IExecutionExceptionHandler {

	@Override
	public int handleExecutionException(Exception ex, CommandLine cmd, CommandLine.ParseResult parseResult) {
		cmd.getErr().println(cmd.getColorScheme().errorText(ex.getMessage()));

		return cmd.getExitCodeExceptionMapper() != null
				? cmd.getExitCodeExceptionMapper().getExitCode(ex)
				: cmd.getCommandSpec().exitCodeOnExecutionException();
	}
}
