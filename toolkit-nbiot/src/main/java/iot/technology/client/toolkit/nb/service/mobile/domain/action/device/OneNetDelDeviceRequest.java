package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class OneNetDelDeviceRequest implements Serializable {

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("device_name")
    private String deviceName;

    private String imei;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
