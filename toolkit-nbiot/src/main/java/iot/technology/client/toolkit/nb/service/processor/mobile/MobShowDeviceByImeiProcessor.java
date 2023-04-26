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
package iot.technology.client.toolkit.nb.service.processor.mobile;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.mobile.MobileDeviceService;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.MobQueryDeviceByImeiResponse;
import iot.technology.client.toolkit.nb.service.processor.MobProcessContext;

import java.util.List;

/**
 * format: show imei
 *
 * @author mushuwei
 */
public class MobShowDeviceByImeiProcessor implements TkProcessor {

	private final MobileDeviceService mobileDeviceService = new MobileDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("show");
	}

	@Override
	public void handle(ProcessContext context) {
		List<String> arguArgs = List.of(context.getData().split(" "));
		if (arguArgs.size() != 2) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format(ColorUtils.redError("argument:%s is illegal"), context.getData()))
					.append(StringUtils.lineSeparator());
			sb.append(ColorUtils.blackBold("usage: show imei"));
			System.out.println(sb);
			return;
		}
		String imei = arguArgs.get(1);
		MobProcessContext mobProcessContext = (MobProcessContext) context;
		MobileConfigDomain mobileConfigDomain = mobProcessContext.getMobileConfigDomain();
		MobQueryDeviceByImeiResponse queryDeviceByImeiResponse = mobileDeviceService.queryDeviceByImei(mobileConfigDomain, imei);
		if (queryDeviceByImeiResponse.isSuccess()) {
			queryDeviceByImeiResponse.printToConsole();
		}


	}
}
