package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import iot.technology.client.toolkit.nb.service.mobile.domain.BaseOneNetResponse;

public class OneNetDeviceDetailResponse extends BaseOneNetResponse {

    private OneNetDeviceDetailBody data;

    public OneNetDeviceDetailBody getData() {
        return data;
    }

    public void setData(OneNetDeviceDetailBody data) {
        this.data = data;
    }
}
