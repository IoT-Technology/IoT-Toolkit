package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class OneNetCreateDeviceRequest implements Serializable {

    /**
     * required
     */
    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("device_name")
    private String deviceName;

    private String imei;

    private String imsi;


    /**
     * optional
     */
    private String psk;

    private String desc;

    private String lon;

    private String lat;

    @JsonProperty("auth_code")
    private String authCode;

    private String tags;

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

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getPsk() {
        return psk;
    }

    public void setPsk(String psk) {
        this.psk = psk;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
