package iot.technology.client.toolkit.common.constants;

import okhttp3.MediaType;

public interface TelecomSettings {

    /**
     * general configurations
     */
    String VERSION = "version";
    String MASTER_KEY = "MasterKey";
    String TIMESTAMP = "timestamp";
    String APPLICATION = "application";
    String DATA_HEADER = "Date";
    String SIGNATURE = "signature";
    String timestampHeader = "x-ag-timestamp";
    String mediaType = "application/x-www-form-urlencoded; charset=UTF-8";



    String ROOT_URL = "http://ag-api.ctwing.cn";
    /**
     * echo url
     */
    String ECHO_URL = ROOT_URL + "/echo";

    /**
     * product management
     */
    String PRODUCT_URL = "aep_product_management";
    String PID_URL = ROOT_URL + PRODUCT_URL + "/product";

    /**
     * device management
     */
    String DEVICE_URL = "/aep_device_management";
    String ADD_DEVICE_URL = ROOT_URL + DEVICE_URL  + "/device";
    String DEL_DEVICE_URL = ROOT_URL + DEVICE_URL + "/deleteDevice";

}
