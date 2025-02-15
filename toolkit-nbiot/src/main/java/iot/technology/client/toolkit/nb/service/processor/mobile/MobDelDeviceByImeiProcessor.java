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
package iot.technology.client.toolkit.nb.service.processor.mobile;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.mobile.MobileDeviceService;
import iot.technology.client.toolkit.nb.service.mobile.domain.BaseMobileResponse;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.processor.MobProcessContext;

import java.util.List;

/**
 * format: del imei
 *
 * @author mushuwei
 */
public class MobDelDeviceByImeiProcessor implements TkProcessor {

	private final MobileDeviceService mobileDeviceService = new MobileDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return (context.getData().startsWith("del") || context.getData().startsWith("delete"));
	}

	@Override
	public void handle(ProcessContext context) {
		List<String> arguArgs = List.of(context.getData().split(" "));
		if (arguArgs.size() != 2) {
			StringBuilder sb = new StringBuilder();
			sb.append(ColorUtils.redError("imei is required")).append(StringUtils.lineSeparator);
			sb.append(ColorUtils.blackBold("detail usage please enter: help del"));
			System.out.println(sb);
			return;
		}
		String imei = arguArgs.get(1);
		MobProcessContext mobProcessContext = (MobProcessContext) context;
		MobileConfigDomain mobileConfigDomain = mobProcessContext.getMobileConfigDomain();
		BaseMobileResponse baseMobileResponse = mobileDeviceService.delDeviceByImei(mobileConfigDomain, imei);
		if (baseMobileResponse.isSuccess()) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format(ColorUtils.blackBold("imei:%s delete success"), imei));
			sb.append(StringUtils.lineSeparator());
			System.out.println(sb);
		}
	}
}
