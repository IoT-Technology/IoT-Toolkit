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

	public static String listToString(List<String> list) {
		return list.stream().collect(Collectors.joining(", "));
	}
}
