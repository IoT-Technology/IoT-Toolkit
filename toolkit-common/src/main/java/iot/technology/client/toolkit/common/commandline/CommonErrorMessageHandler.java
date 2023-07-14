/*
 * Copyright © 2019-2023 The Toolkit Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
