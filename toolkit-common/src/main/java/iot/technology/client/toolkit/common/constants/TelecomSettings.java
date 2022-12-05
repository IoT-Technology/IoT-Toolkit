package iot.technology.client.toolkit.common.constants;

public interface TelecomSettings {

    /**
     * media_type
     */
    String HTTP_MEDIA_TYPE_HEADER = "Content-Type";
    String MEDIA_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded; charset=UTF-8";

    /**
     * telecom general configurations
     */
    String VERSION = "version";
    String MASTER_KEY = "MasterKey";
    String TIMESTAMP = "timestamp";
    String APPLICATION = "application";
    String DATA_HEADER = "Date";
    String SIGNATURE = "signature";
    String timestampHeader = "x-ag-timestamp";
    String PRODUCT = "productId";


    String ROOT_URL = "http://ag-api.ctwing.cn";
    /**
     * echo url
     */
    String ECHO_URL = ROOT_URL + "/echo";

    /**
     * product management
     */
    String QUERY_PRODUCT_API_VERSION = "20181031202055";
    String DELETE_PRODUCT_API_VERSION = "20181031202029";
    String PRODUCT_ROOT_URL = "/aep_product_management";
    String PRODUCT_URL = ROOT_URL + PRODUCT_ROOT_URL + "/product";

    /**
     * device management
     */
    String DEVICE_URL = "/aep_device_management";
    String ADD_DEVICE_URL = ROOT_URL + DEVICE_URL + "/device";
    String DEL_DEVICE_URL = ROOT_URL + DEVICE_URL + "/deleteDevice";

}
