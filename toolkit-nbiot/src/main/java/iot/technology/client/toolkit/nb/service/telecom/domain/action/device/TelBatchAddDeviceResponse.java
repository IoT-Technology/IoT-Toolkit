package iot.technology.client.toolkit.nb.service.telecom.domain.action.device;

import iot.technology.client.toolkit.nb.service.telecom.domain.BaseTelResponse;

import java.util.List;

/**
 * @author mushuwei
 */
public class TelBatchAddDeviceResponse extends BaseTelResponse {

	private List<TelBatchAddDeviceBody> result;

	public List<TelBatchAddDeviceBody> getResult() {
		return result;
	}

	public void setResult(List<TelBatchAddDeviceBody> result) {
		this.result = result;
	}
}
