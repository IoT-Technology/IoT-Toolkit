package iot.technology.client.toolkit.common.http;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class HttpGetResponseEntity implements Serializable {

    private String body;

    private Map<String, List<String>> multiMap;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, List<String>> getMultiMap() {
        return multiMap;
    }

    public void setMultiMap(Map<String, List<String>> multiMap) {
        this.multiMap = multiMap;
    }
}
