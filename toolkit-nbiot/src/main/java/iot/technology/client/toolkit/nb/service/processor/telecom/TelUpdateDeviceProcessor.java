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

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.processor.TelProcessContext;
import iot.technology.client.toolkit.nb.service.telecom.TelecomDeviceService;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelUpdateDeviceResponse;
import org.apache.commons.cli.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * format: update imei name
 *
 * @author mushuwei
 */
public class TelUpdateDeviceProcessor extends TkAbstractProcessor implements TkProcessor {

	private final TelecomDeviceService telecomDeviceService = new TelecomDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("update");
	}

	@Override
	public void handle(ProcessContext context) {
		TelProcessContext telProcessContext = (TelProcessContext) context;
		TelecomConfigDomain telecomConfigDomain = telProcessContext.getTelecomConfigDomain();

		Options options = new Options();
		Option imeiOption = new Option("imei", true, "the device imei");
		Option nameOption = new Option("name", true, "the device name");
		Option notObserverOption = new Option("notObserv", false, "not observer, default: 0- auto observer");
		Option imsiOption = new Option("imsi", true, "the device imsi");

		options.addOption(imeiOption)
				.addOption(nameOption)
				.addOption(notObserverOption)
				.addOption(imsiOption);

		String imei = "";
		String name = null;
		Integer autoObserver = null;
		String imsi = null;
		try {
			CommandLineParser parser = new DefaultParser();
			CommandLine cmd = parser.parse(options, convertCommandData(context.getData()));
			if (!cmd.hasOption(imeiOption)) {
				StringBuilder sb = new StringBuilder();
				sb.append(ColorUtils.redError("imei is required")).append(StringUtils.lineSeparator);
				sb.append(ColorUtils.blackBold("detail usage please enter: help update"));
				System.out.println(sb);
				return;
			}
			imei = cmd.getOptionValue(imeiOption);
			if (!cmd.hasOption(nameOption) && !cmd.hasOption(notObserverOption) && !cmd.hasOption(imsiOption)) {
				StringBuilder sb = new StringBuilder();
				sb.append(ColorUtils.redError("name, notObserv and imsi need one")).append(StringUtils.lineSeparator);
				sb.append(ColorUtils.blackBold("detail usage please enter: help update"));
				System.out.println(sb);
				return;
			}
			if (cmd.hasOption(nameOption)) {
				name = cmd.getOptionValue(nameOption);
			}
			if (cmd.hasOption(notObserverOption)) {
				autoObserver = 1;
			}
			if (cmd.hasOption(imsiOption)) {
				imsi = cmd.getOptionValue(imsiOption);
				if (!checkImsiFormat(imsi)) {
					StringBuilder sb = new StringBuilder();
					sb.append(ColorUtils.redError("imsi format incorrect")).append(StringUtils.lineSeparator);
					sb.append(ColorUtils.blackBold("detail usage please enter: help add"));
					System.out.println(sb);
					return;
				}
			}
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

		TelUpdateDeviceResponse updateDeviceResponse = telecomDeviceService.updateDevice(telecomConfigDomain, imei, name,
				autoObserver, imsi);
		if (updateDeviceResponse.isSuccess()) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format(ColorUtils.blackBold("imei:%s update success"), imei));
			sb.append(StringUtils.lineSeparator());
			System.out.println(sb);
		}
	}

	public boolean checkImsiFormat(String input) {
		String pattern = "^[0-9]{1,15}$";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(input);
		return matcher.matches();
	}
}
