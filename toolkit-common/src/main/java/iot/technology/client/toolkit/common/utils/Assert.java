package iot.technology.client.toolkit.common.utils;

/**
 * @author mushuwei
 */
public abstract class Assert {

	public static void hasText(String text, String message) {
		if (!StringUtils.hasText(text)) {
			SysLog.error(message);
		}
	}

	public static void isTrue(Boolean ql, String message) {
		if (!ql) {
			SysLog.error(message);
		}
	}

	public static void notNull(Object object, String message) {
		if (object == null) {
			SysLog.error(message);
		}
	}

}
