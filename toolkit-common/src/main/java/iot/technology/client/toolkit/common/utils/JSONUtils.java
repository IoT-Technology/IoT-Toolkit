package iot.technology.client.toolkit.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;

public class JSONUtils {

    public static String toJsonString(Object object) {
        String json = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public static byte[] toByteArrays(Object object) {
        String jsonStr = toJsonString(object);
        return jsonStr.getBytes(StandardCharsets.UTF_8);
    }
}
