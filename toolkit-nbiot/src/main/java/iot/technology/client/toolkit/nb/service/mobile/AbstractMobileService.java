/*
 * Copyright © 2019-2022 The Toolkit Authors
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
package iot.technology.client.toolkit.nb.service.mobile;

import iot.technology.client.toolkit.common.constants.MobileSettings;
import iot.technology.client.toolkit.common.utils.SignUtils;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author mushuwei
 */
public abstract class AbstractMobileService {

	public static Map<String, String> getHeaderMap(MobileConfigDomain config) {
		Map<String, String> authInfoMap = new HashMap<>();
		authInfoMap.put(MobileSettings.MOBILE_AUTH_HEADER, getAuthInfo(config));
		return authInfoMap;
	}


	public static String getAuthInfo(MobileConfigDomain config) {
		String version = MobileSettings.MOBILE_AUTH_VERSION;
		String res = MobileSettings.MOBILE_AUTH_RES_PREFIX + config.getProductId();
		long et = System.currentTimeMillis() / 1000 + TimeUnit.DAYS.toSeconds(1);
		String method = MobileSettings.MOBILE_AUTH_METHOD;
		return SignUtils.getMobileAssembleToken(version, res, Long.toString(et), method, config.getAccessKey());
	}

}
