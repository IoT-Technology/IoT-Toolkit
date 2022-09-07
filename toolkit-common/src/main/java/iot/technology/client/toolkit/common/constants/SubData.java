package iot.technology.client.toolkit.common.constants;

import iot.technology.client.toolkit.common.utils.StringUtils;

import java.io.Serializable;
import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class SubData implements Serializable {

	private String operation;

	private String topic;

	private int qos;

	public static SubData validate(String data) {
		SubData subData = new SubData();
		String topic = "";
		int qos = 0;
		ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);
		if (StringUtils.isBlank(data)) {
			throw new IllegalArgumentException(bundle.getString("param.error"));
		}
		if ((!data.contains("add") && !data.contains("del")) || !data.contains(" ")) {
			throw new IllegalArgumentException(bundle.getString("param.error"));
		}
		int spaceIndex = data.indexOf(" ");
		String operationStr = data.substring(0, spaceIndex);
		if ((!operationStr.contains("add") && !operationStr.contains("del"))) {
			throw new IllegalArgumentException(bundle.getString("param.error"));
		}
		subData.setOperation(operationStr);
		String topicAndQos = data.substring(spaceIndex + 1).trim();
		if (StringUtils.isBlank(topicAndQos)) {
			throw new IllegalArgumentException(bundle.getString("param.error"));
		}
		if (!topicAndQos.contains(":")) {
			topic = topicAndQos;
		} else {
			int divide = topicAndQos.indexOf(":");
			String qosStr = topicAndQos.substring(divide + 1);
			String topicStr = topicAndQos.substring(0, divide);
			if (StringUtils.isBlank(topicStr)) {
				throw new IllegalArgumentException(bundle.getString("param.error"));
			}
			topic = topicStr;
			if (StringUtils.isBlank(qosStr) || !StringUtils.isNumeric(qosStr)) {
				throw new IllegalArgumentException(bundle.getString("param.error"));
			}
			Integer qosValue = Integer.parseInt(qosStr);
			if (qosValue.equals(0)
					|| qosValue.equals(1)
					|| qosValue.equals(2)) {
				qos = qosValue;
			} else {
				throw new IllegalArgumentException(bundle.getString("mqtt.qos.error"));
			}
		}
		subData.setTopic(topic);
		subData.setQos(qos);
		return subData;
	}


	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public int getQos() {
		return qos;
	}

	public void setQos(int qos) {
		this.qos = qos;
	}
}
