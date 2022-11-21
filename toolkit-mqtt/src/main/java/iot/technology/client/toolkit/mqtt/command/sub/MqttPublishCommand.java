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
package iot.technology.client.toolkit.mqtt.command.sub;

import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.mqtt.service.impl.MqttBizService;
import picocli.CommandLine;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "publish",
		aliases = "pub",
		requiredOptionMarker = '*',
		description = "${bundle:mqtt.pub.description}",
		optionListHeading = "%n${bundle:general.option}:%n",
		sortOptions = false,
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei"
)
public class MqttPublishCommand implements Callable<Integer> {

	private static final Charset UTF8 = StandardCharsets.UTF_8;

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);


	@Override
	public Integer call() throws Exception {
		MqttBizService bizService = new MqttBizService();
		if (bizService.notExistOrContents()) {
			//TODO add a new mqtt settings
		}
		/**
		 *
		 * 1、0~9: select mqtt configs that was set earlier
		 * 2、add a new mqtt settings
		 */
	}
}
