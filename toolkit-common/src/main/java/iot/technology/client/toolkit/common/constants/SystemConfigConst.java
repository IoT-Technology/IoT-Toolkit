package iot.technology.client.toolkit.common.constants;

import java.io.File;

/**
 * @author mushuwei
 */
public class SystemConfigConst {

	public static final String USER_HOME = System.getProperty("user.home") + File.separator + ".toolkit";

	/**
	 * toolkit config path and file name
	 */
	public static final String SYS_CONFIG_ROOT_PATH = USER_HOME + File.separator + "config";

	public static final String CONFIG_LANG_FILE_NAME = SYS_CONFIG_ROOT_PATH + File.separator + "lang";

	/**
	 * toolkit mqtt settings and file name
	 */
	public static final String SYS_MQTT_ROOT_PATH = USER_HOME + File.separator + "mqtt";

	public static final String MQTT_SETTINGS_FILE_NAME = SYS_MQTT_ROOT_PATH + File.separator + "settings.json";
}
