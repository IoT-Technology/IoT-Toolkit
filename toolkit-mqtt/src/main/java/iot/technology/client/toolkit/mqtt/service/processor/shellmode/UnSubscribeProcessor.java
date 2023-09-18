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

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.mqtt.config.MqttShellModeDomain;
import org.apache.commons.cli.*;

/**
 * @author mushuwei
 */
public class UnSubscribeProcessor extends TkAbstractProcessor implements TkProcessor {

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().startsWith("unsub");
    }

    @Override
    public void handle(ProcessContext context) {
        MqttProcessContext mqttProcessContext = (MqttProcessContext) context;
        MqttShellModeDomain domain = mqttProcessContext.getDomain();

        Options options = new Options();
        Option topicOption = new Option("t", "topic", true, "the mqtt topic");
        options.addOption(topicOption);


        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, convertMqttCommandData(context.getData()));
            String topic = "";
            if (cmd.hasOption(topicOption)) {
                topic = cmd.getOptionValue(topicOption);
            }

            if (StringUtils.isBlank(topic)) {
                StringBuilder sb = new StringBuilder();
                sb.append(ColorUtils.redError("topic is empty")).append(StringUtils.lineSeparator);
                sb.append(ColorUtils.blackBold("detail usage please enter: help pub"));
                System.out.println(sb);
                return;
            }
            domain.getClient().off(topic);
        } catch (ParseException e) {
            StringBuilder sb = new StringBuilder();
            sb.append(ColorUtils.redError("command parse failed!")).append(StringUtils.lineSeparator);
            System.out.println(sb);
        }


    }
}
