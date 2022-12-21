package iot.technology.client.toolkit.nb.service.processor;


import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.nb.service.processor.telecom.*;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mushuwei
 */
public class TelecomBizService {

	public final List<TkProcessor> getTkProcessorList() {
		List<TkProcessor> tkProcessorList = new ArrayList<>();
		tkProcessorList.add(new TelGetDeviceByImeiProcessor());
		tkProcessorList.add(new TelDelDeviceByImeiProcessor());
		tkProcessorList.add(new TelUpdateDeviceProcessor());
		tkProcessorList.add(new TelAddDeviceProcessor());
		tkProcessorList.add(new TelListDeviceProcessor());
		return tkProcessorList;
	}

	public boolean call(TelecomConfigDomain telecomConfigDomain) {
		try {
			Terminal terminal = TerminalBuilder.builder()
					.system(true)
					.build();
			LineReader reader = LineReaderBuilder.builder()
					.terminal(terminal)
					.parser(new DefaultParser())
					.build();

			String prompt = telecomConfigDomain.getProductName() + ":" + GlobalConstants.promptSeparator;
			boolean isEnd = true;
			TelProcessContext context = new TelProcessContext();
			context.setTelecomConfigDomain(telecomConfigDomain);
			while (isEnd) {
				String data;
				data = reader.readLine(prompt);
				if (data.equals("quit")) {
					return false;
				}
				context.setData(data);
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
