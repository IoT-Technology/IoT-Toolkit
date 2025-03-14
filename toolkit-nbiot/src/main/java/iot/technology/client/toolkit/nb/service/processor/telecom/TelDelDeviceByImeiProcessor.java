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
package iot.technology.client.toolkit.nb.service.processor.telecom;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.processor.TelProcessContext;
import iot.technology.client.toolkit.nb.service.telecom.TelecomDeviceService;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelDelDeviceByImeiResponse;

import java.util.List;

/**
 * format: del imei
 *
 * @author mushuwei
 */
public class TelDelDeviceByImeiProcessor implements TkProcessor {

	private final TelecomDeviceService telecomDeviceService = new TelecomDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return (context.getData().startsWith("del") || context.getData().startsWith("delete"));
	}

	@Override
	public void handle(ProcessContext context) {
		TelProcessContext telProcessContext = (TelProcessContext) context;
		String[] arr = context.getData().split(" ");
		if (arr.length != 2) {
			StringBuilder sb = new StringBuilder();
			sb.append(ColorUtils.redError("imei is required")).append(StringUtils.lineSeparator);
			sb.append(ColorUtils.blackBold("detail usage please enter: help del"));
			System.out.println(sb);
			return;
		}
		String imeiListString = arr[1];
		if (StringUtils.isNotBlank(imeiListString)) {
			List<String> imeiList = List.of(imeiListString.split(","));
			TelDelDeviceByImeiResponse response =
					telecomDeviceService.delDeviceByImei(telProcessContext.getTelecomConfigDomain(), imeiList);
			if (response.isSuccess()) {
				System.out.format(ColorUtils.blackBold("imeiList:%s delete success"), imeiListString);
				System.out.format(" " + "%n");
			}
		}
	}
}
