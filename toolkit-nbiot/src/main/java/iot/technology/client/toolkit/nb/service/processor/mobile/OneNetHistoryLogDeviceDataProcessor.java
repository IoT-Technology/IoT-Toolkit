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
import iot.technology.client.toolkit.nb.service.mobile.OneNetService;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.data.OneNetDeviceHisDataResponse;
import iot.technology.client.toolkit.nb.service.processor.MobProcessContext;
import org.apache.commons.cli.*;

/**
 * @author mushuwei
 */
public class OneNetHistoryLogDeviceDataProcessor extends TkAbstractProcessor implements TkProcessor {

	private final OneNetService oneNetService = new OneNetService();

	@Override
	public boolean supports(ProcessContext context) {
		return (context.getData().startsWith("hl") || context.getData().startsWith("history-log"));
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
		Option sortOption = new Option("sort", true, "sort of the device log data list");

		options.addOption(imeiOption)
				.addOption(limitOption)
				.addOption(startTimeOption)
				.addOption(sortOption)
				.addOption(endTimeOption);

		String imei = "";
		String sort = "DESC";
		Integer limit = 100;
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
				sb.append(ColorUtils.blackBold("detail usage please enter: help hl"));
				System.out.println(sb);
				return;
			}
			imei = cmd.getOptionValue(imeiOption);
			if (cmd.hasOption(limitOption)) {
				String limitStr = cmd.getOptionValue(limitOption);
				if (!validateParam(limitStr)) {
					StringBuilder sb = new StringBuilder();
					sb.append(ColorUtils.redError("limit is not a number")).append(StringUtils.lineSeparator);
					sb.append(ColorUtils.blackBold("detail usage please enter: help hl"));
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
					sb.append(ColorUtils.blackBold("detail usage please enter: help hl"));
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
					sb.append(ColorUtils.blackBold("detail usage please enter: help hl"));
					System.out.println(sb);
					return;
				}
				endTime = consoleEndTime;
			}
			if (cmd.hasOption(sortOption)) {
				String sortStr = cmd.getOptionValue(sortOption);
				if (!(sortStr.equals("DESC") || sortStr.equals("ASC"))) {
					StringBuilder sb = new StringBuilder();
					sb.append(ColorUtils.redError("the sort format is incorrect, correct sort value: DESC or ASC"))
							.append(StringUtils.lineSeparator);
					sb.append(ColorUtils.blackBold("detail usage please enter: help hl"));
					System.out.println(sb);
					return;
				}
			}

		} catch (ParseException e) {
			StringBuilder sb = new StringBuilder();
			sb.append(ColorUtils.redError("command parse failed!")).append(StringUtils.lineSeparator);
			System.out.println(sb);
		}
		OneNetDeviceHisDataResponse hisDataResponse = oneNetService.getHisDataPoints(mobileConfigDomain, imei, startTime, endTime, limit, sort);
		if (hisDataResponse.isSuccess()) {
			hisDataResponse.printToConsole(imei, startTime, endTime);
		}

	}
}
