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
package iot.technology.client.toolkit.nb.service.mobile;

import iot.technology.client.toolkit.common.constants.MobileSettings;
import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.http.HttpRequestEntity;
import iot.technology.client.toolkit.common.http.HttpRequestExecutor;
import iot.technology.client.toolkit.common.http.HttpResponseEntity;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.data.OneNetCachedCommandResponse;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.data.MobDeviceHisDataResponse;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.data.MobDeviceLatestDataResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mushuwei
 */
public class MobileDeviceDataService extends AbstractMobileService {

	public MobDeviceLatestDataResponse getLatestDataPoints(MobileConfigDomain config, String id) {
		MobDeviceLatestDataResponse deviceLatestDataResponse = new MobDeviceLatestDataResponse();
		try {
			HttpRequestEntity entity = new HttpRequestEntity();
			entity.setType(NBTypeEnum.MOBILE.getValue());
			entity.setUrl(MobileSettings.MOBILE_DATA_POINTS);
			Map<String, String> headerMap = getHeaderMap(config);
			entity.setHeaders(headerMap);
			Map<String, String> params = new HashMap<>();
			params.put("devIds", id);
			entity.setParams(params);
			HttpResponseEntity response = HttpRequestExecutor.executeGet(entity);
			if (StringUtils.isNotBlank(response.getBody())) {
				deviceLatestDataResponse = JsonUtils.jsonToObject(response.getBody(), MobDeviceLatestDataResponse.class);
				if (deviceLatestDataResponse.getErrno() == 0) {
					deviceLatestDataResponse.setSuccess(Boolean.TRUE);
				} else {
					System.out.format(ColorUtils.redError(deviceLatestDataResponse.getError()));
					deviceLatestDataResponse.setSuccess(Boolean.FALSE);
				}
			} else {
				deviceLatestDataResponse.setSuccess(Boolean.FALSE);
				System.out.format(config.getProductId() + ColorUtils.redError(" getLatestDataPoints failed!"));
			}

			return deviceLatestDataResponse;
		} catch (Exception e) {
			deviceLatestDataResponse.setSuccess(Boolean.FALSE);
			System.out.format(config.getProductId() + ColorUtils.redError(" getLatestDataPoints failed!"));
			return deviceLatestDataResponse;
		}
	}


	public MobDeviceHisDataResponse getHisDataPoints(MobileConfigDomain config, String id,
													 String dataStreamIds, String start,
													 String end, int limit) {
		MobDeviceHisDataResponse deviceHisDataResponse = new MobDeviceHisDataResponse();
		if (StringUtils.isBlank(dataStreamIds)) {
			return deviceHisDataResponse;
		}
		try {
			HttpRequestEntity entity = new HttpRequestEntity();
			entity.setType(NBTypeEnum.MOBILE.getValue());
			entity.setUrl(MobileSettings.MOBILE_DEVICE_URL + "/" + id + "/datapoints");
			Map<String, String> headerMap = getHeaderMap(config);
			entity.setHeaders(headerMap);
			Map<String, String> params = new HashMap<>();
			params.put("datastream_id", dataStreamIds);
			params.put("start", start);
			params.put("end", end);
			params.put("limit", limit + "");
			entity.setParams(params);
			HttpResponseEntity response = HttpRequestExecutor.executeGet(entity);
			if (StringUtils.isNotBlank(response.getBody())) {
				deviceHisDataResponse = JsonUtils.jsonToObject(response.getBody(), MobDeviceHisDataResponse.class);
				if (deviceHisDataResponse.getErrno() == 0) {
					deviceHisDataResponse.setSuccess(Boolean.TRUE);
				} else {
					System.out.format(ColorUtils.redError(deviceHisDataResponse.getError()));
					deviceHisDataResponse.setSuccess(Boolean.FALSE);
				}
			} else {
				deviceHisDataResponse.setSuccess(Boolean.FALSE);
				System.out.format(config.getProductId() + ColorUtils.redError(" getHisDataPoints failed!"));
			}
			return deviceHisDataResponse;
		} catch (Exception e) {
			deviceHisDataResponse.setSuccess(Boolean.FALSE);
			System.out.format(config.getProductId() + ColorUtils.redError(" getHisDataPoints failed!"));
			return deviceHisDataResponse;
		}
	}

}
