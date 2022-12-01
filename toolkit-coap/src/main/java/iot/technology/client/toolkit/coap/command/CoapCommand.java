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
package iot.technology.client.toolkit.coap.command;

import iot.technology.client.toolkit.coap.command.sub.*;
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import picocli.CommandLine;

import java.util.ResourceBundle;
import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "coap",
		requiredOptionMarker = '*',
		header = "@|fg(blue),bold ${bundle:coap.header}|@",
		description = "@|fg(blue),italic ${bundle:coap.description}|@",
		optionListHeading = "%n${bundle:general.option}:%n",
		subcommands = {
				CoapDescribeCommand.class,
				CoapMediaTypesCommand.class,
				CoapDiscoverCommand.class,
				CoapGetCommand.class,
				CoapPostCommand.class,
				CoapPutCommand.class,
				CoapDeleteCommand.class,
		},
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei")
public class CoapCommand implements Callable<Integer> {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "${bundle:general.help.description}")
	boolean usageHelpRequested;

	@Override
	public Integer call() {
		System.out.format("describe, desc:  " + ColorUtils.blueAnnotation(bundle.getString("coap.desc.description")));
		System.out.format("media-type, mt:  " + ColorUtils.blueAnnotation(bundle.getString("coap.media.types.description")));
		System.out.format("discover, disc:  " + ColorUtils.blueAnnotation(bundle.getString("coap.disc.description")));
		System.out.format("get:             " + ColorUtils.blueAnnotation(bundle.getString("coap.get.description")));
		System.out.format("post:            " + ColorUtils.blueAnnotation(bundle.getString("coap.post.description")));
		System.out.format("put:             " + ColorUtils.blueAnnotation(bundle.getString("coap.put.description")));
		System.out.format("delete, del:     " + ColorUtils.blueAnnotation(bundle.getString("coap.del.description")));
		return ExitCodeEnum.SUCCESS.getValue();
	}

}
