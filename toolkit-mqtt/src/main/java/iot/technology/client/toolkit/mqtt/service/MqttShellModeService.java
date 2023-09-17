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

    Completer subscribeCompleter = new ArgumentCompleter(new StringsCompleter("sub"), NullCompleter.INSTANCE);

    Completer unSubscribeCompleter = new ArgumentCompleter(new StringsCompleter("unsub"), NullCompleter.INSTANCE);

    Completer publishCompleter = new ArgumentCompleter(new StringsCompleter("pub"), NullCompleter.INSTANCE);

    Completer exitCompleter = new ArgumentCompleter(new StringsCompleter("exit"), NullCompleter.INSTANCE);

    Completer mqttShellModeCompleter = new AggregateCompleter(publishCompleter, subscribeCompleter, unSubscribeCompleter,
            exitCompleter);


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
