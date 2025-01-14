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
package iot.technology.client.toolkit.mqtt.config;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class MqttSettingsInfo implements Serializable {

	private String version;

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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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

}
