package iot.technology.client.toolkit.nb.service.telecom.domain.action.device;

import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.DateUtils;
import iot.technology.client.toolkit.nb.service.telecom.domain.BaseTelResponse;

import java.util.Objects;


/**
 * @author mushuwei
 */
public class TelQueryDeviceByImeiResponse extends BaseTelResponse {

	private TelQueryDeviceByImeiBody result;

	public void printToConsole() {
		System.out.format("deviceId: " + ColorUtils.blackBold(result.getDeviceId()) + "%n");
		System.out.format("deviceName: " + ColorUtils.blackBold(result.getDeviceName()) + "%n");
		System.out.format("productId: " + ColorUtils.blackBold(String.valueOf(result.getProductId())) + "%n");
		System.out.format("imei: " + ColorUtils.blackBold(result.getImei()) + "%n");
		System.out.format("deviceStatus: " + ColorUtils.blackBold(String.valueOf(result.getDeviceStatus())) + "%n");
		System.out.format(
				"imsi: " + (Objects.nonNull(result.getImsi()) ? ColorUtils.blackBold(String.valueOf(result.getImsi())) : "") + "%n");
		System.out.format("firmwareVersion: " +
				(Objects.nonNull(result.getFirmwareVersion()) ? ColorUtils.blackBold(result.getFirmwareVersion()) : "") + "%n");
		System.out.format("autoObserver: " + ColorUtils.blackBold(String.valueOf(result.getAutoObserver())) + "%n");
		System.out.format("onlineAt: " +
				(Objects.nonNull(result.getOnlineAt()) ? ColorUtils.blackBold(DateUtils.timestampToFormatterTime(result.getOnlineAt())) :
						"") + "%n");
		System.out.format("offlineAt: " +
				(Objects.nonNull(result.getOfflineAt()) ? ColorUtils.blackBold(DateUtils.timestampToFormatterTime(result.getOfflineAt())) :
						"") + "%n");
	}

	public TelQueryDeviceByImeiBody getResult() {
		return result;
	}

	public void setResult(TelQueryDeviceByImeiBody result) {
		this.result = result;
	}
}
