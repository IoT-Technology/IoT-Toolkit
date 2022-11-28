/*
 * Copyright © 2019-2022 The Toolkit Authors
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
package iot.technology.client.toolkit.mqtt.command.sub;

import iot.technology.client.toolkit.common.constants.ExitCodeEnum;
import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.mqtt.service.processor.AddProcessor;
import iot.technology.client.toolkit.mqtt.service.processor.DelProcessor;
import iot.technology.client.toolkit.mqtt.service.processor.ListProcessor;
import iot.technology.client.toolkit.mqtt.service.processor.ShowProcessor;
import org.jline.reader.*;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.NullCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

/**
 * @author mushuwei
 */
@CommandLine.Command(
		name = "settings",
		aliases = "set",
		requiredOptionMarker = '*',
		description = "${bundle:mqtt.settings.desc}",
		optionListHeading = "%n${bundle:general.option}:%n",
		sortOptions = false,
		footerHeading = "%nCopyright (c) 2019-2022, ${bundle:general.copyright}",
		footer = "%nDeveloped by mushuwei"
)
public class MqttSettingsCommand implements Callable<Integer> {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	Completer listCompleter = new ArgumentCompleter(new StringsCompleter("list"), NullCompleter.INSTANCE);

	Completer showCompleter = new ArgumentCompleter(new StringsCompleter("show"), NullCompleter.INSTANCE);

	Completer delCompleter = new ArgumentCompleter(new StringsCompleter("del"), NullCompleter.INSTANCE);

	Completer addCompleter = new ArgumentCompleter(new StringsCompleter("add"), NullCompleter.INSTANCE);

	Completer mqttSettingsCompleter = new AggregateCompleter(listCompleter, showCompleter, delCompleter, addCompleter);


	public final List<TkProcessor> getTkProcessorList() {
		List<TkProcessor> tkProcessorList = new ArrayList<>();
		tkProcessorList.add(new AddProcessor());
		tkProcessorList.add(new DelProcessor());
		tkProcessorList.add(new ListProcessor());
		tkProcessorList.add(new ShowProcessor());
		return tkProcessorList;
	}

	@Override
	public Integer call() throws Exception {
		Terminal terminal = TerminalBuilder.builder()
				.system(true)
				.build();
		LineReader reader = LineReaderBuilder.builder()
				.terminal(terminal)
				.completer(mqttSettingsCompleter)
				.history(new DefaultHistory())
				.parser(new DefaultParser())
				.build();

		String prompt = bundle.getString("mqtt.settings.command.prompt") + GlobalConstants.promptSeparator;
		while (true) {
			String data;
			try {
				data = reader.readLine(prompt);
				ProcessContext context = new ProcessContext();
				context.setData(data);
				for (TkProcessor processor : getTkProcessorList()) {
					if (processor.supports(context)) {
						processor.handle(context);
					}
				}
			} catch (UserInterruptException | EndOfFileException e) {
				return ExitCodeEnum.ERROR.getValue();
			}
		}
	}
}
