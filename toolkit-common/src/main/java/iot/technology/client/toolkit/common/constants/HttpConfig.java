package iot.technology.client.toolkit.common.constants;

import java.io.Serializable;

public class HttpConfig implements Serializable {

    private Integer maxIdleConnections;

    private Integer keepAliveSecond;

    private Integer callTimeout;

    private Integer connectTimeout;

    private Integer readTimeout;

    private Integer writeTimeout;

    private Boolean retryOnConnectionFailure;

    public HttpConfig(Integer maxIdleConnections, Integer keepAliveSecond, Integer callTimeout,
                      Integer connectTimeout, Integer readTimeout, Integer writeTimeout,
                      Boolean retryOnConnectionFailure) {
        this.maxIdleConnections = maxIdleConnections;
        this.keepAliveSecond = keepAliveSecond;
        this.callTimeout = callTimeout;
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
        this.retryOnConnectionFailure = retryOnConnectionFailure;
    }
}
