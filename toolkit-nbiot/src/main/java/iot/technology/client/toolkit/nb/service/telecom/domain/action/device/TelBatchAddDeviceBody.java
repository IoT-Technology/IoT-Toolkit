package iot.technology.client.toolkit.nb.service.telecom.domain.action.device;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class TelBatchAddDeviceBody implements Serializable {

	private String imei;

	private Integer resultCode;

	private String deviceId;

	private String description;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Integer getResultCode() {
		return resultCode;
	}

	public void setResultCode(Integer resultCode) {
		this.resultCode = resultCode;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
