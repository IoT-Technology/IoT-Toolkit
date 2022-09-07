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

	PUBLISH_MESSAGE("publishMessage", "iot.technology.client.toolkit.mqtt.service.node.PublishMessageNode"),

	SUBSCRIBE_MESSAGE("subscribeMessage", "iot.technology.client.toolkit.mqtt.service.node.SubscribeMessageNode"),

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
