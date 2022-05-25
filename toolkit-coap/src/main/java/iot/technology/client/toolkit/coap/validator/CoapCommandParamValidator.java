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
