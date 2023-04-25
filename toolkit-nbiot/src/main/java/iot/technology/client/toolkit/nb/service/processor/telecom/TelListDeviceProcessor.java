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

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import com.github.freva.asciitable.OverflowBehaviour;
import iot.technology.client.toolkit.common.constants.TelAutoObserverEnum;
import iot.technology.client.toolkit.common.constants.TelDeviceStatusEnum;
import iot.technology.client.toolkit.common.constants.TelNetStatusEnum;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.processor.TelProcessContext;
import iot.technology.client.toolkit.nb.service.telecom.TelecomDeviceService;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelDeviceBody;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelQueryDeviceListBody;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelQueryDeviceListResponse;

import java.util.Arrays;
import java.util.List;

/**
 * format : list [search] [pageNo]
 * <p>
 * 1、list : print first page device list
 * <p>
 * 2、list pageNo : print pageNo device list
 * <p>
 * 3、list searchValue pageNo : print searchValue pageNo device list, searchValue support name/deviceId/imei
 * <p>
 *
 * @author mushuwei
 */
public class TelListDeviceProcessor extends TkAbstractProcessor implements TkProcessor {

	private final TelecomDeviceService telecomDeviceService = new TelecomDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("list");
	}

	@Override
	public void handle(ProcessContext context) {
		List<String> arguArgs = List.of(context.getData().split(" "));
		if (arguArgs.size() > 3) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format(ColorUtils.redError("argument:%s is illegal"), context.getData()))
					.append(StringUtils.lineSeparator());
			sb.append(ColorUtils.blackBold("detail usage please type: help list"));
			System.out.println(sb);
			return;
		}
		Integer pageNo = 1;
		String searchValue = null;
		if (arguArgs.size() == 1) {
		}
		if (arguArgs.size() == 2) {
			String pageNoStr = arguArgs.get(1);
			if (!validateParam(pageNoStr)) {
				StringBuilder sb = new StringBuilder();
				sb.append(ColorUtils.redError("pageNo is not a number")).append(StringUtils.lineSeparator);
				sb.append(ColorUtils.blackBold("detail usage please type: help list"));
				System.out.println(sb);
				return;
			}
			pageNo = Integer.parseInt(pageNoStr);
		}

		if (arguArgs.size() == 3) {
			searchValue = arguArgs.get(1);
			String pageNoStr = arguArgs.get(2);
			if (!validateParam(pageNoStr)) {
				StringBuilder sb = new StringBuilder();
				sb.append(ColorUtils.redError("pageNo is not a number")).append(StringUtils.lineSeparator);
				sb.append(ColorUtils.blackBold("detail usage please type: help list"));
				System.out.println(sb);
				return;
			}
			pageNo = Integer.parseInt(pageNoStr);
		}
		TelProcessContext telProcessContext = (TelProcessContext) context;
		TelecomConfigDomain telecomConfigDomain = telProcessContext.getTelecomConfigDomain();
		TelQueryDeviceListResponse queryDeviceListResponse = telecomDeviceService.queryDeviceList(telecomConfigDomain, searchValue, pageNo);
		if (queryDeviceListResponse.isSuccess()) {
			TelQueryDeviceListBody deviceListBody = queryDeviceListResponse.getResult();
			System.out.format(ColorUtils.blackBold("productId:%s query success, totalNumber:%s, currentPageNo:%s"),
					telecomConfigDomain.getProductId(),
					deviceListBody.getTotal(), deviceListBody.getPageNum());
			System.out.format(" " + "%n");
			if (!deviceListBody.getList().isEmpty()) {
				String asciiTable = AsciiTable.getTable(AsciiTable.NO_BORDERS, deviceListBody.getList(), Arrays.asList(
						new Column().header("deviceId").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
								TelDeviceBody::getDeviceId),
						new Column().header("deviceName").maxWidth(20, OverflowBehaviour.ELLIPSIS_RIGHT)
								.minWidth(20).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(TelDeviceBody::getDeviceName),
						new Column().header("imei").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
								TelDeviceBody::getImei),
						new Column().header("imsi").minWidth(20).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
								TelDeviceBody::getImsi),
						new Column().header("deviceStatus").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(s -> TelDeviceStatusEnum.type(s.getDeviceStatus())),
						new Column().header("autoObserver").minWidth(10).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(
										s -> TelAutoObserverEnum.type(s.getAutoObserver())),
						new Column().header("netStatus").minWidth(10).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(
										s -> TelNetStatusEnum.type(s.getNetStatus()))
				));
				System.out.format(asciiTable);
				System.out.format(" " + "%n");
			}

		}
	}
}
