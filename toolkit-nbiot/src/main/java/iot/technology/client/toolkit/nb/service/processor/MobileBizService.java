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
package iot.technology.client.toolkit.nb.service.processor;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.NbActionEnum;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.processor.mobile.*;
import org.jline.reader.*;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.completer.*;
import org.jline.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mushuwei
 */
public class MobileBizService {

	public final List<TkProcessor> getTkProcessorList() {
		List<TkProcessor> tkProcessorList = new ArrayList<>();
		tkProcessorList.add(new OneNetListProcessor());
		tkProcessorList.add(new OneNetAddDeviceProcessor());
		tkProcessorList.add(new OneNetDelDeviceProcessor());
		tkProcessorList.add(new OneNetShowDeviceProcessor());
		tkProcessorList.add(new OneNetUpdateDeviceProcessor());
		tkProcessorList.add(new OneNetHelpProcessor());
		tkProcessorList.add(new MobLogDeviceDataProcessor());
		tkProcessorList.add(new OneNetCommandDataDeviceProcessor());
		return tkProcessorList;
	}

	Completer listCompleter = new ArgumentCompleter(new StringsCompleter("list"), NullCompleter.INSTANCE);

	Completer showCompleter = new ArgumentCompleter(new StringsCompleter("show"), NullCompleter.INSTANCE);

	Completer delCompleter = new ArgumentCompleter(new StringsCompleter("del"), NullCompleter.INSTANCE);

	Completer addCompleter = new ArgumentCompleter(new StringsCompleter("add"), NullCompleter.INSTANCE);

	Completer updateCompleter = new ArgumentCompleter(new StringsCompleter("update"), NullCompleter.INSTANCE);

	Completer helpCompleter = new ArgumentCompleter(new StringsCompleter("help"), new EnumCompleter(NbActionEnum.class), NullCompleter.INSTANCE);

	Completer logCompleter = new ArgumentCompleter(new StringsCompleter("log"), NullCompleter.INSTANCE);

	Completer commandCompleter = new ArgumentCompleter(new StringsCompleter("command"), NullCompleter.INSTANCE);

	Completer nbMobileCompleter =
			new AggregateCompleter(listCompleter, showCompleter, delCompleter, addCompleter, helpCompleter, updateCompleter, logCompleter,
					commandCompleter);

	public boolean call(MobileConfigDomain mobileConfigDomain, Terminal terminal) {
		try {
			LineReader reader = LineReaderBuilder.builder()
					.terminal(terminal)
					.completer(nbMobileCompleter)
					.parser(new DefaultParser())
					.build();

			String prompt = mobileConfigDomain.getProductName() + ":" + GlobalConstants.promptSeparator;
			boolean isEnd = true;
			MobProcessContext context = new MobProcessContext();
			context.setMobileConfigDomain(mobileConfigDomain);

			var mobHelpProcessor = new MobHelpProcessor();
			mobHelpProcessor.printAllHelpInfo();
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
