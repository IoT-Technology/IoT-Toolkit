package iot.technology.client.toolkit.nb.service.mobile.domain.settings;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum OneNetDeviceStatusEnum {

    ONLINE(1, "online"),
    OFFLINE(0, "offline"),
    UN_ACTIVE(2, "un_active");

    private static final Map<Integer, String> codeToMsgMap = new HashMap<>();

    OneNetDeviceStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    static {
        for (OneNetDeviceStatusEnum deviceStatusEnum : OneNetDeviceStatusEnum.values()) {
            codeToMsgMap.put(deviceStatusEnum.getCode(), deviceStatusEnum.getMsg());
        }
    }

    public static String getMsgByCode(Integer code) {
        return Objects.isNull(codeToMsgMap.get(code)) ? UN_ACTIVE.getMsg() : codeToMsgMap.get(code);
    }

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
