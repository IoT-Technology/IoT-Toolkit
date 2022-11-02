package iot.technology.client.toolkit.common.constants;

public enum NbIoTHttpConfigEnum {

    TELECOM_HTTP_CONFIG(1, new HttpConfig(10, 60, 5,
            5, 5, 5, true)),

    MOBILE_HTTP_CONFIG(2, new HttpConfig(10, 60, 5,
            5, 5, 5, true));


    private int type;

    private HttpConfig config;

    NbIoTHttpConfigEnum(int type, HttpConfig config) {
        this.type = type;
        this.config = config;
    }

    public int getType() {
        return type;
    }

    public HttpConfig getConfig() {
        return config;
    }
}
