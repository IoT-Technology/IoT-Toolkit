package iot.technology.client.toolkit.nb.service.telecom.domain.action.device;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class TelUpdateDeviceOtherRequest implements Serializable {

	private Integer autoObserver;

	private String imsi;

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
}
