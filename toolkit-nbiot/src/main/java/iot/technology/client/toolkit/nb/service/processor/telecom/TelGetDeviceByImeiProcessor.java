package iot.technology.client.toolkit.nb.service.processor.telecom;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.nb.service.processor.TelProcessContext;
import iot.technology.client.toolkit.nb.service.telecom.TelecomDeviceService;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelQueryDeviceByImeiResponse;

/**
 * @author mushuwei
 */
public class TelGetDeviceByImeiProcessor implements TkProcessor {

	private final TelecomDeviceService telecomDeviceService = new TelecomDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().contains("get ");
	}

	@Override
	public void handle(ProcessContext context) {
		String imei = context.getData().substring(context.getData().indexOf(" ") + 1);
		TelProcessContext telProcessContext = (TelProcessContext) context;
		TelQueryDeviceByImeiResponse response =
				telecomDeviceService.querySingleDeviceByImei(telProcessContext.getTelecomConfigDomain(), imei);
		System.out.println(JsonUtils.object2Json(response));
	}
}
