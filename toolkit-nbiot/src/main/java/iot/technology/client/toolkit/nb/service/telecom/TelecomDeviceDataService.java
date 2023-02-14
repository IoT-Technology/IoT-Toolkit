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
import iot.technology.client.toolkit.nb.service.telecom.domain.action.data.TelQueryDeviceDataListResponse;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.data.TelQueryDeviceDataTotalResponse;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mushuwei
 */
public class TelecomDeviceDataService extends AbstractTelecomService {

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
}
