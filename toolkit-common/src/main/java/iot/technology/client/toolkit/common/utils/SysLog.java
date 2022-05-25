package iot.technology.client.toolkit.common.utils;

/**
 * @author mushuwei
 */
public abstract class SysLog {

	public static void error(String message) {
		throw new IllegalArgumentException(message);
	}

	public static void info(String message) {
		System.out.println(message);
	}
	
}
