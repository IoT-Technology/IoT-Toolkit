package iot.technology.client.toolkit.nb.service.processor;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.nb.service.lwm2m.domain.Lwm2mConfigSettingsDomain;

/**
 * @author mushuwei
 */
public class Lwm2mProcessContext extends ProcessContext {

    private Lwm2mConfigSettingsDomain domain;

    public Lwm2mConfigSettingsDomain getDomain() {
        return domain;
    }

    public void setDomain(Lwm2mConfigSettingsDomain domain) {
        this.domain = domain;
    }
}
