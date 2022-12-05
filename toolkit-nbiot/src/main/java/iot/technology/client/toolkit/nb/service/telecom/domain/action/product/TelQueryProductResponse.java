package iot.technology.client.toolkit.nb.service.telecom.domain.action.product;

import iot.technology.client.toolkit.nb.service.telecom.domain.BaseTelResponse;

/**
 * @author mushuwei
 */
public class TelQueryProductResponse extends BaseTelResponse {

	private TelQueryProductResponseBody result;

	public TelQueryProductResponseBody getResult() {
		return result;
	}

	public void setResult(TelQueryProductResponseBody result) {
		this.result = result;
	}
}
