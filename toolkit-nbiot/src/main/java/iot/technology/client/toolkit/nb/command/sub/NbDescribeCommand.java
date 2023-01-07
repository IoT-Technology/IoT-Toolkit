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
package iot.technology.client.toolkit.nb.command.sub;

import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "describe",
		aliases = "desc",
		requiredOptionMarker = '*',
		description = "${bundle:nb.desc.desc}",
		optionListHeading = "%n${bundle:general.option}:%n",
		sortOptions = false,
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei"
)
public class NbDescribeCommand implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(blue),bold " +
				"                            LwM2M (Lightweight machine-to-machine)" + "|@") + "%n");
		System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(blue),bold " +
				"LwM2M协议是OMA组织制定的轻量化的M2M协议,主要面向基于蜂窝的窄带物联网" +
				"|@") + "%n");
		System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(blue),bold " +
				"（Narrow Band Internet of Things, NB-IoT）场景下物联网应用,聚焦于低功耗广覆盖（LPWA）物联网（IoT）市场" + "|@") + "%n");
		System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(blue),bold " +
				"是一种可在全球范围内广泛应用的新兴技术。具有覆盖广、连接多、速率低、成本低、功耗低、架构优等特点。" +
				"|@") + "%n");
		System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(blue),bold " +
				"NB-IoT具备四大特点：" + "|@") + "%n");
		System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(blue),bold " +
				"* 广覆盖：在同样的频段下，NB-IoT比现有的网络增益20dB，相当于提升了100倍覆盖区域的能力" + "|@") + "%n");
		System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(blue),bold " +
				"* 大连接：轻松支持大量设备联网需求，具备支撑海量连接的能力，NB-IoT一个扇区能够支持10万个连接" + "|@") + "%n");
		System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(blue),bold " +
				"* 低功耗：聚焦小数据量、小速率应用特别对于一些不能经常更换电池的设备和场合，理论上NB-IoT终端模块的待机时间可长达10年" +
				"|@") + "%n");
		System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(blue),bold " +
				"* 低成本：预期的单个接连模块不超过5美元" + "|@") + "%n");
		System.out.format("" + "%n");
		System.out.format(
				CommandLine.Help.Ansi.AUTO.string("@|fg(blue),italic " + "官方地址: " + "https://omaspecworks.org/" + "|@") + "%n");
		System.out.format(CommandLine.Help.Ansi.AUTO.string("@|fg(blue),italic " + "版本协议文档: "
				+ "http://www.openmobilealliance.org/release/LightweightM2M/" + "|@") + "%n");
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
