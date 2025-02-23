package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import iot.technology.client.toolkit.nb.service.mobile.domain.BaseOneNetResponse;

public class OneNetDeviceListResponse extends BaseOneNetResponse {

    private OneNetDevicePageResponse data;

    public OneNetDevicePageResponse getData() {
        return data;
    }

    public void setData(OneNetDevicePageResponse data) {
        this.data = data;
    }
}
