package iot.technology.client.toolkit.common.rule;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class NodeContext implements Serializable {

	private String data;

	private String type;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
