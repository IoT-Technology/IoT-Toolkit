package iot.technology.client.toolkit.nb.service.mobile;

import iot.technology.client.toolkit.common.constants.MobileSettings;
import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.constants.OneNetSettings;
import iot.technology.client.toolkit.common.http.HttpRequestEntity;
import iot.technology.client.toolkit.common.http.HttpRequestExecutor;
import iot.technology.client.toolkit.common.http.HttpResponseEntity;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.data.OneNetCachedCommandResponse;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.data.OneNetDeviceLatestDataResponse;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.*;
import iot.technology.client.toolkit.nb.service.mobile.domain.settings.OneNetRespCodeEnum;

import java.util.HashMap;
import java.util.Map;

public class OneNetService extends AbstractMobileService {

    public OneNetProductResponse getProductDetail(MobileConfigDomain config) {
        OneNetProductResponse productDetailResponse = new OneNetProductResponse();
        try {
            HttpRequestEntity entity = new HttpRequestEntity();
            entity.setType(NBTypeEnum.MOBILE.getValue());
            entity.setUrl(OneNetSettings.PRODUCT_DETAIL_URL);
            Map<String, String> headerMap = getOneNetHeaderMap(config);
            entity.setHeaders(headerMap);
            Map<String, String> params = new HashMap<>();
            params.put("product_id", config.getProductId());
            entity.setParams(params);
            HttpResponseEntity response = HttpRequestExecutor.executeGet(entity);
            if (StringUtils.isNotBlank(response.getBody())) {
                productDetailResponse = JsonUtils.jsonToObject(response.getBody(), OneNetProductResponse.class);
                if (productDetailResponse.getCode().equals(OneNetRespCodeEnum.SUCCESS.getCode())) {
                    productDetailResponse.setSuccess(Boolean.TRUE);
                } else {
                    System.out.format(ColorUtils.redError(productDetailResponse.getMsg()));
                    productDetailResponse.setSuccess(Boolean.FALSE);
                }
            } else {
                System.out.format(config.getProductId() + ColorUtils.redError(" get product detail failed!"));
                productDetailResponse.setSuccess(Boolean.FALSE);
            }
            return productDetailResponse;
        } catch (Exception e) {
            productDetailResponse.setSuccess(Boolean.FALSE);
            System.out.format(config.getProductId() + ColorUtils.redError(" get product detail failed!"));
            return productDetailResponse;
        }
    }


    public OneNetCreateDeviceResponse create(MobileConfigDomain config, OneNetCreateDeviceRequest request) {
        OneNetCreateDeviceResponse createDeviceResponse = new OneNetCreateDeviceResponse();
        try {
            HttpRequestEntity entity = new HttpRequestEntity();
            entity.setType(NBTypeEnum.MOBILE.getValue());
            entity.setUrl(OneNetSettings.ADD_DEVICE_URL);
            Map<String, String> headerMap = getOneNetHeaderMap(config);
            entity.setHeaders(headerMap);

            String requestJson = JsonUtils.object2Json(request);
            entity.setJson(requestJson);
            HttpResponseEntity response = HttpRequestExecutor.executePost(entity);
            if (StringUtils.isNotBlank(response.getBody())) {
                createDeviceResponse = JsonUtils.jsonToObject(response.getBody(), OneNetCreateDeviceResponse.class);
                if (createDeviceResponse.getCode().equals(OneNetRespCodeEnum.SUCCESS.getCode())) {
                    createDeviceResponse.setSuccess(Boolean.TRUE);
                } else {
                    System.out.format(ColorUtils.redError(createDeviceResponse.getMsg()));
                    createDeviceResponse.setSuccess(Boolean.FALSE);
                }
            } else {
                System.out.format(request.getImei() + ColorUtils.redError(" addDevice failed!"));
                createDeviceResponse.setSuccess(Boolean.FALSE);
            }
            return createDeviceResponse;
        } catch (Exception e) {
            createDeviceResponse.setSuccess(Boolean.FALSE);
            System.out.format(request.getImei() + ColorUtils.redError(" addDevice failed!"));
            return createDeviceResponse;
        }
    }

    public OneNetDelDeviceResponse delete(MobileConfigDomain config, OneNetDelDeviceRequest request) {
        OneNetDelDeviceResponse delDeviceResponse = new OneNetDelDeviceResponse();
        try {
            HttpRequestEntity entity = new HttpRequestEntity();
            entity.setType(NBTypeEnum.MOBILE.getValue());
            entity.setUrl(OneNetSettings.DELETE_DEVICE_URL);
            Map<String, String> headerMap = getOneNetHeaderMap(config);
            entity.setHeaders(headerMap);

            String requestJson = JsonUtils.object2Json(request);
            entity.setJson(requestJson);
            HttpResponseEntity response = HttpRequestExecutor.executePost(entity);
            if (StringUtils.isNotBlank(response.getBody())) {
                delDeviceResponse = JsonUtils.jsonToObject(response.getBody(), OneNetDelDeviceResponse.class);
                if (delDeviceResponse.getCode().equals(OneNetRespCodeEnum.SUCCESS.getCode())) {
                    delDeviceResponse.setSuccess(Boolean.TRUE);
                } else {
                    System.out.format(ColorUtils.redError(delDeviceResponse.getMsg()));
                    delDeviceResponse.setSuccess(Boolean.FALSE);
                }
            } else {
                System.out.format(request.getImei() + ColorUtils.redError(" delDevice failed!"));
                delDeviceResponse.setSuccess(Boolean.FALSE);
            }
            return delDeviceResponse;
        } catch (Exception e) {
            delDeviceResponse.setSuccess(Boolean.FALSE);
            System.out.format(request.getImei() + ColorUtils.redError(" delDevice failed!"));
            return delDeviceResponse;
        }
    }

