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
import iot.technology.client.toolkit.common.utils.DateUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.mobile.MobileDeviceDataService;
import iot.technology.client.toolkit.nb.service.mobile.MobileDeviceService;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.data.*;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.MobQueryDeviceByImeiResponse;
import iot.technology.client.toolkit.nb.service.processor.MobProcessContext;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * format:log imei startTime endTime limit
 * 1、log imei: 50 logs reported today
 * <p>
 * 2、log imei limit: limit count logs reported today
 * <p>
 * 3、log imei startTime endTime
 * <p>
 * 4、log imei startTime endTime limit
 *
 * @author mushuwei
 */
public class MobLogDeviceDataProcessor implements TkProcessor {

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

		List<String> arguArgs = List.of(context.getData().split(" "));
		if (arguArgs.size() > 5 || arguArgs.size() < 2) {
			System.out.format(ColorUtils.blackBold("argument:%s is illegal"), context.getData());
			System.out.format(" " + "%n");
			return;
		}
		int limit = 50;
		String imei = "";
		String startTime = "";
		String endTime = "";
		if (arguArgs.size() == 2) {
			imei = arguArgs.get(1);
		}
		if (arguArgs.size() == 3) {
			imei = arguArgs.get(1);
			String limitStr = arguArgs.get(2);
			if (!validateLimit(limitStr)) {
				return;
			}
			limit = Integer.parseInt(limitStr);
		}
		if (arguArgs.size() == 4 || arguArgs.size() == 5) {
			imei = arguArgs.get(1);
			startTime = arguArgs.get(2);
			endTime = arguArgs.get(3);
			if (!DateUtils.mobileTimePattern(startTime) || !DateUtils.mobileTimePattern(endTime)) {
				System.out.format(ColorUtils.blackBold("time format is illegal"));
				System.out.format(" " + "%n");
				return;
			}
		}
		if (arguArgs.size() == 5) {
			imei = arguArgs.get(1);
			String limitStr = arguArgs.get(4);
			if (!validateLimit(limitStr)) {
				return;
			}
			limit = Integer.parseInt(limitStr);
		}
		startTime = StringUtils.isBlank(startTime) ? DateUtils.getCurrentDayStartTimeForMob() : startTime;
		endTime = StringUtils.isBlank(endTime) ? DateUtils.getCurrentDayEndTimeForMob() : endTime;

		MobQueryDeviceByImeiResponse response = mobileDeviceService.queryDeviceByImei(mobileConfigDomain, imei);
		if (response.isSuccess() && Objects.nonNull(response.getData().getId())) {
			String deviceId = response.getData().getId();
			MobDeviceLatestDataResponse deviceLatestDataResponse =
					mobileDeviceDataService.getLatestDataPoints(mobileConfigDomain, deviceId);
			if (deviceLatestDataResponse.isSuccess() && Objects.nonNull(deviceLatestDataResponse.getData())) {
				List<MobDeviceLatestDataStreamsBody> datastreams = deviceLatestDataResponse.getData().getDevices().get(0).getDatastreams();

				System.out.println(ColorUtils.colorBold("--- 最新上报设备数据点 ---", "green"));
				datastreams.forEach(lst -> {
					StringBuilder sb = new StringBuilder();
					sb.append(String.format("数据点:         %s", lst.getId())).append(StringUtils.lineSeparator());
					sb.append(String.format("数据值:         %s", lst.getValue())).append(StringUtils.lineSeparator());
					sb.append(String.format("数据上报时间:    %s", lst.getAt()));
					System.out.println(sb);
				});

				List<String> dataStreamIds = datastreams.stream().map(MobDeviceLatestDataStreamsBody::getId).collect(Collectors.toList());
				String dataStreamIdStr = String.join(",", dataStreamIds);
				MobDeviceHisDataResponse deviceHisDataResponse =
						mobileDeviceDataService.getHisDataPoints(mobileConfigDomain, deviceId, dataStreamIdStr, startTime, endTime, limit);
				if (deviceHisDataResponse.isSuccess()) {
					int count = deviceHisDataResponse.getData().getCount();
					System.out.println(ColorUtils.colorBold(String.format("--- %s 至 %s 历史设备数据点 ---", startTime, endTime), "green"));
					System.out.println(String.format("日志数量:    %s", count));
					List<MobDeviceHisDataStreamsBody> deviceHisDataStreamsBodies = deviceHisDataResponse.getData().getDatastreams();
					deviceHisDataStreamsBodies.forEach(hds -> {
						StringBuilder sb = new StringBuilder();
						sb.append(String.format("数据点:    %s", hds.getId())).append(StringUtils.lineSeparator());
						List<MobDeviceHisDataPointsBody> deviceHisDataPointsBodies = hds.getDatapoints();
						deviceHisDataPointsBodies.forEach(dpBody -> {
							sb.append(String.format("数据值: %s", dpBody.getValue())).append(StringUtils.lineSeparator());
							sb.append(String.format("数据上报时间:    %s", dpBody.getAt())).append(StringUtils.lineSeparator());
						});
						System.out.println(sb);
					});
				}
			}
		}


	}

	private boolean validateLimit(String limitStr) {
		if (!StringUtils.isNumeric(limitStr)) {
			System.out.format(ColorUtils.blackBold("limit:%s is illegal"), limitStr);
			System.out.format(" " + "%n");
			return false;
		}
		int limit = Integer.parseInt(limitStr);
		if (limit > 500) {
			System.out.format(ColorUtils.blackBold("limit:%s > 500 illegal"), limitStr);
			System.out.format(" " + "%n");
			return false;
		}
		return true;
	}
}
