package iot.technology.client.toolkit.nb.service.telecom.domain.action.device;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class TelDeviceRequest implements Serializable {

	private String imei;

	private String deviceName;

	private Integer autoObserver;

	private String imsi;

	private Integer pskType;

	private String pskValue;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Integer getAutoObserver() {
		return autoObserver;
	}

	public void setAutoObserver(Integer autoObserver) {
		this.autoObserver = autoObserver;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public Integer getPskType() {
		return pskType;
	}

	public void setPskType(Integer pskType) {
		this.pskType = pskType;
	}

	public String getPskValue() {
		return pskValue;
	}

	public void setPskValue(String pskValue) {
		this.pskValue = pskValue;
	}
}
