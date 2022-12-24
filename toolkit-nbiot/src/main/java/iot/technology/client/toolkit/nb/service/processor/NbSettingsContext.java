package iot.technology.client.toolkit.nb.service.processor;

import iot.technology.client.toolkit.common.rule.ProcessContext;

/**
 * @author mushuwei
 */
public class NbSettingsContext extends ProcessContext {

	private String nbType;

	public String getNbType() {
		return nbType;
	}

	public void setNbType(String nbType) {
		this.nbType = nbType;
	}
}
