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
package iot.technology.client.toolkit.mqtt.service.domain;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.handler.codec.mqtt.MqttVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import iot.technology.client.toolkit.common.constants.CertTypeEnum;
import iot.technology.client.toolkit.common.constants.ConfirmCodeEnum;
import iot.technology.client.toolkit.common.constants.MqttVersionEnum;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.mqtt.config.MqttSettings;
import iot.technology.client.toolkit.mqtt.config.MqttSettingsInfo;
import iot.technology.client.toolkit.mqtt.service.MqttClientConfig;
import iot.technology.client.toolkit.mqtt.service.core.MqttClientService;

import javax.net.ssl.SSLException;
import java.io.File;
import java.io.Serializable;

/**
 * @author mushuwei
 */
public class MqttConfigSettingsDomain implements Serializable {

	private String mqttAppConfig;

	private String selectConfig;

	private String settingsName;

	private String mqttVersion;

	private String clientId;

	private String host;

	private String port;

	private String username;

	private String password;

	private String ssl;

	private String certType;

	private String ca;

	private String clientCert;

	private String clientKey;

	private String advanced;

	private String connectTimeout;

	private String keepAlive;

	private String cleanSession;

	private String autoReconnect;

	private String lastWillAndTestament;

	private String lastWillTopic;

	private String lastWillQoS;

	private String lastWillRetain;

	private String lastWillPayload;


	private MqttClientService client;

	public MqttClientConfig convertMqttClientConfig() {
		MqttClientConfig config = new MqttClientConfig();
		if (mqttVersion.equals(MqttVersionEnum.MQTT_3_1.getValue())) {
			config.setProtocolVersion(MqttVersion.MQTT_3_1);
		}
		if (mqttVersion.equals(MqttVersionEnum.MQTT_5_0.getValue())) {
			config.setProtocolVersion(MqttVersion.MQTT_5);
		}
		config.setProtocolVersion(MqttVersion.MQTT_3_1_1);

		config.setClientId(clientId);
		config.setHost(host);
		config.setPort(Integer.parseInt(port));
		config.setUsername(username);
		config.setPassword(password);
		if (advanced.equals(ConfirmCodeEnum.YES.getValue())) {
			config.setKeepAlive(Integer.parseInt(keepAlive));
			config.setTimeoutSeconds(Integer.parseInt(connectTimeout));
			config.setCleanSession(cleanSession.equals(ConfirmCodeEnum.YES.getValue()));
			config.setReconnect(autoReconnect.equals(ConfirmCodeEnum.YES.getValue()));

		} else {
			config.setReconnect(false);
			config.setCleanSession(true);
			config.setTimeoutSeconds(config.getTimeoutSeconds());
			config.setKeepAlive(config.getKeepAlive());
		}
		if (ssl.equals(ConfirmCodeEnum.YES.getValue()) && certType.equals(CertTypeEnum.SELF_SIGNED.getDesc())) {
			SslContext sslContext = null;
			try {
				sslContext = SslContextBuilder.forClient()
						.keyManager(new File(clientCert), new File(clientKey))
						.trustManager(new File(ca))
						.build();
			} catch (SSLException e) {
				StringBuilder sb = new StringBuilder();
				sb.append("sslContext load fail!");
				sb.append(StringUtils.lineSeparator());
				System.out.println(sb);
			}
			config.setSslContext(sslContext);
		}
		if (lastWillAndTestament.equals(ConfirmCodeEnum.YES.getValue())) {
			MqttQoS qos = MqttQoS.valueOf(Integer.parseInt(lastWillQoS));
			boolean retain = lastWillRetain.equals(ConfirmCodeEnum.YES.getValue());
			MqttLastWill lastWill = MqttLastWill.builder()
					.setTopic(lastWillTopic)
					.setQos(qos)
					.setRetain(retain)
					.setMessage(lastWillPayload)
					.build();
			config.setLastWill(lastWill);
		}
		return config;
	}

