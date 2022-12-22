package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public interface MobileSettings {

	String MOBILE_AUTH_VERSION = "2018-10-31";
	String MOBILE_AUTH_RES_PREFIX = "products/";
	String MOBILE_AUTH_METHOD = "sha256";
	String MOBILE_AUTH_HEADER = "Authorization";
	String MOBILE_IMEI = "imei";


	String MOBILE_ROOT_URL = "https://api.heclouds.com/";
	String MOBILE_DEVICE_URL = MOBILE_ROOT_URL + "/devices";

	String MOBILE_DELETE_DEVICE_BY_IMEI = MOBILE_DEVICE_URL + "/getbyimei";
}
