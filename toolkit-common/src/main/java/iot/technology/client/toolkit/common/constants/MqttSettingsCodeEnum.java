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
package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public enum MqttSettingsCodeEnum {

	MQTT_VERSION("mqttVersion", "iot.technology.client.toolkit.mqtt.service.node.MqttVersionNode"),

	CLIENT_ID("clientId", "iot.technology.client.toolkit.mqtt.service.node.ClientIdNode"),

	HOST("host", "iot.technology.client.toolkit.mqtt.service.node.HostNode"),

	PORT("port", "iot.technology.client.toolkit.mqtt.service.node.PortNode"),

	USERNAME("username", "iot.technology.client.toolkit.mqtt.service.node.UsernameNode"),

	PASSWORD("password", "iot.technology.client.toolkit.mqtt.service.node.PasswordNode"),

	SSL("ssl", "iot.technology.client.toolkit.mqtt.service.node.SslNode"),

	CERT_TYPE("certType", "iot.technology.client.toolkit.mqtt.service.node.CertTypeNode"),

	CA("ca", "iot.technology.client.toolkit.mqtt.service.node.CaNode"),

	CLIENT_CERT("clientCert", "iot.technology.client.toolkit.mqtt.service.node.ClientCertNode"),

	CLIENT_KEY("clientKey", "iot.technology.client.toolkit.mqtt.service.node.ClientKeyNode"),

	ADVANCED("advanced", "iot.technology.client.toolkit.mqtt.service.node.AdvancedNode"),

	CONNECT_TIMEOUT("connectTimeout", "iot.technology.client.toolkit.mqtt.service.node.ConnectTimeoutNode"),

	KEEP_ALIVE("keepAlive", "iot.technology.client.toolkit.mqtt.service.node.KeepAliveNode"),

	CLEAN_SESSION("cleanSession", "iot.technology.client.toolkit.mqtt.service.node.CleanSessionNode"),

	AUTO_RECONNECT("autoReconnect", "iot.technology.client.toolkit.mqtt.service.node.AutoReconnectNode"),

	LAST_WILL_TOPIC("lastWillTopic", "iot.technology.client.toolkit.mqtt.service.node.lastWillTopicNode"),

	LAST_WILL_QOS("lastWillQoS", "iot.technology.client.toolkit.mqtt.service.node.LastWillQoSNode"),

	LAST_WILL_RETAIN("lastWillRetain", "iot.technology.client.toolkit.mqtt.service.node.LastWillRetainNode"),

	LAST_WILL_PAYLOAD("lastWillPayload", "iot.technology.client.toolkit.mqtt.service.node.LastWillPayloadNode"),

	LASTWILLANDTESTAMENT("lastWillAndTestament", "iot.technology.client.toolkit.mqtt.service.node.lastWillAndTestamentNode"),

	MQTT_BIZ_TYPE("biz", "iot.technology.client.toolkit.mqtt.service.node.MqttBizTypeNode"),

	MQTT_APP_CONFIG("mqttAppConfig", "iot.technology.client.toolkit.mqtt.service.node.MqttAppConfigNode"),

	SETTINGS_NAME("settingsName", "iot.technology.client.toolkit.mqtt.service.node.SettingsNameNode"),

	// wait for commands
	CALL("call", ""),

	END("end", "");

	private String code;

	private String clazzName;

	public String getCode() {
		return code;
	}

	public String getClazzName() {
		return clazzName;
	}

	MqttSettingsCodeEnum(String code, String clazzName) {
		this.code = code;
		this.clazzName = clazzName;
	}
}
