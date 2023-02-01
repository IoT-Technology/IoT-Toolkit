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
import iot.technology.client.toolkit.nb.service.processor.telecom.*;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
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
public class TelecomBizService {

	public final List<TkProcessor> getTkProcessorList() {
		List<TkProcessor> tkProcessorList = new ArrayList<>();
		tkProcessorList.add(new TelShowDeviceByImeiProcessor());
		tkProcessorList.add(new TelDelDeviceByImeiProcessor());
		tkProcessorList.add(new TelUpdateDeviceProcessor());
		tkProcessorList.add(new TelAddDeviceProcessor());
		tkProcessorList.add(new TelListDeviceProcessor());
		tkProcessorList.add(new TelHelpProcessor());
		return tkProcessorList;
	}

	Completer listCompleter = new ArgumentCompleter(new StringsCompleter("list"), NullCompleter.INSTANCE);

	Completer showCompleter = new ArgumentCompleter(new StringsCompleter("show"), NullCompleter.INSTANCE);

	Completer delCompleter = new ArgumentCompleter(new StringsCompleter("del"), NullCompleter.INSTANCE);

	Completer addCompleter = new ArgumentCompleter(new StringsCompleter("add"), NullCompleter.INSTANCE);

	Completer updateCompleter = new ArgumentCompleter(new StringsCompleter("update"), NullCompleter.INSTANCE);

	Completer helpCompleter = new ArgumentCompleter(new StringsCompleter("help"), NullCompleter.INSTANCE);

	Completer nbTelecomCompleter =
			new AggregateCompleter(listCompleter, showCompleter, delCompleter, addCompleter, helpCompleter, updateCompleter);

	public boolean call(TelecomConfigDomain telecomConfigDomain, Terminal terminal) {
		try {
			LineReader reader = LineReaderBuilder.builder()
					.terminal(terminal)
					.completer(nbTelecomCompleter)
					.parser(new DefaultParser())
					.build();

			String prompt = telecomConfigDomain.getProductName() + ":" + GlobalConstants.promptSeparator;
			boolean isEnd = true;
			TelProcessContext context = new TelProcessContext();
			context.setTelecomConfigDomain(telecomConfigDomain);
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
