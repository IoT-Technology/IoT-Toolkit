package iot.technology.client.toolkit.nb.service.mobile.domain.action.data;

import java.io.Serializable;
import java.util.List;

/**
 * @author mushuwei
 */
public class MobCachedCommandBody implements Serializable {

	private Integer totalCount;

	private List<MobCachedCommandItem> items;

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public List<MobCachedCommandItem> getItems() {
		return items;
	}

	public void setItems(List<MobCachedCommandItem> items) {
		this.items = items;
	}
}
