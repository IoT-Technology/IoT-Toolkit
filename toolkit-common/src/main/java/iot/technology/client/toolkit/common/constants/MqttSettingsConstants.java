/*
 * Copyright © 2019-2022 The Toolkit Authors
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
public interface MqttSettingsConstants {
	String endMark = " > ";

	String mqttVersionPrompt = "请选择您要使用的mqtt版本, (1) *默认* - 3.1 或 3.1.1; (2) - 5.0" + endMark;

	String clientIdPrompt = "请填写您的Client Id:" + endMark;

	String hostPrompt = "请填写您的服务器地址:" + endMark;

	String portPrompt = "请填写您的端口:" + endMark;

	String usernamePrompt = "请填写您的用户名:" + endMark;

	String passwordPrompt = "请填写您的密码:" + endMark;

	String isSslOrTLS = "是否需要设置SSL/TLS ？(Y/N)" + endMark;

	String certTypePrompt = "请选择您要使用的证书类型,(1) CA signed Server; (2) Self signed" + endMark;

	String caPrompt = "请填写您的CA文件路径:" + endMark;

	String clientCertPrompt = "请填写您的客户端证书路径:" + endMark;

	String clientKeyPrompt = "请填写您的客户端key路径:" + endMark;

	String advancedPrompt = "是否需要高级设置,例如自动重连、清除会话等？(Y/N)" + endMark;

	String connectTimeoutPrompt = "请填写您的连接超时时长，单位(秒)" + endMark;

	String keepAlivePrompt = "请填写您的保持存活时长,单位(秒)" + endMark;

	String cleanSessionPrompt = "是否清除会话? (Y/N)" + endMark;

	String autoReconnect = "是否自动连接？(Y/N)" + endMark;

	String lastWillAndTestamentPrompt = "是否设置您的遗嘱消息？(Y/N)" + endMark;

	String lastWillTopicPrompt = "请填写您的遗嘱消息主题:" + endMark;

	String lastWillQoSPrompt = "请选择您的遗嘱消息QoS, 0/1/2:" + endMark;

	String lastWillRetainPrompt = "是否保留遗嘱消息？(Y/N)" + endMark;

	String lastWillPayloadPrompt = "请填写您的遗嘱消息:" + endMark;

}
