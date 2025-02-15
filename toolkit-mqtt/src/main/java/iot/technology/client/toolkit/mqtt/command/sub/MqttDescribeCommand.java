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
package iot.technology.client.toolkit.mqtt.command.sub;

import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.mqtt.service.MqttBizService;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "describe",
		aliases = "desc",
		requiredOptionMarker = '*',
		description = "${bundle:mqtt.desc.description}",
		optionListHeading = "%n${bundle:general.option}:%n",
		footerHeading = "%nCopyright (c) 2019-2025, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei"
)
public class MqttDescribeCommand implements Callable<Integer> {


	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "${bundle:general.help.description}")
	boolean usageHelpRequested;

	private final MqttBizService bizService = new MqttBizService();

	@Override
	public Integer call() {
		bizService.getMqttDescription();
		return ExitCodeEnum.SUCCESS.getValue();
	}

}
