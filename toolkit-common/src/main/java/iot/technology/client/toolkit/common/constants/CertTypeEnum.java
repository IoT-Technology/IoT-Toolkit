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
public enum CertTypeEnum {

	CA_SIGNED_SERVER("1", "CA signed server"),

	SELF_SIGNED("2", "Self signed");

	private String value;

	private String desc;

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	CertTypeEnum(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public static CertTypeEnum getCertTypeEnum(String value) {
		for (CertTypeEnum elem : CertTypeEnum.values()) {
			if (elem.getValue().equals(value)) {
				return elem;
			}
		}
		return null;
	}
}
