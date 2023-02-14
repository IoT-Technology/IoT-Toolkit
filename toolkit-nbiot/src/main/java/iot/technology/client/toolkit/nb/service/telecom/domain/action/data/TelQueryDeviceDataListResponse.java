package iot.technology.client.toolkit.nb.service.telecom.domain.action.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import iot.technology.client.toolkit.nb.service.telecom.domain.BaseTelResponse;

import java.util.List;

/**
 * @author mushuwei
 */
public class TelQueryDeviceDataListResponse extends BaseTelResponse {

	@JsonProperty("page_timestamp")
	private String pageTimestamp;

	private List<Object> deviceStatusList;

	public String getPageTimestamp() {
		return pageTimestamp;
	}

	public void setPageTimestamp(String pageTimestamp) {
		this.pageTimestamp = pageTimestamp;
	}

	public List<Object> getDeviceStatusList() {
		return deviceStatusList;
	}

	public void setDeviceStatusList(List<Object> deviceStatusList) {
		this.deviceStatusList = deviceStatusList;
	}
}
