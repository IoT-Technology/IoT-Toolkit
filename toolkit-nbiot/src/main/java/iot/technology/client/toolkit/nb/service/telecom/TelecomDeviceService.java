package iot.technology.client.toolkit.nb.service.telecom;

import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.constants.TelecomSettings;
import iot.technology.client.toolkit.common.http.HttpRequestEntity;
import iot.technology.client.toolkit.common.http.HttpRequestExecutor;
import iot.technology.client.toolkit.common.http.HttpResponseEntity;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.AbstractTelecomService;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.*;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * device management
 * <p>
 * 1、batch add nb device
 * 2、delete device by imei list
 * 3、update device info
 * 4、query device info by imei
 */
public class TelecomDeviceService extends AbstractTelecomService {

    public TelQueryDeviceByImeiResponse querySingleDeviceByImei(TelecomConfigDomain config, String imei) {
        TelQueryDeviceByImeiResponse queryDeviceByImeiResponse = new TelQueryDeviceByImeiResponse();
        try {
            HttpRequestEntity entity = new HttpRequestEntity();
            entity.setType(NBTypeEnum.TELECOM.getValue());
            entity.setUrl(TelecomSettings.TEL_QUERY_DEVICE_BY_IMEI);
            Map<String, String> params = new HashMap<>();
            params.put(TelecomSettings.PRODUCT, config.getProductId());
            params.put(TelecomSettings.IMEI, imei);
            params.put(TelecomSettings.MASTER_KEY, config.getMasterKey());
            Map<String, String> headerMap = getHeaderMap(config, TelecomSettings.TEL_QUERY_DEVICE_BY_IMEI_API_VERSION, params, null);

            entity.setHeaders(headerMap);
            entity.setParams(params);
            HttpResponseEntity response = HttpRequestExecutor.executeGet(entity);
            if (StringUtils.isNotBlank(response.getBody())) {
                queryDeviceByImeiResponse = JsonUtils.jsonToObject(response.getBody(), TelQueryDeviceByImeiResponse.class);
                if (queryDeviceByImeiResponse.getCode() == 0) {
                    queryDeviceByImeiResponse.setSuccess(Boolean.TRUE);
                } else {
                    System.out.format(ColorUtils.redError(queryDeviceByImeiResponse.getMsg()));
                    queryDeviceByImeiResponse.setSuccess(Boolean.FALSE);
                }
            } else {
                queryDeviceByImeiResponse.setSuccess(Boolean.FALSE);
                System.out.format(config.getProductId() + ColorUtils.redError(" querySingleDeviceByImei failed!"));
            }
            return queryDeviceByImeiResponse;
        } catch (Exception e) {
            queryDeviceByImeiResponse.setSuccess(Boolean.FALSE);
            System.out.format(config.getProductId() + ColorUtils.redError(" querySingleDeviceByImei failed!"));
            return queryDeviceByImeiResponse;
        }

    }

    public TelDelDeviceByImeiResponse delDeviceByImei(TelecomConfigDomain config, List<String> imeiList) {
        TelDelDeviceByImeiResponse delDeviceByImeiResponse = new TelDelDeviceByImeiResponse();
        try {
            HttpRequestEntity entity = new HttpRequestEntity();
            entity.setType(NBTypeEnum.TELECOM.getValue());
            entity.setUrl(TelecomSettings.TEL_DELETE_DEVICE_BY_IMEI);
            Map<String, String> params = new HashMap<>();
            params.put(TelecomSettings.MASTER_KEY, config.getMasterKey());

            TelDelDeviceByImeiRequest request = new TelDelDeviceByImeiRequest();
            request.setImeiList(imeiList);
            request.setProductId(Integer.valueOf(config.getProductId()));
            String requestJson = JsonUtils.object2Json(request);
            entity.setJson(requestJson);
            byte[] bodyArrays = requestJson.getBytes(StandardCharsets.UTF_8);
            Map<String, String> headerMap = getHeaderMap(config, TelecomSettings.TEL_DELETE_DEVICE_BY_IMEI_API_VERSION, params, bodyArrays);
            entity.setHeaders(headerMap);
            entity.setParams(params);
            HttpResponseEntity response = HttpRequestExecutor.executePost(entity);
            if (StringUtils.isNotBlank(response.getBody())) {
                delDeviceByImeiResponse = JsonUtils.jsonToObject(response.getBody(), TelDelDeviceByImeiResponse.class);
                if (delDeviceByImeiResponse.getCode() == 0) {
                    delDeviceByImeiResponse.setSuccess(Boolean.TRUE);
                } else {
                    System.out.format(ColorUtils.redError(delDeviceByImeiResponse.getMsg()));
                    delDeviceByImeiResponse.setSuccess(Boolean.FALSE);
                }
            } else {
                delDeviceByImeiResponse.setSuccess(Boolean.FALSE);
                System.out.format(config.getProductId() + ColorUtils.redError(" deleteDeviceByImei failed!"));
            }
            return delDeviceByImeiResponse;
        } catch (Exception e) {
            delDeviceByImeiResponse.setSuccess(Boolean.FALSE);
            System.out.format(config.getProductId() + ColorUtils.redError(" deleteDeviceByImei failed!"));
            return delDeviceByImeiResponse;
        }
    }

