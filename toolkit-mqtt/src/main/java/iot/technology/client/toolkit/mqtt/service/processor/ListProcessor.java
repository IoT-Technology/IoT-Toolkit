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
package iot.technology.client.toolkit.mqtt.service.processor;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.mqtt.service.MqttBizService;

import java.util.Objects;

/**
 * @author mushuwei
 */
public class ListProcessor implements TkProcessor {

	private final MqttBizService bizService = new MqttBizService();

	@Override
	public boolean supports(ProcessContext context) {
		return Objects.requireNonNull(context.getData()).equals("list");
	}

	@Override
	public void handle(ProcessContext context) {
	}
}
