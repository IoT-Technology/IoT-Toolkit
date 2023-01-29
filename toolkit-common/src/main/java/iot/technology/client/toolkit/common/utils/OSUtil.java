package iot.technology.client.toolkit.common.utils;

import iot.technology.client.toolkit.common.constants.OSEnum;

/**
 * @author mushuwei
 * @description: get the operating system of the host
 */
public class OSUtil {

	private static OSEnum osEnum = null;
	private static final String operateSys = System.getProperty("os.name").toLowerCase();

	public static OSEnum getOS() {
		if (operateSys.contains("win")) {
			osEnum = OSEnum.WINDOWS;
		} else if (operateSys.contains("nix")
				|| operateSys.contains("nux")
				|| operateSys.contains("aix")) {
			osEnum = OSEnum.LINUX;
		} else if (operateSys.contains("mac")) {
			osEnum = OSEnum.MAC;
		}
		return osEnum;
	}
}
