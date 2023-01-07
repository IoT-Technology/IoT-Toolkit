/*
 * Copyright © 2019-2022 The Toolkit Authors
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
