package iot.technology.client.toolkit.nb.service.mobile.domain.settings;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum OneNetRespCodeEnum {

    SUCCESS(0, "success"),
    FAIL(500, "other reasons"),
    PARAM_ERROR(10001, "param error"),
    AUTH_ERROR(10403, "auth error"),
    USER_OPERATIONS_ERROR(10407, "user operations error"),
    INTERNAL_ERROR(10500, "internal error"),

    PRODUCT_NOT_EXIST(10408, "product not exist"),
    PRODUCT_NOT_CREATE_DEVICE(10409, "product not create device"),

    DEVICE_IS_EXIST(10406, "device is exist"),
    DEVICE_NOT_EXIST(10410, "device not exist"),
    DEVICE_NOT_ONLINE(10421, "device not online"),
    ;

    private static final Map<Integer, String> codeToMsgMap = new HashMap<>();

    static {
        for (OneNetRespCodeEnum oneNetRespCodeEnum : OneNetRespCodeEnum.values()) {
            codeToMsgMap.put(oneNetRespCodeEnum.getCode(), oneNetRespCodeEnum.getMsg());
        }
    }

    public static String getMsgByCode(Integer code) {
        return Objects.isNull(codeToMsgMap.get(code)) ? FAIL.msg : codeToMsgMap.get(code);
    }

    OneNetRespCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
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
