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

/**
 * @author mushuwei
 */
public class CommandLineConfig {

    private final static  CommandLine.Help.ColorScheme COLOR_SCHEME =
            new CommandLine.Help.ColorScheme.Builder(CommandLine.Help.Ansi.AUTO)
                    .commands(CommandLine.Help.Ansi.Style.bold, CommandLine.Help.Ansi.Style.fg_yellow)
                    .options(CommandLine.Help.Ansi.Style.italic, CommandLine.Help.Ansi.Style.fg_yellow)
                    .parameters(CommandLine.Help.Ansi.Style.fg_yellow)
                    .optionParams(CommandLine.Help.Ansi.Style.italic)
                    .errors(CommandLine.Help.Ansi.Style.bold, CommandLine.Help.Ansi.Style.fg_red)
                    .build();
    private static final int CLI_WIDTH = 160;

    public CommandLineConfig() {
    }

    public static CommandLine.Help.ColorScheme getColorScheme() {
        return COLOR_SCHEME;
    }

    public static int getCliWidth() {
        return CLI_WIDTH;
    }
}
