package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class OneNetDelDeviceBody implements Serializable {

    private String did;

    private String pid;

    @JsonProperty("device_name")
    private String deviceName;

    private String imei;

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
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
