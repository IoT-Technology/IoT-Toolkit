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

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.DateUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.mobile.MobileDeviceDataService;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.data.MobCachedCommandItem;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.data.MobCachedCommandResponse;
import iot.technology.client.toolkit.nb.service.processor.MobProcessContext;
import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author mushuwei
 */
public class MobCommandDataDeviceProcessor extends TkAbstractProcessor implements TkProcessor {

	private final MobileDeviceDataService mobileDeviceDataService = new MobileDeviceDataService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("command");
	}

	@Override
	public void handle(ProcessContext context) {
		MobProcessContext mobProcessContext = (MobProcessContext) context;
		MobileConfigDomain mobileConfigDomain = mobProcessContext.getMobileConfigDomain();

		Options options = new Options();
		Option imeiOption = new Option("imei", true, "the device imei");
		Option startTimeOption = new Option("st","startTime", true, "start time of search device command data list");
		Option endTimeOption = new Option("et","endTime", true, "end time of search device command data list");
		Option pageNoOption = new Option("pn", "pageNo", true, "the pageNo of device command data list");
		Option pageSizeOption = new Option("ps", "pageSize", true, "the pageSize of device command data list");

		options.addOption(imeiOption)
				.addOption(pageNoOption)
				.addOption(pageSizeOption)
				.addOption(startTimeOption)
				.addOption(endTimeOption);

		String imei = "";
		Integer pageNo = 1;
		Integer pageSize = 20;
		String startTime = DateUtils.getCurrentDayStartTimeForMob();
		String endTime = DateUtils.getCurrentDayEndTimeForMob();
		String consoleStartTime = "";
		String consoleEndTime = "";
		try {
			CommandLineParser parser = new DefaultParser();
			CommandLine cmd = parser.parse(options, convertCommandData(context.getData()));
			// the imei option
			if (!cmd.hasOption(imeiOption)) {
				StringBuilder sb = new StringBuilder();
				sb.append(ColorUtils.redError("imei is required")).append(StringUtils.lineSeparator);
				sb.append(ColorUtils.blackBold("detail usage please enter: help command"));
				System.out.println(sb);
				return;
			}
			imei = cmd.getOptionValue(imeiOption);
			// the startTime option
			if (cmd.hasOption(startTimeOption)) {
				consoleStartTime = cmd.getOptionValue(startTimeOption);
				if (!DateUtils.mobileTimePattern(consoleStartTime)) {
					StringBuilder sb = new StringBuilder();
					sb.append(ColorUtils.redError("the time format is incorrect, correct time format:2019-02-01T00:01:01"))
							.append(StringUtils.lineSeparator);
					sb.append(ColorUtils.blackBold("detail usage please enter: help command"));
					System.out.println(sb);
					return;
				}
				startTime = consoleStartTime;
			}
			// the endTime option
			if (cmd.hasOption(endTimeOption)) {
				consoleEndTime = cmd.getOptionValue(endTimeOption);
				if (!DateUtils.mobileTimePattern(consoleEndTime)) {
					StringBuilder sb = new StringBuilder();
					sb.append(ColorUtils.redError("the time format is incorrect, correct time format:2019-02-01T00:01:01"))
							.append(StringUtils.lineSeparator);
					sb.append(ColorUtils.blackBold("detail usage please enter: help command"));
					System.out.println(sb);
					return;
				}
				endTime = consoleEndTime;
			}

			// pageNo option
			if (cmd.hasOption(pageNoOption)) {
				String pageNoStr = cmd.getOptionValue(pageNoOption);
				if (!validateParam(pageNoStr)) {
					StringBuilder sb = new StringBuilder();
					sb.append(ColorUtils.redError("pageNo is not a number")).append(StringUtils.lineSeparator);
					sb.append(ColorUtils.blackBold("detail usage please enter: help command"));
					System.out.println(sb);
					return;
				}
				pageNo = Integer.parseInt(pageNoStr);
			}
			// pageSize Option
			if (cmd.hasOption(pageSizeOption)) {
				String pageSizeStr = cmd.getOptionValue(pageSizeOption);
				if (!validateParam(pageSizeStr)) {
					StringBuilder sb = new StringBuilder();
					sb.append(ColorUtils.redError("pageSize is not a number")).append(StringUtils.lineSeparator);
					sb.append(ColorUtils.blackBold("detail usage please enter: help command"));
					System.out.println(sb);
					return;
				}
				pageSize = Integer.parseInt(pageSizeStr);
			}

		} catch (ParseException e) {
			StringBuilder sb = new StringBuilder();
			sb.append(ColorUtils.redError("command parse failed!")).append(StringUtils.lineSeparator);
			System.out.println(sb);
		}
		MobCachedCommandResponse
				mobCachedCommandResponse = mobileDeviceDataService.getCachedCommandList(
						mobileConfigDomain, imei, startTime, endTime, pageNo, pageSize);
		if (mobCachedCommandResponse.isSuccess()
				&& mobCachedCommandResponse.getData() != null
				&& !mobCachedCommandResponse.getData().getItems().isEmpty()) {
			List<MobCachedCommandItem> items = mobCachedCommandResponse.getData().getItems();
			String asciiTable = AsciiTable.getTable(AsciiTable.NO_BORDERS, items, Arrays.asList(
					new Column().header("cmdUuid").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
							MobCachedCommandItem::getCmdUuid),
					new Column().header("type").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
							MobCachedCommandItem::getType),
					new Column().header("createTime").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
							MobCachedCommandItem::getCreateTime),
					new Column().header("validTime").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
							MobCachedCommandItem::getValidTime),
					new Column().header("expiredTime").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
							MobCachedCommandItem::getExpiredTime),
					new Column().header("sendTime").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
							MobCachedCommandItem::getSendTime),
					new Column().header("sendStatus").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
							s -> String.valueOf(s.getSendStatus())),
					new Column().header("confirmTime").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
							MobCachedCommandItem::getConfirmTime),
					new Column().header("confirmStatus").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
							MobCachedCommandItem::getConfirmStatus),
					new Column().header("remain").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
							s -> String.valueOf(s.getRemain()))
			));
			System.out.format(asciiTable);
			System.out.format(" " + "%n");
		}
	}
}