    public TelUpdateDeviceResponse updateDevice(TelecomConfigDomain config, String imei, String deviceName) {
        TelUpdateDeviceResponse updateDeviceResponse = new TelUpdateDeviceResponse();
        try {
            TelQueryDeviceByImeiResponse queryDeviceByImeiResponse = this.querySingleDeviceByImei(config, imei);
            if (queryDeviceByImeiResponse.isSuccess()) {
                TelQueryDeviceByImeiBody deviceInfo = queryDeviceByImeiResponse.getResult();
                HttpRequestEntity entity = new HttpRequestEntity();
                entity.setType(NBTypeEnum.TELECOM.getValue());
                entity.setUrl(TelecomSettings.TEL_UPDATE_DEVICE);

                Map<String, String> params = new HashMap<>();
                params.put(TelecomSettings.MASTER_KEY, config.getMasterKey());
                params.put("deviceId", deviceInfo.getDeviceId());

                TelUpdateDeviceOtherRequest deviceOtherRequest = new TelUpdateDeviceOtherRequest();
                deviceOtherRequest.setImsi(deviceInfo.getImsi());
                deviceOtherRequest.setAutoObserver(deviceInfo.getAutoObserver());
                TelUpdateDeviceRequest updateDeviceRequest = new TelUpdateDeviceRequest();
                updateDeviceRequest.setDeviceName(deviceName);
                updateDeviceRequest.setOperator("toolkit");
                updateDeviceRequest.setProductId(Integer.valueOf(config.getProductId()));
                updateDeviceRequest.setOther(deviceOtherRequest);

                String requestJson = JsonUtils.object2Json(updateDeviceRequest);
                entity.setJson(requestJson);
                byte[] bodyArrays = requestJson.getBytes(StandardCharsets.UTF_8);
                Map<String, String> headerMap = getHeaderMap(config, TelecomSettings.TEL_UPDATE_DEVICE_VERSION, params, bodyArrays);
                entity.setHeaders(headerMap);
                entity.setParams(params);
                HttpResponseEntity response = HttpRequestExecutor.executePut(entity);
                if (StringUtils.isNotBlank(response.getBody())) {
                    updateDeviceResponse = JsonUtils.jsonToObject(response.getBody(), TelUpdateDeviceResponse.class);
                    if (updateDeviceResponse.getCode() == 0) {
                        updateDeviceResponse.setSuccess(Boolean.TRUE);
                    } else {
                        System.out.format(ColorUtils.redError(updateDeviceResponse.getMsg()));
                        updateDeviceResponse.setSuccess(Boolean.FALSE);
                    }
                } else {
                    updateDeviceResponse.setSuccess(Boolean.FALSE);
                    System.out.format(config.getProductId() + ColorUtils.redError(" updateDevice failed!"));
                }
                return updateDeviceResponse;
            }
        } catch (Exception e) {
            updateDeviceResponse.setSuccess(Boolean.TRUE);
            System.out.format(config.getProductId() + ColorUtils.redError(" updateDevice failed!"));
            return updateDeviceResponse;
        }
        return updateDeviceResponse;
    }


    public TelBatchAddDeviceResponse batchAddDevice(TelecomConfigDomain config, TelBatchAddDeviceRequest batchAddDeviceRequest) {
        TelBatchAddDeviceResponse batchAddDeviceResponse = new TelBatchAddDeviceResponse();
        try {
            HttpRequestEntity entity = new HttpRequestEntity();
            entity.setType(NBTypeEnum.TELECOM.getValue());
            entity.setUrl(TelecomSettings.TEL_BATCH_ADD_NB_DEVICE);
            String requestJson = JsonUtils.object2Json(batchAddDeviceRequest);
            entity.setJson(requestJson);
            byte[] bodyArrays = requestJson.getBytes(StandardCharsets.UTF_8);
            Map<String, String> headerMap = getHeaderMap(config, TelecomSettings.TEL_BATCH_ADD_NB_DEVICE_API_VERSION, null, bodyArrays);
            entity.setHeaders(headerMap);
            entity.setParams(null);
            HttpResponseEntity response = HttpRequestExecutor.executePost(entity);
            if (StringUtils.isNotBlank(response.getBody())) {
                batchAddDeviceResponse = JsonUtils.jsonToObject(response.getBody(), TelBatchAddDeviceResponse.class);
                if (batchAddDeviceResponse.getCode() == 0) {
                    batchAddDeviceResponse.setSuccess(Boolean.TRUE);
                } else {
                    System.out.format(ColorUtils.redError(batchAddDeviceResponse.getMsg()));
                    batchAddDeviceResponse.setSuccess(Boolean.FALSE);
                }
            } else {
                batchAddDeviceResponse.setSuccess(Boolean.FALSE);
                System.out.format(config.getProductId() + ColorUtils.redError(" batchAddDevice failed!"));
            }
            return batchAddDeviceResponse;
        } catch (Exception e) {
            batchAddDeviceResponse.setSuccess(Boolean.FALSE);
            System.out.format(config.getProductId() + ColorUtils.redError(" batchAddDevice failed!"));
            return batchAddDeviceResponse;
        }
    }

}
