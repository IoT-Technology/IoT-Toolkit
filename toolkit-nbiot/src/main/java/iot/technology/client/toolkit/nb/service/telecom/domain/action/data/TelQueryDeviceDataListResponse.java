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
package iot.technology.client.toolkit.nb.service.telecom.domain.action.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import iot.technology.client.toolkit.nb.service.telecom.domain.BaseTelResponse;

import java.util.List;

/**
 * @author mushuwei
 */
public class TelQueryDeviceDataListResponse extends BaseTelResponse {

	@JsonProperty("page_timestamp")
	private String pageTimestamp;

	private List<Object> deviceStatusList;

	public String getPageTimestamp() {
		return pageTimestamp;
	}

	public void setPageTimestamp(String pageTimestamp) {
		this.pageTimestamp = pageTimestamp;
	}

	public List<Object> getDeviceStatusList() {
		return deviceStatusList;
	}

	public void setDeviceStatusList(List<Object> deviceStatusList) {
		this.deviceStatusList = deviceStatusList;
	}
}
