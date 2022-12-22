package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.nb.service.mobile.domain.BaseMobileResponse;


/**
 * @author mushuwei
 */
public class MobQueryDeviceByImeiResponse extends BaseMobileResponse {

	private MobQueryDeviceByImeiBody data;

	public void printToConsole() {
		System.out.format("deviceName:    " + ColorUtils.blackBold(data.getTitle()) + "%n");
		System.out.format("deviceId:      " + ColorUtils.blackBold(data.getId()) + "%n");
		System.out.format("online:        " + ColorUtils.blackBold(data.isOnline() ? "在线" : "离线") + "%n");
		System.out.format("observeStatus: " + ColorUtils.blackBold(data.isObserve_status() ? "订阅" : "不订阅") + "%n");
		System.out.format("createTime:    " + ColorUtils.blackBold(data.getCreate_time()) + "%n");
	}


	public MobQueryDeviceByImeiBody getData() {
		return data;
	}

	public void setData(MobQueryDeviceByImeiBody data) {
		this.data = data;
	}
}
