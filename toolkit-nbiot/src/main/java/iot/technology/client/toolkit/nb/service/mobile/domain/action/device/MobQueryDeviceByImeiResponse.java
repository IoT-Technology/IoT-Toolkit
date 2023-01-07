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
package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.nb.service.mobile.domain.BaseMobileResponse;


/**
 * @author mushuwei
 */
public class MobQueryDeviceByImeiResponse extends BaseMobileResponse {

	private MobQueryDeviceByImeiBody data;

	public void printToConsole() {
		System.out.format("deviceName:    " + ColorUtils.blackBold(data.getTitle()) + "%n");
		System.out.format("deviceId:      " + ColorUtils.blackBold(data.getId()) + "%n");
		System.out.format("online:        " + ColorUtils.blackBold(data.isOnline() ? "在线" : "离线") + "%n");
		System.out.format("observeStatus: " + ColorUtils.blackBold(data.isObserve_status() ? "订阅" : "不订阅") + "%n");
		System.out.format("createTime:    " + ColorUtils.blackBold(data.getCreate_time()) + "%n");
	}


	public MobQueryDeviceByImeiBody getData() {
		return data;
	}

	public void setData(MobQueryDeviceByImeiBody data) {
		this.data = data;
	}
}
