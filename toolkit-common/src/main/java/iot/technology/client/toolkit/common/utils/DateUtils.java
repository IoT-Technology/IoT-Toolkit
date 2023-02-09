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
package iot.technology.client.toolkit.common.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mushuwei
 */
public class DateUtils {

	private static ZoneId zoneId = ZoneId.of("Asia/Shanghai");

	public static String timestampToFormatterTime(long timestamp) {
		LocalDateTime resultDateTime = Instant.ofEpochMilli(timestamp).atZone(zoneId).toLocalDateTime();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return formatter.format(resultDateTime);
	}

	public static String getCurrentDayStartTimeForMob() {
		LocalDate today = LocalDate.now(zoneId);
		return today + "T" + "00:00:00";
	}

	public static String getCurrentDayEndTimeForMob() {
		LocalDate today = LocalDate.now(zoneId);
		return today + "T" + "23:59:59";
	}

	public static boolean mobileTimePattern(String dateTime) {
		String pattern = "^\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}$";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(dateTime);
		return m.find() ? Boolean.TRUE : Boolean.FALSE;
	}
}
