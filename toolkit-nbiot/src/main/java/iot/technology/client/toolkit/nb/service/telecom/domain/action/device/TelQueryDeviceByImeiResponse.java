package iot.technology.client.toolkit.nb.service.telecom.domain.action.device;

import iot.technology.client.toolkit.nb.service.telecom.domain.BaseTelResponse;


/**
 * @author mushuwei
 */
public class TelQueryDeviceByImeiResponse extends BaseTelResponse {

	private TelQueryDeviceByImeiBody result;

	public TelQueryDeviceByImeiBody getResult() {
		return result;
	}

	public void setResult(TelQueryDeviceByImeiBody result) {
		this.result = result;
	}
}
