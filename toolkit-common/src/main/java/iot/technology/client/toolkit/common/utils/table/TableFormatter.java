package iot.technology.client.toolkit.common.utils.table;

import java.io.IOException;

/**
 * Formatting of table data
 *
 * @author atlas
 * @date 2012-12-5
 */
public interface TableFormatter {
	/**
	 * @param table
	 * @return
	 * @deprecated may cause OOM
	 */
	String format(Table table);

	/**
	 * Format the table data and output it to the Appendable object
	 *
	 * @param table
	 * @param writer
	 * @throws IOException
	 */
	void format(Table table, Appendable writer) throws IOException;
}
