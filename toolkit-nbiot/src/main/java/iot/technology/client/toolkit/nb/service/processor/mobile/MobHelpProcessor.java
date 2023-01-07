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
		System.out.format(ColorUtils.blueAnnotation("list: " + bundle.getString("nb.operation.list.desc")));
		System.out.format("    usage: list pageNo searchValue" + "%n");
		System.out.format(" " + "%n");
		System.out.format(ColorUtils.blueAnnotation("get: " + bundle.getString("nb.operation.get.desc")));
		System.out.format("    usage: get imei" + "%n");
		System.out.format(" " + "%n");
		System.out.format(ColorUtils.blueAnnotation("del:  " + bundle.getString("nb.operation.del.desc")));
		System.out.format("    usage: del imei" + "%n");
		System.out.format(" " + "%n");
		System.out.format(ColorUtils.blueAnnotation("add:  " + bundle.getString("nb.operation.add.desc")));
		System.out.format("    usage: add imei name" + "%n");
		System.out.format(" " + "%n");
		System.out.format(ColorUtils.blueAnnotation("update:  " + bundle.getString("nb.operation.update.desc")));
		System.out.format("    usage: update imei name" + "%n");
		System.out.format(" " + "%n");
	}
}
