package iot.technology.client.toolkit.nb.service.processor;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;

/**
 * @author mushuwei
 */
public class MobProcessContext extends ProcessContext {

	private MobileConfigDomain mobileConfigDomain;

	public MobileConfigDomain getMobileConfigDomain() {
		return mobileConfigDomain;
	}

	public void setMobileConfigDomain(MobileConfigDomain mobileConfigDomain) {
		this.mobileConfigDomain = mobileConfigDomain;
	}
}
