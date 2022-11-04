package iot.technology.client.toolkit.nb.service.telecom;

import com.fasterxml.jackson.databind.ObjectMapper;
import iot.technology.client.toolkit.common.constants.TelecomSettings;
import iot.technology.client.toolkit.common.http.HttpGetResponseEntity;
import iot.technology.client.toolkit.common.http.HttpRequestEntity;
import iot.technology.client.toolkit.common.http.HttpRequestExecutor;
import iot.technology.client.toolkit.common.utils.SignUtils;
import iot.technology.client.toolkit.nb.config.TelecomNbConfig;
import iot.technology.client.toolkit.nb.service.AbstractTelecomService;

import java.util.HashMap;
import java.util.Map;

public class TelecomProductService extends AbstractTelecomService {

    public static String queryProduct(TelecomNbConfig config) {
        try {
            long timestamp = System.currentTimeMillis() + SignUtils.getTelecomRequestTimeOffset();
            HttpRequestEntity entity = new HttpRequestEntity();
            entity.setType("telecom");
            entity.setUrl(TelecomSettings.PRODUCT_URL);
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

    public static String deleteProduct(TelecomNbConfig config) {
        try {
            long timestamp = System.currentTimeMillis() + SignUtils.getTelecomRequestTimeOffset();
            HttpRequestEntity entity = new HttpRequestEntity();
            entity.setType("telecom");
            entity.setUrl(TelecomSettings.PRODUCT_URL);
            Map<String, String> headerMap =  getHeaders(config, timestamp);
            headerMap.put("version", "20181031202029");
            Map<String, String> params = new HashMap<>();
            params.put("productId", config.getProductId());
            headerMap.put("MasterKey", config.getMasterKey());
            String signature = SignUtils.aepSignAlgorithm(params, timestamp, config.getAppKey(), config.getAppSecret(), null);
            headerMap.put("signature", signature);
            entity.setHeaders(headerMap);
            entity.setParams(params);
            HttpGetResponseEntity response = HttpRequestExecutor.executeDelete(entity);
            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        TelecomNbConfig config = new TelecomNbConfig();
        config.setAppKey("WLGBbIxHJI4");
        config.setAppSecret("PiqkIxyCC0");
        config.setMasterKey("09e418fdd0154c4aaef3231bf04bf19c");
        config.setProductId("15413351");
        String body = deleteProduct(config);
        System.out.println(body);
    }
}
