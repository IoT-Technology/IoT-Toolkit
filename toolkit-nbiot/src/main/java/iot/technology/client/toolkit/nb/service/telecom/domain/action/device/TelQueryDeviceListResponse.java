package iot.technology.client.toolkit.nb.service.telecom.domain.action.device;

import iot.technology.client.toolkit.nb.service.telecom.domain.BaseTelResponse;

/**
 * @author mushuwei
 */
public class TelQueryDeviceListResponse extends BaseTelResponse {

	private TelQueryDeviceListBody result;

	public TelQueryDeviceListBody getResult() {
		return result;
	}

	public void setResult(TelQueryDeviceListBody result) {
		this.result = result;
	}
}
