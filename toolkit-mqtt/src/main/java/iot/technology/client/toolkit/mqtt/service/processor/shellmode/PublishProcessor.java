package iot.technology.client.toolkit.mqtt.service.processor.shellmode;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;

/**
 * @author mushuwei
 */
public class PublishProcessor implements TkProcessor {

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().startsWith("pub");
    }

    @Override
    public void handle(ProcessContext context) {

    }
}
