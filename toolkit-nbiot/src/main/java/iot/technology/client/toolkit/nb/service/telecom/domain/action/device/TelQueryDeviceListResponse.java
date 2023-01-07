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

import iot.technology.client.toolkit.nb.service.telecom.domain.BaseTelResponse;

/**
 * @author mushuwei
 */
public class TelQueryDeviceListResponse extends BaseTelResponse {

	private TelQueryDeviceListBody result;

	public TelQueryDeviceListBody getResult() {
		return result;
	}

	public void setResult(TelQueryDeviceListBody result) {
		this.result = result;
	}
}
