package iot.technology.client.toolkit.nb.domain.telecom;

import java.io.Serializable;

public class DeviceInfoDomain implements Serializable {

    private String deviceName;

    private String operator;

    private OtherInfo other;

    private String productId;


    public static class OtherInfo {

        private int autoObserver;

        private String imsi;

        public int getAutoObserver() {
            return autoObserver;
        }

        public void setAutoObserver(int autoObserver) {
            this.autoObserver = autoObserver;
        }

        public String getImsi() {
            return imsi;
        }

        public void setImsi(String imsi) {
            this.imsi = imsi;
        }
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public OtherInfo getOther() {
        return other;
    }

    public void setOther(OtherInfo other) {
        this.other = other;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
