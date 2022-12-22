package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import iot.technology.client.toolkit.nb.service.mobile.domain.BaseMobileResponse;

/**
 * @author mushuwei
 */
public class MobQueryDeviceListResponse extends BaseMobileResponse {

	private MobQueryDeviceListBody data;

	public MobQueryDeviceListBody getData() {
		return data;
	}

	public void setData(MobQueryDeviceListBody data) {
		this.data = data;
	}
}
