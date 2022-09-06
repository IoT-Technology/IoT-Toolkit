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
package iot.technology.client.toolkit.mqtt.service.handler;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.mqtt.MqttQoS;
import iot.technology.client.toolkit.common.constants.EmojiEnum;
import iot.technology.client.toolkit.common.utils.ColorUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author mushuwei
 */
public class MqttSubMessageHandler implements MqttHandler {

	/**
	 * @param topic   topic
	 * @param qos     quality of service
	 * @param payload
	 */
	@Override
	public void onMessage(String topic, MqttQoS qos, ByteBuf payload) {
		System.out.println("------subscribe" + String.format(EmojiEnum.subscribeEmoji) + "------");
		System.out.format(String.format("Topic:%s   QoS:%s", topic, qos.value()) + "%n");
		System.out.format(ColorUtils.blackBold(payload.toString(StandardCharsets.UTF_8)) + "%n");
		LocalDateTime nowDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
		System.out.format(formatter.format(nowDateTime) + "%n");
	}
}
