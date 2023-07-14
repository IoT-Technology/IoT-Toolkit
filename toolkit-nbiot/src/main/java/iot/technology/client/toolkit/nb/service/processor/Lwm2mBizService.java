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
package iot.technology.client.toolkit.nb.service.processor;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.nb.service.lwm2m.domain.Lwm2mConfigSettingsDomain;
import iot.technology.client.toolkit.nb.service.processor.lwm2m.*;
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
public class Lwm2mBizService {

    public final List<TkProcessor> getTkProcessorList() {
        List<TkProcessor> tkProcessorList = new ArrayList<>();
        tkProcessorList.add(new LwM2MCollectProcessor());
        tkProcessorList.add(new LwM2MCreateProcessor());
        tkProcessorList.add(new LwM2MDeleteProcessor());
        tkProcessorList.add(new LwM2MListProcessor());
        tkProcessorList.add(new LwM2MSendProcessor());
        tkProcessorList.add(new LwM2MUpdateProcessor());
        return tkProcessorList;
    }

    Completer listCompleter = new ArgumentCompleter(new StringsCompleter("list"), NullCompleter.INSTANCE);

    Completer createCompleter = new ArgumentCompleter(new StringsCompleter("create"), NullCompleter.INSTANCE);

    Completer deleteCompleter = new ArgumentCompleter(new StringsCompleter("delete"), NullCompleter.INSTANCE);

    Completer updateCompleter = new ArgumentCompleter(new StringsCompleter("update"), NullCompleter.INSTANCE);

    Completer sendCompleter = new ArgumentCompleter(new StringsCompleter("send"), NullCompleter.INSTANCE);

    Completer collectCompleter = new ArgumentCompleter(new StringsCompleter("collect"), NullCompleter.INSTANCE);

    Completer lwm2mCompleter = new AggregateCompleter(listCompleter, createCompleter, deleteCompleter, updateCompleter, sendCompleter,
            collectCompleter);

    public boolean call(Lwm2mConfigSettingsDomain domain, Terminal terminal) {
        try {
            LineReader reader = LineReaderBuilder.builder()
                    .terminal(terminal)
                    .completer(lwm2mCompleter)
                    .parser(new DefaultParser())
                    .build();

            String prompt = domain.getServerUrl() + GlobalConstants.promptSeparator;;
            boolean isEnd = true;
            Lwm2mProcessContext context = new Lwm2mProcessContext();
            context.setDomain(domain);
            domain.getLeshanClient().start();
            while (isEnd) {
                String data;
                data = reader.readLine(prompt);
                context.setData(data);
                if (data.equals("exit")) {
                    domain.getLeshanClient().destroy(true);
                    return false;
                }
                for (TkProcessor processor : getTkProcessorList()) {
                    if (processor.supports(context)) {
                        processor.handle(context);
                    }
                }
            }

        } catch (UserInterruptException | EndOfFileException e) {
            domain.getLeshanClient().destroy(true);
            return false;
        }
        domain.getLeshanClient().destroy(true);
        return true;
    }
}
