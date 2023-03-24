package iot.technology.client.toolkit.nb.service.processor.mobile;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.DateUtils;
import iot.technology.client.toolkit.nb.service.mobile.MobileDeviceDataService;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.data.MobCachedCommandResponse;
import iot.technology.client.toolkit.nb.service.processor.MobProcessContext;

import java.util.List;

/**
 * <p>
 * 1、command imei : print today's command for the device
 * <p>
 * 2、command imei pageNo : print today's first page command for the device
 * <p>
 * 3、command imei start pageNo : print startTime pageNo command for the device
 * <p>
 *
 * @author mushuwei
 */
public class MobCommandDataDeviceProcessor extends TkAbstractProcessor implements TkProcessor {

	private final MobileDeviceDataService mobileDeviceDataService = new MobileDeviceDataService();

	@Override
	public boolean supports(ProcessContext context) {
		return context.getData().startsWith("command");
	}

	@Override
	public void handle(ProcessContext context) {
		MobProcessContext mobProcessContext = (MobProcessContext) context;
		MobileConfigDomain mobileConfigDomain = mobProcessContext.getMobileConfigDomain();

		List<String> arguArgs = List.of(context.getData().split(" "));
		if (arguArgs.size() < 2 || arguArgs.size() > 4) {
			System.out.format(ColorUtils.blackBold("argument:%s is illegal"), context.getData());
			System.out.format(" " + "%n");
			return;
		}
		String imei = arguArgs.get(1);
		String pageNo = "1";
		String startTime = DateUtils.getCurrentDayStartTimeForMob();
		// command imei
		if (arguArgs.size() == 2) {
		}
		if (arguArgs.size() == 3) {
			String pageNoStr = arguArgs.get(2);
			if (!validateLimit(pageNoStr)) {
				return;
			}
			pageNo = pageNoStr;
		}
		if (arguArgs.size() == 4) {
			String startTimeStr = arguArgs.get(2);
			if (!validateLimit(startTimeStr)) {
				System.out.format(ColorUtils.blackBold("time format is illegal"));
				System.out.format(" " + "%n");
				return;
			}
			startTime = startTimeStr;
			String pageNoStr = arguArgs.get(3);
			if (!validateLimit(pageNoStr)) {
				System.out.format(ColorUtils.blackBold("pageNo format is illegal"));
				System.out.format(" " + "%n");
				return;
			}
			pageNo = pageNoStr;
		}
		MobCachedCommandResponse
				mobCachedCommandResponse = mobileDeviceDataService.getCachedCommandList(mobileConfigDomain, imei, startTime, pageNo);
		if (mobCachedCommandResponse.isSuccess() && mobCachedCommandResponse.getData() != null) {
			
		}
	}
}
