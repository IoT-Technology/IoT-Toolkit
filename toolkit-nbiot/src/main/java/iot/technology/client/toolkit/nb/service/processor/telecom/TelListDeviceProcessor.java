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
import org.apache.commons.cli.*;

import java.util.Arrays;

/**
 * format : list [-pn pageNo] [-ps pageSize] [-sv search keyword]
 *
 * @author mushuwei
 */
public class TelListDeviceProcessor extends TkAbstractProcessor implements TkProcessor {

	private final TelecomDeviceService telecomDeviceService = new TelecomDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("list") || context.getData().startsWith("ls");
	}

	@Override
	public void handle(ProcessContext context) {
		TelProcessContext telProcessContext = (TelProcessContext) context;
		TelecomConfigDomain telecomConfigDomain = telProcessContext.getTelecomConfigDomain();

		Options options = new Options();
		Option searchValueOption = new Option("sv", "searchValue", true, "search keyword value");
		Option pageNoOption = new Option("pn", "pageNo", true, "the pageNo of data list");
		Option pageSizeOption = new Option("ps", "pageSize", true, "the pageSize of data list");

		options.addOption(searchValueOption)
				.addOption(pageSizeOption)
				.addOption(pageNoOption);

		Integer pageNo = 1;
		Integer pageSize = 20;
		String searchValue = null;
		try {
			CommandLineParser parser = new DefaultParser();
			CommandLine cmd = parser.parse(options, convertCommandData(context.getData()));
			// pageNo option
			if (cmd.hasOption(pageNoOption)) {
				String pageNoStr = cmd.getOptionValue(pageNoOption);
				if (!validateParam(pageNoStr)) {
					StringBuilder sb = new StringBuilder();
					sb.append(ColorUtils.redError("pageNo is not a number")).append(StringUtils.lineSeparator);
					sb.append(ColorUtils.blackBold("detail usage please enter: help list"));
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
					sb.append(ColorUtils.blackBold("detail usage please enter: help list"));
					System.out.println(sb);
					return;
				}
				pageSize = Integer.parseInt(pageSizeStr);
			}

			// searchValue option
			if (cmd.hasOption(searchValueOption)) {
				searchValue = cmd.getOptionValue(searchValueOption);
			}

		} catch (ParseException e) {
			StringBuilder sb = new StringBuilder();
			sb.append(ColorUtils.redError("command parse failed!")).append(StringUtils.lineSeparator);
			System.out.println(sb);
		}

		TelQueryDeviceListResponse queryDeviceListResponse = telecomDeviceService.queryDeviceList(telecomConfigDomain,
				searchValue, pageNo, pageSize);
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
