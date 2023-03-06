package iot.technology.client.toolkit.nb.service.telecom.domain.action.data;

import java.io.Serializable;
import java.util.List;

/**
 * @author mushuwei
 */
public class TelQueryDeviceCommandListBody implements Serializable {

	private Integer pageNum;

	private Integer pageSize;

	private Integer total;

	private List<TelQueryDeviceCommandBody> list;

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<TelQueryDeviceCommandBody> getList() {
		return list;
	}

	public void setList(List<TelQueryDeviceCommandBody> list) {
		this.list = list;
	}
}
