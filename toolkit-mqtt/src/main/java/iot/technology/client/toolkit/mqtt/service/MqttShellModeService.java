package iot.technology.client.toolkit.mqtt.service;

import iot.technology.client.toolkit.common.rule.TkProcessor;
import org.jline.reader.Completer;
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
        return tkProcessorList;
    }

    Completer subscribeCompleter = new ArgumentCompleter(new StringsCompleter("sub"), NullCompleter.INSTANCE);

    Completer unSubscribeCompleter = new ArgumentCompleter(new StringsCompleter("unsub"), NullCompleter.INSTANCE);

    Completer publishCompleter = new ArgumentCompleter(new StringsCompleter("pub"), NullCompleter.INSTANCE);

    Completer exitCompleter = new ArgumentCompleter(new ArgumentCompleter("exit"), NullCompleter.INSTANCE);

    Completer mqttShellModeCompleter = new AggregateCompleter(publishCompleter, subscribeCompleter, unSubscribeCompleter,
            exitCompleter);


    public boolean call(Terminal terminal) {

    }
}
