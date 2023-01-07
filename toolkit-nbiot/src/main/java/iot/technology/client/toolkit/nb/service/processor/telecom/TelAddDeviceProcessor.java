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
package iot.technology.client.toolkit.nb.service.processor.telecom;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.nb.service.processor.TelProcessContext;
import iot.technology.client.toolkit.nb.service.telecom.TelecomDeviceService;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelAddDeviceRequest;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelBatchAddDeviceRequest;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelBatchAddDeviceResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * add imei name
 *
 * @author mushuwei
 */
public class TelAddDeviceProcessor implements TkProcessor {

	private final TelecomDeviceService telecomDeviceService = new TelecomDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("add");
	}

	@Override
	public void handle(ProcessContext context) {
		List<String> arguArgs = List.of(context.getData().split(" "));
		if (arguArgs.size() != 3) {
			System.out.format(ColorUtils.blackBold("argument:%s is illegal"), context.getData());
			System.out.format(" " + "%n");
			return;
		}
		TelProcessContext telProcessContext = (TelProcessContext) context;
		TelecomConfigDomain telecomConfigDomain = telProcessContext.getTelecomConfigDomain();
		String imei = arguArgs.get(1);
		String name = arguArgs.get(2);
		TelBatchAddDeviceRequest batchAddDeviceRequest = new TelBatchAddDeviceRequest();
		batchAddDeviceRequest.setProductId(Integer.valueOf(telecomConfigDomain.getProductId()));
		batchAddDeviceRequest.setOperator("toolkit");
		TelAddDeviceRequest addDeviceRequest = new TelAddDeviceRequest();
		addDeviceRequest.setDeviceName(name);
		addDeviceRequest.setImei(imei);
		addDeviceRequest.setAutoObserver(0);
		List<TelAddDeviceRequest> deviceRequests = new ArrayList<>();
		deviceRequests.add(addDeviceRequest);
		batchAddDeviceRequest.setDevices(deviceRequests);
		TelBatchAddDeviceResponse batchAddDeviceResponse = telecomDeviceService.batchAddDevice(telecomConfigDomain, batchAddDeviceRequest);
		if (batchAddDeviceResponse.isSuccess()) {
			System.out.format(ColorUtils.blackBold("imei:%s add success"), imei);
			System.out.format(" " + "%n");
		}

	}
}
