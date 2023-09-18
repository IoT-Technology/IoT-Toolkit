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
package iot.technology.client.toolkit.mqtt.service;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.mqtt.config.MqttShellModeDomain;
import iot.technology.client.toolkit.mqtt.service.processor.shellmode.*;
import org.jline.reader.*;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.NullCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mushuwei
 */
public class MqttShellModeService {

    public final List<TkProcessor> getTkProcessorList() {
        List<TkProcessor> tkProcessorList = new ArrayList<>();
        tkProcessorList.add(new ListProcessor());
        tkProcessorList.add(new DisconnectProcessor());
        tkProcessorList.add(new PublishProcessor());
        tkProcessorList.add(new SubscribeProcessor());
        tkProcessorList.add(new UnSubscribeProcessor());
        return tkProcessorList;
    }

    Completer listCompleter = new ArgumentCompleter(new StringsCompleter("list"), NullCompleter.INSTANCE);

    Completer subscribeCompleter = new ArgumentCompleter(new StringsCompleter("sub"), NullCompleter.INSTANCE);

    Completer unSubscribeCompleter = new ArgumentCompleter(new StringsCompleter("unsub"), NullCompleter.INSTANCE);

    Completer publishCompleter = new ArgumentCompleter(new StringsCompleter("pub"), NullCompleter.INSTANCE);

    Completer disconnectCompleter = new ArgumentCompleter(new StringsCompleter("dis"), NullCompleter.INSTANCE);

    Completer exitCompleter = new ArgumentCompleter(new StringsCompleter("exit"), NullCompleter.INSTANCE);

    Completer mqttShellModeCompleter = new AggregateCompleter(listCompleter, publishCompleter, subscribeCompleter, unSubscribeCompleter,
            disconnectCompleter, exitCompleter);


    public boolean call(MqttShellModeDomain domain, Terminal terminal) {
        try {
            LineReader reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(mqttShellModeCompleter)
                    .parser(new DefaultParser())
                    .build();

            String prompt = domain.getName() + ":" + GlobalConstants.promptSeparator;
            boolean isEnd = true;

            MqttProcessContext context = new MqttProcessContext();
            context.setDomain(domain);
            while (isEnd) {
                String data;
                data = reader.readLine(prompt);
                context.setData(data);
                if (data.equals("exit")) {
                    domain.getClient().disconnect();
                    return false;
                }
                for (TkProcessor processor : getTkProcessorList()) {
                    if (processor.supports(context)) {
                        processor.handle(context);
                    }
                }
            }
        } catch (UserInterruptException | EndOfFileException e) {
            return false;
        }

        return true;
    }
}
