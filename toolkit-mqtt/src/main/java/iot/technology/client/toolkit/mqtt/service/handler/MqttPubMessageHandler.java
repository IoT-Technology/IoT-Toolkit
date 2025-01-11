/*
 * Copyright © 2019-2025 The Toolkit Authors
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
package iot.technology.client.toolkit.mqtt.service.handler;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.mqtt.MqttQoS;
import iot.technology.client.toolkit.common.constants.EmojiEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class MqttPubMessageHandler implements MqttHandler {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public void onMessage(String topic, MqttQoS qos, ByteBuf payload) {
		LocalDateTime nowDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		StringBuilder sb = new StringBuilder();
		sb.append(StringUtils.lineSeparator());
		System.out.format("%n" + "------"+ bundle.getString("mqtt.received.desc") + String.format(EmojiEnum.subscribeEmoji) + "------" + "%n");
		String qosConsoleString = "";
		if (qos.equals(MqttQoS.AT_MOST_ONCE)) {
			qosConsoleString = bundle.getString("mqtt.qos0.prompt");
		}
		if (qos.equals(MqttQoS.AT_LEAST_ONCE)) {
			qosConsoleString = bundle.getString("mqtt.qos1.prompt");
		}
		if (qos.equals(MqttQoS.EXACTLY_ONCE)) {
			qosConsoleString = bundle.getString("mqtt.qos2.prompt");
		}
		sb.append(String.format("Topic:%s   QoS:%s", topic, qosConsoleString)).append(StringUtils.lineSeparator());
		sb.append(ColorUtils.colorBold(payload.toString(StandardCharsets.UTF_8), "yellow")).append(StringUtils.lineSeparator());
		sb.append(formatter.format(nowDateTime)).append(StringUtils.lineSeparator());
		sb.append(StringUtils.lineSeparator());
		System.out.print(sb);
	}
}
