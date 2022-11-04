package iot.technology.client.toolkit.common.constants;

import okhttp3.MediaType;

public interface TelecomSettings {

    String timestampHeader = "x-ag-timestamp";
    String mediaType = "application/x-www-form-urlencoded; charset=UTF-8";

    String ROOT_URL = "http://ag-api.ctwing.cn/aep_product_management";
    String ECHO_URL = "http://ag-api.ctwing.cn/echo";
    String PRODUCT_URL = ROOT_URL + "/product";

    String DEVICE_URL = ROOT_URL + "/device";

}