	public MqttSettings convertMqttSettingsJsonString() {
		MqttSettings settings = new MqttSettings();
		settings.setName(clientId + "@" + host + ":" + port);
		MqttSettingsInfo info = new MqttSettingsInfo();
		info.setVersion(mqttVersion);
		info.setClientId(clientId);
		info.setHost(host);
		info.setPort(port);
		info.setUsername(username);
		info.setPassword(password);
		info.setSsl(ssl);
		info.setCertType(certType);
		info.setCa(ca);
		info.setClientCert(clientCert);
		info.setClientKey(clientKey);
		info.setAdvanced(advanced);
		if (advanced.equals(ConfirmCodeEnum.YES.getValue())) {
			info.setKeepAlive(keepAlive);
			info.setAutoReconnect(autoReconnect);
			info.setConnectTimeout(connectTimeout);
			info.setCleanSession(cleanSession);
		} else {
			info.setAutoReconnect(ConfirmCodeEnum.NO.getValue());
			info.setCleanSession(ConfirmCodeEnum.YES.getValue());
			info.setConnectTimeout("10");
			info.setKeepAlive("10");
		}
		if (lastWillAndTestament.equals(ConfirmCodeEnum.YES.getValue())) {
			info.setLastWillQoS(lastWillQoS);
			info.setLastWillTopic(lastWillTopic);
			info.setLastWillRetain(lastWillRetain);
			info.setLastWillPayload(lastWillPayload);
		}
		settings.setInfo(info);
		settings.setSettingTime(System.currentTimeMillis());
		return settings;
	}

	public String getMqttAppConfig() {
		return mqttAppConfig;
	}

	public void setMqttAppConfig(String mqttAppConfig) {
		this.mqttAppConfig = mqttAppConfig;
	}

	public String getSelectConfig() {
		return selectConfig;
	}

	public void setSelectConfig(String selectConfig) {
		this.selectConfig = selectConfig;
	}

	public String getSettingsName() {
		return settingsName;
	}

	public void setSettingsName(String settingsName) {
		this.settingsName = settingsName;
	}

	public String getMqttVersion() {
		return mqttVersion;
	}

	public void setMqttVersion(String mqttVersion) {
		this.mqttVersion = mqttVersion;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSsl() {
		return ssl;
	}

	public void setSsl(String ssl) {
		this.ssl = ssl;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCa() {
		return ca;
	}

	public void setCa(String ca) {
		this.ca = ca;
	}

	public String getClientCert() {
		return clientCert;
	}

	public void setClientCert(String clientCert) {
		this.clientCert = clientCert;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public String getAdvanced() {
		return advanced;
	}

	public void setAdvanced(String advanced) {
		this.advanced = advanced;
	}

	public String getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(String connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public String getKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(String keepAlive) {
		this.keepAlive = keepAlive;
	}

	public String getCleanSession() {
		return cleanSession;
	}

	public void setCleanSession(String cleanSession) {
		this.cleanSession = cleanSession;
	}

	public String getAutoReconnect() {
		return autoReconnect;
	}

	public void setAutoReconnect(String autoReconnect) {
		this.autoReconnect = autoReconnect;
	}

	public String getLastWillAndTestament() {
		return lastWillAndTestament;
	}

	public void setLastWillAndTestament(String lastWillAndTestament) {
		this.lastWillAndTestament = lastWillAndTestament;
	}

	public String getLastWillTopic() {
		return lastWillTopic;
	}

	public void setLastWillTopic(String lastWillTopic) {
		this.lastWillTopic = lastWillTopic;
	}

	public String getLastWillQoS() {
		return lastWillQoS;
	}

	public void setLastWillQoS(String lastWillQoS) {
		this.lastWillQoS = lastWillQoS;
	}

	public String getLastWillRetain() {
		return lastWillRetain;
	}

	public void setLastWillRetain(String lastWillRetain) {
		this.lastWillRetain = lastWillRetain;
	}

	public String getLastWillPayload() {
		return lastWillPayload;
	}

	public void setLastWillPayload(String lastWillPayload) {
		this.lastWillPayload = lastWillPayload;
	}

	public MqttClientService getClient() {
		return client;
	}

	public void setClient(MqttClientService client) {
		this.client = client;
	}


}
