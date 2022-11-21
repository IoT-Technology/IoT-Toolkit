package iot.technology.client.toolkit.mqtt.service.impl;

import iot.technology.client.toolkit.common.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static iot.technology.client.toolkit.common.constants.SystemConfigConst.MQTT_SETTINGS_FILE_NAME;

/**
 * @author mushuwei
 */
public class MqttBizService {

	public Boolean notExistOrContents() {
		if (!FileUtils.isExist(MQTT_SETTINGS_FILE_NAME)) {
			return true;
		}
		List<String> content;
		try {
			Path path = Paths.get(MQTT_SETTINGS_FILE_NAME);
			content = Files.lines(path).collect(Collectors.toList());
		} catch (IOException e) {
			return true;
		}
		return content.isEmpty();
	}
}
