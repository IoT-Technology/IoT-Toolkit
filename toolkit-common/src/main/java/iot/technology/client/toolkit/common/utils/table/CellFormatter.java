package iot.technology.client.toolkit.common.utils.table;

/**
 * Each cell of the table outputs the formatting class
 *
 * @author mushuwei
 */
public interface CellFormatter {
	/**
	 * Formatted output string
	 *
	 * @param cell
	 * @return
	 */
	String format(Object cell);

	/**
	 * @param cell May be null
	 * @returnWhether the object cell can be formatted
	 */
	boolean accepts(Object cell);
}
