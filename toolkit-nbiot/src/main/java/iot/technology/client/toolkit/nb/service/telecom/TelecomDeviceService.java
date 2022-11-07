package iot.technology.client.toolkit.nb.service.telecom;

import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.constants.TelecomSettings;
import iot.technology.client.toolkit.common.http.HttpRequestEntity;
import iot.technology.client.toolkit.common.http.HttpRequestExecutor;
import iot.technology.client.toolkit.common.utils.JSONUtils;
import iot.technology.client.toolkit.common.utils.SignUtils;
import iot.technology.client.toolkit.nb.config.TelecomConfig;
import iot.technology.client.toolkit.nb.domain.telecom.BatchDelDeviceDomain;
import iot.technology.client.toolkit.nb.domain.telecom.BatchAddNBDeviceDomain;
import iot.technology.client.toolkit.nb.domain.telecom.DeviceInfoDomain;
import iot.technology.client.toolkit.nb.domain.telecom.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.AbstractTelecomService;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * device management
 *
 * 1、add nb device
 * 2、delete device by imei list
 * 3、update device info
 * 4、query device info by deviceId
 */
public class TelecomDeviceService extends AbstractTelecomService {

    public static String addNBDevice(TelecomConfigDomain config, BatchAddNBDeviceDomain batchAddNBDeviceDomain) throws Exception {
        long timestamp = System.currentTimeMillis() + SignUtils.getTelecomRequestTimeOffset();
        HttpRequestEntity entity = new HttpRequestEntity();
        entity.setType(NBTypeEnum.TELECOM.getType());
        entity.setUrl(TelecomSettings.ADD_DEVICE_URL);
        Map<String, String> headerMap =  getHeaders(config, timestamp);
        headerMap.put(TelecomSettings.VERSION, "20181031202117");
        headerMap.put(TelecomSettings.MASTER_KEY, config.getMasterKey());

        String jsonStr = JSONUtils.toJsonString(batchAddNBDeviceDomain);
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
        entity.setType(NBTypeEnum.TELECOM.getType());
        entity.setUrl(TelecomSettings.DEL_DEVICE_URL);
        Map<String, String> headerMap = getHeaders(config, timestamp);
        headerMap.put(TelecomSettings.VERSION, "20211009132842");
        headerMap.put(TelecomSettings.MASTER_KEY, config.getMasterKey());

        String jsonStr = JSONUtils.toJsonString(batchDelDeviceDomain);
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


    public static void main(String[] args) throws Exception {
    }
}
