package iot.technology.client.toolkit.nb.service;

import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.telecom.domain.settings.TelProjectSettings;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class NbConfigSettingsDomain implements Serializable {

	private String nbType;

	private String nbTelecomAppConfig;

	private String nbTelAppSecret;

	private String nbTelAppKey;

	private String nbTelProductId;

	private String nbTelApiKey;

	private String nbMobileAppConfig;

	private String nbMobProductName;

	private String nbMobProductId;

	private String nbMobAccessKey;

	public TelecomConfigDomain convertTelecomConfig() {
		TelecomConfigDomain domain = new TelecomConfigDomain();
		if (nbType.equals(NBTypeEnum.TELECOM.getValue())) {
			if (nbTelecomAppConfig.equals("new")) {
				domain.setAppSecret(nbTelAppSecret);
				domain.setAppKey(nbTelAppKey);
				domain.setProductId(nbTelProductId);
				domain.setMasterKey(nbTelApiKey);
			} else {
				TelProjectSettings projectSettings = JsonUtils.jsonToObject(nbTelecomAppConfig, TelProjectSettings.class);
				domain.setAppKey(projectSettings.getAppKey());
				domain.setAppSecret(projectSettings.getAppSecret());
				domain.setProductId(projectSettings.getProductId());
				domain.setMasterKey(projectSettings.getMasterApiKey());
			}
		}
		return domain;
	}

	public TelecomConfigDomain oldSettingsConvertTelecomConfig() {
		TelecomConfigDomain domain = new TelecomConfigDomain();
		TelProjectSettings telProjectSettings = JsonUtils.jsonToObject(nbTelecomAppConfig, TelProjectSettings.class);
		domain.setAppKey(telProjectSettings.getAppKey());
		domain.setAppSecret(telProjectSettings.getAppSecret());
		domain.setProductId(telProjectSettings.getProductId());
		domain.setMasterKey(telProjectSettings.getMasterApiKey());
		domain.setProductName(telProjectSettings.getProductName());
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

	public String getNbMobProductName() {
		return nbMobProductName;
	}

	public void setNbMobProductName(String nbMobProductName) {
		this.nbMobProductName = nbMobProductName;
	}

	public String getNbMobProductId() {
		return nbMobProductId;
	}

	public void setNbMobProductId(String nbMobProductId) {
		this.nbMobProductId = nbMobProductId;
	}

	public String getNbMobAccessKey() {
		return nbMobAccessKey;
	}

	public void setNbMobAccessKey(String nbMobAccessKey) {
		this.nbMobAccessKey = nbMobAccessKey;
	}

	public String getNbMobileAppConfig() {
		return nbMobileAppConfig;
	}

	public void setNbMobileAppConfig(String nbMobileAppConfig) {
		this.nbMobileAppConfig = nbMobileAppConfig;
	}
}
