package iot.technology.client.toolkit.nb.service.mobile.domain.settings;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class MobProjectSettings implements Serializable {

	private String productName;

	private Integer productId;

	private String accessKey;

	private String serial;

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

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}
}
