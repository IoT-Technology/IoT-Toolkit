package iot.technology.client.toolkit.nb.service;

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
