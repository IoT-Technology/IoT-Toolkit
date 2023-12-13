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
import iot.technology.client.toolkit.nb.service.mobile.domain.BaseMobileResponse;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.processor.MobProcessContext;
import org.apache.commons.cli.*;

/**
 * format: update imei name
 *
 * @author mushuwei
 */
public class MobUpdateDeviceProcessor extends TkAbstractProcessor implements TkProcessor {

	private final MobileDeviceService mobileDeviceService = new MobileDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("update");
	}

	@Override
	public void handle(ProcessContext context) {
		MobProcessContext mobProcessContext = (MobProcessContext) context;
		MobileConfigDomain mobileConfigDomain = mobProcessContext.getMobileConfigDomain();

		Options options = new Options();
		Option imeiOption = new Option("imei", true, "the device imei");
		Option nameOption = new Option("name", true, "the device name");

		options.addOption(imeiOption)
				.addOption(nameOption);

		String imei = "";
		String name = "";
		try {
			CommandLineParser parser = new DefaultParser();
			CommandLine cmd = parser.parse(options, convertCommandData(context.getData()));

			// imei and name required
			if (!(cmd.hasOption(imeiOption) && cmd.hasOption(nameOption))) {
				StringBuilder sb = new StringBuilder();
				sb.append(ColorUtils.redError("imei and name is required")).append(StringUtils.lineSeparator);
				sb.append(ColorUtils.blackBold("detail usage please enter: help update"));
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

		} catch (ParseException e) {
			StringBuilder sb = new StringBuilder();
			sb.append(ColorUtils.redError("command parse failed!")).append(StringUtils.lineSeparator);
			System.out.println(sb);
		}
		BaseMobileResponse response = mobileDeviceService.updateByImei(mobileConfigDomain, imei, name);
		if (response.isSuccess()) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format(ColorUtils.blackBold("imei:%s update success"), imei));
			System.out.println(sb);
		}
	}
}
