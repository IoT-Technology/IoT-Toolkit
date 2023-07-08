/*
 * Copyright © 2019-2023 The Toolkit Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package iot.technology.client.toolkit.nb.service;

import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.settings.MobProjectSettings;
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

	private String mobAppConfig;

	private String mobProductName;

	private String mobProductId;

	private String mobAccessKey;

	private String lwm2mConfig;

	private String lwm2mServer;

	private String lwm2mPort;

	private String lwm2mBootstrapServer;

	private String lwm2mEndpoint;

	private String lwm2mLocalAddress;

	private String lwm2mModelsFolder;

	private String lwm2mLifeTime;

	private String lwm2mCommunicationPeriod;

	private String lwm2mDtls;

	private String lwm2mChooseAlgorithm;

	private String lwm2mPskIdentity;

	private String lwm2mPskShareKey;

	private String lwm2mClientPrivateKey;

	private String lwm2mClientPublicKey;

	private String lwm2mServerPublicKey;

	private String lwm2mClientCert;

	private String lwm2mServerCert;

	private String lwm2mCertUsage;


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
				domain.setProductName(projectSettings.getProductName());
				domain.setAppKey(projectSettings.getAppKey());
				domain.setAppSecret(projectSettings.getAppSecret());
				domain.setProductId(projectSettings.getProductId());
				domain.setMasterKey(projectSettings.getMasterApiKey());
			}
		}
		return domain;
	}


	public MobileConfigDomain convertMobileConfig() {
		MobileConfigDomain domain = new MobileConfigDomain();
		if (nbType.equals(NBTypeEnum.MOBILE.getValue())) {
			if (mobAppConfig.equals("new")) {
				domain.setProductName(mobProductName);
				domain.setProductId(Integer.valueOf(mobProductId));
				domain.setAccessKey(mobAccessKey);
			} else {
				MobProjectSettings mobProjectSettings = JsonUtils.jsonToObject(mobAppConfig, MobProjectSettings.class);
				domain.setProductName(mobProjectSettings.getProductName());
				domain.setProductId(mobProjectSettings.getProductId());
				domain.setAccessKey(mobProjectSettings.getAccessKey());
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

	public String getMobAppConfig() {
		return mobAppConfig;
	}

	public void setMobAppConfig(String mobAppConfig) {
		this.mobAppConfig = mobAppConfig;
	}

	public String getMobProductName() {
		return mobProductName;
	}

	public void setMobProductName(String mobProductName) {
		this.mobProductName = mobProductName;
	}

	public String getMobProductId() {
		return mobProductId;
	}

	public void setMobProductId(String mobProductId) {
		this.mobProductId = mobProductId;
	}

	public String getMobAccessKey() {
		return mobAccessKey;
	}

	public void setMobAccessKey(String mobAccessKey) {
		this.mobAccessKey = mobAccessKey;
	}
}
