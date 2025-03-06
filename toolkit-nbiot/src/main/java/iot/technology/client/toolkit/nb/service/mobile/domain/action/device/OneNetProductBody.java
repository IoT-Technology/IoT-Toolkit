package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class OneNetProductBody implements Serializable {

    @JsonProperty("product_id")
    private String productId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("node_type")
    private String nodeType;

    @JsonProperty("desc")
    private String desc;

    @JsonProperty("own_device_count")
    private Integer ownDeviceCount;

    @JsonProperty("online_device_count")
    private Integer onlineDeviceCount;

    @JsonProperty("offline_device_count")
    private Integer offlineDeviceCount;

    @JsonProperty("not_active_device_count")
    private Integer notActiveDeviceCount;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("update_time")
    private String updateTime;

    @JsonProperty("status")
    private String status;

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getOwnDeviceCount() {
        return ownDeviceCount;
    }

    public void setOwnDeviceCount(Integer ownDeviceCount) {
        this.ownDeviceCount = ownDeviceCount;
    }

    public Integer getOnlineDeviceCount() {
        return onlineDeviceCount;
    }

    public void setOnlineDeviceCount(Integer onlineDeviceCount) {
        this.onlineDeviceCount = onlineDeviceCount;
    }

    public Integer getOfflineDeviceCount() {
        return offlineDeviceCount;
    }

    public void setOfflineDeviceCount(Integer offlineDeviceCount) {
        this.offlineDeviceCount = offlineDeviceCount;
    }

    public Integer getNotActiveDeviceCount() {
        return notActiveDeviceCount;
    }

    public void setNotActiveDeviceCount(Integer notActiveDeviceCount) {
        this.notActiveDeviceCount = notActiveDeviceCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
