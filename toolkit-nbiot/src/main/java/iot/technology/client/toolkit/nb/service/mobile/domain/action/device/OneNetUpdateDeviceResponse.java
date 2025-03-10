package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import iot.technology.client.toolkit.nb.service.mobile.domain.BaseOneNetResponse;

public class OneNetUpdateDeviceResponse extends BaseOneNetResponse {

    private OneNetUpdateDeviceBody data;

    public OneNetUpdateDeviceBody getData() {
        return data;
    }

    public void setData(OneNetUpdateDeviceBody data) {
        this.data = data;
    }
}
