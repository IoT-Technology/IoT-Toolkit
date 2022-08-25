package iot.technology.client.toolkit.common.utils;

import picocli.CommandLine;

/**
 * @author mushuwei
 */
public class ColorUtils {
	
	public static String greenItalic(String text) {
		return colorItalic(text, "green");
	}

	public static String colorItalic(String text, String color) {
		return CommandLine.Help.Ansi.AUTO.string("@|italic," + color + " " + text + "|@");
	}
}
