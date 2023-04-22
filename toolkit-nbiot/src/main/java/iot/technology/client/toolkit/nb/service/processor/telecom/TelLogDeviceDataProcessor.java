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

import com.fasterxml.jackson.databind.JsonNode;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.DateUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.processor.TelProcessContext;
import iot.technology.client.toolkit.nb.service.telecom.TelecomDeviceDataService;
import iot.technology.client.toolkit.nb.service.telecom.TelecomDeviceService;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.data.TelQueryDeviceDataListResponse;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.data.TelQueryDeviceDataTotalResponse;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelQueryDeviceByImeiResponse;

import java.util.List;
import java.util.Objects;

/**
 * format:log imei [startTime] [endTime] [pageNo]
 * <p>
 * 1、log imei: 50 logs reported today;
 * <p>
 * 2、log imei limit: limit count logs reported today;
 * <p>
 * 3、log imei startTime endTime: startTime - endTime 50 reported logs;
 * <p>
 * 4、log imei startTime endTime limit: startTime - endTime limit reported logs;
 *
 * @author mushuwei
 */
public class TelLogDeviceDataProcessor extends TkAbstractProcessor implements TkProcessor {

	private static TelecomDeviceDataService deviceDataService = new TelecomDeviceDataService();
	private static TelecomDeviceService deviceService = new TelecomDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("log");
	}

	@Override
	public void handle(ProcessContext context) {
		TelProcessContext processContext = (TelProcessContext) context;
		TelecomConfigDomain configDomain = processContext.getTelecomConfigDomain();

		List<String> arguArgs = List.of(context.getData().split(" "));
		if (arguArgs.size() > 5 || arguArgs.size() < 2) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format(ColorUtils.redError("argument:%s is illegal"), context.getData()))
					.append(StringUtils.lineSeparator());
			sb.append(ColorUtils.blackBold("usage: log imei [startTime] [endTime] [pageNo]; Time format:2019-02-01T00:01:01"));
			System.out.println(sb);
		}
		int limit = 50;
		String imei = "";
		String startTime = "";
		String endTime = "";
		String consoleStartTime = "";
		String consoleEndTime = "";
		if (arguArgs.size() == 2) {
			imei = arguArgs.get(1);
		}
		if (arguArgs.size() == 3) {
			imei = arguArgs.get(1);
			String limitStr = arguArgs.get(2);
			if (!validateParam(limitStr)) {
				StringBuilder sb = new StringBuilder();
				sb.append(ColorUtils.redError("pageNo is not a number"))
						.append(StringUtils.lineSeparator);
				sb.append(ColorUtils.blackBold("usage: log imei [startTime] [endTime] [pageNo]"));
				System.out.println(sb);
				return;
			}
			limit = Integer.parseInt(limitStr);
		}
		if (arguArgs.size() == 4 || arguArgs.size() == 5) {
			imei = arguArgs.get(1);
			startTime = arguArgs.get(2);
			endTime = arguArgs.get(3);
			if (!DateUtils.mobileTimePattern(startTime) || !DateUtils.mobileTimePattern(endTime)) {
				StringBuilder sb = new StringBuilder();
				sb.append(ColorUtils.redError("the time format is incorrect, correct time format:2019-02-01T00:01:01"))
						.append(StringUtils.lineSeparator);
				sb.append(ColorUtils.blackBold("usage: log imei [startTime] [endTime] [pageNo]"));
				System.out.println(sb);
				return;
			}
		}
		if (arguArgs.size() == 5) {
			imei = arguArgs.get(1);
			String limitStr = arguArgs.get(4);
			if (!validateParam(limitStr)) {
				StringBuilder sb = new StringBuilder();
				sb.append(ColorUtils.redError("pageNo is not a number"))
						.append(StringUtils.lineSeparator);
				sb.append(ColorUtils.blackBold("usage: log imei [startTime] [endTime] [pageNo]"));
				System.out.println(sb);
				return;
			}
			limit = Integer.parseInt(limitStr);
		}
		consoleStartTime = StringUtils.isBlank(startTime) ? DateUtils.getCurrentDayStartTimeForMob() : startTime;
		consoleEndTime = StringUtils.isBlank(endTime) ? DateUtils.getCurrentDayEndTimeForMob() : endTime;
		startTime = StringUtils.isBlank(startTime) ? DateUtils.getCurrentDayStartTimeForTel() :
				DateUtils.covertNbTimeFormatToUnixTime(startTime);
		endTime = StringUtils.isBlank(endTime) ? DateUtils.getCurrentDayEndTimeForTel() : DateUtils.covertNbTimeFormatToUnixTime(endTime);
		TelQueryDeviceByImeiResponse queryDeviceByImeiResponse = deviceService.querySingleDeviceByImei(configDomain, imei);
		if (queryDeviceByImeiResponse.isSuccess() && Objects.nonNull(queryDeviceByImeiResponse.getResult().getDeviceId())) {
			String deviceId = queryDeviceByImeiResponse.getResult().getDeviceId();
			TelQueryDeviceDataTotalResponse
					queryDeviceDataTotalResponse = deviceDataService.getTelDeviceDataTotal(configDomain, deviceId, startTime, endTime);
			if (queryDeviceDataTotalResponse.isSuccess()) {
				Integer count = queryDeviceDataTotalResponse.getTotal();
				System.out.println(
						ColorUtils.colorBold(String.format("--- %s %s至%s 历史设备数据点 ---", imei, consoleStartTime, consoleEndTime),
								"green"));
				System.out.println(String.format("日志数量:    %s", count));
				System.out.println("");
				if (count > 0) {
					TelQueryDeviceDataListResponse
							queryDeviceEventListResponse =
							deviceDataService.getTelDeviceDataList(configDomain, deviceId, startTime, endTime, limit);
					if (queryDeviceEventListResponse.isSuccess() && !queryDeviceEventListResponse.getDeviceStatusList().isEmpty()) {
						List<Object> deviceDataLists = queryDeviceEventListResponse.getDeviceStatusList();
						StringBuilder sb = new StringBuilder();
						deviceDataLists.forEach(del -> {
							String jsonString = JsonUtils.object2Json(del);
							JsonNode jsonNode = JsonUtils.stringToJsonNode(jsonString);
							Long reportTime = jsonNode.get("timestamp").asLong();
							sb.append(String.format("数据内容: %s", jsonString)).append(StringUtils.lineSeparator());
							sb.append(String.format("数据上报时间: %s", DateUtils.timestampToFormatterTime(reportTime)))
									.append(StringUtils.lineSeparator());
							sb.append(StringUtils.lineSeparator());
						});
						System.out.println(sb);
					}
				}

			}
		}

	}
}
