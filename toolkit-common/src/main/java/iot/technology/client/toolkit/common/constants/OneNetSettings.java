package iot.technology.client.toolkit.common.constants;


public interface OneNetSettings {

    String AUTH_VERSION = "2022-05-01";
    String AUTH_RES_PREFIX = "products/";
    String AUTH_METHOD = "sha256";
    String AUTH_HEADER = "Authorization";

    String ROOT_URL = "https://iot-api.heclouds.com";
    String DEVICE_URL = ROOT_URL + "/device";
    String PRODUCT_URL = ROOT_URL + "/product";
    String COMMAND_URL = ROOT_URL + "/nb-iot";
    String DATAPOINT_URL = ROOT_URL + "/datapoint";

    String ADD_DEVICE_URL = DEVICE_URL + "/create";
    String DETAIL_DEVICE_URL = DEVICE_URL + "/detail";
    String DELETE_DEVICE_URL = DEVICE_URL + "/delete";
    String UPDATE_DEVICE_URL = DEVICE_URL + "/update";
    String LIST_DEVICE_URL = DEVICE_URL + "/list";

    String PRODUCT_DETAIL_URL = PRODUCT_URL + "/detail";

    String OFFLINE_COMMAND_URL = COMMAND_URL + "/offline";
    String HISTORY_OFFLINE_COMMAND_URL = OFFLINE_COMMAND_URL + "/history";

    String CURRENT_DATA_POINTS_URL = DATAPOINT_URL + "/current-datapoints";
    String HISTORY_DATA_POINTS_URL = DATAPOINT_URL + "/history-datapoints";

}
