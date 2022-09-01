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
package iot.technology.client.toolkit.mqtt.command.sub;

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.MqttQoS;
import io.netty.util.concurrent.Future;
import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.HelpVersionGroup;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.mqtt.service.MqttClientConfig;
import iot.technology.client.toolkit.mqtt.service.MqttClientService;
import iot.technology.client.toolkit.mqtt.service.domain.MqttConnectResult;
import iot.technology.client.toolkit.mqtt.service.domain.MqttLastWill;
import iot.technology.client.toolkit.mqtt.service.handler.MqttPubMessageHandler;
import iot.technology.client.toolkit.mqtt.service.impl.MqttClientServiceImpl;
import picocli.CommandLine;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "publish",
		aliases = "pub",
		requiredOptionMarker = '*',
		description = "${bundle:mqtt.pub.description}",
		optionListHeading = "%n${bundle:general.option}:%n",
		sortOptions = false,
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei",
		versionProvider = iot.technology.client.toolkit.common.constants.VersionInfo.class
)
public class MqttPublishCommand implements Callable<Integer> {

	private static final Charset UTF8 = StandardCharsets.UTF_8;

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);


	@CommandLine.ArgGroup
	HelpVersionGroup helpVersionGroup;

	@CommandLine.Option(
			order = 0,
			names = {"-host", "--hostname"},
			required = true,
			description = "${bundle:mqtt.broker.desc}")
	String host;

	@CommandLine.Option(
			order = 1,
			names = {"-p", "--port"},
			required = true,
			description = "${bundle:mqtt.port.desc}",
			defaultValue = "1883",
			showDefaultValue = CommandLine.Help.Visibility.ALWAYS)
	Integer port;

	@CommandLine.Option(
			order = 2,
			names = {"-i", "--client-id"},
			description = "${bundle:mqtt.client.id.desc}"
	)
	String clientId;

	@CommandLine.Option(
			order = 3,
			names = {"-u", "--username"},
			description = "${bundle:mqtt.username.desc}"
	)
	String username;

	@CommandLine.Option(
			order = 4,
			names = {"-P", "--password"},
			description = "${bundle:mqtt.password.desc}"
	)
	String password;

	@CommandLine.Option(
			order = 5,
			names = {"-q", "--qos"},
			defaultValue = "0",
			showDefaultValue = CommandLine.Help.Visibility.ALWAYS,
			description = "${bundle:mqtt.qos.desc}")
	Integer qos;

	@CommandLine.Option(
			order = 6,
			names = {"-t", "--topic"},
			required = true,
			description = "${bundle:mqtt.topic.desc}")
	String topic;

	@CommandLine.Option(
			order = 7,
			required = true,
			names = {"-m", "--message"},
			description = "${bundle:mqtt.message.desc}")
	String message;

	@CommandLine.Option(
			order = 8,
			names = {"--ca"},
			description = "${bundle:mqtt.ca.desc}")
	String ca;

	@CommandLine.Option(
			order = 9,
			names = {"--key"},
			description = "${bundle:mqtt.key.desc}")
	String key;

	@CommandLine.Option(
			order = 10,
			names = {"--cert"},
			description = "${bundle:mqtt.cert.desc}")
	String cert;

	@CommandLine.Option(
			order = 11,
			names = {"--insecure"},
			description = "${bundle:mqtt.insecure.desc}")
	boolean insecure;

	@CommandLine.Option(
			order = 12,
			names = {"--will-topic"},
			description = "${bundle:mqtt.will.topic.desc}")
	String willTopic;

	@CommandLine.Option(
			order = 13,
			names = {"--will-qos"},
			description = "${bundle:mqtt.will.qos.desc}"
	)
	int willQos;

	@CommandLine.Option(
			order = 14,
			names = {"--will-retain"},
			description = "${bundle:mqtt.will.retain.desc}"
	)
	boolean willRetain;

	@CommandLine.Option(
			order = 15,
			names = {"--will-payload"},
			description = "${bundle:mqtt.will.payload.desc}"
	)
	String willPayload;


	@Override
	public Integer call() throws Exception {
		MqttClientConfig config = new MqttClientConfig();
		MqttQoS willQosConfig = MqttQoS.valueOf(willQos);
		MqttLastWill lastWill = MqttLastWill.builder()
				.setTopic(willTopic)
				.setQos(willQosConfig)
				.setRetain(willRetain)
				.setMessage(willPayload)
				.build();
		config.setLastWill(lastWill);
		config.setClientId(Objects.nonNull(clientId) ? clientId : config.getClientId());
		config.setUsername(Objects.nonNull(username) ? username : null);
		config.setPassword(Objects.nonNull(password) ? password : null);
		MqttClientService mqttClientService = new MqttClientServiceImpl(config, new MqttPubMessageHandler());
		Future<MqttConnectResult> connectFuture = mqttClientService.connect(host, port);
		MqttQoS qosLevel = MqttQoS.valueOf(qos);
		MqttConnectResult result;
		try {
			result = connectFuture.get(config.getTimeoutSeconds(), TimeUnit.SECONDS);
		} catch (TimeoutException ex) {
			connectFuture.cancel(true);
			mqttClientService.disconnect();
			String hostPort = host + ":" + port;
			throw new RuntimeException(String.format("%s %s.", bundle.getString("mqtt.failed.connect"), hostPort));
		}
		if (!result.isSuccess()) {
			connectFuture.cancel(true);
			mqttClientService.disconnect();
			String hostPort = host + ":" + port;
			throw new RuntimeException(
					String.format("%s %s. %s %s", bundle.getString("mqtt.failed.connect"),
							hostPort, bundle.getString("mqtt.result.code"), result.getReturnCode()));
		}
		mqttClientService.publish(topic, Unpooled.wrappedBuffer(message.getBytes(UTF8)), qosLevel, false);
		return ExitCodeEnum.SUCCESS.getValue();
	}
}
