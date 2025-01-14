/*
 * Copyright © 2019-2025 The Toolkit Authors
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
package iot.technology.client.toolkit.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    /**
     * @param src
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String src, Class<T> clazz) {
        if (StringUtils.isBlank(src) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T) src : objectMapper.readValue(src, clazz);
        } catch (Exception e) {
            System.out.format("Parse Json to Object error %s", e);
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> decodeJsonToList(List<String> strList, Class<T> c) {
        return strList.stream()
                .map(str -> jsonToObject(str, c))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    public static String object2Json(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            System.out.format("Parse Object to Json String error %s", e);
            e.printStackTrace();
            return "";
        }
    }

    public static JsonNode stringToJsonNode(String jsonString) {
        try {
            return objectMapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            System.out.format("Parse json to JsonNode error %s", e);
            e.printStackTrace();
            return null;
        }
    }

    public static JsonNode objectToJsonNode(Object object) {
        try {
            return objectMapper.valueToTree(object);
        } catch (IllegalArgumentException e) {
            System.out.format("Parse json to JsonNode error %s", e);
            e.printStackTrace();
            return null;
        }
    }

    public static String convertPrettyJson(String jsonString) {
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            String prettyJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
            return prettyJson;
        } catch (JsonProcessingException e) {
            System.out.format("Parse json to JsonNode error %s", e);
            e.printStackTrace();
            return "";
        }
    }

    public static String object2JsonWithInclude(Object o) {
        try {
            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            System.out.format("Parse Object to Json String error %s", e);
            e.printStackTrace();
            return "";
        }
    }
}
