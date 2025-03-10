package iot.technology.client.toolkit.nb.service.processor.mobile;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.mobile.OneNetService;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.OneNetDelDeviceRequest;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.OneNetDelDeviceResponse;
import iot.technology.client.toolkit.nb.service.processor.MobProcessContext;

import java.util.List;

public class OneNetDelDeviceProcessor extends TkAbstractProcessor {

    private final OneNetService oneNetService = new OneNetService();


    @Override
    public boolean supports(ProcessContext context) {
        return (context.getData().startsWith("del") || context.getData().startsWith("delete"));
    }

    @Override
    public void handle(ProcessContext context) {
        List<String> arguArgs = List.of(context.getData().split(" "));
        if (arguArgs.size() != 2) {
            StringBuilder sb = new StringBuilder();
            sb.append(ColorUtils.redError("imei is required")).append(StringUtils.lineSeparator);
            sb.append(ColorUtils.blackBold("detail usage please enter: help del"));
            System.out.println(sb);
            return;
        }
        String imei = arguArgs.get(1);
        MobProcessContext mobProcessContext = (MobProcessContext) context;
        MobileConfigDomain mobileConfigDomain = mobProcessContext.getMobileConfigDomain();
        OneNetDelDeviceRequest request = new OneNetDelDeviceRequest();
        request.setProductId(mobileConfigDomain.getProductId());
        request.setImei(imei);
        OneNetDelDeviceResponse response = oneNetService.delete(mobileConfigDomain, request);
        if (response.isSuccess()) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format(ColorUtils.blackBold("imei:%s delete success"), imei));
            sb.append(StringUtils.lineSeparator());
            System.out.println(sb);
        }
    }
}
