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
package iot.technology.client.toolkit.mqtt.service.processor;

import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class HelpProcessor implements TkProcessor {

	private static final ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("help");
	}

	@Override
	public void handle(ProcessContext context) {
		printMqttSettingsHelpInfo();
	}


	public static void printMqttSettingsHelpInfo() {
		StringBuilder sb = new StringBuilder();
		sb.append(ColorUtils.colorBold("Welcome to Toolkit ", "red"))
				.append(StorageConstants.TOOLKIT_VERSION)
				.append(StringUtils.lineSeparator);
		sb.append(StringUtils.lineSeparator());
		sb.append(ColorUtils.colorBold(bundle.getString("general.usage"), ""))
				.append(StringUtils.lineSeparator());
		sb.append(StringUtils.lineSeparator());
		sb.append(String.format("%s %s %s",
						ColorUtils.colorBold("list: ", "yellow"),
						ColorUtils.colorItalic(bundle.getString("mqtt.settings.list.desc"), "cyan"),
						"(usage: list)"))
				.append(StringUtils.lineSeparator());
		sb.append(String.format("%s %s %s",
						ColorUtils.colorBold("show: ", "yellow"),
						ColorUtils.colorItalic(bundle.getString("mqtt.settings.show.desc"), "cyan"),
						"(usage: show serial)"))
				.append(StringUtils.lineSeparator());
		sb.append(String.format("%s %s %s",
						ColorUtils.colorBold("del:  ", "yellow"),
						ColorUtils.colorItalic(bundle.getString("mqtt.settings.del.desc"), "cyan"),
						"(usage: del serial)"))
				.append(StringUtils.lineSeparator());
		sb.append(String.format("%s %s %s",
						ColorUtils.colorBold("add:  ", "yellow"),
						ColorUtils.colorItalic(bundle.getString("mqtt.settings.add.desc"), "cyan"),
						"(usage: add)"))
				.append(StringUtils.lineSeparator());
		sb.append(StringUtils.lineSeparator());
		sb.append(ColorUtils.colorBold("Press Ctrl+C or `exit` to exit the REPL", ""))
				.append(StringUtils.lineSeparator());
		System.out.println(sb);
	}
}
