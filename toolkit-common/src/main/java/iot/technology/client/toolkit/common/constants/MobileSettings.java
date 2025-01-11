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
package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public interface MobileSettings {

	String MOBILE_AUTH_VERSION = "2018-10-31";
	String MOBILE_AUTH_RES_PREFIX = "products/";
	String MOBILE_AUTH_METHOD = "sha256";
	String MOBILE_AUTH_HEADER = "Authorization";
	String MOBILE_IMEI = "imei";


	String MOBILE_ROOT_URL = "https://api.heclouds.com";
	String MOBILE_DEVICE_URL = MOBILE_ROOT_URL + "/devices";
	String MOBILE_CACHED_COMMAND_LIST = MOBILE_ROOT_URL + "/nbiot/offline/history";

	String MOBILE_DELETE_DEVICE_BY_IMEI = MOBILE_DEVICE_URL + "/getbyimei";
	String MOBILE_DATA_POINTS = MOBILE_DEVICE_URL + "/datapoints";
}
