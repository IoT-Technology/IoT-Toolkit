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

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import com.github.freva.asciitable.OverflowBehaviour;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.nb.service.mobile.MobileDeviceService;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.MobQueryDeviceBody;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.MobQueryDeviceListBody;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.MobQueryDeviceListResponse;
import iot.technology.client.toolkit.nb.service.processor.MobProcessContext;

import java.util.Arrays;
import java.util.List;

/**
 * @author mushuwei
 */
public class MobListDeviceProcessor implements TkProcessor {

	private final MobileDeviceService mobileDeviceService = new MobileDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("list");
	}

	@Override
	public void handle(ProcessContext context) {
		List<String> arguArgs = List.of(context.getData().split(" "));
		if (arguArgs.size() > 3 || arguArgs.size() < 2) {
			System.out.format(ColorUtils.blackBold("argument:%s is illegal"), context.getData());
			System.out.format(" " + "%n");
			return;
		}
		String searchValue = null;
		Integer pageNo = Integer.valueOf(arguArgs.get(1));
		if (arguArgs.size() > 2) {
			searchValue = arguArgs.get(2);
		}
		MobProcessContext mobProcessContext = (MobProcessContext) context;
		MobileConfigDomain mobileConfigDomain = mobProcessContext.getMobileConfigDomain();
		MobQueryDeviceListResponse queryDeviceListResponse = mobileDeviceService.queryDeviceList(mobileConfigDomain, pageNo, searchValue);
		if (queryDeviceListResponse.isSuccess()) {
			MobQueryDeviceListBody deviceListBody = queryDeviceListResponse.getData();
			System.out.format(ColorUtils.blackBold("productId:%s query success, totalNumber:%s, currentPageNo:%s"),
					mobileConfigDomain.getProductId(),
					deviceListBody.getTotal_count(), deviceListBody.getPage());
			System.out.format(" " + "%n");
			if (!deviceListBody.getDevices().isEmpty()) {
				List<MobQueryDeviceBody> devices = deviceListBody.getDevices();
				String asciiTable = AsciiTable.getTable(AsciiTable.NO_BORDERS, devices, Arrays.asList(
						new Column().header("protocol").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
								MobQueryDeviceBody::getProtocol),
						new Column().header("deviceName").maxWidth(20, OverflowBehaviour.ELLIPSIS_RIGHT)
								.minWidth(20).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(MobQueryDeviceBody::getTitle),
						new Column().header("deviceId").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
								MobQueryDeviceBody::getId),
						new Column().header("imei").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
								s -> s.getAuth_info().keySet().iterator().next()),
						new Column().header("online").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
								s -> s.isOnline() ? "在线" : "离线"),
						new Column().header("observer").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
								s -> s.isObsv() ? "订阅" : "不订阅"),
						new Column().header("createTime").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
								MobQueryDeviceBody::getCreate_time),
						new Column().header("activeTime").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
								MobQueryDeviceBody::getAct_time)
				));
				System.out.format(asciiTable);
				System.out.format(" " + "%n");
			}

		}
	}
}
