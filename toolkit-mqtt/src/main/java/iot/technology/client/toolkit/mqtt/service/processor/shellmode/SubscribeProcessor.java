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
package iot.technology.client.toolkit.mqtt.service.processor.shellmode;

import io.netty.handler.codec.mqtt.MqttQoS;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.mqtt.config.MqttShellModeDomain;
import iot.technology.client.toolkit.mqtt.service.handler.*;
import org.apache.commons.cli.*;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class SubscribeProcessor extends TkAbstractProcessor implements TkProcessor {

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().startsWith("sub");
    }

    @Override
    public void handle(ProcessContext context) {
        MqttProcessContext mqttProcessContext = (MqttProcessContext) context;
        MqttShellModeDomain domain = mqttProcessContext.getDomain();

        Options options = new Options();
        Option topicOption = new Option("t", "topic", true, "the mqtt topic");
        Option qosOption = new Option("q", "qos", true, "the quality of service level");
        Option messageHexFormat = new Option("hex", "hex", false, "The hex format message");
        Option messageBase64Format = new Option("b64", "base64", false, "The base64 message");
        Option messageJsonFormat = new Option("json", "json", false, "The hex format message");
        options.addOption(topicOption)
                .addOption(messageHexFormat)
                .addOption(messageBase64Format)
                .addOption(messageJsonFormat)
                .addOption(qosOption);

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, convertCommandData(context.getData()));
            int qos = 0;
            String topic = "";

            if (cmd.hasOption(topicOption)) {
                topic = cmd.getOptionValue(topicOption);
            }

            if (StringUtils.isBlank(topic)) {
                StringBuilder sb = new StringBuilder();
                sb.append(ColorUtils.redError("topic is empty")).append(StringUtils.lineSeparator);
                sb.append(ColorUtils.blackBold("detail usage please enter: help sub"));
                System.out.println(sb);
                return;
            }
            if (cmd.hasOption(qosOption)) {
                String qosStr = cmd.getOptionValue(qosOption);
                if (!validateParam(qosStr)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ColorUtils.redError(String.format("%s is not a number", qosStr))).append(StringUtils.lineSeparator);
                    sb.append(ColorUtils.blackBold("detail usage please enter: help sub"));
                    System.out.println(sb);
                    return;
                }
                qos = Integer.parseInt(qosStr);
                if (qos < 0 || qos > 2) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ColorUtils.redError(String.format("qos: %s is error param", qos))).append(StringUtils.lineSeparator);
                    System.out.println(sb);
                    return;
                }
            }
            MqttQoS qosLevel = MqttQoS.valueOf(qos);
            MqttHandler mqttHandler = new MqttSubMessageHandler();
            int messageConditions = 0;
            if (cmd.hasOption(messageHexFormat)) {
                messageConditions++;
                mqttHandler = new MqttHexMessageHandler();
            }
            if (cmd.hasOption(messageBase64Format)) {
                messageConditions++;
                mqttHandler = new MqttBase64MessageHandler();
            }
            if (cmd.hasOption(messageJsonFormat)) {
                messageConditions++;
                mqttHandler = new MqttJsonFormatMessageHandler();
            }
            if (messageConditions > 1 || messageConditions > 2) {
                StringBuilder sb = new StringBuilder();
                sb.append(ColorUtils.redError(String.format("-hex、-base64 or -json not found or set multi")))
                        .append(StringUtils.lineSeparator);
                sb.append(ColorUtils.blackBold("detail usage please enter: help sub"));
                System.out.println(sb);
                return;
            }
            domain.getClient().on(topic, mqttHandler, qosLevel);
        } catch (ParseException e) {
            StringBuilder sb = new StringBuilder();
            sb.append(ColorUtils.redError("command parse failed!")).append(StringUtils.lineSeparator);
            System.out.println(sb);
        }
    }
}
