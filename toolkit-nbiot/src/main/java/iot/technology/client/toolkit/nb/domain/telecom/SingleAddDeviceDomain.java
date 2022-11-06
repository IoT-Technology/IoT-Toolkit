package iot.technology.client.toolkit.nb.domain.telecom;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SingleAddDeviceDomain implements Serializable {

    private String deviceName;

    private String imei;

    private String operator;

    private OtherInfo other;

    private Integer productId;


    /**
     * this parameter is mandatory for LWM2M protocol
     */
    public static class OtherInfo {

        /**
         * Required;
         * 0 - auto subscription
         * 1 - cancel auto subscription
         */
        private Integer autoObserver;

        /**
         * Optional;
         * the value contains a maximum of 15 digits ranging from 0 to 9
         */
        private String imsi;

        /**
         * Optional;
         * 16 characters consisting of uppercase and lowercase letters and 0 to 9 digits
         */
        private String pskValue;


        public Integer getAutoObserver() {
            return autoObserver;
        }

        public void setAutoObserver(Integer autoObserver) {
            this.autoObserver = autoObserver;
        }

        public String getImsi() {
            return imsi;
        }

        public void setImsi(String imsi) {
            this.imsi = imsi;
        }

        public String getPskValue() {
            return pskValue;
        }

        public void setPskValue(String pskValue) {
            this.pskValue = pskValue;
        }
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
