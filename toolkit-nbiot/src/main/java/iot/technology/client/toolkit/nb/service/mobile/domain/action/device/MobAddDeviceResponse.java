package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import iot.technology.client.toolkit.nb.service.mobile.domain.BaseMobileResponse;

/**
 * @author mushuwei
 */
public class MobAddDeviceResponse extends BaseMobileResponse {

	private AddDeviceBody data;

	public AddDeviceBody getData() {
		return data;
	}

	public void setData(AddDeviceBody data) {
		this.data = data;
	}
}
