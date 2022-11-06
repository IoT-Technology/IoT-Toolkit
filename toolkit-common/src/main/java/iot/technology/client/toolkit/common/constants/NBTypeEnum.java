package iot.technology.client.toolkit.common.constants;

public enum NBTypeEnum {

    TELECOM("telecom"),

    MOBILE("mobile");

    NBTypeEnum(String type) {
        this.type = type;
    }

    private String type;

    public String getType() {
        return type;
    }
}
