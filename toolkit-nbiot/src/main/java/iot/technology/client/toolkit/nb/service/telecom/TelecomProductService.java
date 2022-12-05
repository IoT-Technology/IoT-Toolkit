package iot.technology.client.toolkit.nb.service.telecom;

import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.constants.TelecomSettings;
import iot.technology.client.toolkit.common.http.HttpGetResponseEntity;
import iot.technology.client.toolkit.common.http.HttpRequestEntity;
import iot.technology.client.toolkit.common.http.HttpRequestExecutor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.common.utils.SignUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.domain.telecom.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.AbstractTelecomService;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.product.TelDeleteProductResponse;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.product.TelQueryProductResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * product management
 * 1、 delete product
 * 2、 single query product
 */
public class TelecomProductService extends AbstractTelecomService {

	public static TelQueryProductResponse queryProduct(TelecomConfigDomain config) {
		try {
			long timestamp = System.currentTimeMillis() + SignUtils.getTelecomRequestTimeOffset();
			HttpRequestEntity entity = new HttpRequestEntity();
			entity.setType(NBTypeEnum.TELECOM.getType());
			entity.setUrl(TelecomSettings.PRODUCT_URL);
			Map<String, String> headerMap = getHeaders(config, timestamp);
			headerMap.put(TelecomSettings.VERSION, TelecomSettings.QUERY_PRODUCT_API_VERSION);
			Map<String, String> params = new HashMap<>();
			params.put(TelecomSettings.PRODUCT, config.getProductId());
			String signature = SignUtils.aepSignAlgorithm(params, timestamp, config.getAppKey(), config.getAppSecret(), null);
			headerMap.put(TelecomSettings.SIGNATURE, signature);
			entity.setHeaders(headerMap);
			entity.setParams(params);
			HttpGetResponseEntity response = HttpRequestExecutor.executeGet(entity);
			if (StringUtils.isNotBlank(response.getBody())) {
				return JsonUtils.jsonToObject(response.getBody(), TelQueryProductResponse.class);
			}
		} catch (Exception e) {
			System.out.format(ColorUtils.redError("query product failed!"));
		}
		return null;
	}

	public static TelDeleteProductResponse deleteProduct(TelecomConfigDomain config) {
		try {
			long timestamp = System.currentTimeMillis() + SignUtils.getTelecomRequestTimeOffset();
			HttpRequestEntity entity = new HttpRequestEntity();
			entity.setType(NBTypeEnum.TELECOM.getType());
			entity.setUrl(TelecomSettings.PRODUCT_URL);
			Map<String, String> headerMap = getHeaders(config, timestamp);
			headerMap.put(TelecomSettings.VERSION, TelecomSettings.DELETE_PRODUCT_API_VERSION);
			headerMap.put(TelecomSettings.MASTER_KEY, config.getMasterKey());
			headerMap.put(TelecomSettings.HTTP_MEDIA_TYPE_HEADER, TelecomSettings.MEDIA_TYPE_FORM_URLENCODED);

			Map<String, String> params = new HashMap<>();
			params.put(TelecomSettings.PRODUCT, config.getProductId());
			params.put(TelecomSettings.MASTER_KEY, config.getMasterKey());

			Map<String, String> delParam = new HashMap<>();
			delParam.put(TelecomSettings.PRODUCT, config.getProductId());
			String signature = SignUtils.aepSignAlgorithm(params, timestamp, config.getAppKey(), config.getAppSecret(), null);
			headerMap.put(TelecomSettings.SIGNATURE, signature);
			entity.setHeaders(headerMap);
			entity.setParams(delParam);
			HttpGetResponseEntity response = HttpRequestExecutor.executeDelete(entity);
			if (StringUtils.isNotBlank(response.getBody())) {
				return JsonUtils.jsonToObject(response.getBody(), TelDeleteProductResponse.class);
			}
		} catch (Exception e) {
			System.out.format(ColorUtils.redError("delete product failed!"));
		}
		return null;
	}
}
