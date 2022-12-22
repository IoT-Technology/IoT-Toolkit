package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import java.io.Serializable;
import java.util.List;

/**
 * @author mushuwei
 */
public class MobQueryDeviceListBody implements Serializable {

	private Integer per_page;

	private Integer total_count;

	private List<MobQueryDeviceBody> devices;

	private Integer page;

	public Integer getPer_page() {
		return per_page;
	}

	public void setPer_page(Integer per_page) {
		this.per_page = per_page;
	}

	public Integer getTotal_count() {
		return total_count;
	}

	public void setTotal_count(Integer total_count) {
		this.total_count = total_count;
	}

	public List<MobQueryDeviceBody> getDevices() {
		return devices;
	}

	public void setDevices(
			List<MobQueryDeviceBody> devices) {
		this.devices = devices;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}
}
