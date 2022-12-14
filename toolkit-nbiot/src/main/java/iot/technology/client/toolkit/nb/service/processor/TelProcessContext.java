package iot.technology.client.toolkit.nb.service.processor;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;

/**
 * @author mushuwei
 */
public class TelProcessContext extends ProcessContext {

	private TelecomConfigDomain telecomConfigDomain;

	public TelecomConfigDomain getTelecomConfigDomain() {
		return telecomConfigDomain;
	}

	public void setTelecomConfigDomain(TelecomConfigDomain telecomConfigDomain) {
		this.telecomConfigDomain = telecomConfigDomain;
	}
}
