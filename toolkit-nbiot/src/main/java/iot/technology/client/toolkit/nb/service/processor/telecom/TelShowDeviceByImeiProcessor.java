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
package iot.technology.client.toolkit.nb.service.processor.telecom;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.processor.TelProcessContext;
import iot.technology.client.toolkit.nb.service.telecom.TelecomDeviceService;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelQueryDeviceByImeiResponse;

/**
 * format: show imei
 *
 * @author mushuwei
 */
public class TelShowDeviceByImeiProcessor implements TkProcessor {

	private final TelecomDeviceService telecomDeviceService = new TelecomDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("show");
	}

	@Override
	public void handle(ProcessContext context) {
		String imei = context.getData().substring(context.getData().indexOf(" ") + 1);
		TelProcessContext telProcessContext = (TelProcessContext) context;
		if (StringUtils.isNotBlank(imei)) {
			TelQueryDeviceByImeiResponse response =
					telecomDeviceService.querySingleDeviceByImei(telProcessContext.getTelecomConfigDomain(), imei);
			if (response.isSuccess()) {
				response.printToConsole();
			}
		}
	}
}
