package iot.technology.client.toolkit.common.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

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
}
