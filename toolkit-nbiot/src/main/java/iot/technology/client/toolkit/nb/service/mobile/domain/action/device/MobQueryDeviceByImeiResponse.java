package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import iot.technology.client.toolkit.nb.service.mobile.domain.BaseMobileResponse;


/**
 * @author mushuwei
 */
public class MobQueryDeviceByImeiResponse extends BaseMobileResponse {

	private MobQueryDeviceByImeiBody data;


	public MobQueryDeviceByImeiBody getData() {
		return data;
	}

	public void setData(MobQueryDeviceByImeiBody data) {
		this.data = data;
	}
}
