package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;


import iot.technology.client.toolkit.nb.service.mobile.domain.BaseOneNetResponse;

public class OneNetCreateDeviceResponse extends BaseOneNetResponse {

   private OneNetCreateDeviceBody data;


    public OneNetCreateDeviceBody getData() {
        return data;
    }

    public void setData(OneNetCreateDeviceBody data) {
        this.data = data;
    }
}
