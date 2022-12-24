package iot.technology.client.toolkit.nb.service.telecom.domain.settings;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class TelProjectSettings implements Serializable {

	private String productName;

	private String appKey;

	private String appSecret;

	private String productId;

	private String masterApiKey;

	private String serial;

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
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

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
}
