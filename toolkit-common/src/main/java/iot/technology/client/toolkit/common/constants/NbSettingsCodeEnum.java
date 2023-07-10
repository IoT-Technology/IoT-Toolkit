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
package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public enum NbSettingsCodeEnum {

	NB_TYPE("nbType", "iot.technology.client.toolkit.nb.service.node.NbTypeNode"),

	NB_SETTINGS("nbSettings", "iot.technology.client.toolkit.nb.service.node.NbSettingsNode"),

	NB_TELECOM_APP_CONFIG("nbTelecomAppConfig", "iot.technology.client.toolkit.nb.service.node.NbTelecomAppConfigNode"),

	NB_TEL_APP_SECRET("nbTelAppSecret", "iot.technology.client.toolkit.nb.service.node.TelAppSecretNode"),

	NB_TEL_APP_KEY("nbTelAppKey", "iot.technology.client.toolkit.nb.service.node.TelAppKeyNode"),

	NB_TEL_PRODUCT_ID("nbTelProductId", "iot.technology.client.toolkit.nb.service.node.TelProductIdNode"),

	NB_TEL_API_KEY("nbTelApiKey", "iot.technology.client.toolkit.nb.service.node.TelApiKeyNode"),

	NB_MOB_APP_CONFIG("mobAppConfig", "iot.technology.client.toolkit.nb.service.node.MobAppConfigNode"),

	NB_MOB_PRODUCT_NAME("mobProductName", "iot.technology.client.toolkit.nb.service.node.MobProductNameNode"),

	NB_MOB_PRODUCT_ID("mobProductId", "iot.technology.client.toolkit.nb.service.node.MobProductIdNode"),

	NB_MOB_ACCESS_KEY("mobAccessKey", "iot.technology.client.toolkit.nb.service.node.MobAccessKeyNode"),

	/**
	 * lwm2m config
	 */

	NB_LWM2M_CONFIG("lwm2mConfig", "iot.technology.client.toolkit.nb.service.node.NbLwM2MConfigNode"),

	NB_LWM2M_URL("lwm2mServer", "iot.technology.client.toolkit.nb.service.node.LwM2MServerUrlNode"),

	NB_LWM2M_PORT("lwm2mPort", "iot.technology.client.toolkit.nb.service.node.LwM2MPortNode"),

	NB_LWM2M_BOOTSTRAP("lwm2mBootstrapServer", "iot.technology.client.toolkit.nb.service.node.LwM2MBootstrapServerNode"),

	NB_LWM2M_ENDPOINT("lwm2mEndpoint", "iot.technology.client.toolkit.nb.service.node.LwM2MEndpointNode"),

	NB_LWM2M_LOCAL_ADDRESS("lwm2mLocalAddress", "iot.technology.client.toolkit.nb.service.node.LwM2MLocalAddressNode"),

	NB_LWM2M_LIFETIME("lwm2mLifeTime", "iot.technology.client.toolkit.nb.service.node.LwM2MLifeTimeNode"),

	NB_LWM2M_COMMUNICATION_PERIOD("lwm2mCommunicationPeriod", "iot.technology.client.toolkit.nb.service.node.LwM2MComPeriodNode"),

	NB_LWM2M_MODELS_FOLDER("lwm2mModelsFolder", "iot.technology.client.toolkit.nb.service.node.LwM2MModelsFolderNode"),

	NB_LWM2M_DTLS("lwm2mDtls", "iot.technology.client.toolkit.nb.service.node.LwM2MDtlsNode"),

	NB_LWM2M_SELECT_ALGORITHM("lwm2mChooseAlgorithm", "iot.technology.client.toolkit.nb.service.node.LwM2MChooseAlgorithmNode"),

	NB_LWM2M_PSK_IDENTITY("lwm2mPskIdentity", "iot.technology.client.toolkit.nb.service.node.LwM2MPskIdentityNode"),

	NB_LWM2M_PSK_SHARE_KEY("lwm2mPskShareKey", "iot.technology.client.toolkit.nb.service.node.LwM2MShareKeyNode"),

	NB_LWM2M_CLIENT_PRIVATE_KEY("lwm2mClientPrivateKey", "iot.technology.client.toolkit.nb.service.node.LwM2MClientPrivateKeyNode"),

	NB_LWM2M_CLIENT_PUBLIC_KEY("lwm2mClientPublicKey", "iot.technology.client.toolkit.nb.service.node.LwM2MClientPublicKeyNode"),

	NB_LWM2M_SERVER_PUBLIC_KEY("lwm2mServerPublicKey", "iot.technology.client.toolkit.nb.service.node.LwM2MServerPublicKeyNode"),

	NB_LWM2M_CLIENT_CERT("lwm2mClientCert", "iot.technology.client.toolkit.nb.service.node.LwM2MClientCertNode"),

	NB_LWM2M_SERVER_CERT("lwm2mServerCert", "iot.technology.client.toolkit.nb.service.node.LwM2MServerCertNode"),

	NB_LWM2M_CERT_USAGE("lwm2mCertUsage", "iot.technology.client.toolkit.nb.service.node.LwM2MCertUsageNode"),

	END("end", "");


	private String code;

	private String clazzName;

	public String getCode() {
		return code;
	}

	public String getClazzName() {
		return clazzName;
	}

	NbSettingsCodeEnum(String code, String clazzName) {
		this.code = code;
		this.clazzName = clazzName;
	}
}
