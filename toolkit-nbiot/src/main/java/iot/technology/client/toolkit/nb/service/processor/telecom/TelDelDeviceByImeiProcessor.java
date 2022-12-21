package iot.technology.client.toolkit.nb.service.processor.telecom;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.processor.TelProcessContext;
import iot.technology.client.toolkit.nb.service.telecom.TelecomDeviceService;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelDelDeviceByImeiResponse;

import java.util.List;

/**
 * @author mushuwei
 */
public class TelDelDeviceByImeiProcessor implements TkProcessor {

	private final TelecomDeviceService telecomDeviceService = new TelecomDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return (context.getData().startsWith("del") || context.getData().startsWith("delete"));
	}

	@Override
	public void handle(ProcessContext context) {
		String imeiListString = context.getData().substring(context.getData().indexOf(" ") + 1);
		TelProcessContext telProcessContext = (TelProcessContext) context;
		if (StringUtils.isNotBlank(imeiListString)) {
			List<String> imeiList = List.of(imeiListString.split(","));
			TelDelDeviceByImeiResponse response =
					telecomDeviceService.delDeviceByImei(telProcessContext.getTelecomConfigDomain(), imeiList);
			if (response.isSuccess()) {
				System.out.format(ColorUtils.blackBold("imeiList:%s delete success"), imeiListString);
				System.out.format(" " + "%n");
			}
		}
	}
}