    public OneNetDeviceDetailResponse get(MobileConfigDomain config, OneNetDeviceDetailRequest request) {
        OneNetDeviceDetailResponse deviceDetailResponse = new OneNetDeviceDetailResponse();
        try {
            HttpRequestEntity entity = new HttpRequestEntity();
            entity.setType(NBTypeEnum.MOBILE.getValue());
            entity.setUrl(OneNetSettings.DETAIL_DEVICE_URL);
            Map<String, String> headerMap = getOneNetHeaderMap(config);
            entity.setHeaders(headerMap);

            Map<String, String> params = new HashMap<>();
            params.put("product_id", config.getProductId());
            params.put("imei", request.getImei());
            entity.setParams(params);
            HttpResponseEntity response = HttpRequestExecutor.executeGet(entity);
            if (StringUtils.isNotBlank(response.getBody())) {
                deviceDetailResponse = JsonUtils.jsonToObject(response.getBody(), OneNetDeviceDetailResponse.class);
                if (deviceDetailResponse.getCode().equals(OneNetRespCodeEnum.SUCCESS.getCode())) {
                    deviceDetailResponse.setSuccess(Boolean.TRUE);
                } else {
                    System.out.format(ColorUtils.redError(deviceDetailResponse.getMsg()));
                    deviceDetailResponse.setSuccess(Boolean.FALSE);
                }
            } else {
                System.out.format(request.getImei() + ColorUtils.redError(" getDevice failed!"));
                deviceDetailResponse.setSuccess(Boolean.FALSE);
            }
            return deviceDetailResponse;
        } catch (Exception e) {
            deviceDetailResponse.setSuccess(Boolean.FALSE);
            System.out.format(request.getImei() + ColorUtils.redError(" getDevice failed!"));
            return deviceDetailResponse;
        }
    }

    public OneNetUpdateDeviceResponse update(MobileConfigDomain config, OneNetUpdateDeviceRequest request) {
        OneNetUpdateDeviceResponse updateDeviceResponse = new OneNetUpdateDeviceResponse();
        try {
            HttpRequestEntity entity = new HttpRequestEntity();
            entity.setType(NBTypeEnum.MOBILE.getValue());
            entity.setUrl(OneNetSettings.UPDATE_DEVICE_URL);
            Map<String, String> headerMap = getOneNetHeaderMap(config);
            entity.setHeaders(headerMap);

            String requestJson = JsonUtils.object2Json(request);
            entity.setJson(requestJson);
            HttpResponseEntity response = HttpRequestExecutor.executePost(entity);
            if (StringUtils.isNotBlank(response.getBody())) {
                updateDeviceResponse = JsonUtils.jsonToObject(response.getBody(), OneNetUpdateDeviceResponse.class);
                if (updateDeviceResponse.getCode().equals(OneNetRespCodeEnum.SUCCESS.getCode())) {
                    updateDeviceResponse.setSuccess(Boolean.TRUE);
                } else {
                    System.out.format(ColorUtils.redError(updateDeviceResponse.getMsg()));
                    updateDeviceResponse.setSuccess(Boolean.FALSE);
                }
            } else {
                System.out.format(request.getImei() + ColorUtils.redError(" updateDevice failed!"));
                updateDeviceResponse.setSuccess(Boolean.FALSE);
            }
            return updateDeviceResponse;
        } catch (Exception e) {
            updateDeviceResponse.setSuccess(Boolean.FALSE);
            System.out.format(request.getImei() + ColorUtils.redError(" updateDevice failed!"));
            return updateDeviceResponse;
        }
    }

