package iot.technology.client.toolkit.nb.service.telecom.domain.action.data;

import iot.technology.client.toolkit.nb.service.telecom.domain.BaseTelResponse;

/**
 * @author mushuwei
 */
public class TelQueryDeviceCommandListResponse extends BaseTelResponse {

	private TelQueryDeviceCommandListBody result;

	public TelQueryDeviceCommandListBody getResult() {
		return result;
	}

	public void setResult(TelQueryDeviceCommandListBody result) {
		this.result = result;
	}
}
