package iot.technology.client.toolkit.nb.domain.telecom;

import java.io.Serializable;
import java.util.List;

public class BatchDelDeviceDomain implements Serializable {

    private Integer productId;

    private List<String> imeiList;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public List<String> getImeiList() {
        return imeiList;
    }

    public void setImeiList(List<String> imeiList) {
        this.imeiList = imeiList;
    }
}
