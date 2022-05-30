package iot.technology.client.toolkit.common.utils.table;

import java.util.List;

/**
 * Data that needs to be presented
 *
 * @author mushuwei
 */
public interface Table {
	/**
	 * Form the head
	 *
	 * @return
	 */
	String getTitle();

	/**
	 * The head of each column of the table
	 *
	 * @return
	 */
	String[] getHeaders();

	/**
	 * The data per row that the table has
	 *
	 * @return
	 */
	List<Row> getRows();

}
