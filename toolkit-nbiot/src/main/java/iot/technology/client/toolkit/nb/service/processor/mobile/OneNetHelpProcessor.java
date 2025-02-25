package iot.technology.client.toolkit.nb.service.processor.mobile;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;

public class OneNetHelpProcessor extends TkAbstractProcessor {

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().startsWith("help");
    }

    @Override
    public void handle(ProcessContext context) {

    }
}
