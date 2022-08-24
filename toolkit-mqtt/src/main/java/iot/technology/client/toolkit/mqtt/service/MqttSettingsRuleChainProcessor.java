package iot.technology.client.toolkit.mqtt.service;

import iot.technology.client.toolkit.common.constants.MqttSettingsCodeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mushuwei
 */
public class MqttSettingsRuleChainProcessor {

	public String getRootNode() {
		return MqttSettingsCodeEnum.MQTT_VERSION.getCode();
	}


	public Map<String, String> getProcessor() {
		Map<String, String> map = new HashMap<>();
		map.put(MqttSettingsCodeEnum.MQTT_VERSION.getCode(), MqttSettingsCodeEnum.MQTT_VERSION.getClazzName());
		map.put(MqttSettingsCodeEnum.CLIENT_ID.getCode(), MqttSettingsCodeEnum.CLIENT_ID.getClazzName());
		map.put(MqttSettingsCodeEnum.HOST.getCode(), MqttSettingsCodeEnum.HOST.getClazzName());
		map.put(MqttSettingsCodeEnum.PORT.getCode(), MqttSettingsCodeEnum.PORT.getClazzName());
		map.put(MqttSettingsCodeEnum.USERNAME.getCode(), MqttSettingsCodeEnum.USERNAME.getClazzName());
		map.put(MqttSettingsCodeEnum.PASSWORD.getCode(), MqttSettingsCodeEnum.PASSWORD.getClazzName());
		map.put(MqttSettingsCodeEnum.SSL.getCode(), MqttSettingsCodeEnum.SSL.getClazzName());
		map.put(MqttSettingsCodeEnum.CERT_TYPE.getCode(), MqttSettingsCodeEnum.CLIENT_ID.getClazzName());
		map.put(MqttSettingsCodeEnum.CA.getCode(), MqttSettingsCodeEnum.CA.getClazzName());
		map.put(MqttSettingsCodeEnum.CLIENT_KEY.getCode(), MqttSettingsCodeEnum.CLIENT_KEY.getClazzName());
		map.put(MqttSettingsCodeEnum.CLIENT_CERT.getCode(), MqttSettingsCodeEnum.CLIENT_CERT.getClazzName());
		map.put(MqttSettingsCodeEnum.ADVANCED.getCode(), MqttSettingsCodeEnum.ADVANCED.getClazzName());
		map.put(MqttSettingsCodeEnum.CONNECT_TIMEOUT.getCode(), MqttSettingsCodeEnum.CONNECT_TIMEOUT.getClazzName());
		map.put(MqttSettingsCodeEnum.KEEP_ALIVE.getCode(), MqttSettingsCodeEnum.KEEP_ALIVE.getClazzName());
		map.put(MqttSettingsCodeEnum.CLEAN_SESSION.getCode(), MqttSettingsCodeEnum.CLEAN_SESSION.getClazzName());
		map.put(MqttSettingsCodeEnum.AUTO_RECONNECT.getCode(), MqttSettingsCodeEnum.AUTO_RECONNECT.getClazzName());
		map.put(MqttSettingsCodeEnum.LASTWILLANDTESTAMENT.getCode(), MqttSettingsCodeEnum.LASTWILLANDTESTAMENT.getClazzName());
		map.put(MqttSettingsCodeEnum.LAST_WILL_TOPIC.getCode(), MqttSettingsCodeEnum.LAST_WILL_TOPIC.getClazzName());
		map.put(MqttSettingsCodeEnum.LAST_WILL_QOS.getCode(), MqttSettingsCodeEnum.LAST_WILL_QOS.getClazzName());
		map.put(MqttSettingsCodeEnum.LAST_WILL_RETAIN.getCode(), MqttSettingsCodeEnum.LAST_WILL_RETAIN.getClazzName());
		map.put(MqttSettingsCodeEnum.LAST_WILL_PAYLOAD.getCode(), MqttSettingsCodeEnum.LAST_WILL_PAYLOAD.getClazzName());
		return map;
	}
}
