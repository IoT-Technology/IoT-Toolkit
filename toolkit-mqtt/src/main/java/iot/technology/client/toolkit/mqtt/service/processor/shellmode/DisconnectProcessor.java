package iot.technology.client.toolkit.mqtt.service.processor.shellmode;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.mqtt.config.MqttShellModeDomain;
import org.jline.reader.EndOfFileException;

/**
 * @author mushuwei
 */
public class DisconnectProcessor implements TkProcessor {

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().equals("disconnect");
    }

    @Override
    public void handle(ProcessContext context) {
        MqttProcessContext mqttProcessContext = (MqttProcessContext) context;
        MqttShellModeDomain domain = mqttProcessContext.getDomain();
        domain.getClient().disconnect();
        throw new EndOfFileException("client: " + domain.getName() + " close!");
    }
}
