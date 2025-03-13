/*
 * Copyright © 2019-2025 The Toolkit Authors
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
package iot.technology.client.toolkit.nb.service.mobile.domain.action.data;

import iot.technology.client.toolkit.common.utils.CollectionUtils;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.nb.service.mobile.domain.BaseOneNetResponse;

import java.util.List;

/**
 * @author mushuwei
 */
public class OneNetDeviceLatestDataResponse extends BaseOneNetResponse {

	private OneNetDeviceLatestDataBody data;

	public void printToConsole() {
		if (!CollectionUtils.isEmpty(data.getDevices())) {
			List<OneNetDeviceLatestDataDevicesBody> result =  data.getDevices();
			result.forEach(item -> {
				System.out.format("deviceId:        " + ColorUtils.blackBold(item.getId()) + "%n");
				System.out.format("deviceName:      " + ColorUtils.blackBold(item.getTitle()) + "%n");
				if (!CollectionUtils.isEmpty(item.getDatastreams())) {
					item.getDatastreams().forEach(ds -> {
						System.out.format("dataStreamId:    " + ColorUtils.blackBold(ds.getId()) + "%n");
						System.out.format("time:            " + ColorUtils.blackBold(ds.getAt()) + "%n");
						System.out.format("value:           " + ColorUtils.blackBold(ds.getValue()) + "%n");
					});
				}
			});
		}
	}

	public OneNetDeviceLatestDataBody getData() {
		return data;
	}

	public void setData(OneNetDeviceLatestDataBody data) {
		this.data = data;
	}
}
