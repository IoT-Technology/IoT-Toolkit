/*
 * Copyright © 2019-2023 The Toolkit Authors
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

import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.MqttQoS;
import iot.technology.client.toolkit.common.constants.EmojiEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.mqtt.config.MqttShellModeDomain;
import org.apache.commons.cli.*;
import org.eclipse.leshan.core.util.Hex;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.ResourceBundle;


/**
 * @author mushuwei
 */
public class PublishProcessor extends TkAbstractProcessor implements TkProcessor {

    private final ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().startsWith("pub")
                || context.getData().startsWith("publish");
    }

    @Override
    public void handle(ProcessContext context) {
        MqttProcessContext mqttProcessContext = (MqttProcessContext) context;
        MqttShellModeDomain domain = mqttProcessContext.getDomain();

        Options options = new Options();
        Option topicOption = new Option("t", "topic", true, "the mqtt topic");
        Option qosOption = new Option("q", "qos", true, "the quality of service level");
        Option retainOption = new Option("r", "retain", false, "The message will be retained (default: false)");
        Option messageOption = new Option("m", "message", true, "the message");
        Option messageFromFile = new Option("mf", "message-file", true, "The message read in from a file");
        Option messageHexFormat = new Option("mh", "message-hex", true, "The hex format message");
        Option messageBase64Format = new Option("mb", "message-base64", true, "The base64 format message");

        options.addOption(topicOption)
                .addOption(qosOption)
                .addOption(retainOption)
                .addOption(messageFromFile)
                .addOption(messageHexFormat)
                .addOption(messageBase64Format)
                .addOption(messageOption);

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, convertMqttCommandData(context.getData()));
            int qos = 0;
            String topic = "";
            // the message of print console
            String message = "";
            // retain
            boolean retain = false;
            if (cmd.hasOption(retainOption)) {
                retain = true;
            }
            if (cmd.hasOption(topicOption)) {
                topic = cmd.getOptionValue(topicOption);
            }
            int messageConditions = 0;
            byte[] messageBytes = new byte[0];
            // plaintext or json message
            if (cmd.hasOption(messageOption)) {
                messageConditions++;
                messageBytes = cmd.getOptionValue(messageOption).getBytes(StandardCharsets.UTF_8);
                message = cmd.getOptionValue(messageOption);
            }
            // The hex format message
            if (cmd.hasOption(messageHexFormat)) {
                messageConditions++;
                String hexMessage = cmd.getOptionValue(messageHexFormat);
                messageBytes = Hex.decodeHex(hexMessage.toCharArray());
                message = hexMessage;
            }
            // The base64 format message
            if (cmd.hasOption(messageBase64Format)) {
                messageConditions++;
                String base64Message = cmd.getOptionValue(messageBase64Format);
                messageBytes = Base64.getDecoder().decode(base64Message);
                message = base64Message;
            }
            // The message read in from a file
            if (cmd.hasOption(messageFromFile)) {
                messageConditions++;
                String fileName = cmd.getOptionValue(messageFromFile);
                final Path path = Paths.get(fileName);
                if (!Files.isReadable(path)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ColorUtils.redError("File not found or not readable: " + fileName)).append(StringUtils.lineSeparator);
                    sb.append(ColorUtils.blackBold("detail usage please enter: help pub"));
                    System.out.println(sb);
                }
                try {
                    messageBytes = Files.readAllBytes(path);
                    message = fileName;
                } catch (IOException e) {
                    System.out.println(ColorUtils.redError("file convert message failed"));
                }
            }

            if (messageConditions == 0 || messageConditions > 2) {
                StringBuilder sb = new StringBuilder();
                sb.append(ColorUtils.redError(String.format("-m、-mf、-mh or -mb not found or set multi")))
                        .append(StringUtils.lineSeparator);
                sb.append(ColorUtils.blackBold("detail usage please enter: help pub"));
                System.out.println(sb);
                return;
            }
            if (cmd.hasOption(qosOption)) {
                String qosStr = cmd.getOptionValue(qosOption);
                if (!validateParam(qosStr)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ColorUtils.redError(String.format("%s is not a number", qosStr))).append(StringUtils.lineSeparator);
                    sb.append(ColorUtils.blackBold("detail usage please enter: help pub"));
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
            domain.getClient().publish(topic, Unpooled.wrappedBuffer(messageBytes), qosLevel, retain);
            System.out.format(message + " " + bundle.getString("publishMessage.success") + String.format(EmojiEnum.successEmoji) + "%n");

        } catch (ParseException e) {
            StringBuilder sb = new StringBuilder();
            sb.append(ColorUtils.redError("command parse failed!")).append(StringUtils.lineSeparator);
            System.out.println(sb);
        }
    }
}
