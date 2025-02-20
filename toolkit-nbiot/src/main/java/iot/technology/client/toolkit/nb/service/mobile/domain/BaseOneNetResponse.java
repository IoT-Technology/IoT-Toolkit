package iot.technology.client.toolkit.nb.service.mobile.domain;

import java.io.Serializable;

public class BaseOneNetResponse implements Serializable {

    private Integer code;

    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
