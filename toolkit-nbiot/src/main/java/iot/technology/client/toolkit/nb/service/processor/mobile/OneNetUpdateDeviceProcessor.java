package iot.technology.client.toolkit.nb.service.processor.mobile;

import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.mobile.OneNetService;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.OneNetUpdateDeviceRequest;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.OneNetUpdateDeviceResponse;
import iot.technology.client.toolkit.nb.service.processor.MobProcessContext;
import org.apache.commons.cli.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OneNetUpdateDeviceProcessor extends TkAbstractProcessor {

    private final OneNetService oneNetService = new OneNetService();

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().startsWith("update");
    }

    @Override
    public void handle(ProcessContext context) {
        MobProcessContext mobProcessContext = (MobProcessContext) context;
        MobileConfigDomain mobileConfigDomain = mobProcessContext.getMobileConfigDomain();

        Options options = new Options();
        Option imeiOption = new Option("imei", true, "the device imei");
        Option nameOption = new Option("name", true, "the device name");
        Option imsiOption = new Option("imsi", true, "the device imsi");
        Option pskValueOption = new Option("psk", true, "the psk value");
        Option authCodeOption = new Option("auth_code", true,  "the auth code");
        Option descOption = new Option("desc", true, "the device desc");
        Option latOption = new Option("lat", true, "the device lat");
        Option lonOption = new Option("lon", true, "the device lon");

        options.addOption(imeiOption)
                .addOption(nameOption)
                .addOption(imsiOption)
                .addOption(pskValueOption)
                .addOption(authCodeOption)
                .addOption(descOption)
                .addOption(latOption)
                .addOption(lonOption);

        String imei = null;
        String imsi = null;
        String psk = null;
        String authCode = null;
        String desc = null;
        String lat = null;
        String lon = null;

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, convertCommandData(context.getData()));

            if (!cmd.hasOption(imeiOption)) {
                StringBuilder sb = new StringBuilder();
                sb.append(ColorUtils.redError("imei and name is required")).append(StringUtils.lineSeparator);
                sb.append(ColorUtils.blackBold("detail usage please enter: help update"));
                System.out.println(sb);
                return;
            }

            // the device imei
            if (cmd.hasOption(imeiOption)) {
                imei = cmd.getOptionValue(imeiOption);
            }
            if (cmd.hasOption(imsiOption)) {
                imsi = cmd.getOptionValue(imsiOption);
            }
            // psk
            if (cmd.hasOption(pskValueOption)) {
                String pskValueStr = cmd.getOptionValue(pskValueOption);
                if (!checkPskValueFormat(pskValueStr)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ColorUtils.redError("psk format incorrect")).append(StringUtils.lineSeparator);
                    sb.append(ColorUtils.blackBold("detail usage please enter: help update"));
                    System.out.println(sb);
                    return;
                }
                psk = pskValueStr;
            }
            if (cmd.hasOption(authCodeOption)) {
                authCode = cmd.getOptionValue(authCodeOption);
            }
            if (cmd.hasOption(descOption)) {
                desc = cmd.getOptionValue(descOption);
            }
            if (cmd.hasOption(latOption)) {
                lat = cmd.getOptionValue(latOption);
            }
            if (cmd.hasOption(lonOption)) {
                lon = cmd.getOptionValue(lonOption);
            }

        } catch (ParseException e) {
            StringBuilder sb = new StringBuilder();
            sb.append(ColorUtils.redError("command parse failed!")).append(StringUtils.lineSeparator);
            System.out.println(sb);
        }
        OneNetUpdateDeviceRequest request = new OneNetUpdateDeviceRequest();
        request.setProductId(mobileConfigDomain.getProductId());
        request.setImei(imei);
        request.setImsi(imsi);
        request.setPsk(psk);
        request.setAuthCode(authCode);
        request.setDesc(desc);
        request.setLat(lat);
        request.setLon(lon);
        OneNetUpdateDeviceResponse response = oneNetService.update(mobileConfigDomain, request);
        if (response.isSuccess()) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format(ColorUtils.blackBold("imei:%s update success"), imei));
            sb.append(StringUtils.lineSeparator());
            System.out.println(sb);
        }
    }

    public boolean checkPskValueFormat(String input) {
        String pattern = "^[a-zA-Z0-9]{8,16}$";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);
        return matcher.matches();
    }
}
