package iot.technology.client.toolkit.nb.service.processor.settings;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;

/**
 * @author mushuwei
 */
public class NbSettingsHelpProcessor implements TkProcessor {

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("help");
	}

	@Override
	public void handle(ProcessContext context) {

	}
}
