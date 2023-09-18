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

import com.google.common.collect.HashMultimap;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.mqtt.config.MqttShellModeDomain;
import iot.technology.client.toolkit.mqtt.service.domain.MqttSubscription;

import java.util.HashMap;

/**
 * @author mushuwei
 */
public class ListProcessor implements TkProcessor {

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().equals("list");
    }

    @Override
    public void handle(ProcessContext context) {
        MqttProcessContext mqttProcessContext = (MqttProcessContext) context;
        MqttShellModeDomain domain = mqttProcessContext.getDomain();

        HashMultimap<String, MqttSubscription> subscriptions = domain.getClient().getSubscriptions();
        if (!subscriptions.isEmpty()) {
            subscriptions.entries().forEach(entry -> {
                String key = entry.getKey();
                System.out.println("topic: " + key);
            });
        }

    }
}
