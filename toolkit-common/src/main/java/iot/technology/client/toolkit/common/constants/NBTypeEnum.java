package iot.technology.client.toolkit.common.constants;

public enum NBTypeEnum {

    TELECOM("1", "telecom"),

    MOBILE("2", "mobile"),

    LWM2M("3", "lwm2m");

    NBTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    private String code;

    private String value;

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
