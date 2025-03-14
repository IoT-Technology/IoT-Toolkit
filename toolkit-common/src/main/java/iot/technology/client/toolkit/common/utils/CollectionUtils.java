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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mushuwei
 */
public class CollectionUtils {

	public static boolean isEmpty(Map<?, ?> map) {
		return (map == null || map.isEmpty());
	}

	public static boolean isEmpty(List<?> list) {
		return (list == null || list.isEmpty());
	}

	public static String listToString(List<String> list) {
		return list.stream().collect(Collectors.joining(", "));
	}
}
