package iot.technology.client.toolkit.nb.service.telecom.domain.action.device;

import java.io.Serializable;
import java.util.List;

/**
 * @author mushuwei
 */
public class TelBatchAddDeviceRequest implements Serializable {

	private Integer productId;

	private String operator;

	private List<TelDeviceRequest> devices;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public List<TelDeviceRequest> getDevices() {
		return devices;
	}

	public void setDevices(List<TelDeviceRequest> devices) {
		this.devices = devices;
	}
}
