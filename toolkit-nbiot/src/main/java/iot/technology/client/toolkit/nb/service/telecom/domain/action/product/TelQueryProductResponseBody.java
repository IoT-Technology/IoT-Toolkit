package iot.technology.client.toolkit.nb.service.telecom.domain.action.product;


import java.io.Serializable;

/**
 * @author mushuwei
 */
public class TelQueryProductResponseBody implements Serializable {

	private Integer productId;

	private String productName;

	private Integer onlineDeviceCount;

	private Integer deviceCount;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getOnlineDeviceCount() {
		return onlineDeviceCount;
	}

	public void setOnlineDeviceCount(Integer onlineDeviceCount) {
		this.onlineDeviceCount = onlineDeviceCount;
	}

	public Integer getDeviceCount() {
		return deviceCount;
	}

	public void setDeviceCount(Integer deviceCount) {
		this.deviceCount = deviceCount;
	}
}
