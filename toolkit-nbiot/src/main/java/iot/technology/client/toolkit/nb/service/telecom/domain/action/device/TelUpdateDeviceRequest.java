package iot.technology.client.toolkit.nb.service.telecom.domain.action.device;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class TelUpdateDeviceRequest implements Serializable {

	private String deviceName;

	private String operator;

	private TelUpdateDeviceOtherRequest other;

	private Integer productId;

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public TelUpdateDeviceOtherRequest getOther() {
		return other;
	}

	public void setOther(TelUpdateDeviceOtherRequest other) {
		this.other = other;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}
