package iot.technology.client.toolkit.nb.service.telecom;

import iot.technology.client.toolkit.common.constants.TelecomSettings;
import iot.technology.client.toolkit.common.http.HttpGetResponseEntity;
import iot.technology.client.toolkit.common.http.HttpRequestEntity;
import iot.technology.client.toolkit.common.http.HttpRequestExecutor;
import iot.technology.client.toolkit.common.utils.SignUtils;
import iot.technology.client.toolkit.nb.domain.telecom.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.AbstractTelecomService;

import java.util.HashMap;
import java.util.Map;

/**
 * product management
 * 1、 delete product
 * 2、 single query product
 */
public class TelecomProductService extends AbstractTelecomService {

    public static String queryProduct(TelecomConfigDomain config) {
        try {
            long timestamp = System.currentTimeMillis() + SignUtils.getTelecomRequestTimeOffset();
            HttpRequestEntity entity = new HttpRequestEntity();
            entity.setType("telecom");
            entity.setUrl(TelecomSettings.PID_URL);
            Map<String, String> headerMap =  getHeaders(config, timestamp);
            headerMap.put("version", "20181031202055");
            Map<String, String> params = new HashMap<>();
            params.put("productId", config.getProductId());
            String signature = SignUtils.aepSignAlgorithm(params, timestamp, config.getAppKey(), config.getAppSecret(), null);
            headerMap.put("signature", signature);
            entity.setHeaders(headerMap);
            entity.setParams(params);
            HttpGetResponseEntity response = HttpRequestExecutor.executeGet(entity);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String deleteProduct(TelecomConfigDomain config) {
        try {
            long timestamp = System.currentTimeMillis() + SignUtils.getTelecomRequestTimeOffset();
            HttpRequestEntity entity = new HttpRequestEntity();
            entity.setType("telecom");
            entity.setUrl(TelecomSettings.PID_URL);
            Map<String, String> headerMap =  getHeaders(config, timestamp);
            headerMap.put("version", "20181031202029");
            headerMap.put("MasterKey", config.getMasterKey());
            headerMap.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            Map<String, String> params = new HashMap<>();
            params.put("productId", config.getProductId());
            params.put("MasterKey", config.getMasterKey());

            Map<String, String> queryParam = new HashMap<>();
            queryParam.put("productId", config.getProductId());
            String signature = SignUtils.aepSignAlgorithm(params, timestamp, config.getAppKey(), config.getAppSecret(), null);
            headerMap.put("signature", signature);
            entity.setHeaders(headerMap);
            entity.setParams(queryParam);
            HttpGetResponseEntity response = HttpRequestExecutor.executeDelete(entity);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        TelecomConfigDomain config = new TelecomConfigDomain();
        config.setAppKey("WLGBbIxHJI4");
        config.setAppSecret("PiqkIxyCC0");
        config.setMasterKey("f91dd23fece44c52af93ffe67196fa77");
        config.setProductId("15414775");
        String body = deleteProduct(config);
        System.out.println(body);
    }
}
