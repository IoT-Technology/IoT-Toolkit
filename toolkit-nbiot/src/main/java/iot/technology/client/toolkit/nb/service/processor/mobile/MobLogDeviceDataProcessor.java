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
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.DateUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.mobile.MobileDeviceDataService;
import iot.technology.client.toolkit.nb.service.mobile.MobileDeviceService;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.data.*;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.MobQueryDeviceByImeiResponse;
import iot.technology.client.toolkit.nb.service.processor.MobProcessContext;
import org.apache.commons.cli.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * format:log imei startTime endTime limit
 * <p>
 * 1、log imei: 50 logs reported today
 * <p>
 * 2、log imei limit: limit count logs reported today
 * <p>
 * 3、log imei startTime endTime: startTime - endTime 50 reported logs;
 * <p>
 * 4、log imei startTime endTime limit: startTime - endTime limit reported logs;
 *
 * @author mushuwei
 */
public class MobLogDeviceDataProcessor extends TkAbstractProcessor implements TkProcessor {

	private final MobileDeviceDataService mobileDeviceDataService = new MobileDeviceDataService();
	private final static MobileDeviceService mobileDeviceService = new MobileDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("log");
	}

	@Override
	public void handle(ProcessContext context) {
		MobProcessContext mobProcessContext = (MobProcessContext) context;
		MobileConfigDomain mobileConfigDomain = mobProcessContext.getMobileConfigDomain();

		Options options = new Options();
		Option imeiOption = new Option("imei", true, "the device imei");
		Option limitOption = new Option("limit", true, "limit of the device log data list");
		Option startTimeOption = new Option("startTime", true, "start time of search device log data list");
		Option endTimeOption = new Option("endTime", true, "end time of search device log data list");

		options.addOption(imeiOption)
				.addOption(limitOption)
				.addOption(startTimeOption)
				.addOption(endTimeOption);

		String imei = "";
		Integer limit = 50;
		String startTime = DateUtils.getCurrentDayStartTimeForMob();
		String endTime = DateUtils.getCurrentDayEndTimeForMob();
		String consoleStartTime = "";
		String consoleEndTime = "";

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
				startTime = consoleStartTime;
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
				endTime = consoleEndTime;
			}

		} catch (ParseException e) {
			StringBuilder sb = new StringBuilder();
			sb.append(ColorUtils.redError("command parse failed!")).append(StringUtils.lineSeparator);
			System.out.println(sb);
		}
		MobQueryDeviceByImeiResponse response = mobileDeviceService.queryDeviceByImei(mobileConfigDomain, imei);
//		if (response.isSuccess() && Objects.nonNull(response.getData().getId())) {
//			String deviceId = response.getData().getId();
//			OneNetDeviceLatestDataResponse deviceLatestDataResponse =
//					mobileDeviceDataService.getLatestDataPoints(mobileConfigDomain, deviceId);
//			if (deviceLatestDataResponse.isSuccess() && Objects.nonNull(deviceLatestDataResponse.getData())) {
//				List<OneNetDeviceLatestDataStreamsBody> datastreams = deviceLatestDataResponse.getData().getDevices().get(0).getDatastreams();
//
//				System.out.println(ColorUtils.colorBold("--- 最新上报设备数据点 ---", "green"));
//
//				if (Objects.nonNull(datastreams) && !datastreams.isEmpty()) {
//					datastreams.forEach(lst -> {
//						StringBuilder sb = new StringBuilder();
//						sb.append(String.format("数据点:         %s", lst.getId())).append(StringUtils.lineSeparator());
//						sb.append(String.format("数据值:         %s", lst.getValue())).append(StringUtils.lineSeparator());
//						sb.append(String.format("数据上报时间:    %s", lst.getAt()));
//						System.out.println(sb);
//					});
//
//					List<String> dataStreamIds =
//							datastreams.stream().map(OneNetDeviceLatestDataStreamsBody::getId).collect(Collectors.toList());
//					String dataStreamIdStr = String.join(",", dataStreamIds);
//					MobDeviceHisDataResponse deviceHisDataResponse =
//							mobileDeviceDataService.getHisDataPoints(mobileConfigDomain, deviceId, dataStreamIdStr, startTime, endTime, limit);
//					if (deviceHisDataResponse.isSuccess()) {
//						int count = deviceHisDataResponse.getData().getCount();
//						System.out.println(
//								ColorUtils.colorBold(String.format("--- %s %s至%s 历史设备数据点 ---", imei, startTime, endTime), "green"));
//						System.out.println(String.format("日志数量:    %s", count));
//						List<MobDeviceHisDataStreamsBody> deviceHisDataStreamsBodies = deviceHisDataResponse.getData().getDatastreams();
//						deviceHisDataStreamsBodies.forEach(hds -> {
//							StringBuilder sb = new StringBuilder();
//							sb.append(String.format("数据点:    %s", hds.getId())).append(StringUtils.lineSeparator());
//							List<MobDeviceHisDataPointsBody> deviceHisDataPointsBodies = hds.getDatapoints();
//							deviceHisDataPointsBodies.forEach(dpBody -> {
//								sb.append(String.format("数据值: %s", dpBody.getValue())).append(StringUtils.lineSeparator());
//								sb.append(String.format("数据上报时间:    %s", dpBody.getAt())).append(StringUtils.lineSeparator());
//								sb.append(StringUtils.lineSeparator());
//							});
//							System.out.println(sb);
//						});
//					}
//				}
//			}
//		}


	}
}
