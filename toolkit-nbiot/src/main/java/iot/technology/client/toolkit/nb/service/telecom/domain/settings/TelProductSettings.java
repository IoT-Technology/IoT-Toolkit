package iot.technology.client.toolkit.nb.service.telecom.domain.settings;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class TelProductSettings implements Serializable {

	private String appKey;

	private String productName;

	private String productId;

	private String masterApiKey;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMasterApiKey() {
		return masterApiKey;
	}

	public void setMasterApiKey(String masterApiKey) {
		this.masterApiKey = masterApiKey;
	}
}
