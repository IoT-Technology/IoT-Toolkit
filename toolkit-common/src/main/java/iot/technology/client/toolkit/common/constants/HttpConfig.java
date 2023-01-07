/*
 * Copyright © 2019-2023 The Toolkit Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    public Integer getMaxIdleConnections() {
        return maxIdleConnections;
    }

    public Integer getKeepAliveSecond() {
        return keepAliveSecond;
    }

    public Integer getCallTimeout() {
        return callTimeout;
    }

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public Integer getReadTimeout() {
        return readTimeout;
    }

    public Integer getWriteTimeout() {
        return writeTimeout;
    }

    public Boolean getRetryOnConnectionFailure() {
        return retryOnConnectionFailure;
    }
}
