package iot.technology.client.toolkit.nb.service.telecom.domain.action.device;

import java.io.Serializable;
import java.util.List;

/**
 * @author mushuwei
 */
public class TelDelDeviceByImeiRequest implements Serializable {

	private Integer productId;

	private List<String> imeiList;


	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public List<String> getImeiList() {
		return imeiList;
	}

	public void setImeiList(List<String> imeiList) {
		this.imeiList = imeiList;
	}
}
