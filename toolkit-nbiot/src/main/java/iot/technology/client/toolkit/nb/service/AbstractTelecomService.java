package iot.technology.client.toolkit.nb.service;

import iot.technology.client.toolkit.common.constants.TelecomSettings;
import iot.technology.client.toolkit.common.utils.SignUtils;
import iot.technology.client.toolkit.nb.domain.telecom.TelecomConfigDomain;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTelecomService {

    public static Map<String, String> getHeaders(TelecomConfigDomain config, long timestamp) {
        Map<String, String> headers = new HashMap<>();
        try {
            String dateString = SignUtils.getTelecomDataString(timestamp);
            headers.put(TelecomSettings.TIMESTAMP, "" + timestamp);
            headers.put(TelecomSettings.APPLICATION, config.getAppKey());
            headers.put(TelecomSettings.DATA_HEADER, dateString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return headers;
    }
}
