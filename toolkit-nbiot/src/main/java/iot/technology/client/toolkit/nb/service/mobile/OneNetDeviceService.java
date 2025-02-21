package iot.technology.client.toolkit.nb.service.mobile;

import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.constants.OneNetSettings;
import iot.technology.client.toolkit.common.http.HttpRequestEntity;
import iot.technology.client.toolkit.common.http.HttpRequestExecutor;
import iot.technology.client.toolkit.common.http.HttpResponseEntity;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.OneNetCreateDeviceRequest;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.OneNetCreateDeviceResponse;

import java.util.Map;

public class OneNetDeviceService extends AbstractMobileService {

    public OneNetCreateDeviceResponse create(MobileConfigDomain config, OneNetCreateDeviceRequest request) {
        OneNetCreateDeviceResponse createDeviceResponse = new OneNetCreateDeviceResponse();

        try {
            HttpRequestEntity entity = new HttpRequestEntity();
            entity.setType(NBTypeEnum.MOBILE.getValue());
            entity.setUrl(OneNetSettings.ADD_DEVICE_URL);
            Map<String, String> headerMap = getHeaderMap(config);
            entity.setHeaders(headerMap);

            String requestJson = JsonUtils.object2Json(request);
            entity.setJson(requestJson);
            HttpResponseEntity response = HttpRequestExecutor.executePost(entity);
            if (StringUtils.isNotBlank(response.getBody())) {
                createDeviceResponse = JsonUtils.jsonToObject(response.getBody(), OneNetCreateDeviceResponse.class);
            } else {

            }
            return createDeviceResponse;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
