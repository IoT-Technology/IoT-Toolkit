/*
 * Copyright © 2019-2025 The Toolkit Authors
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
package iot.technology.client.toolkit.coap.command.sub;

import iot.technology.client.toolkit.common.utils.Assert;

import java.net.URI;
import java.util.Objects;

/**
 * @author mushuwei
 */
public abstract class AbstractCoapContext {

    public static final String COAPS = "coaps";
    public static final String COAP = "coap";


    public static String validateUri(URI uri) {
        Assert.isTrue(uri != null, "Null URI");
        Assert.hasText(Objects.requireNonNull(uri).getScheme(), "Missing CoAP URI schema! Either `coap:` or `coaps:` is required.");
        Assert.isTrue(uri.getScheme().equalsIgnoreCase(COAP)
                || uri.getScheme().equalsIgnoreCase(COAPS), String.format("Invalid CoAP URI schema [%s]. " +
                "Use either `coap:` or `coaps:`", uri.getScheme()));
        return uri.getScheme();
    }

    public static String validatePayloadAndFile(String payload) {
        Assert.hasText(payload, "Missing payload");
        return payload;
    }
}
