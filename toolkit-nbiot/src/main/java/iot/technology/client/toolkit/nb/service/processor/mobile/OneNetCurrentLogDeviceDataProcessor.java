package iot.technology.client.toolkit.nb.service.processor.mobile;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.mobile.OneNetService;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.data.OneNetDeviceLatestDataResponse;
import iot.technology.client.toolkit.nb.service.processor.MobProcessContext;
import org.apache.commons.cli.*;

public class OneNetCurrentLogDeviceDataProcessor extends TkAbstractProcessor {

    private final OneNetService oneNetService = new OneNetService();

    @Override
    public boolean supports(ProcessContext context) {
        return (context.getData().startsWith("cl") || context.getData().startsWith("current-log"));
    }

    @Override
    public void handle(ProcessContext context) {
        MobProcessContext mobProcessContext = (MobProcessContext) context;
        MobileConfigDomain mobileConfigDomain = mobProcessContext.getMobileConfigDomain();

        Options options = new Options();
        Option imeiOption = new Option("imei", true, "the device imei");

        options.addOption(imeiOption);

        String imei = "";

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, convertCommandData(context.getData()));

            if (!cmd.hasOption(imeiOption)) {
                StringBuilder sb = new StringBuilder();
                sb.append(ColorUtils.redError("imei is required")).append(StringUtils.lineSeparator);
                sb.append(ColorUtils.blackBold("detail usage please enter: help cl"));
                System.out.println(sb);
                return;
            }
            imei = cmd.getOptionValue(imeiOption);

            OneNetDeviceLatestDataResponse response = oneNetService.getCurrentDataPoints(mobileConfigDomain, imei);
            if (response.isSuccess()) {
                response.printToConsole();
            }
        } catch (ParseException e) {
            StringBuilder sb = new StringBuilder();
            sb.append(ColorUtils.redError("command parse failed!")).append(StringUtils.lineSeparator);
            System.out.println(sb);
        }
    }
}
