package iot.technology.client.toolkit.nb.service.mobile;

import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.OneNetCreateDeviceRequest;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.OneNetCreateDeviceResponse;

public class OneNetDeviceService extends AbstractMobileService {

    public OneNetCreateDeviceResponse create(MobileConfigDomain config, OneNetCreateDeviceRequest request) {
        OneNetCreateDeviceResponse response = new OneNetCreateDeviceResponse();
        return response;
    }

}
