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
		map.put(NbSettingsCodeEnum.NB_TELECOM_APP_CONFIG.getCode(), NbSettingsCodeEnum.NB_TELECOM_APP_CONFIG.getClazzName());
		map.put(NbSettingsCodeEnum.NB_TEL_APP_KEY.getCode(), NbSettingsCodeEnum.NB_TEL_APP_KEY.getClazzName());
		map.put(NbSettingsCodeEnum.NB_TEL_APP_SECRET.getCode(), NbSettingsCodeEnum.NB_TEL_APP_SECRET.getClazzName());
		map.put(NbSettingsCodeEnum.NB_TEL_PRODUCT_ID.getCode(), NbSettingsCodeEnum.NB_TEL_PRODUCT_ID.getClazzName());
		map.put(NbSettingsCodeEnum.NB_TEL_API_KEY.getCode(), NbSettingsCodeEnum.NB_TEL_API_KEY.getClazzName());
		return map;
	}
}
