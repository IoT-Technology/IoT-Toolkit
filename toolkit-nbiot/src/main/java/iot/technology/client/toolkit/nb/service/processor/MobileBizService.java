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
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.processor.mobile.*;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mushuwei
 */
public class MobileBizService {

	public final List<TkProcessor> getTkProcessorList() {
		List<TkProcessor> tkProcessorList = new ArrayList<>();
		tkProcessorList.add(new MobListDeviceProcessor());
		tkProcessorList.add(new MobAddDeviceProcessor());
		tkProcessorList.add(new MobDelDeviceByImeiProcessor());
		tkProcessorList.add(new MobShowDeviceByImeiProcessor());
		tkProcessorList.add(new MobUpdateDeviceProcessor());
		tkProcessorList.add(new MobHelpProcessor());
		return tkProcessorList;
	}

	public boolean call(MobileConfigDomain mobileConfigDomain, Terminal terminal) {
		try {
			LineReader reader = LineReaderBuilder.builder()
					.terminal(terminal)
					.parser(new DefaultParser())
					.build();

			String prompt = mobileConfigDomain.getProductName() + ":" + GlobalConstants.promptSeparator;
			boolean isEnd = true;
			MobProcessContext context = new MobProcessContext();
			context.setMobileConfigDomain(mobileConfigDomain);
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
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return true;
	}

}
