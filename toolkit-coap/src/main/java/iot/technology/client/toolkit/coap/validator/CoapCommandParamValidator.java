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
package iot.technology.client.toolkit.coap.validator;

import iot.technology.client.toolkit.common.utils.Assert;
import iot.technology.client.toolkit.common.validator.BaseValidator;

import java.net.URI;

/**
 * @author mushuwei
 */
public class CoapCommandParamValidator extends BaseValidator {

	public static final String COAPS = "coaps";
	public static final String COAP = "coap";

	public static void validateUri(URI uri) {
		Assert.isTrue(uri != null, "Null URI");
		Assert.hasText(uri.getScheme(), "Missing CoAP URI schema! Either `coap:` or `coaps:` is required.");
		Assert.isTrue(uri.getScheme().equalsIgnoreCase(COAP)
				|| uri.getScheme().equalsIgnoreCase(COAPS), String.format("Invalid CoAP URI schema [%s]. " +
				"Use either `coap:` or `coaps:`", uri.getScheme()));
	}

	public static String validatePayloadAndFile(String payload) {
		Assert.hasText(payload, "Missing payload");
		return payload;
	}
}
