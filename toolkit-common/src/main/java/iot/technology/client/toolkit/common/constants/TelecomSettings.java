/*
 * Copyright © 2019-2023 The Toolkit Authors
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

    /**
     * device data
     */
    String TEL_QUERY_DEVICE_DATA_URL = TEL_ROOT_URL + "/aep_device_status";
    String TEL_QUERY_DEVICE_DATA_LIST_URL = TEL_QUERY_DEVICE_DATA_URL + "/getDeviceStatusHisInPage";
    String TEL_QUERY_DEVICE_DATA_TOTAL_URL = TEL_QUERY_DEVICE_DATA_URL + "/api/v1/getDeviceStatusHisInTotal";
    String TEL_QUERY_DEVICE_DATA_TOTAL_VERSION = "20190928013529";
    String TEL_QUERY_DEVICE_DATA_LIST_VERSION = "20190928013337";

    /**
     * device command url and version
     */
    String TEL_DEVICE_COMMAND_ROOT_URL = TEL_ROOT_URL + "/aep_device_command";
    String TEL_QUERY_DEVICE_COMMAND_URL = TEL_DEVICE_COMMAND_ROOT_URL + "/commands";
    String TEL_QUERY_DEVICE_COMMAND_URL_VERSION = "20200814163736";

}
