package iot.technology.client.toolkit.nb.service.processor.telecom;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import com.github.freva.asciitable.OverflowBehaviour;
import iot.technology.client.toolkit.common.constants.TelAutoObserverEnum;
import iot.technology.client.toolkit.common.constants.TelDeviceStatusEnum;
import iot.technology.client.toolkit.common.constants.TelNetStatusEnum;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.nb.service.processor.TelProcessContext;
import iot.technology.client.toolkit.nb.service.telecom.TelecomDeviceService;
import iot.technology.client.toolkit.nb.service.telecom.domain.TelecomConfigDomain;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelDeviceBody;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelQueryDeviceListBody;
import iot.technology.client.toolkit.nb.service.telecom.domain.action.device.TelQueryDeviceListResponse;

import java.util.Arrays;
import java.util.List;

/**
 * @author mushuwei
 */
public class TelListDeviceProcessor implements TkProcessor {

	private final TelecomDeviceService telecomDeviceService = new TelecomDeviceService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("list");
	}

	@Override
	public void handle(ProcessContext context) {
		List<String> arguArgs = List.of(context.getData().split(" "));
		if (arguArgs.size() > 3 || arguArgs.size() < 2) {
			System.out.format(ColorUtils.blackBold("argument:%s is illegal"), context.getData());
			System.out.format(" " + "%n");
			return;
		}
		String searchValue = null;
		Integer pageNo = Integer.valueOf(arguArgs.get(1));
		if (arguArgs.size() > 2) {
			searchValue = arguArgs.get(2);
		}
		TelProcessContext telProcessContext = (TelProcessContext) context;
		TelecomConfigDomain telecomConfigDomain = telProcessContext.getTelecomConfigDomain();
		TelQueryDeviceListResponse queryDeviceListResponse = telecomDeviceService.queryDeviceList(telecomConfigDomain, searchValue, pageNo);
		if (queryDeviceListResponse.isSuccess()) {
			TelQueryDeviceListBody deviceListBody = queryDeviceListResponse.getResult();
			System.out.format(ColorUtils.blackBold("productId:%s query success, totalNumber:%s, currentPageNo:%s"),
					telecomConfigDomain.getProductId(),
					deviceListBody.getTotal(), deviceListBody.getPageNum());
			System.out.format(" " + "%n");
			if (!deviceListBody.getList().isEmpty()) {
				String asciiTable = AsciiTable.getTable(AsciiTable.NO_BORDERS, deviceListBody.getList(), Arrays.asList(
						new Column().header("deviceId").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
								TelDeviceBody::getDeviceId),
						new Column().header("deviceName").maxWidth(20, OverflowBehaviour.ELLIPSIS_RIGHT)
								.minWidth(20).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(TelDeviceBody::getDeviceName),
						new Column().header("imei").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
								TelDeviceBody::getImei),
						new Column().header("imsi").minWidth(20).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
								TelDeviceBody::getImsi),
						new Column().header("deviceStatus").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(s -> TelDeviceStatusEnum.type(s.getDeviceStatus())),
						new Column().header("autoObserver").minWidth(10).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(
										s -> TelAutoObserverEnum.type(s.getAutoObserver())),
						new Column().header("netStatus").minWidth(10).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
								.with(
										s -> TelNetStatusEnum.type(s.getNetStatus()))
				));
				System.out.format(asciiTable);
				System.out.format(" " + "%n");
			}

		}
	}
}
