package iot.technology.client.toolkit.nb.service.telecom.domain.settings;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class TelProductSettings implements Serializable {

	private Integer serialNumber;

	private String productName;

	private Integer productId;

	private String masterApiKey;

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getMasterApiKey() {
		return masterApiKey;
	}

	public void setMasterApiKey(String masterApiKey) {
		this.masterApiKey = masterApiKey;
	}
}
