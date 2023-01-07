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
package iot.technology.client.toolkit.nb.service.mobile;

import iot.technology.client.toolkit.common.constants.MobileSettings;
import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.http.HttpRequestEntity;
import iot.technology.client.toolkit.common.http.HttpRequestExecutor;
import iot.technology.client.toolkit.common.http.HttpResponseEntity;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.mobile.domain.BaseMobileResponse;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.MobAddDeviceResponse;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.MobQueryDeviceByImeiResponse;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.MobQueryDeviceListResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author mushuwei
 */
public class MobileDeviceService extends AbstractMobileService {

	public MobQueryDeviceByImeiResponse queryDeviceByImei(MobileConfigDomain config, String imei) {
		MobQueryDeviceByImeiResponse queryDeviceByImeiResponse = new MobQueryDeviceByImeiResponse();
		try {
			HttpRequestEntity entity = new HttpRequestEntity();
			entity.setType(NBTypeEnum.MOBILE.getValue());
			entity.setUrl(MobileSettings.MOBILE_DELETE_DEVICE_BY_IMEI);

			Map<String, String> params = new HashMap<>();
			params.put(MobileSettings.MOBILE_IMEI, imei);
			Map<String, String> headerMap = getHeaderMap(config);
			entity.setHeaders(headerMap);
			entity.setParams(params);
			HttpResponseEntity response = HttpRequestExecutor.executeGet(entity);
			if (StringUtils.isNotBlank(response.getBody())) {
				queryDeviceByImeiResponse = JsonUtils.jsonToObject(response.getBody(), MobQueryDeviceByImeiResponse.class);
				if (queryDeviceByImeiResponse.getErrno() == 0) {
					queryDeviceByImeiResponse.setSuccess(Boolean.TRUE);
				} else {
					System.out.format(ColorUtils.redError(queryDeviceByImeiResponse.getError()));
					queryDeviceByImeiResponse.setSuccess(Boolean.FALSE);
				}
			} else {
				queryDeviceByImeiResponse.setSuccess(Boolean.FALSE);
				System.out.format(config.getProductId() + ColorUtils.redError(" queryDeviceByImei failed!"));
			}
			return queryDeviceByImeiResponse;
		} catch (Exception e) {
			queryDeviceByImeiResponse.setSuccess(Boolean.FALSE);
			System.out.format(config.getProductId() + ColorUtils.redError(" queryDeviceByImei failed!"));
			return queryDeviceByImeiResponse;
		}
	}

	public BaseMobileResponse delDeviceByImei(MobileConfigDomain config, String imei) {
		BaseMobileResponse baseMobileResponse = new BaseMobileResponse();
		try {
			MobQueryDeviceByImeiResponse queryDeviceByImeiResponse = queryDeviceByImei(config, imei);
			if (!queryDeviceByImeiResponse.isSuccess()) {
				return baseMobileResponse;
			}
			HttpRequestEntity entity = new HttpRequestEntity();
			entity.setType(NBTypeEnum.MOBILE.getValue());
			entity.setUrl(MobileSettings.MOBILE_DEVICE_URL + "/" + queryDeviceByImeiResponse.getData().getId());
			Map<String, String> headerMap = getHeaderMap(config);
			entity.setHeaders(headerMap);
			HttpResponseEntity response = HttpRequestExecutor.executeDelete(entity);
			if (StringUtils.isNotBlank(response.getBody())) {
				baseMobileResponse = JsonUtils.jsonToObject(response.getBody(), BaseMobileResponse.class);
				if (baseMobileResponse.getErrno() == 0) {
					baseMobileResponse.setSuccess(Boolean.TRUE);
				} else {
					System.out.format(ColorUtils.redError(baseMobileResponse.getError()));
					baseMobileResponse.setSuccess(Boolean.FALSE);
				}
			} else {
				baseMobileResponse.setSuccess(Boolean.FALSE);
				System.out.format(imei + ColorUtils.redError(" delete failed!"));
			}
			return baseMobileResponse;
		} catch (Exception e) {
			baseMobileResponse.setSuccess(Boolean.FALSE);
			System.out.format(imei + ColorUtils.redError(" delete failed!"));
			return baseMobileResponse;
		}

	}

