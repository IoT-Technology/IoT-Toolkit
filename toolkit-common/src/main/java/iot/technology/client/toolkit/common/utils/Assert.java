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

/**
 * @author mushuwei
 */
public abstract class Assert {

	public static void hasText(String text, String message) {
		if (!StringUtils.hasText(text)) {
			SysLog.error(message);
		}
	}

	public static void isTrue(Boolean ql, String message) {
		if (!ql) {
			SysLog.error(message);
		}
	}

	public static void notNull(Object object, String message) {
		if (object == null) {
			SysLog.error(message);
		}
	}

	public static void hasLength(String text, String message) {
		if (!StringUtils.hasLength(text)) {
			SysLog.error(message);
		}
	}

	public static boolean isOnlyOneTrue(boolean... conditions) {
		int trueCount = 0;
		for (boolean condition : conditions) {
			if (condition) {
				trueCount++;
				if (trueCount > 1) {
					return false;
				}
			}
		}
		return trueCount == 1;
	}

}
