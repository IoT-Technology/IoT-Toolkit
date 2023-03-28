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
package iot.technology.client.toolkit.nb.service.processor.telecom;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import com.github.freva.asciitable.OverflowBehaviour;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.DateUtils;
import iot.technology.client.toolkit.nb.service.processor.TelProcessContext;
import iot.technology.client.toolkit.nb.service.telecom.TelecomDeviceDataService;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.data.TelQueryDeviceCommandBody;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.data.TelQueryDeviceCommandListBody;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.data.TelQueryDeviceCommandListResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * format:command pageNow imei startTime endTime
 *
 * <p>
 * 1、command imei
 * <p>
 * 2、command imei pageNo
 *
 * @author mushuwei
 */
public class TelCommandDataDeviceProcessor extends TkAbstractProcessor implements TkProcessor {

	private static TelecomDeviceDataService deviceDataService = new TelecomDeviceDataService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("command");
	}

	@Override
	public void handle(ProcessContext context) {
		TelProcessContext processContext = (TelProcessContext) context;
		TelecomConfigDomain configDomain = processContext.getTelecomConfigDomain();

		List<String> arguArgs = List.of(context.getData().split(" "));
		if (arguArgs.size() < 2 || arguArgs.size() > 3) {
			System.out.format(ColorUtils.blackBold("argument:%s is illegal"), context.getData());
			System.out.format(" " + "%n");
			return;
		}
		String imei = "";
		int pageNo = 1;
		imei = arguArgs.get(1);
		// command imei
		if (arguArgs.size() == 2) {
		}
		// command imei pageNo
		if (arguArgs.size() == 3) {
			String pageNoStr = arguArgs.get(2);
			if (!validateLimit(pageNoStr)) {
				return;
			}
			pageNo = Integer.parseInt(pageNoStr);
		}
		TelQueryDeviceCommandListResponse responseList =
				deviceDataService.getDeviceCommandData(configDomain, imei, pageNo);
		if (responseList.isSuccess()
				&& Objects.nonNull(responseList.getResult())) {

			TelQueryDeviceCommandListBody response = responseList.getResult();
			System.out.format(ColorUtils.blackBold("productId:%s query success, totalNumber:%s, currentPageNo:%s"),
					configDomain.getProductId(),
					response.getTotal(), response.getPageNum());
			System.out.format(" " + "%n");
			if (!response.getList().isEmpty()) {
				String asciiTable = AsciiTable.getTable(AsciiTable.NO_BORDERS, response.getList(), Arrays.asList(
						new Column().header("commandId").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
								TelQueryDeviceCommandBody::getCommandId),
						new Column().header("command").maxWidth(40, OverflowBehaviour.NEWLINE)
								.minWidth(40).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(TelQueryDeviceCommandBody::getCommand),
						new Column().header("createBy").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
								TelQueryDeviceCommandBody::getCreateBy),
						new Column().header("commandStatus").minWidth(15).headerAlign(HorizontalAlign.CENTER)
								.dataAlign(HorizontalAlign.LEFT).with(
										TelQueryDeviceCommandBody::getCommandStatus),
						new Column().header("createTime").minWidth(25).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.CENTER)
								.with(s -> DateUtils.timestampToFormatterTime(s.getCreateTime())),
						new Column().header("finishTime").minWidth(25).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.CENTER)
								.with(
										s -> DateUtils.timestampToFormatterTime(s.getFinishTime())),
						new Column().header("resultCode").minWidth(20).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.CENTER)
								.with(s -> s.getResultCode()),
						new Column().header("resultMessage").maxWidth(20, OverflowBehaviour.NEWLINE).minWidth(20)
								.headerAlign(HorizontalAlign.CENTER)
								.dataAlign(HorizontalAlign.LEFT)
								.with(
										s -> s.getResultMessage())
				));
				System.out.format(asciiTable);
				System.out.format(" " + "%n");
			}
		}
	}
}
