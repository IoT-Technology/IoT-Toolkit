package iot.technology.client.toolkit.nb.service.telecom.domain.action.device;

import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.nb.service.telecom.domain.BaseTelResponse;

import java.util.Objects;


/**
 * @author mushuwei
 */
public class TelQueryDeviceByImeiResponse extends BaseTelResponse {

	private TelQueryDeviceByImeiBody result;

	public void printToConsole() {
		System.out.format("deviceId: " + ColorUtils.blackBold(result.getDeviceId()));
		System.out.format("deviceName: " + ColorUtils.blackBold(result.getDeviceName()));
		System.out.format("productId: " + ColorUtils.blackBold(String.valueOf(result.getProductId())));
		System.out.format("imei: " + ColorUtils.blackBold(result.getImei()));
		System.out.format("deviceStatus: " + ColorUtils.blackBold(String.valueOf(result.getDeviceStatus())));
		System.out.format("imsi: " + ColorUtils.blackBold(Objects.requireNonNull(result.getImsi())));
		System.out.format("firmwareVersion: " + ColorUtils.blackBold(Objects.requireNonNull(result.getFirmwareVersion())));
		System.out.format("autoObserver: " + ColorUtils.blackBold(String.valueOf(Objects.requireNonNull(result.getAutoObserver()))));
	}

	public TelQueryDeviceByImeiBody getResult() {
		return result;
	}

	public void setResult(TelQueryDeviceByImeiBody result) {
		this.result = result;
	}
}
