package iot.technology.client.toolkit.nb.service.processor;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.processor.mobile.*;
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
public class MobileBizService {

	public final List<TkProcessor> getTkProcessorList() {
		List<TkProcessor> tkProcessorList = new ArrayList<>();
		tkProcessorList.add(new MobListDeviceProcessor());
		tkProcessorList.add(new MobAddDeviceProcessor());
		tkProcessorList.add(new MobDelDeviceByImeiProcessor());
		tkProcessorList.add(new MobGetDeviceByImeiProcessor());
		tkProcessorList.add(new MobUpdateDeviceProcessor());
		return tkProcessorList;
	}

	public boolean call(MobileConfigDomain mobileConfigDomain) {
		try {
			Terminal terminal = TerminalBuilder.builder()
					.system(true)
					.build();
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
