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

import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class MobHelpProcessor implements TkProcessor {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("help");
	}

	@Override
	public void handle(ProcessContext context) {
		StringBuilder sb = new StringBuilder();
		// list telecom nb-iot devices
		sb.append(ColorUtils.cyanAnnotation("list:    " + bundle.getString("nb.operation.list.desc")))
				.append(StringUtils.lineSeparator());
		sb.append("    usage: list searchValue pageNo").append(StringUtils.lineSeparator());
		sb.append(StringUtils.lineSeparator());

		// get telecom nb-iot device detail info
		sb.append(ColorUtils.cyanAnnotation("show:     " + bundle.getString("nb.operation.get.desc")))
				.append(StringUtils.lineSeparator());
		sb.append("    usage: show imei").append(StringUtils.lineSeparator());
		sb.append(StringUtils.lineSeparator());

		// delete telecom nb-iot device
		sb.append(ColorUtils.cyanAnnotation("del:     " + bundle.getString("nb.operation.del.desc")))
				.append(StringUtils.lineSeparator());
		sb.append("    usage: del imei").append(StringUtils.lineSeparator());
		sb.append(StringUtils.lineSeparator());

		// add telecom nb-iot device
		sb.append(ColorUtils.cyanAnnotation("add:     " + bundle.getString("nb.operation.add.desc")))
				.append(StringUtils.lineSeparator());
		sb.append("    usage: add imei name").append(StringUtils.lineSeparator());
		sb.append(StringUtils.lineSeparator());

		// update telecom nb-iot device name
		sb.append(ColorUtils.cyanAnnotation("update:  " + bundle.getString("nb.operation.update.desc")))
				.append(StringUtils.lineSeparator());
		sb.append("    usage: update imei name").append(StringUtils.lineSeparator());
		sb.append(StringUtils.lineSeparator());

		// print mobile nb-iot device reported data
		sb.append(ColorUtils.cyanAnnotation("log:      " + bundle.getString("nb.operation.log.desc")))
				.append(StringUtils.lineSeparator());
		sb.append("    usage: log imei startTime endTime pageSize").append(StringUtils.lineSeparator());
		sb.append(StringUtils.lineSeparator());

		// print mobile nb-iot device command data
		sb.append(ColorUtils.cyanAnnotation("command:  " + bundle.getString("nb.operation.command.desc")))
				.append(StringUtils.lineSeparator());
		sb.append("    usage: command imei start pageNo").append(StringUtils.lineSeparator());
		System.out.format(sb.toString());
	}
}
