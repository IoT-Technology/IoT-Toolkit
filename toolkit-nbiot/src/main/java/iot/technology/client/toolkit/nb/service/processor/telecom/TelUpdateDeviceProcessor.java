package iot.technology.client.toolkit.nb.service.processor.telecom;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.nb.service.processor.TelProcessContext;
import iot.technology.client.toolkit.nb.service.telecom.TelecomDeviceService;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelUpdateDeviceResponse;

import java.util.List;

/**
 * @author mushuwei
 */
public class TelUpdateDeviceProcessor implements TkProcessor {

	private final TelecomDeviceService telecomDeviceService = new TelecomDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("update");
	}

	@Override
	public void handle(ProcessContext context) {
		List<String> arguArgs = List.of(context.getData().split(" "));
		if (arguArgs.size() != 3) {
			System.out.format(ColorUtils.blackBold("argument:%s is illegal"), context.getData());
			System.out.format(" " + "%n");
			return;
		}
		TelProcessContext telProcessContext = (TelProcessContext) context;
		TelecomConfigDomain telecomConfigDomain = telProcessContext.getTelecomConfigDomain();
		String imei = arguArgs.get(1);
		String name = arguArgs.get(2);
		TelUpdateDeviceResponse updateDeviceResponse = telecomDeviceService.updateDevice(telecomConfigDomain, imei, name);
		if (updateDeviceResponse.isSuccess()) {
			System.out.format(ColorUtils.blackBold("imei:%s update success"), imei + "%n");
			System.out.format(" " + "%n");
		}
	}
}
