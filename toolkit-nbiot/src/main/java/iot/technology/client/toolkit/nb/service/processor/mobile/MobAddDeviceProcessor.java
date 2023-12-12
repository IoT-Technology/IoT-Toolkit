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
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.mobile.MobileDeviceService;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.MobAddDeviceResponse;
import iot.technology.client.toolkit.nb.service.processor.MobProcessContext;
import org.apache.commons.cli.*;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * format: add imei name
 *
 * @author mushuwei
 */
public class MobAddDeviceProcessor extends TkAbstractProcessor implements TkProcessor {

	private final MobileDeviceService mobileDeviceService = new MobileDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("add");
	}

	@Override
	public void handle(ProcessContext context) {
		MobProcessContext mobProcessContext = (MobProcessContext) context;
		MobileConfigDomain mobileConfigDomain = mobProcessContext.getMobileConfigDomain();

		Options options = new Options();
		Option imeiOption = new Option("imei", true, "the device imei");
		Option nameOption = new Option("name", true, "the device name");
		Option imsiOption = new Option("imsi", true, "the device imsi");
		Option obsvOption = new Option("obsv", false, "observer device resources, default: true");
		Option pskValueOption = new Option("psk", true, "psk value");


		options.addOption(imeiOption)
				.addOption(nameOption)
				.addOption(imsiOption)
				.addOption(obsvOption)
				.addOption(pskValueOption);

		String imei = "";
		String name = "";
		String imsi = "";
		boolean obsv = true;
		String psk = null;

		try {
			CommandLineParser parser = new DefaultParser();
			CommandLine cmd = parser.parse(options, convertCommandData(context.getData()));

			// imei and name required
			if (!(cmd.hasOption(imeiOption) && cmd.hasOption(nameOption))) {
				StringBuilder sb = new StringBuilder();
				sb.append(ColorUtils.redError("imei and name is required")).append(StringUtils.lineSeparator);
				sb.append(ColorUtils.blackBold("detail usage please enter: help add"));
				System.out.println(sb);
				return;
			}

			// the device imei
			if (cmd.hasOption(imeiOption)) {
				imei = cmd.getOptionValue(imeiOption);
			}
			// the device name
			if (cmd.hasOption(nameOption)) {
				name = cmd.getOptionValue(nameOption);
			}
			// imsi
			if (cmd.hasOption(imsiOption)) {
				imsi = cmd.getOptionValue(imsiOption);
			} else {
				imsi = imei;
			}
			// psk
			if (cmd.hasOption(pskValueOption)) {
				String pskValueStr = cmd.getOptionValue(pskValueOption);
				if (!checkPskValueFormat(pskValueStr)) {
					StringBuilder sb = new StringBuilder();
					sb.append(ColorUtils.redError("psk format incorrect")).append(StringUtils.lineSeparator);
					sb.append(ColorUtils.blackBold("detail usage please enter: help add"));
					System.out.println(sb);
					return;
				}
				psk = pskValueStr;
			}
			// observer device resources
			if (cmd.hasOption(obsvOption)) {
				String obsvStr = cmd.getOptionValue(obsvOption);
				if (!checkObsvFormat(obsvStr)) {
					StringBuilder sb = new StringBuilder();
					sb.append(ColorUtils.redError("obsv format incorrect")).append(StringUtils.lineSeparator);
					sb.append(ColorUtils.blackBold("detail usage please enter: help add"));
					System.out.println(sb);
					return;
				}
				obsv = Boolean.parseBoolean(obsvStr);
			}

		} catch (ParseException e) {
			StringBuilder sb = new StringBuilder();
			sb.append(ColorUtils.redError("command parse failed!")).append(StringUtils.lineSeparator);
			System.out.println(sb);
		}
		MobAddDeviceResponse response = mobileDeviceService.addDevice(mobileConfigDomain, imei, name, psk, imsi, obsv);
		if (response.isSuccess()) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format(ColorUtils.blackBold("imei:%s add success"), imei));
			sb.append(StringUtils.lineSeparator());
			System.out.println(sb);
		}
	}

	public boolean checkPskValueFormat(String input) {
		String pattern = "^[a-zA-Z0-9]{8,16}$";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(input);
		return matcher.matches();
	}

	public boolean checkObsvFormat(String input) {
		String pattern = "^(?i)(true|false)$";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(input);
		return matcher.matches();
	}
}
