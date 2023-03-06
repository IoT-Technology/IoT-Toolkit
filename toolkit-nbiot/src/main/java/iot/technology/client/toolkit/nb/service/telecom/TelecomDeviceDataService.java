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
package iot.technology.client.toolkit.nb.service.telecom;

import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.constants.TelecomSettings;
import iot.technology.client.toolkit.common.http.HttpRequestEntity;
import iot.technology.client.toolkit.common.http.HttpRequestExecutor;
import iot.technology.client.toolkit.common.http.HttpResponseEntity;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.data.TelQueryDeviceCommandListResponse;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.data.TelQueryDeviceDataListResponse;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.data.TelQueryDeviceDataTotalResponse;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelQueryDeviceByImeiResponse;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author mushuwei
 */
public class TelecomDeviceDataService extends AbstractTelecomService {

	private static TelecomDeviceService deviceService = new TelecomDeviceService();
	public static final String DEFAULT_VALUE = null;

	public TelQueryDeviceDataTotalResponse getTelDeviceDataTotal(TelecomConfigDomain config, String id,
																 String startTime, String endTime) {
		TelQueryDeviceDataTotalResponse queryDeviceDataTotalResponse = new TelQueryDeviceDataTotalResponse();

		try {
			HttpRequestEntity entity = new HttpRequestEntity();
			entity.setType(NBTypeEnum.TELECOM.getValue());
			entity.setUrl(TelecomSettings.TEL_QUERY_DEVICE_DATA_TOTAL_URL);

			Map<String, Object> body = new HashMap<>();
			body.put("productId", config.getProductId());
			body.put("deviceId", id);
			body.put("begin_timestamp", startTime);
			body.put("end_timestamp", endTime);
			String requestJson = JsonUtils.object2Json(body);
			entity.setJson(requestJson);
			byte[] bodyArrays = requestJson.getBytes(StandardCharsets.UTF_8);
			Map<String, String> headerMap = getHeaderMap(config, TelecomSettings.TEL_QUERY_DEVICE_DATA_TOTAL_VERSION, null, bodyArrays);
			entity.setHeaders(headerMap);
			entity.setParams(null);
			HttpResponseEntity response = HttpRequestExecutor.executePost(entity);
			if (StringUtils.isNotBlank(response.getBody())) {
				queryDeviceDataTotalResponse = JsonUtils.jsonToObject(response.getBody(), TelQueryDeviceDataTotalResponse.class);
				if (queryDeviceDataTotalResponse.getCode() == 0) {
					queryDeviceDataTotalResponse.setSuccess(Boolean.TRUE);
				} else {
					System.out.format(ColorUtils.redError(queryDeviceDataTotalResponse.getMsg()));
					queryDeviceDataTotalResponse.setSuccess(Boolean.FALSE);
				}
			} else {
				queryDeviceDataTotalResponse.setSuccess(Boolean.FALSE);
				System.out.format(config.getProductId() + ColorUtils.redError(" getTelDeviceDataTotal failed!"));
			}
			return queryDeviceDataTotalResponse;
		} catch (Exception e) {
			queryDeviceDataTotalResponse.setSuccess(Boolean.FALSE);
			System.out.format(config.getProductId() + ColorUtils.redError(" getTelDeviceDataTotal failed!"));
			return queryDeviceDataTotalResponse;
		}

	}


