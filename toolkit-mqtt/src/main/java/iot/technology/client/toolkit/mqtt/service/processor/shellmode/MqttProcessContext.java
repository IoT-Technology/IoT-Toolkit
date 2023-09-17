package iot.technology.client.toolkit.mqtt.service.processor.shellmode;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.mqtt.config.MqttShellModeDomain;

/**
 * @author mushuwei
 */
public class MqttProcessContext  extends ProcessContext {

   private MqttShellModeDomain domain;

    public MqttShellModeDomain getDomain() {
        return domain;
    }

    public void setDomain(MqttShellModeDomain domain) {
        this.domain = domain;
    }
}
