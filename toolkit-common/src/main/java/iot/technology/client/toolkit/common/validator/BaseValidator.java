package iot.technology.client.toolkit.common.validator;

import iot.technology.client.toolkit.common.utils.Assert;
import iot.technology.client.toolkit.common.utils.StringUtils;

/**
 * @author mushuwei
 */
public abstract class BaseValidator {

	public static void validateBlankParam(String param, String value) {
		Assert.isTrue(StringUtils.isNotBlank(param), value);
	}

	public static void validateNullParam(String param, Object value) {
		Assert.isTrue(value != null, param);
	}
}
