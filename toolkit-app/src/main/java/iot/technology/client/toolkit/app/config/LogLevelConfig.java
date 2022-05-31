package iot.technology.client.toolkit.app.config;

/**
 * @author mushuwei
 */
public class LogLevelConfig {

	// log level
	public static void setLogLevel() {
		System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "ERROR");
		System.setProperty("org.slf4j.simpleLogger.log.org.eclipse.californium", "ERROR");
		System.setProperty("org.slf4j.simpleLogger.log.org.eclipse.californium.scandium", "ERROR");
	}
}
