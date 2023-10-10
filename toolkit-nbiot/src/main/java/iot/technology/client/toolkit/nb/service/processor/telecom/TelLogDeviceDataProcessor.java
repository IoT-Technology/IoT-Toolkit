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
import org.apache.commons.cli.*;
import org.eclipse.leshan.core.util.Hex;

import java.util.Base64;
import java.util.List;
import java.util.Objects;

/**
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

		Options options = new Options();
		Option imeiOption = new Option("imei", true, "the device imei");
		Option limitOption = new Option("limit", true, "limit of the device log data list");
		Option startTimeOption = new Option("startTime", true, "start time of search device log data list");
		Option endTimeOption = new Option("endTime", true, "end time of search device log data list");
		Option hexOption = new Option("hex", false, "the hex data format");

		options.addOption(imeiOption)
				.addOption(limitOption)
				.addOption(startTimeOption)
				.addOption(endTimeOption)
				.addOption(hexOption);

		String imei = "";
		Integer limit = 50;
		String startTime = DateUtils.getCurrentDayStartTimeForTel();
		String endTime = DateUtils.getCurrentDayEndTimeForTel();
		String consoleStartTime = DateUtils.getCurrentDayStartTimeForMob();
		String consoleEndTime = DateUtils.getCurrentDayEndTimeForMob();
		Boolean hex = false;
		try {
			CommandLineParser parser = new DefaultParser();
			CommandLine cmd = parser.parse(options, convertCommandData(context.getData()));

			if (!cmd.hasOption(imeiOption)) {
				StringBuilder sb = new StringBuilder();
				sb.append(ColorUtils.redError("imei is required")).append(StringUtils.lineSeparator);
				sb.append(ColorUtils.blackBold("detail usage please enter: help log"));
				System.out.println(sb);
				return;
			}
			imei = cmd.getOptionValue(imeiOption);
			if (cmd.hasOption(limitOption)) {
				String limitStr = cmd.getOptionValue(limitOption);
				if (!validateParam(limitStr)) {
					StringBuilder sb = new StringBuilder();
					sb.append(ColorUtils.redError("limit is not a number")).append(StringUtils.lineSeparator);
					sb.append(ColorUtils.blackBold("detail usage please enter: help log"));
					System.out.println(sb);
					return;
				}
				limit = Integer.parseInt(limitStr);
			}
			if (cmd.hasOption(startTimeOption)) {
				consoleStartTime = cmd.getOptionValue(startTimeOption);
				if (!DateUtils.mobileTimePattern(consoleStartTime)) {
					StringBuilder sb = new StringBuilder();
					sb.append(ColorUtils.redError("the time format is incorrect, correct time format:2019-02-01T00:01:01"))
							.append(StringUtils.lineSeparator);
					sb.append(ColorUtils.blackBold("detail usage please enter: help log"));
					System.out.println(sb);
					return;
				}
				startTime = DateUtils.covertNbTimeFormatToUnixTime(consoleStartTime);
			}
			if (cmd.hasOption(endTimeOption)) {
				consoleEndTime = cmd.getOptionValue(endTimeOption);
				if (!DateUtils.mobileTimePattern(consoleEndTime)) {
					StringBuilder sb = new StringBuilder();
					sb.append(ColorUtils.redError("the time format is incorrect, correct time format:2019-02-01T00:01:01"))
							.append(StringUtils.lineSeparator);
					sb.append(ColorUtils.blackBold("detail usage please enter: help log"));
					System.out.println(sb);
					return;
				}
				endTime = DateUtils.covertNbTimeFormatToUnixTime(consoleEndTime);
			}
			if (cmd.hasOption(hexOption)) {
				hex = true;
			}
		} catch (ParseException e) {
			StringBuilder sb = new StringBuilder();
			sb.append(ColorUtils.redError("command parse failed!")).append(StringUtils.lineSeparator);
			System.out.println(sb);
		}
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
					TelQueryDeviceDataListResponse queryDeviceEventListResponse =
							deviceDataService.getTelDeviceDataList(configDomain, deviceId, startTime, endTime, limit);
					if (queryDeviceEventListResponse.isSuccess() && !queryDeviceEventListResponse.getDeviceStatusList().isEmpty()) {
						List<Object> deviceDataLists = queryDeviceEventListResponse.getDeviceStatusList();
						StringBuilder sb = new StringBuilder();
						Boolean finalHex = hex;
						deviceDataLists.forEach(del -> {
							String jsonString = JsonUtils.object2Json(del);
							JsonNode jsonNode = JsonUtils.objectToJsonNode(del);
							JsonNode dataNode = jsonNode.get("APPdata");
							Long reportTime = jsonNode.get("timestamp").asLong();
							if (finalHex && Objects.nonNull(dataNode)) {
								byte[] decodedBytes = Base64.getDecoder().decode(dataNode.asText());
								sb.append(String.format("数据内容: %s", Hex.encodeHexString(decodedBytes)))
										.append(StringUtils.lineSeparator());
							} else {
								sb.append(String.format("数据内容: %s", jsonString)).append(StringUtils.lineSeparator());
							}
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
