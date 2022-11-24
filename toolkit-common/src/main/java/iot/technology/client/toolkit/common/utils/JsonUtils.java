/*
 * Copyright © 2019-2022 The Toolkit Authors
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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


/**
 * @author mushuwei
 */
public class JsonUtils {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
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


}
