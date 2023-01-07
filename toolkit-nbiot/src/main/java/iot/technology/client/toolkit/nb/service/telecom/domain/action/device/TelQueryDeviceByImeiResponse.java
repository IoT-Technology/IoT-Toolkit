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
package iot.technology.client.toolkit.nb.service.telecom.domain.action.device;

import iot.technology.client.toolkit.common.constants.TelAutoObserverEnum;
import iot.technology.client.toolkit.common.constants.TelDeviceStatusEnum;
import iot.technology.client.toolkit.common.constants.TelNetStatusEnum;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.DateUtils;
import iot.technology.client.toolkit.nb.service.telecom.domain.BaseTelResponse;

import java.util.Objects;


/**
 * @author mushuwei
 */
public class TelQueryDeviceByImeiResponse extends BaseTelResponse {

	private TelQueryDeviceByImeiBody result;

	public void printToConsole() {
		System.out.format("deviceId: " + ColorUtils.blackBold(result.getDeviceId()) + "%n");
		System.out.format("deviceName: " + ColorUtils.blackBold(result.getDeviceName()) + "%n");
		System.out.format("productId: " + ColorUtils.blackBold(String.valueOf(result.getProductId())) + "%n");
		System.out.format("imei: " + ColorUtils.blackBold(result.getImei()) + "%n");
		System.out.format("deviceStatus: " + ColorUtils.blackBold(TelDeviceStatusEnum.type(result.getDeviceStatus())) + "%n");
		System.out.format(
				"imsi: " + (Objects.nonNull(result.getImsi()) ? ColorUtils.blackBold(String.valueOf(result.getImsi())) : "") + "%n");
		System.out.format("firmwareVersion: " +
				(Objects.nonNull(result.getFirmwareVersion()) ? ColorUtils.blackBold(result.getFirmwareVersion()) : "") + "%n");
		System.out.format("autoObserver: " + ColorUtils.blackBold(TelAutoObserverEnum.type(result.getAutoObserver())) + "%n");
		System.out.format("netStatus: " + ColorUtils.blackBold(TelNetStatusEnum.type(result.getNetStatus())) + "%n");
		System.out.format("onlineAt: " +
				(Objects.nonNull(result.getOnlineAt()) ? ColorUtils.blackBold(DateUtils.timestampToFormatterTime(result.getOnlineAt())) :
						"") + "%n");
		System.out.format("offlineAt: " +
				(Objects.nonNull(result.getOfflineAt()) ? ColorUtils.blackBold(DateUtils.timestampToFormatterTime(result.getOfflineAt())) :
						"") + "%n");
	}

	public TelQueryDeviceByImeiBody getResult() {
		return result;
	}

	public void setResult(TelQueryDeviceByImeiBody result) {
		this.result = result;
	}
}
