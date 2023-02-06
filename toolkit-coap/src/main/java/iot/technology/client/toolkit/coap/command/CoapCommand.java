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
package iot.technology.client.toolkit.coap.command;

import iot.technology.client.toolkit.coap.command.sub.*;
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.utils.StringUtils;
import picocli.CommandLine;

import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import static iot.technology.client.toolkit.common.utils.ColorUtils.colorItalic;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "coap",
		requiredOptionMarker = '*',
		header = "@|fg(Cyan),bold ${bundle:coap.header}|@",
		description = "@|fg(Cyan),italic ${bundle:coap.description}|@",
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
		footerHeading = "%nCopyright (c) 2019-2023, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei")
public class CoapCommand implements Callable<Integer> {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	@CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "${bundle:general.help.description}")
	boolean usageHelpRequested;

	@Override
	public Integer call() {
		StringBuilder sb = new StringBuilder();
		sb.append("describe, desc:  ").append(colorItalic(bundle.getString("coap.desc.description"), "cyan"))
				.append(StringUtils.lineSeparator());
		sb.append("media-type, mt:  ").append(colorItalic(bundle.getString("coap.media.types.description"), "cyan"))
				.append(StringUtils.lineSeparator());
		sb.append("discover, disc:  ").append(colorItalic(bundle.getString("coap.disc.description"), "cyan"))
				.append(StringUtils.lineSeparator());
		sb.append("get:             ").append(colorItalic(bundle.getString("coap.get.description"), "cyan"))
				.append(StringUtils.lineSeparator());
		sb.append("post:            ").append(colorItalic(bundle.getString("coap.post.description"), "cyan"))
				.append(StringUtils.lineSeparator());
		sb.append("put:             ").append(colorItalic(bundle.getString("coap.put.description"), "cyan"))
				.append(StringUtils.lineSeparator());
		sb.append("delete, del:     ").append(colorItalic(bundle.getString("coap.del.description"), "cyan"))
				.append(StringUtils.lineSeparator());
		System.out.println(sb);
		return ExitCodeEnum.SUCCESS.getValue();
	}

}
