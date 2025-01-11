/*
 * Copyright © 2019-2025 The Toolkit Authors
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
