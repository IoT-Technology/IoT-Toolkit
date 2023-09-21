package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public enum MqttActionEnum {

    PUB("pub"),

    SUB("sub"),

    UNSUB("unsub"),

    LIST("list"),

    DIS("dis"),
    ;

    MqttActionEnum(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}
