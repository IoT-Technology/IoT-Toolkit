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

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author mushuwei
 */
public class DateUtils {

	private static final ZoneId zoneId = ZoneId.of("Asia/Shanghai");
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static String timestampToFormatterTime(long timestamp) {
		LocalDateTime resultDateTime = Instant.ofEpochMilli(timestamp).atZone(zoneId).toLocalDateTime();
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

	public static String getCurrentDayStartTimeForTel() {
		LocalDate today = LocalDate.now();
		LocalDateTime todayStart = today.atStartOfDay();
		return todayStart.toInstant(ZoneOffset.ofHours(8)).toEpochMilli() + "";

	}

	public static String getCurrentDayEndTimeForTel() {
		LocalDateTime endOfDay = LocalDateTime.of(LocalDateTime.now().getYear(),
				LocalDateTime.now().getMonthValue(),
				LocalDateTime.now().getDayOfMonth(),
				23, 59, 59);
		return endOfDay.toInstant(ZoneOffset.ofHours(8)).toEpochMilli() + "";
	}

	/**
	 * @param nbTime 2019-02-01T00:01:01
	 * @return unixTime
	 */
	public static String covertNbTimeFormatToUnixTime(String nbTime) {
		DateTimeFormatter nbFormatter = DateTimeFormatter.ISO_DATE_TIME;
		LocalDateTime dateTime = LocalDateTime.parse(nbTime, nbFormatter);
		Instant instant = dateTime.atZone(ZoneId.systemDefault()).toInstant();
		return instant.toEpochMilli() + "";

	}

	public static boolean mobileTimePattern(String dateTime) {
		String pattern = "^\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{1,2}:\\d{1,2}$";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(dateTime);
		return m.find() ? Boolean.TRUE : Boolean.FALSE;
	}
}
