package iot.technology.client.toolkit.nb.service.processor;

import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;

import java.util.Objects;

/**
 * @author mushuwei
 */
public class MobileProcessor implements TkProcessor {

	@Override
	public boolean supports(ProcessContext context) {
		return Objects.requireNonNull(context.getData()).equals(NBTypeEnum.MOBILE.getCode());
	}

	@Override
	public void handle(ProcessContext context) {

	}
}
