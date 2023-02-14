package iot.technology.client.toolkit.nb.service.telecom.domain.action.data;

import iot.technology.client.toolkit.nb.service.telecom.domain.BaseTelResponse;

/**
 * @author mushuwei
 */
public class TelQueryDeviceDataTotalResponse extends BaseTelResponse {

	private Integer total;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
}
