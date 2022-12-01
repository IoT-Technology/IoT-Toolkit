/*
 * Copyright © 2019-2022 The Toolkit Authors
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
package iot.technology.client.toolkit.mqtt.command;

import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.mqtt.command.sub.MqttDescribeCommand;
import iot.technology.client.toolkit.mqtt.command.sub.MqttPublishCommand;
import iot.technology.client.toolkit.mqtt.command.sub.MqttSettingsCommand;
import iot.technology.client.toolkit.mqtt.command.sub.MqttSubscribeCommand;
import picocli.CommandLine;

import java.util.ResourceBundle;
import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "mqtt",
		requiredOptionMarker = '*',
		header = "@|fg(Cyan),bold ${bundle:mqtt.header}|@",
		description = "@|fg(Cyan),italic ${bundle:mqtt.description}|@",
		optionListHeading = "%n${bundle:general.option}:%n",
		subcommands = {
				MqttDescribeCommand.class,
				MqttSettingsCommand.class,
				MqttPublishCommand.class,
				MqttSubscribeCommand.class
		},
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei")
public class MqttCommand implements Callable<Integer> {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "${bundle:general.help.description}")
	boolean usageHelpRequested;

	@Override
	public Integer call() {
		System.out.format("describe, desc: " + ColorUtils.blueAnnotation(bundle.getString("mqtt.desc.description")));
		System.out.format("settings, set:  " + ColorUtils.blueAnnotation(bundle.getString("mqtt.settings.desc")));
		System.out.format("publish, pub:   " + ColorUtils.blueAnnotation(bundle.getString("mqtt.pub.description")));
		System.out.format("subscribe, sub: " + ColorUtils.blueAnnotation(bundle.getString("mqtt.sub.description")));
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
