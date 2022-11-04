package iot.technology.client.toolkit.common.constants;

import java.util.HashMap;
import java.util.Map;

public enum NbIoTHttpConfigEnum {

    TELECOM_HTTP_CONFIG("telecom", new HttpConfig(10, 60, 5,
            5, 5, 5, true)),

    MOBILE_HTTP_CONFIG("mobile", new HttpConfig(10, 60, 5,
            5, 5, 5, true)),

    DEFAULT_HTTP_CONFIG("default", new HttpConfig(10, 60, 5,
            5, 5, 5,true));


    public static final Map<String, NbIoTHttpConfigEnum> cache = new HashMap();

    static {
        for (NbIoTHttpConfigEnum config : NbIoTHttpConfigEnum.values()) {
            cache.put(config.type, config);
        }
    }

    public static HttpConfig type(String key) {
        NbIoTHttpConfigEnum configEnum = cache.get(key);
        if (configEnum != null) {
            return configEnum.config;
        }
        return DEFAULT_HTTP_CONFIG.getConfig();
    }

    private String type;

    private HttpConfig config;

    NbIoTHttpConfigEnum(String type, HttpConfig config) {
        this.type = type;
        this.config = config;
    }

    public String getType() {
        return type;
    }

    public HttpConfig getConfig() {
        return config;
    }
}
