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

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class TelAddDeviceRequest implements Serializable {

	/**
	 * required
	 */
	private String imei;

	/**
	 * required
	 */
	private String deviceName;

	/**
	 * optional
	 * 0-true, 1-false
	 */
	private Integer autoObserver;

	/**
	 * optional
	 * The value contains a maximum of 15 digits, ranging from 0 to 9
	 */
	private String imsi;

	/**
	 * optional, 0-char, 1-hex
	 */
	private Integer pskType;

	/**
	 * optional
	 * A 16-bit string of uppercase and lowercase letters plus 0-9 digits
	 */
	private String pskValue;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Integer getAutoObserver() {
		return autoObserver;
	}

	public void setAutoObserver(Integer autoObserver) {
		this.autoObserver = autoObserver;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public Integer getPskType() {
		return pskType;
	}

	public void setPskType(Integer pskType) {
		this.pskType = pskType;
	}

	public String getPskValue() {
		return pskValue;
	}

	public void setPskValue(String pskValue) {
		this.pskValue = pskValue;
	}
}
