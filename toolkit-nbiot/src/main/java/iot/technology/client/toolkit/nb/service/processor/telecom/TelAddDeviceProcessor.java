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

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.processor.TelProcessContext;
import iot.technology.client.toolkit.nb.service.telecom.TelecomDeviceService;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelAddDeviceRequest;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelBatchAddDeviceRequest;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelBatchAddDeviceResponse;
import org.apache.commons.cli.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * format: add imei name
 *
 * @author mushuwei
 */
public class TelAddDeviceProcessor extends TkAbstractProcessor implements TkProcessor {

	private final TelecomDeviceService telecomDeviceService = new TelecomDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("add");
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
		Option pskTypeOption = new Option("pskType", true, "psk type");
		Option pskValueOption = new Option("pskValue", true, "psk value");

		options.addOption(imeiOption)
				.addOption(nameOption)
				.addOption(notObserverOption)
				.addOption(imsiOption)
				.addOption(pskTypeOption)
				.addOption(pskValueOption);

		String imei = "";
		String name = "";
		Integer autoObserver = 0;
		String imsi = null;
		Integer pskType = null;
		String pskValue = null;
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
			// auto observer
			if (cmd.hasOption(notObserverOption)) {
				autoObserver = 1;
			}
			// imsi
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
			// pskType
			if (cmd.hasOption(pskTypeOption)) {
				String pskTypeStr = cmd.getOptionValue(pskTypeOption);
				if (!validateParam(pskTypeStr)) {
					StringBuilder sb = new StringBuilder();
					sb.append(ColorUtils.redError("pskType is not a number")).append(StringUtils.lineSeparator);
					sb.append(ColorUtils.blackBold("detail usage please enter: help add"));
					System.out.println(sb);
					return;
				}
				pskType = Integer.valueOf(pskTypeStr);
				if (!(pskType == 0 || pskType == 1)) {
					StringBuilder sb = new StringBuilder();
					sb.append(ColorUtils.redError("pskType should be 0 or 1")).append(StringUtils.lineSeparator);
					sb.append(ColorUtils.blackBold("detail usage please enter: help add"));
					System.out.println(sb);
					return;
				}
			}
			// pskValue
			if (cmd.hasOption(pskValueOption)) {
				if (Objects.isNull(pskType)) {
					pskType = 0;
				}
				String pskValueStr = cmd.getOptionValue(pskValueOption);
				if (!checkPskValueFormat(pskValueStr)) {
					StringBuilder sb = new StringBuilder();
					sb.append(ColorUtils.redError("pskValue format incorrect")).append(StringUtils.lineSeparator);
					sb.append(ColorUtils.blackBold("detail usage please enter: help add"));
					System.out.println(sb);
					return;
				}
				pskValue = pskValueStr;
			}

		} catch (ParseException e) {
			StringBuilder sb = new StringBuilder();
			sb.append(ColorUtils.redError("command parse failed!")).append(StringUtils.lineSeparator);
			System.out.println(sb);
		}
		TelBatchAddDeviceRequest batchAddDeviceRequest = new TelBatchAddDeviceRequest();
		batchAddDeviceRequest.setProductId(Integer.valueOf(telecomConfigDomain.getProductId()));
		batchAddDeviceRequest.setOperator("toolkit");
		TelAddDeviceRequest addDeviceRequest = new TelAddDeviceRequest();
		addDeviceRequest.setDeviceName(name);
		addDeviceRequest.setImei(imei);
		addDeviceRequest.setAutoObserver(autoObserver);
		addDeviceRequest.setImsi(imsi);
		addDeviceRequest.setPskType(pskType);
		addDeviceRequest.setPskValue(pskValue);
		List<TelAddDeviceRequest> deviceRequests = new ArrayList<>();
		deviceRequests.add(addDeviceRequest);
		batchAddDeviceRequest.setDevices(deviceRequests);
		TelBatchAddDeviceResponse batchAddDeviceResponse = telecomDeviceService.batchAddDevice(telecomConfigDomain, batchAddDeviceRequest);
		if (batchAddDeviceResponse.isSuccess()) {
			System.out.format(ColorUtils.blackBold("imei:%s add success"), imei);
			System.out.format(" " + "%n");
		}

	}

	public boolean checkImsiFormat(String input) {
		String pattern = "^[0-9]{1,15}$";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(input);
		return matcher.matches();
	}

	public boolean checkPskValueFormat(String input) {
		String pattern = "^[A-Za-z0-9]{16}$";
		Pattern regex = Pattern.compile(pattern);
		Matcher matcher = regex.matcher(input);
		return matcher.matches();
	}
}