	public TelQueryDeviceDataListResponse getTelDeviceDataList(TelecomConfigDomain config, String id,
															   String startTime, String endTime, Integer pageSize) {

		TelQueryDeviceDataListResponse queryDeviceEventListResponse = new TelQueryDeviceDataListResponse();

		try {
			HttpRequestEntity entity = new HttpRequestEntity();
			entity.setType(NBTypeEnum.TELECOM.getValue());
			entity.setUrl(TelecomSettings.TEL_QUERY_DEVICE_DATA_LIST_URL);

			Map<String, Object> body = new HashMap<>();
			body.put("productId", config.getProductId());
			body.put("deviceId", id);
			body.put("begin_timestamp", startTime);
			body.put("end_timestamp", endTime);
			body.put("page_size", pageSize);

			String requestJson = JsonUtils.object2Json(body);
			entity.setJson(requestJson);
			byte[] bodyArrays = requestJson.getBytes(StandardCharsets.UTF_8);
			Map<String, String> headerMap = getHeaderMap(config, TelecomSettings.TEL_QUERY_DEVICE_DATA_LIST_VERSION, null, bodyArrays);
			entity.setHeaders(headerMap);
			entity.setParams(null);
			HttpResponseEntity response = HttpRequestExecutor.executePost(entity);

			if (StringUtils.isNotBlank(response.getBody())) {
				queryDeviceEventListResponse = JsonUtils.jsonToObject(response.getBody(), TelQueryDeviceDataListResponse.class);
				if (queryDeviceEventListResponse.getCode() == 0) {
					queryDeviceEventListResponse.setSuccess(Boolean.TRUE);
				} else {
					System.out.format(ColorUtils.redError(queryDeviceEventListResponse.getMsg()));
					queryDeviceEventListResponse.setSuccess(Boolean.FALSE);
				}
			} else {
				queryDeviceEventListResponse.setSuccess(Boolean.FALSE);
				System.out.format(config.getProductId() + ColorUtils.redError(" getTelDeviceDataList failed!"));
			}
			return queryDeviceEventListResponse;
		} catch (Exception e) {
			queryDeviceEventListResponse.setSuccess(Boolean.FALSE);
			System.out.format(config.getProductId() + ColorUtils.redError(" getTelDeviceDataList failed!"));
			return queryDeviceEventListResponse;
		}
	}

	public TelQueryDeviceCommandListResponse getDeviceCommandData(TelecomConfigDomain config, String imei, Integer pageNo) {
		TelQueryDeviceCommandListResponse telQueryDeviceCommandListResponse = new TelQueryDeviceCommandListResponse();
		try {
			TelQueryDeviceByImeiResponse queryDeviceByImeiResponse = deviceService.querySingleDeviceByImei(config, imei);
			if (queryDeviceByImeiResponse.isSuccess() && Objects.nonNull(queryDeviceByImeiResponse.getResult().getDeviceId())) {
				String deviceId = queryDeviceByImeiResponse.getResult().getDeviceId();
				HttpRequestEntity entity = new HttpRequestEntity();
				entity.setType(NBTypeEnum.TELECOM.getValue());
				entity.setUrl(TelecomSettings.TEL_QUERY_DEVICE_COMMAND_URL);

				Map<String, String> queryParams = new HashMap<>();
				Map<String, String> encryptParams = new HashMap<>();

				encryptParams.put(TelecomSettings.MASTER_KEY, config.getMasterKey());

				encryptParams.put(TelecomSettings.PRODUCT, config.getProductId());
				queryParams.put(TelecomSettings.PRODUCT, config.getProductId());
				encryptParams.put("deviceId", deviceId);
				queryParams.put("deviceId", deviceId);

				encryptParams.put("pageSize", "20");
				queryParams.put("pageSize", "20");

				encryptParams.put("pageNow", String.valueOf(pageNo));
				queryParams.put("pageNow", String.valueOf(pageNo));

				encryptParams.put("startTime", null);
				queryParams.put("startTime", null);

				encryptParams.put("endTime", null);
				queryParams.put("endTime", null);

				Map<String, String> headerMap =
						getHeaderMap(config, TelecomSettings.TEL_QUERY_DEVICE_COMMAND_URL_VERSION, encryptParams, null);
				entity.setHeaders(headerMap);
				entity.setParams(queryParams);
				HttpResponseEntity response = HttpRequestExecutor.executeGet(entity);
				if (StringUtils.isNotBlank(response.getBody())) {
					telQueryDeviceCommandListResponse = JsonUtils.jsonToObject(response.getBody(), TelQueryDeviceCommandListResponse.class);
					if (telQueryDeviceCommandListResponse.getCode() == 0) {
						telQueryDeviceCommandListResponse.setSuccess(Boolean.TRUE);
					} else {
						System.out.format(ColorUtils.redError(telQueryDeviceCommandListResponse.getMsg()));
						telQueryDeviceCommandListResponse.setSuccess(Boolean.FALSE);
					}
				} else {
					telQueryDeviceCommandListResponse.setSuccess(Boolean.FALSE);
					System.out.format(config.getProductId() + ColorUtils.redError(" getDeviceCommandData failed!"));
				}
			}
		} catch (Exception e) {
			telQueryDeviceCommandListResponse.setSuccess(Boolean.FALSE);
			System.out.format(config.getProductId() + ColorUtils.redError(" getDeviceCommandData failed!"));
		}
		return telQueryDeviceCommandListResponse;
	}

}