    public OneNetDeviceListResponse list(MobileConfigDomain config, OneNetDeviceListRequest request) {
        OneNetDeviceListResponse deviceListResponse = new OneNetDeviceListResponse();
        try {
            HttpRequestEntity entity = new HttpRequestEntity();
            entity.setType(NBTypeEnum.MOBILE.getValue());
            entity.setUrl(OneNetSettings.LIST_DEVICE_URL);
            Map<String, String> headerMap = getOneNetHeaderMap(config);
            entity.setHeaders(headerMap);
            Map<String, String> params = new HashMap<>();
            params.put("product_id", request.getProductId());
            params.put("device_name", request.getDeviceName() + "");
            params.put("offset", request.getOffset() + "");
            params.put("limit", request.getLimit() + "");
            entity.setParams(params);
            HttpResponseEntity response = HttpRequestExecutor.executeGet(entity);
            if (StringUtils.isNotBlank(response.getBody())) {
                deviceListResponse = JsonUtils.jsonToObject(response.getBody(), OneNetDeviceListResponse.class);
                if (deviceListResponse.getCode().equals(OneNetRespCodeEnum.SUCCESS.getCode())) {
                    deviceListResponse.setSuccess(Boolean.TRUE);
                } else {
                    System.out.format(ColorUtils.redError(deviceListResponse.getMsg()));
                    deviceListResponse.setSuccess(Boolean.FALSE);
                }
            } else {
                System.out.format(request.getDeviceName() + ColorUtils.redError(" device list failed!"));
                deviceListResponse.setSuccess(Boolean.FALSE);
            }
            return deviceListResponse;
        } catch (Exception e) {
            deviceListResponse.setSuccess(Boolean.FALSE);
            System.out.format(request.getDeviceName() + ColorUtils.redError(" device list failed!"));
            return deviceListResponse;
        }
    }

    public OneNetCachedCommandResponse getCachedCommandList(MobileConfigDomain config, String imei,
                                                            String startTime, String endTime,
                                                            Integer pageNo, Integer pageSize) {
        OneNetCachedCommandResponse oneNetCachedCommandResponse = new OneNetCachedCommandResponse();
        try {
            HttpRequestEntity entity = new HttpRequestEntity();
            entity.setType(NBTypeEnum.MOBILE.getValue());
            entity.setUrl(OneNetSettings.HISTORY_OFFLINE_COMMAND_URL);
            Map<String, String> headerMap = getOneNetHeaderMap(config);
            entity.setHeaders(headerMap);

            Map<String, String> params = new HashMap<>();
            params.put("imei", imei);
            params.put("start", startTime);
            params.put("end", endTime);
            params.put("page", pageNo + "");
            params.put("per_page", pageSize + "");
            entity.setParams(params);
            HttpResponseEntity response = HttpRequestExecutor.executeGet(entity);
            if (StringUtils.isNotBlank(response.getBody())) {
                oneNetCachedCommandResponse = JsonUtils.jsonToObject(response.getBody(), OneNetCachedCommandResponse.class);
                if (oneNetCachedCommandResponse.getCode().equals(OneNetRespCodeEnum.SUCCESS.getCode())) {
                    oneNetCachedCommandResponse.setSuccess(Boolean.TRUE);
                } else {
                    System.out.format(ColorUtils.redError(oneNetCachedCommandResponse.getMsg()));
                    oneNetCachedCommandResponse.setSuccess(Boolean.FALSE);
                }
            } else {
                oneNetCachedCommandResponse.setSuccess(Boolean.FALSE);
                System.out.format(config.getProductId() + ColorUtils.redError(" getCachedCommandList failed!"));
            }
            return oneNetCachedCommandResponse;
        } catch (Exception e) {
            oneNetCachedCommandResponse.setSuccess(Boolean.FALSE);
            System.out.format(config.getProductId() + ColorUtils.redError(" getCachedCommandList failed!"));
            return oneNetCachedCommandResponse;
        }
    }

    public OneNetDeviceLatestDataResponse getCurrentDataPoints(MobileConfigDomain config, String imei) {
        OneNetDeviceLatestDataResponse deviceLatestDataResponse = new OneNetDeviceLatestDataResponse();
        try {
            HttpRequestEntity entity = new HttpRequestEntity();
            entity.setType(NBTypeEnum.MOBILE.getValue());
            entity.setUrl(OneNetSettings.CURRENT_DATA_POINTS_URL);
            Map<String, String> headerMap = getOneNetHeaderMap(config);
            entity.setHeaders(headerMap);
            Map<String, String> params = new HashMap<>();
            params.put("imei", imei);
            params.put("product_id", config.getProductId());
            entity.setParams(params);
            HttpResponseEntity response = HttpRequestExecutor.executeGet(entity);
            if (StringUtils.isNotBlank(response.getBody())) {
                deviceLatestDataResponse = JsonUtils.jsonToObject(response.getBody(), OneNetDeviceLatestDataResponse.class);
                if (deviceLatestDataResponse.getCode().equals(OneNetRespCodeEnum.SUCCESS.getCode())) {
                    deviceLatestDataResponse.setSuccess(Boolean.TRUE);
                } else {
                    System.out.format(ColorUtils.redError(deviceLatestDataResponse.getMsg()));
                    deviceLatestDataResponse.setSuccess(Boolean.FALSE);
                }
            } else {
                deviceLatestDataResponse.setSuccess(Boolean.FALSE);
                System.out.format(config.getProductId() + ColorUtils.redError(" getCurrentDataPoints failed!"));
            }

            return deviceLatestDataResponse;
        } catch (Exception e) {
            deviceLatestDataResponse.setSuccess(Boolean.FALSE);
            System.out.format(config.getProductId() + ColorUtils.redError(" getCurrentDataPoints failed!"));
            return deviceLatestDataResponse;
        }
    }

}
