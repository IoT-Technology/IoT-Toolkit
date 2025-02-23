package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class OneNetDeviceDetailBody implements Serializable {

    private String did;

    private String pid;

    @JsonProperty("access_pt")
    private Integer accessPt;

    @JsonProperty("data_pt")
    private Integer dataPt;

    private String name;

    private String desc;

    private Integer status;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("activate_time")
    private String activateTime;

    @JsonProperty("last_time")
    private String lastTime;

    @JsonProperty("sec_key")
    private String secKey;

    private String imei;

    private String imsi;

    private String psk;

    @JsonProperty("enable_status")
    private Boolean enableStatus;

    private Boolean obsv;

    @JsonProperty("obsv_st")
    private Boolean obsvSt;

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

    public Integer getAccessPt() {
        return accessPt;
    }

    public void setAccessPt(Integer accessPt) {
        this.accessPt = accessPt;
    }

    public Integer getDataPt() {
        return dataPt;
    }

    public void setDataPt(Integer dataPt) {
        this.dataPt = dataPt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getActivateTime() {
        return activateTime;
    }

    public void setActivateTime(String activateTime) {
        this.activateTime = activateTime;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getSecKey() {
        return secKey;
    }

    public void setSecKey(String secKey) {
        this.secKey = secKey;
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

    public Boolean getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Boolean enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Boolean getObsv() {
        return obsv;
    }

    public void setObsv(Boolean obsv) {
        this.obsv = obsv;
    }

    public Boolean getObsvSt() {
        return obsvSt;
    }

    public void setObsvSt(Boolean obsvSt) {
        this.obsvSt = obsvSt;
    }
}
