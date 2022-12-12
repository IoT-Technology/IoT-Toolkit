package iot.technology.client.toolkit.nb.service.processor;


import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class TelecomBizService {

	ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

	public final List<TkProcessor> getTkProcessorList() {
		List<TkProcessor> tkProcessorList = new ArrayList<>();
		return tkProcessorList;
	}

	public boolean call(TelecomConfigDomain telecomConfigDomain) {
		try {
			Terminal terminal = TerminalBuilder.builder()
					.system(true)
					.build();
			LineReader reader = LineReaderBuilder.builder()
					.terminal(terminal)
					.history(new DefaultHistory())
					.parser(new DefaultParser())
					.build();

			String prompt = bundle.getString("nb.command.prompt") + GlobalConstants.promptSeparator;
			while (true) {
				String data;
				data = reader.readLine(prompt);
				ProcessContext context = new ProcessContext();
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
	}

}
