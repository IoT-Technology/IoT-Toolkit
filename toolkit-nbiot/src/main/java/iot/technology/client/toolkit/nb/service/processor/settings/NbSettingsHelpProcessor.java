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
package iot.technology.client.toolkit.nb.service.processor.settings;

import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class NbSettingsHelpProcessor implements TkProcessor {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("help");
	}

	@Override
	public void handle(ProcessContext context) {
		StringBuilder sb = new StringBuilder();
		// list all mqtt settings
		sb.append(ColorUtils.cyanAnnotation("list: " + bundle.getString("nb.settings.list.desc")))
				.append(StringUtils.lineSeparator());
		sb.append("    usage: list").append(StringUtils.lineSeparator());
		sb.append(StringUtils.lineSeparator());

		// show mqtt setting detail
		sb.append(ColorUtils.cyanAnnotation("show: " + bundle.getString("nb.settings.show.desc")))
				.append(StringUtils.lineSeparator());
		sb.append("    usage: show serial").append(StringUtils.lineSeparator());
		sb.append(StringUtils.lineSeparator());

		// delete mqtt settings
		sb.append(ColorUtils.cyanAnnotation("del:  " + bundle.getString("nb.settings.del.desc")))
				.append(StringUtils.lineSeparator());
		sb.append("    usage: del serial").append(StringUtils.lineSeparator());
		sb.append(StringUtils.lineSeparator());

		// add mqtt setting
		sb.append(ColorUtils.cyanAnnotation("add:  " + bundle.getString("nb.settings.add.desc")))
				.append(StringUtils.lineSeparator());
		sb.append("    usage: add").append(StringUtils.lineSeparator());
		System.out.format(sb.toString());
	}
}