	public BaseMobileResponse updateByImei(MobileConfigDomain config, String imei, String name) {
		BaseMobileResponse baseMobileResponse = new BaseMobileResponse();
		MobQueryDeviceByImeiResponse queryDeviceByImeiResponse = queryDeviceByImei(config, imei);
		if (!queryDeviceByImeiResponse.isSuccess()) {
			return baseMobileResponse;
		}
		try {
			HttpRequestEntity entity = new HttpRequestEntity();
			entity.setType(NBTypeEnum.MOBILE.getValue());
			entity.setUrl(MobileSettings.MOBILE_DEVICE_URL + "/" + queryDeviceByImeiResponse.getData().getId());
			Map<String, String> headerMap = getHeaderMap(config);
			entity.setHeaders(headerMap);
			Map<String, String> params = new HashMap<>();
			params.put("title", name);
			entity.setParams(params);
			HttpResponseEntity response = HttpRequestExecutor.executePut(entity);
			if (StringUtils.isNotBlank(response.getBody())) {
				baseMobileResponse = JsonUtils.jsonToObject(response.getBody(), BaseMobileResponse.class);
				if (baseMobileResponse.getErrno() == 0) {
					baseMobileResponse.setSuccess(Boolean.TRUE);
				} else {
					System.out.format(ColorUtils.redError(baseMobileResponse.getError()));
					baseMobileResponse.setSuccess(Boolean.FALSE);
				}
			} else {
				baseMobileResponse.setSuccess(Boolean.FALSE);
				System.out.format(imei + ColorUtils.redError(" update failed!"));
			}
			return baseMobileResponse;
		} catch (Exception e) {
			baseMobileResponse.setSuccess(Boolean.FALSE);
			System.out.format(imei + ColorUtils.redError(" update failed!"));
			return baseMobileResponse;
		}

	}

	public MobQueryDeviceListResponse queryDeviceList(MobileConfigDomain config, Integer page, String keyWords) {
		MobQueryDeviceListResponse queryDeviceListResponse = new MobQueryDeviceListResponse();
		try {
			HttpRequestEntity entity = new HttpRequestEntity();
			entity.setType(NBTypeEnum.MOBILE.getValue());
			entity.setUrl(MobileSettings.MOBILE_DEVICE_URL);
			Map<String, String> headerMap = getHeaderMap(config);
			entity.setHeaders(headerMap);
			Map<String, String> params = new HashMap<>();
			params.put("key_words", keyWords);
			params.put("page", page + "");
			params.put("per_page", "20");
			entity.setParams(params);
			HttpResponseEntity response = HttpRequestExecutor.executeGet(entity);
			if (StringUtils.isNotBlank(response.getBody())) {
				queryDeviceListResponse = JsonUtils.jsonToObject(response.getBody(), MobQueryDeviceListResponse.class);
				if (queryDeviceListResponse.getErrno() == 0) {
					queryDeviceListResponse.setSuccess(Boolean.TRUE);
				} else {
					System.out.format(ColorUtils.redError(queryDeviceListResponse.getError()));
					queryDeviceListResponse.setSuccess(Boolean.FALSE);
				}
			} else {
				queryDeviceListResponse.setSuccess(Boolean.FALSE);
				System.out.format(config.getProductId() + ColorUtils.redError(" queryDeviceList failed!"));
			}
			return queryDeviceListResponse;
		} catch (Exception e) {
			queryDeviceListResponse.setSuccess(Boolean.FALSE);
			System.out.format(config.getProductId() + ColorUtils.redError(" queryDeviceList failed!"));
			return queryDeviceListResponse;
		}

	}

	public MobAddDeviceResponse addDevice(MobileConfigDomain config, String imei, String name, String pskValue) {
		MobAddDeviceResponse addDeviceResponse = new MobAddDeviceResponse();
		try {
			HttpRequestEntity entity = new HttpRequestEntity();
			entity.setType(NBTypeEnum.MOBILE.getValue());
			entity.setUrl(MobileSettings.MOBILE_DEVICE_URL);
			Map<String, String> headerMap = getHeaderMap(config);
			entity.setHeaders(headerMap);

			Map<String, String> body = new HashMap<>();
			body.put("protocol", "LwM2M");
			body.put("title", name);
			StringBuilder sb = new StringBuilder();
			sb.append("{\"").append(imei).append("\":\"").append(imei).append("\"}");
			body.put("auth_info", sb.toString());
			if (Objects.nonNull(pskValue)) {
				body.put("psk", pskValue);
			}
			entity.setJson(JsonUtils.object2Json(body));
			HttpResponseEntity response = HttpRequestExecutor.executePost(entity);
			if (StringUtils.isNotBlank(response.getBody())) {
				addDeviceResponse = JsonUtils.jsonToObject(response.getBody(), MobAddDeviceResponse.class);
				if (addDeviceResponse.getErrno() == 0) {
					addDeviceResponse.setSuccess(Boolean.TRUE);
				} else {
					System.out.format(ColorUtils.redError(addDeviceResponse.getError()));
					addDeviceResponse.setSuccess(Boolean.FALSE);
				}
			} else {
				addDeviceResponse.setSuccess(Boolean.FALSE);
				System.out.format(imei + ColorUtils.redError(" addDevice failed!"));
			}
			return addDeviceResponse;
		} catch (Exception e) {
			addDeviceResponse.setSuccess(Boolean.FALSE);
			System.out.format(imei + ColorUtils.redError(" addDevice failed!"));
			return addDeviceResponse;
		}
	}
}
