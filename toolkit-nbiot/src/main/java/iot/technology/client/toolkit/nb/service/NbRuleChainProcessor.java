/*
 * Copyright © 2019-2025 The Toolkit Authors
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

import iot.technology.client.toolkit.common.constants.NbSettingsCodeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mushuwei
 */
public class NbRuleChainProcessor {

	public String getNbTypeNode() {
		return NbSettingsCodeEnum.NB_TYPE.getCode();
	}

	public Map<String, String> getNbRuleChainProcessor() {
		Map<String, String> map = new HashMap<>();
		map.put(NbSettingsCodeEnum.NB_TYPE.getCode(), NbSettingsCodeEnum.NB_TYPE.getClazzName());
		map.put(NbSettingsCodeEnum.NB_SETTINGS.getCode(), NbSettingsCodeEnum.NB_SETTINGS.getClazzName());
		map.put(NbSettingsCodeEnum.NB_TELECOM_APP_CONFIG.getCode(), NbSettingsCodeEnum.NB_TELECOM_APP_CONFIG.getClazzName());
		map.put(NbSettingsCodeEnum.NB_TEL_APP_KEY.getCode(), NbSettingsCodeEnum.NB_TEL_APP_KEY.getClazzName());
		map.put(NbSettingsCodeEnum.NB_TEL_APP_SECRET.getCode(), NbSettingsCodeEnum.NB_TEL_APP_SECRET.getClazzName());
		map.put(NbSettingsCodeEnum.NB_TEL_PRODUCT_ID.getCode(), NbSettingsCodeEnum.NB_TEL_PRODUCT_ID.getClazzName());
		map.put(NbSettingsCodeEnum.NB_TEL_API_KEY.getCode(), NbSettingsCodeEnum.NB_TEL_API_KEY.getClazzName());
		map.put(NbSettingsCodeEnum.NB_MOB_APP_CONFIG.getCode(), NbSettingsCodeEnum.NB_MOB_APP_CONFIG.getClazzName());
		map.put(NbSettingsCodeEnum.NB_MOB_PRODUCT_NAME.getCode(), NbSettingsCodeEnum.NB_MOB_PRODUCT_NAME.getClazzName());
		map.put(NbSettingsCodeEnum.NB_MOB_PRODUCT_ID.getCode(), NbSettingsCodeEnum.NB_MOB_PRODUCT_ID.getClazzName());
		map.put(NbSettingsCodeEnum.NB_MOB_ACCESS_KEY.getCode(), NbSettingsCodeEnum.NB_MOB_ACCESS_KEY.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_CONFIG.getCode(), NbSettingsCodeEnum.NB_LWM2M_CONFIG.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_URL.getCode(), NbSettingsCodeEnum.NB_LWM2M_URL.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_PORT.getCode(), NbSettingsCodeEnum.NB_LWM2M_PORT.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_BOOTSTRAP.getCode(), NbSettingsCodeEnum.NB_LWM2M_BOOTSTRAP.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_ENDPOINT.getCode(), NbSettingsCodeEnum.NB_LWM2M_ENDPOINT.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_LOCAL_ADDRESS.getCode(), NbSettingsCodeEnum.NB_LWM2M_LOCAL_ADDRESS.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_MODELS_FOLDER.getCode(), NbSettingsCodeEnum.NB_LWM2M_MODELS_FOLDER.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_LIFETIME.getCode(), NbSettingsCodeEnum.NB_LWM2M_LIFETIME.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_COMMUNICATION_PERIOD.getCode(), NbSettingsCodeEnum.NB_LWM2M_COMMUNICATION_PERIOD.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_DTLS.getCode(), NbSettingsCodeEnum.NB_LWM2M_DTLS.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_SELECT_ALGORITHM.getCode(), NbSettingsCodeEnum.NB_LWM2M_SELECT_ALGORITHM.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_PSK_IDENTITY.getCode(), NbSettingsCodeEnum.NB_LWM2M_PSK_IDENTITY.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_PSK_SHARE_KEY.getCode(), NbSettingsCodeEnum.NB_LWM2M_PSK_SHARE_KEY.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_CLIENT_PRIVATE_KEY.getCode(), NbSettingsCodeEnum.NB_LWM2M_CLIENT_PRIVATE_KEY.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_CLIENT_PUBLIC_KEY.getCode(), NbSettingsCodeEnum.NB_LWM2M_CLIENT_PUBLIC_KEY.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_SERVER_PUBLIC_KEY.getCode(), NbSettingsCodeEnum.NB_LWM2M_SERVER_PUBLIC_KEY.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_CLIENT_CERT.getCode(), NbSettingsCodeEnum.NB_LWM2M_CLIENT_CERT.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_SERVER_CERT.getCode(), NbSettingsCodeEnum.NB_LWM2M_SERVER_CERT.getClazzName());
		map.put(NbSettingsCodeEnum.NB_LWM2M_CERT_USAGE.getCode(), NbSettingsCodeEnum.NB_LWM2M_CERT_USAGE.getClazzName());
		return map;
	}
}
