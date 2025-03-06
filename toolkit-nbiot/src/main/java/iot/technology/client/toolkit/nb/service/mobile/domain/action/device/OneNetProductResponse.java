package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import iot.technology.client.toolkit.nb.service.mobile.domain.BaseOneNetResponse;


public class OneNetProductResponse extends BaseOneNetResponse {

    private OneNetProductBody data;

    public OneNetProductBody getData() {
        return data;
    }

    public void setData(OneNetProductBody data) {
        this.data = data;
    }
}
