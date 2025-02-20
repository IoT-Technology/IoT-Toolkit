package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import java.io.Serializable;

public class OneNetCreateDeviceBody implements Serializable {

    private Object did;

    private String pid;

    private String name;

    private Integer status;

    public Object getDid() {
        return did;
    }

    public void setDid(Object did) {
        this.did = did;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
