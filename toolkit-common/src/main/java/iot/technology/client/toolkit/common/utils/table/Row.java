package iot.technology.client.toolkit.common.utils.table;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mushuwei
 */
public class Row {
	private List<String> column = new ArrayList<>();

	public List<String> getColumn() {
		return column;
	}

	public void setColumn(List<String> column) {
		this.column = column;
	}
}
