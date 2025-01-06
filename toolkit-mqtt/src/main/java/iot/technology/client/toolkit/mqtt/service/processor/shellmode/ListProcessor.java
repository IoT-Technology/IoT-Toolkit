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
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.mqtt.config.MqttSettings;
import iot.technology.client.toolkit.mqtt.config.MqttShellModeDomain;
import iot.technology.client.toolkit.mqtt.service.domain.MqttSubscription;

import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mushuwei
 */
public class ListProcessor implements TkProcessor {

    ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().equals("list")
                || context.getData().equals("ls");
    }

    @Override
    public void handle(ProcessContext context) {
        MqttProcessContext mqttProcessContext = (MqttProcessContext) context;
        MqttShellModeDomain domain = mqttProcessContext.getDomain();
        MqttSettings settings = domain.getSettings();

        StringBuilder sb = new StringBuilder();
        sb.append(ColorUtils.colorBold("ClientId:", "black")).append(StringUtils.lineSeparator());
        sb.append(StringUtils.lineSeparator());
        sb.append(bundle.getString("mqtt.setting.name") + ": " + settings.getName()).append(StringUtils.lineSeparator());
        sb.append("Client Id" + ": " + settings.getInfo().getClientId()).append(StringUtils.lineSeparator());
        sb.append(bundle.getString("mqtt.setting.userName") + ": " + settings.getInfo().getUsername()).append(StringUtils.lineSeparator());
        sb.append(bundle.getString("mqtt.setting.password") + ": " + settings.getInfo().getPassword()).append(StringUtils.lineSeparator());
        sb.append("Keep Alive           " + ": " + settings.getInfo().getKeepAlive() + "(s)").append(StringUtils.lineSeparator());
        sb.append("Clean Session        " + ": " + settings.getInfo().getCleanSession()).append(StringUtils.lineSeparator());

        sb.append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("Subscribed Topics:", "black")).append(StringUtils.lineSeparator());
        ConcurrentHashMap<String, MqttSubscription> subscriptions = domain.getClient().getSubscriptions();
        if (!subscriptions.isEmpty()) {
            subscriptions.entrySet().stream().forEach(entry -> {
                String key = entry.getKey();
                MqttSubscription subscription = entry.getValue();

                String qosConsoleString = "";
                if (subscription.getMqttQoS().equals(MqttQoS.AT_MOST_ONCE)) {
                    qosConsoleString = bundle.getString("mqtt.qos0.prompt");
                }
                if (subscription.getMqttQoS().equals(MqttQoS.AT_LEAST_ONCE)) {
                    qosConsoleString = bundle.getString("mqtt.qos1.prompt");
                }
                if (subscription.getMqttQoS().equals(MqttQoS.EXACTLY_ONCE)) {
                    qosConsoleString = bundle.getString("mqtt.qos2.prompt");
                }
                sb.append("topic: " + key + " qos: " + qosConsoleString).append(StringUtils.lineSeparator());
            });
        }
        System.out.print(sb);
    }
}
