package iot.technology.client.toolkit.nb.service.telecom;

import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.constants.TelecomSettings;
import iot.technology.client.toolkit.common.http.HttpRequestEntity;
import iot.technology.client.toolkit.common.http.HttpRequestExecutor;
import iot.technology.client.toolkit.common.utils.JSONUtils;
import iot.technology.client.toolkit.common.utils.SignUtils;
import iot.technology.client.toolkit.nb.config.TelecomNbConfig;
import iot.technology.client.toolkit.nb.domain.telecom.SingleAddDeviceDomain;
import iot.technology.client.toolkit.nb.domain.telecom.BatchDeviceDomain;
import iot.technology.client.toolkit.nb.service.AbstractTelecomService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public static String addDevice(TelecomNbConfig config, SingleAddDeviceDomain singleAddDeviceDomain) throws Exception {
        long timestamp = System.currentTimeMillis() + SignUtils.getTelecomRequestTimeOffset();
        HttpRequestEntity entity = new HttpRequestEntity();
        entity.setType(NBTypeEnum.TELECOM.getType());
        entity.setUrl(TelecomSettings.ADD_DEVICE_URL);
        Map<String, String> headerMap =  getHeaders(config, timestamp);
        headerMap.put(TelecomSettings.VERSION, "20181031202117");
        headerMap.put(TelecomSettings.MASTER_KEY, config.getMasterKey());

        String jsonStr = JSONUtils.toJsonString(singleAddDeviceDomain);
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

    public static String delDevice(TelecomNbConfig config, BatchDeviceDomain batchDeviceDomain) throws Exception {
        long timestamp = System.currentTimeMillis() + SignUtils.getTelecomRequestTimeOffset();
        HttpRequestEntity entity = new HttpRequestEntity();
        entity.setType(NBTypeEnum.TELECOM.getType());
        entity.setUrl(TelecomSettings.DEL_DEVICE_URL);
        Map<String, String> headerMap = getHeaders(config, timestamp);
        headerMap.put(TelecomSettings.VERSION, "20211009132842");
        headerMap.put(TelecomSettings.MASTER_KEY, config.getMasterKey());

        String jsonStr = JSONUtils.toJsonString(batchDeviceDomain);
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

    public static void main(String[] args) throws Exception {
        TelecomNbConfig config = new TelecomNbConfig();
        config.setAppKey("WLGBbIxHJI4");
        config.setAppSecret("PiqkIxyCC0");
        config.setMasterKey("546606b218d142579163e562ed5f58de");
        config.setProductId("15415761");
//        AddDeviceDomain domain = new AddDeviceDomain();
//        domain.setDeviceName("苍南测试流量计CVC300W-B");
//        domain.setImei("869619052948095");
//        domain.setOperator("mushuwei123");
//        domain.setProductId(15415761);
//        AddDeviceDomain.OtherInfo otherInfo = new AddDeviceDomain.OtherInfo();
//        otherInfo.setAutoObserver(0);
//        domain.setOther(otherInfo);
//        addDevice(config, domain);
        BatchDeviceDomain domain = new BatchDeviceDomain();
        domain.setProductId(Integer.valueOf(config.getProductId()));
        List<String> deviceIds = new ArrayList<>();
        deviceIds.add("d09bdd55e7554dfcad1d3ce9b26b8def");
        domain.setDeviceIdList(deviceIds);
        delDevice(config, domain);
    }
}
