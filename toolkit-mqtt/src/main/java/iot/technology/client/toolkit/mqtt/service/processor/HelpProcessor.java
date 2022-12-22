package iot.technology.client.toolkit.mqtt.service.processor;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;

/**
 * @author mushuwei
 */
public class HelpProcessor implements TkProcessor {

	@Override
	public boolean supports(ProcessContext context) {
		return false;
	}

	@Override
	public void handle(ProcessContext context) {

	}
}
