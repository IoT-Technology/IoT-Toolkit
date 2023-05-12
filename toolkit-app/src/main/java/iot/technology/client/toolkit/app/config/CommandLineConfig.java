package iot.technology.client.toolkit.app.config;

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
