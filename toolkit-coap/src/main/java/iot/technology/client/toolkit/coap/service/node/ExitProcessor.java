package iot.technology.client.toolkit.coap.service.node;

import iot.technology.client.toolkit.common.rule.TkProcessor;

/**
 * @author mushuwei
 */
public class ExitProcessor implements TkProcessor {

	@Override
	public boolean supports(String data) {
		return false;
	}

	@Override
	public String handle(String data) {
		return null;
	}
}
