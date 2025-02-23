package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import iot.technology.client.toolkit.nb.service.mobile.domain.BaseOneNetResponse;

public class OneNetDelDeviceResponse extends BaseOneNetResponse {

    private OneNetDelDeviceBody data;

    public OneNetDelDeviceBody getData() {
        return data;
    }

    public void setData(OneNetDelDeviceBody data) {
        this.data = data;
    }
}
