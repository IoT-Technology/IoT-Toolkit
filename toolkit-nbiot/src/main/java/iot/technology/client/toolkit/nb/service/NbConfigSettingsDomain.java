package iot.technology.client.toolkit.nb.service;

import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.telecom.domain.settings.TelProductSettings;
import iot.technology.client.toolkit.nb.service.telecom.domain.settings.TelProjectSettings;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class NbConfigSettingsDomain implements Serializable {

	private String nbType;

	private String nbTelecomAppConfig;

	private String nbTelProjectName;

	private String nbTelAppSecret;

	private String nbTelAppKey;

	private String nbTelecomProductConfig;

	private String nbTelProductId;

	private String nbTelApiKey;

	public TelecomConfigDomain convertTelecomConfig() {
		TelecomConfigDomain domain = new TelecomConfigDomain();
		if (nbType.equals(NBTypeEnum.TELECOM.getValue())) {
			if (nbTelecomAppConfig.equals("new")) {
				domain.setAppSecret(nbTelAppSecret);
				domain.setAppKey(nbTelAppKey);
			} else {
				TelProjectSettings projectSettings = JsonUtils.jsonToObject(nbTelecomAppConfig, TelProjectSettings.class);
				domain.setAppKey(projectSettings.getAppKey());
				domain.setAppSecret(projectSettings.getAppSecret());
			}
			if (nbTelecomProductConfig.equals("new")) {
				domain.setProductId(nbTelProductId);
				domain.setMasterKey(nbTelApiKey);
			} else {
				TelProductSettings productSettings = JsonUtils.jsonToObject(nbTelecomProductConfig, TelProductSettings.class);
				domain.setProductId(productSettings.getProductId());
				domain.setMasterKey(productSettings.getMasterApiKey());
			}
		}
		return domain;

	}

	public String getNbType() {
		return nbType;
	}

	public void setNbType(String nbType) {
		this.nbType = nbType;
	}

	public String getNbTelecomAppConfig() {
		return nbTelecomAppConfig;
	}

	public void setNbTelecomAppConfig(String nbTelecomAppConfig) {
		this.nbTelecomAppConfig = nbTelecomAppConfig;
	}

	public String getNbTelProjectName() {
		return nbTelProjectName;
	}

	public void setNbTelProjectName(String nbTelProjectName) {
		this.nbTelProjectName = nbTelProjectName;
	}

	public String getNbTelAppSecret() {
		return nbTelAppSecret;
	}

	public void setNbTelAppSecret(String nbTelAppSecret) {
		this.nbTelAppSecret = nbTelAppSecret;
	}

	public String getNbTelAppKey() {
		return nbTelAppKey;
	}

	public void setNbTelAppKey(String nbTelAppKey) {
		this.nbTelAppKey = nbTelAppKey;
	}

	public String getNbTelecomProductConfig() {
		return nbTelecomProductConfig;
	}

	public void setNbTelecomProductConfig(String nbTelecomProductConfig) {
		this.nbTelecomProductConfig = nbTelecomProductConfig;
	}

	public String getNbTelProductId() {
		return nbTelProductId;
	}

	public void setNbTelProductId(String nbTelProductId) {
		this.nbTelProductId = nbTelProductId;
	}

	public String getNbTelApiKey() {
		return nbTelApiKey;
	}

	public void setNbTelApiKey(String nbTelApiKey) {
		this.nbTelApiKey = nbTelApiKey;
	}
}
