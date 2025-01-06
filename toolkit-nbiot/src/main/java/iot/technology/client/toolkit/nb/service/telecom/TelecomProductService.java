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
package iot.technology.client.toolkit.nb.service.telecom;

import iot.technology.client.toolkit.common.constants.NBTypeEnum;
import iot.technology.client.toolkit.common.constants.TelecomSettings;
import iot.technology.client.toolkit.common.http.HttpRequestEntity;
import iot.technology.client.toolkit.common.http.HttpRequestExecutor;
import iot.technology.client.toolkit.common.http.HttpResponseEntity;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.product.TelQueryProductResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * product management
 * 1、 delete product
 * 2、 single query product
 */
public class TelecomProductService extends AbstractTelecomService {

	public TelQueryProductResponse queryProduct(TelecomConfigDomain config) {
		TelQueryProductResponse queryProductResponse = new TelQueryProductResponse();
		try {
			HttpRequestEntity entity = new HttpRequestEntity();
			entity.setType(NBTypeEnum.TELECOM.getValue());
			entity.setUrl(TelecomSettings.TEL_PRODUCT_URL);
			Map<String, String> params = new HashMap<>();
			params.put(TelecomSettings.PRODUCT, config.getProductId());
			Map<String, String> headerMap = getHeaderMap(config, TelecomSettings.TEL_QUERY_PRODUCT_API_VERSION, params, null);
			entity.setHeaders(headerMap);
			entity.setParams(params);
			HttpResponseEntity response = HttpRequestExecutor.executeGet(entity);
			if (StringUtils.isNotBlank(response.getBody())) {
				queryProductResponse = JsonUtils.jsonToObject(response.getBody(), TelQueryProductResponse.class);
				if (queryProductResponse.getCode() == 0) {
					queryProductResponse.setSuccess(Boolean.TRUE);
					return queryProductResponse;
				} else {
					System.out.format(ColorUtils.redError(queryProductResponse.getMsg()));
					queryProductResponse.setSuccess(Boolean.FALSE);
					return queryProductResponse;
				}
			} else {
				queryProductResponse.setSuccess(Boolean.FALSE);
				System.out.format(config.getProductId() + ColorUtils.redError(" queryProduct failed!"));
				return queryProductResponse;
			}
		} catch (Exception e) {
			queryProductResponse.setSuccess(Boolean.FALSE);
			System.out.format(config.getProductId() + ColorUtils.redError(" queryProduct failed!"));
			return queryProductResponse;
		}
	}
}
