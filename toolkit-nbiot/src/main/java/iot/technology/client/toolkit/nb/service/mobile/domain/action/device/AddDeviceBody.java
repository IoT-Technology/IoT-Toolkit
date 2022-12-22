package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class AddDeviceBody implements Serializable {

	private String device_id;

	private String psk;

	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getPsk() {
		return psk;
	}

	public void setPsk(String psk) {
		this.psk = psk;
	}
}
