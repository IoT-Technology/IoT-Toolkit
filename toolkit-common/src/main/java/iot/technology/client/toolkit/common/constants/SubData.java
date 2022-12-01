/*
 * Copyright © 2019-2022 The Toolkit Authors
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
package iot.technology.client.toolkit.common.constants;

import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.ObjectUtils;
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
			System.out.format(ColorUtils.redError(bundle.getString("param.error")));
		}
		if ((!data.contains("add") && !data.contains("del")) || !data.contains(" ")) {
			System.out.format(ColorUtils.redError(bundle.getString("param.error")));
		}
		int spaceIndex = data.indexOf(" ");
		String operationStr = data.substring(0, spaceIndex);
		if ((!operationStr.contains("add") && !operationStr.contains("del"))) {
			System.out.format(ColorUtils.redError(bundle.getString("param.error")));
		}
		subData.setOperation(operationStr);
		String topicAndQos = data.substring(spaceIndex + 1).trim();
		if (StringUtils.isBlank(topicAndQos)) {
			System.out.format(ColorUtils.redError(bundle.getString("param.error")));
		}
		ObjectUtils.topicAndQosValidator(topicAndQos, topic, qos, bundle);
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
