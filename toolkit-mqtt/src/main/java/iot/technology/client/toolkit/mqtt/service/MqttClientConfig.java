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
package iot.technology.client.toolkit.mqtt.service;

import io.netty.handler.codec.mqtt.MqttVersion;
import io.netty.handler.ssl.SslContext;
import iot.technology.client.toolkit.mqtt.service.domain.MqttLastWill;

/**
 * @author mushuwei
 */
public final class MqttClientConfig {

	private SslContext sslContext;
	private String clientId;
	private int timeoutSeconds = 10;
	private int keepAlive = 10;
	private MqttVersion protocolVersion = MqttVersion.MQTT_3_1_1;
	private String username = null;
	private String password = null;
	private MqttLastWill lastWill;
	private boolean cleanSession = true;

	private int maxBytesInMessage = 8092;
	private boolean reconnect = true;
	private long reconnectDelay = 1L;
	private String host;
	private Integer port;

	public MqttClientConfig() {
		this(null);
	}

	public MqttClientConfig(SslContext sslContext) {
		this.sslContext = sslContext;
	}


	public boolean isReconnect() {
		return reconnect;
	}

	public void setReconnect(boolean reconnect) {
		this.reconnect = reconnect;
	}

	public long getReconnectDelay() {
		return reconnectDelay;
	}

	/**
	 * Sets the reconnect delay in seconds. Defaults to 1 second.
	 *
	 * @param reconnectDelay
	 * @throws IllegalArgumentException if reconnectDelay is smaller than 1.
	 */
	public void setReconnectDelay(long reconnectDelay) {
		if (reconnectDelay <= 0) {
			throw new IllegalArgumentException("reconnectDelay must be > 0");
		}
		this.reconnectDelay = reconnectDelay;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public int getMaxBytesInMessage() {
		return maxBytesInMessage;
	}

	/**
	 * Sets the maximum number of bytes in the message for the {@link io.netty.handler.codec.mqtt.MqttDecoder}
	 * Default value is 8092 as specified by Netty. The absolute maximum size is 256MB as set by the MQTT spec.
	 *
	 * @param maxBytesInMessage
	 * @throws IllegalArgumentException if maxBytesInMessage is smaller than 1 or greater than 256_000_000.
	 */
	public void setMaxBytesInMessage(int maxBytesInMessage) {
		if (maxBytesInMessage <= 0 || maxBytesInMessage > 256_000_000) {
			throw new IllegalArgumentException("maxBytesInMessage must be > 0 or < 256_000_000");
		}
		this.maxBytesInMessage = maxBytesInMessage;
	}

	public int getTimeoutSeconds() {
		return timeoutSeconds;
	}

	public void setTimeoutSeconds(int timeoutSeconds) {
		if (timeoutSeconds != -1 && timeoutSeconds <= 0) {
			throw new IllegalArgumentException("timeoutSeconds must be > 0 or -1");
		}
		this.timeoutSeconds = timeoutSeconds;
	}

	public MqttVersion getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(MqttVersion protocolVersion) {
		if (protocolVersion == null) {
			throw new NullPointerException("protocolVersion");
		}
		this.protocolVersion = protocolVersion;
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

	public MqttLastWill getLastWill() {
		return lastWill;
	}

	public void setLastWill(MqttLastWill lastWill) {
		this.lastWill = lastWill;
	}

	public boolean isCleanSession() {
		return cleanSession;
	}

	public void setCleanSession(boolean cleanSession) {
		this.cleanSession = cleanSession;
	}

	public SslContext getSslContext() {
		return sslContext;
	}

	public void setSslContext(SslContext context) {
		this.sslContext = context;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public int getKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(int keepAlive) {
		this.keepAlive = keepAlive;
	}
}
