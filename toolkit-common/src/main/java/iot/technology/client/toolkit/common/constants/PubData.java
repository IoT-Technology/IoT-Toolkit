/*
 * Copyright © 2019-2023 The Toolkit Authors
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
public class PubData implements Serializable {

	private String topic;

	private int qos;

	private String payload;

	public static boolean validate(String data, TopicAndQos bizDomain) {
		ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);
		if (StringUtils.isBlank(data) || !data.contains(" ")) {
			System.out.format(ColorUtils.redError(bundle.getString("param.error")));
			return false;
		}
		int equalIndex = data.indexOf(" ");
		String topicAndQos = data.substring(0, equalIndex);
		if (StringUtils.isBlank(topicAndQos)) {
			System.out.format(ColorUtils.redError(bundle.getString("param.error")));
			return false;
		}
		boolean validateResult = ObjectUtils.topicAndQosValidator(topicAndQos, bizDomain);
		if (validateResult) {
			String payload = data.substring(equalIndex + 1);
			if (StringUtils.isBlank(payload)) {
				System.out.format(ColorUtils.redError(bundle.getString("param.error")));
				return false;
			}
			bizDomain.setPayload(payload);
			return true;
		}
		return false;
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

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
}
