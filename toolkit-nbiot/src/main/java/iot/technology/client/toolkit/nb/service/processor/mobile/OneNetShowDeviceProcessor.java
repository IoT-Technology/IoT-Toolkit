package iot.technology.client.toolkit.nb.service.processor.mobile;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.mobile.OneNetService;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.OneNetDeviceDetailRequest;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.OneNetDeviceDetailResponse;
import iot.technology.client.toolkit.nb.service.processor.MobProcessContext;

import java.util.List;

public class OneNetShowDeviceProcessor extends TkAbstractProcessor {

    private final OneNetService oneNetService = new OneNetService();

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().startsWith("show");
    }

    @Override
    public void handle(ProcessContext context) {
        List<String> arguArgs = List.of(context.getData().split(" "));
        if (arguArgs.size() != 2) {
            StringBuilder sb = new StringBuilder();
            sb.append(ColorUtils.redError("imei is required")).append(StringUtils.lineSeparator);
            sb.append(ColorUtils.blackBold("detail usage please enter: help show"));
            System.out.println(sb);
            return;
        }
        String imei = arguArgs.get(1);
        MobProcessContext mobProcessContext = (MobProcessContext) context;
        MobileConfigDomain mobileConfigDomain = mobProcessContext.getMobileConfigDomain();

        OneNetDeviceDetailRequest request = new OneNetDeviceDetailRequest();
        request.setImei(imei);
        request.setProductId(mobileConfigDomain.getProductId());
        OneNetDeviceDetailResponse deviceDetailResponse = oneNetService.get(mobileConfigDomain, request);
        if (deviceDetailResponse.isSuccess()) {
            deviceDetailResponse.printToConsole();
        }
    }
}
