package iot.technology.client.toolkit.common.utils;

import iot.technology.client.toolkit.common.utils.table.Row;

import java.util.List;

/**
 * @author mushuwei
 */
public class TableConsoleUtil {

	public static void printCoapMediaTypes(List<Row> rows) {
		String leftAlignFormat = "| %-8s | %-2s |%n";
		System.out.format("+----------+--------------------------------+%n");
		System.out.format("| Type Id  | Type Name                      |%n");
		System.out.format("+----------+--------------------------------+%n");
		rows.forEach(row -> {
			System.out.format(leftAlignFormat, row.getColumn().get(0), row.getColumn().get(1));
		});
		System.out.format("+----------+--------------------------------+%n");
	}
}
