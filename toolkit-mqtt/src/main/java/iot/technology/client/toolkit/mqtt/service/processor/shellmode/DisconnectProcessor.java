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
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.mqtt.config.MqttShellModeDomain;
import org.jline.reader.EndOfFileException;

/**
 * @author mushuwei
 */
public class DisconnectProcessor implements TkProcessor {

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().equals("disconnect") || context.getData().equals("dis");
    }

    @Override
    public void handle(ProcessContext context) {
        MqttProcessContext mqttProcessContext = (MqttProcessContext) context;
        MqttShellModeDomain domain = mqttProcessContext.getDomain();
        domain.getClient().disconnect();
        System.out.println(String.format(ColorUtils.redError("clientId:%s close!"), domain.getSettings().getInfo().getClientId()));
        throw new EndOfFileException("client: " + domain.getSettings().getInfo().getClientId() + " close!");
    }
}
