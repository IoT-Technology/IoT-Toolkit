package iot.technology.client.toolkit.common.constants;


public interface OneNetSettings {

    String AUTH_VERSION = "2022-05-01";
    String AUTH_RES_PREFIX = "products/";
    String AUTH_METHOD = "sha256";
    String AUTH_HEADER = "Authorization";

    String ROOT_URL = "https://iot-api.heclouds.com";
    String DEVICE_URL = ROOT_URL + "/device";
    String ADD_DEVICE_URL = DEVICE_URL + "/create";
    String DETAIL_DEVICE_URL = DEVICE_URL + "/detail";
    String DELETE_DEVICE_URL = DEVICE_URL + "/delete";
    String UPDATE_DEVICE_URL = DEVICE_URL + "/update";
    String LIST_DEVICE_URL = DEVICE_URL + "/list";


}
