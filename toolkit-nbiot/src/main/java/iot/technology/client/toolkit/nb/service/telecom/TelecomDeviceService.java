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
import iot.technology.client.toolkit.nb.domain.telecom.BatchAddNBDeviceDomain;
import iot.technology.client.toolkit.nb.domain.telecom.BatchDelDeviceDomain;
import iot.technology.client.toolkit.nb.domain.telecom.DeviceInfoDomain;
import iot.technology.client.toolkit.nb.service.AbstractTelecomService;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelQueryDeviceByImeiResponse;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * device management
 * <p>
 * 1、add nb device
 * 2、delete device by imei list
 * 3、update device info
 * 4、query device info by deviceId
 */
public class TelecomDeviceService extends AbstractTelecomService {

    public TelQueryDeviceByImeiResponse querySingleDeviceByImei(TelecomConfigDomain config, String imei) {
        TelQueryDeviceByImeiResponse queryDeviceByImeiResponse = new TelQueryDeviceByImeiResponse();
        try {
            long timestamp = System.currentTimeMillis() + SignUtils.getTelecomRequestTimeOffset();
            HttpRequestEntity entity = new HttpRequestEntity();
            entity.setType(NBTypeEnum.TELECOM.getValue());
            entity.setUrl(TelecomSettings.QUERY_DEVICE_BY_IMEI);

            Map<String, String> headerMap = getHeaders(config, timestamp);
            headerMap.put(TelecomSettings.VERSION, TelecomSettings.QUERY_DEVICE_BY_IMEI_API_VERSION);

            Map<String, String> params = new HashMap<>();
            params.put(TelecomSettings.PRODUCT, config.getProductId());
            params.put(TelecomSettings.IMEI, imei);
            params.put(TelecomSettings.MASTER_KEY, config.getMasterKey());
            String signature = SignUtils.aepSignAlgorithm(params, timestamp, config.getAppKey(), config.getAppSecret(), null);
            headerMap.put(TelecomSettings.SIGNATURE, signature);
            headerMap.put(TelecomSettings.MASTER_KEY, config.getMasterKey());
            headerMap.put(TelecomSettings.HTTP_MEDIA_TYPE_HEADER, TelecomSettings.MEDIA_TYPE_JSON);
            entity.setHeaders(headerMap);
            entity.setParams(params);
            HttpGetResponseEntity response = HttpRequestExecutor.executeGet(entity);
            if (StringUtils.isNotBlank(response.getBody())) {
                queryDeviceByImeiResponse = JsonUtils.jsonToObject(response.getBody(), TelQueryDeviceByImeiResponse.class);
                if (queryDeviceByImeiResponse.getCode() == 0) {
                    queryDeviceByImeiResponse.setSuccess(Boolean.TRUE);
                } else {
                    System.out.format(ColorUtils.redError(queryDeviceByImeiResponse.getMsg()));
                    queryDeviceByImeiResponse.setSuccess(Boolean.FALSE);
                }
                return queryDeviceByImeiResponse;
            } else {
                queryDeviceByImeiResponse.setSuccess(Boolean.FALSE);
                System.out.format(config.getProductId() + ColorUtils.redError(" querySingleDeviceByImei failed!"));
                return queryDeviceByImeiResponse;
            }
        } catch (Exception e) {
            queryDeviceByImeiResponse.setSuccess(false);
            System.out.format(config.getProductId() + ColorUtils.redError(" querySingleDeviceByImei failed!"));
            return queryDeviceByImeiResponse;
        }

    }

    public static String addNBDevice(TelecomConfigDomain config, BatchAddNBDeviceDomain batchAddNBDeviceDomain) throws Exception {
        long timestamp = System.currentTimeMillis() + SignUtils.getTelecomRequestTimeOffset();
        HttpRequestEntity entity = new HttpRequestEntity();
        entity.setType(NBTypeEnum.TELECOM.getValue());
        entity.setUrl(TelecomSettings.ADD_DEVICE_URL);
        Map<String, String> headerMap = getHeaders(config, timestamp);
        headerMap.put(TelecomSettings.VERSION, "20181031202117");
        headerMap.put(TelecomSettings.MASTER_KEY, config.getMasterKey());

        String jsonStr = JsonUtils.object2Json(batchAddNBDeviceDomain);
        byte[] bodyBytes = jsonStr.getBytes(StandardCharsets.UTF_8);

        Map<String, String> params = new HashMap<>();
        params.put(TelecomSettings.MASTER_KEY, config.getMasterKey());
        String signature = SignUtils.aepSignAlgorithm(params, timestamp, config.getAppKey(), config.getAppSecret(), bodyBytes);
        headerMap.put(TelecomSettings.SIGNATURE, signature);
        entity.setHeaders(headerMap);
        entity.setJson(jsonStr);
        String response = HttpRequestExecutor.executePost(entity);
        System.out.println(response);
        return response;
    }

    public static String delDeviceByImeiList(TelecomConfigDomain config, BatchDelDeviceDomain batchDelDeviceDomain) throws Exception {
        long timestamp = System.currentTimeMillis() + SignUtils.getTelecomRequestTimeOffset();
        HttpRequestEntity entity = new HttpRequestEntity();
        entity.setType(NBTypeEnum.TELECOM.getValue());
        entity.setUrl(TelecomSettings.DEL_DEVICE_URL);
        Map<String, String> headerMap = getHeaders(config, timestamp);
        headerMap.put(TelecomSettings.VERSION, "20211009132842");
        headerMap.put(TelecomSettings.MASTER_KEY, config.getMasterKey());

        String jsonStr = JsonUtils.object2Json(batchDelDeviceDomain);
        byte[] bodyBytes = jsonStr.getBytes(StandardCharsets.UTF_8);

        Map<String, String> params = new HashMap<>();
        params.put(TelecomSettings.MASTER_KEY, config.getMasterKey());
        String signature = SignUtils.aepSignAlgorithm(params, timestamp, config.getAppKey(), config.getAppSecret(), bodyBytes);
        headerMap.put(TelecomSettings.SIGNATURE, signature);
        entity.setHeaders(headerMap);
        entity.setJson(jsonStr);
        String response = HttpRequestExecutor.executePost(entity);
        System.out.println(response);
        return response;
    }

    public static String updateDevice(DeviceInfoDomain deviceInfo) {
        return null;
    }

    public static String getDeviceList() {
        return null;
    }

//    public static void main(String[] args) {
//        TelecomConfigDomain config = new TelecomConfigDomain();
//        config.setAppKey("WLGBbIxHJI4");
//        config.setAppSecret("PiqkIxyCC0");
//        config.setProductId("15311170");
//        config.setMasterKey("b27336acc98b48889b5a819b65f247f6");
//        TelQueryDeviceByImeiResponse response = querySingleDeviceByImei(config, "869619052945851");
//        System.out.println(JsonUtils.object2Json(response));
//    }

}
