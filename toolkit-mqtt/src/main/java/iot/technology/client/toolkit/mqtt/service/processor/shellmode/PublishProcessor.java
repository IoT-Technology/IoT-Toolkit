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

import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;


/**
 * @author mushuwei
 */
public class PublishProcessor extends TkAbstractProcessor implements TkProcessor {

    private final ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().startsWith("pub");
    }

    @Override
    public void handle(ProcessContext context) {
        MqttProcessContext mqttProcessContext = (MqttProcessContext) context;
        MqttShellModeDomain domain = mqttProcessContext.getDomain();

        Options options = new Options();
        Option topicOption = new Option("t", "topic", true, "the mqtt topic");
        Option qosOption = new Option("q", "qos", true, "the quality of service level");
        Option messageOption = new Option("m", "message", true, "the message");
        options.addOption(topicOption).addOption(qosOption).addOption(messageOption);

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, convertMqttCommandData(context.getData()));
            int qos = 0;
            String topic = "";
            String message = "";
            if (cmd.hasOption(topicOption)) {
                topic = cmd.getOptionValue(topicOption);
            }
            if (cmd.hasOption(messageOption)) {
                message = cmd.getOptionValue(messageOption);
            }
            if (StringUtils.isBlank(topic) || StringUtils.isBlank(message)) {
                StringBuilder sb = new StringBuilder();
                sb.append(ColorUtils.redError("topic or message is empty")).append(StringUtils.lineSeparator);
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
            domain.getClient().publish(topic, Unpooled.wrappedBuffer(message.getBytes(StandardCharsets.UTF_8)), qosLevel, false);
            System.out.format(message + " " + bundle.getString("publishMessage.success") + String.format(EmojiEnum.successEmoji) + "%n");

        } catch (ParseException e) {
            StringBuilder sb = new StringBuilder();
            sb.append(ColorUtils.redError("command parse failed!")).append(StringUtils.lineSeparator);
            System.out.println(sb);
        }
    }
}
