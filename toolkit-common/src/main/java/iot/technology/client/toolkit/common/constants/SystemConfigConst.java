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

	/**
	 * toolkit nb settings and file name
	 */
	public static final String SYS_NB_ROOT_PATH = USER_HOME + File.separator + "nb";

	public static final String SYS_NB_TELECOM_ROOT_PATH = SYS_NB_ROOT_PATH + File.separator + "telecom";

	public static final String SYS_NB_TELECOM_PRODUCT_FILE_NAME = SYS_NB_TELECOM_ROOT_PATH + File.separator + "product_settings.json";

	public static final String SYS_NB_MOBILE_ROOT_PATH = SYS_NB_ROOT_PATH + File.separator + "mobile";

	public static final String SYS_NB_MOBILE_PRODUCT_FILE_NAME = SYS_NB_MOBILE_ROOT_PATH + File.separator + "product_settings.json";

	public static final String SYS_NB_LWM2M_ROOT_PATH = SYS_NB_ROOT_PATH + File.separator + "lwm2m";

	public static final String SYS_NB_LWM2M_SETTINGS_FILE_NAME = SYS_NB_LWM2M_ROOT_PATH + File.separator + "settings.json";

}
