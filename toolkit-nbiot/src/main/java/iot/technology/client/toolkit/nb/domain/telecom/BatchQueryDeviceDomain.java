package iot.technology.client.toolkit.nb.domain.telecom;

import java.io.Serializable;
import java.util.List;

public class BatchQueryDeviceDomain implements Serializable {

    private Integer productId;

    private List<String> deviceIdList;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public List<String> getDeviceIdList() {
        return deviceIdList;
    }

    public void setDeviceIdList(List<String> deviceIdList) {
        this.deviceIdList = deviceIdList;
    }
}
