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
package iot.technology.client.toolkit.nb.service.telecom;

import iot.technology.client.toolkit.common.constants.TelecomSettings;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.SignUtils;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTelecomService {

	public static Map<String, String> getHeaderMap(TelecomConfigDomain config, String apiVersion, Map<String, String> params, byte[] body) {
		Map<String, String> headerMap = null;
		try {
			long timestamp = System.currentTimeMillis() + SignUtils.getTelecomRequestTimeOffset();
			headerMap = getHeaders(config, timestamp);
			headerMap.put(TelecomSettings.VERSION, apiVersion);
			String signature = SignUtils.aepSignAlgorithm(params, timestamp, config.getAppKey(), config.getAppSecret(), body);
			headerMap.put(TelecomSettings.SIGNATURE, signature);
			headerMap.put(TelecomSettings.MASTER_KEY, config.getMasterKey());
			headerMap.put(TelecomSettings.HTTP_MEDIA_TYPE_HEADER, TelecomSettings.MEDIA_TYPE_JSON);
		} catch (Exception e) {
			System.out.format(ColorUtils.redError("getHeaderMap failed!"));
		}
		return headerMap;

	}

	private static Map<String, String> getHeaders(TelecomConfigDomain config, long timestamp) {
		Map<String, String> headers = new HashMap<>();
		String dateString = SignUtils.getTelecomDataString(timestamp);
		headers.put(TelecomSettings.TIMESTAMP, "" + timestamp);
		headers.put(TelecomSettings.APPLICATION, config.getAppKey());
		headers.put(TelecomSettings.DATA_HEADER, dateString);
		return headers;
	}
}
