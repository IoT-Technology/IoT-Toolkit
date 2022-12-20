package iot.technology.client.toolkit.common.constants;


public interface TelecomSettings {

    /**
     * media_type
     */
    String HTTP_MEDIA_TYPE_HEADER = "Content-Type";
    String MEDIA_TYPE_FORM_URLENCODED = "application/x-www-form-urlencoded; charset=UTF-8";
    String MEDIA_TYPE_JSON = "application/json; charset=UTF-8";

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
    String IMEI = "imei";


    /**
     * Telecom API and API VERSION
     */
    String TEL_ROOT_URL = "http://ag-api.ctwing.cn";
    /**
     * echo url
     */
    String TEL_ECHO_URL = TEL_ROOT_URL + "/echo";

    /**
     * product management
     */
    String TEL_QUERY_PRODUCT_API_VERSION = "20181031202055";
    String TEL_PRODUCT_ROOT_URL = "/aep_product_management";
    String TEL_PRODUCT_URL = TEL_ROOT_URL + TEL_PRODUCT_ROOT_URL + "/product";

    /**
     * device management
     */
    String TEL_DEVICE_URL = "/aep_device_management";
    String TEL_NB_DEVICE_URL = "/aep_nb_device_management";
    String TEL_QUERY_DEVICE_BY_IMEI = TEL_ROOT_URL + TEL_NB_DEVICE_URL + "/getDeviceByImei";
    String TEL_DELETE_DEVICE_BY_IMEI = TEL_ROOT_URL + TEL_NB_DEVICE_URL + "/deleteDeviceByImei";
    String TEL_BATCH_ADD_NB_DEVICE = TEL_ROOT_URL + TEL_NB_DEVICE_URL + "/batchNBDevice";
    String TEL_UPDATE_DEVICE = TEL_ROOT_URL + TEL_DEVICE_URL + "/device";
    String TEL_QUERY_DEVICE_LIST = TEL_ROOT_URL + TEL_DEVICE_URL + "/devices";
    String TEL_QUERY_DEVICE_BY_IMEI_API_VERSION = "20221130175656";
    String TEL_DELETE_DEVICE_BY_IMEI_API_VERSION = "20220226071405";
    String TEL_BATCH_ADD_NB_DEVICE_API_VERSION = "20200828140355";
    String TEL_UPDATE_DEVICE_API_VERSION = "20181031202122";
    String TEL_QUERY_DEVICE_LIST_API_VERSION = "20190507012134";
}
