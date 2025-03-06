package iot.technology.client.toolkit.nb.service.processor.mobile;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;
import com.github.freva.asciitable.OverflowBehaviour;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.mobile.OneNetService;
import iot.technology.client.toolkit.nb.service.mobile.domain.MobileConfigDomain;
import iot.technology.client.toolkit.nb.service.mobile.domain.action.device.*;
import iot.technology.client.toolkit.nb.service.mobile.domain.settings.OneNetDeviceStatusEnum;
import iot.technology.client.toolkit.nb.service.processor.MobProcessContext;
import org.apache.commons.cli.*;

import java.util.Arrays;
import java.util.List;

public class OneNetDeviceListProcessor extends TkAbstractProcessor {

    private final OneNetService oneNetService = new OneNetService();

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().startsWith("list") || context.getData().startsWith("ls");
    }

    @Override
    public void handle(ProcessContext context) {
        MobProcessContext mobProcessContext = (MobProcessContext) context;
        MobileConfigDomain mobileConfigDomain = mobProcessContext.getMobileConfigDomain();

        Options options = new Options();
        Option nameOption = new Option("name", true, "the device name");
        Option offsetOption = new Option("offset", true, "offset");
        Option limitOption = new Option("limit", true, "limit");

        options.addOption(nameOption)
                .addOption(offsetOption)
                .addOption(limitOption);
        String name = "";
        Integer offset = 0;
        Integer limit = 10;

        try {
            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, convertCommandData(context.getData()));

            // the device name
            if (cmd.hasOption(nameOption)) {
                name = cmd.getOptionValue(nameOption);
            }
            if (cmd.hasOption(offsetOption)) {
                String offsetStr = cmd.getOptionValue(offsetOption);
                if (!validateParam(offsetStr)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ColorUtils.redError("offset is not a number")).append(StringUtils.lineSeparator);
                    sb.append(ColorUtils.blackBold("detail usage please enter: help list"));
                    System.out.println(sb);
                    return;
                }
                offset = Integer.parseInt(offsetStr);
            }
            if (cmd.hasOption(limitOption)) {
                String limitStr = cmd.getOptionValue(limitOption);
                if (!validateParam(limitStr)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ColorUtils.redError("limit is not a number")).append(StringUtils.lineSeparator);
                    sb.append(ColorUtils.blackBold("detail usage please enter: help list"));
                    System.out.println(sb);
                    return;
                }
                limit = Integer.parseInt(limitStr);
                if (limit > 100) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ColorUtils.redError("limit is less than 100, default:10")).append(StringUtils.lineSeparator);
                    sb.append(ColorUtils.blackBold("detail usage please enter: help list"));
                    System.out.println(sb);
                    return;
                }
            }

        } catch (ParseException e) {
            StringBuilder sb = new StringBuilder();
            sb.append(ColorUtils.redError("command parse failed!")).append(StringUtils.lineSeparator);
            System.out.println(sb);
        }
        OneNetDeviceListRequest request = new OneNetDeviceListRequest();
        request.setProductId(mobileConfigDomain.getProductId());
        request.setDeviceName(name);
        request.setOffset(offset);
        request.setLimit(limit);
        OneNetDeviceListResponse response = oneNetService.list(mobileConfigDomain, request);
        if (response.isSuccess()) {
            OneNetDevicePageResponse dataList = response.getData();
            System.out.format(ColorUtils.blackBold("productId:%s query success, offset:%s, limit:%s"),
                    mobileConfigDomain.getProductId(), offset, limit);
            System.out.format(" " + "%n");
            if (!dataList.getList().isEmpty()) {
                List<OneNetDeviceDetailBody> devices =  dataList.getList();
                String asciiTable = AsciiTable.getTable(AsciiTable.NO_BORDERS, devices, Arrays.asList(
                        new Column().header("deviceId").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
                                OneNetDeviceDetailBody::getDid),
                        new Column().header("imei").maxWidth(20, OverflowBehaviour.ELLIPSIS_RIGHT)
                                .minWidth(20).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
                                .with(OneNetDeviceDetailBody::getImei),
                        new Column().header("imsi").maxWidth(20, OverflowBehaviour.ELLIPSIS_RIGHT)
                                .minWidth(20).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
                                .with(OneNetDeviceDetailBody::getImsi),
                        new Column().header("psk").maxWidth(20, OverflowBehaviour.ELLIPSIS_RIGHT)
                                .minWidth(20).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
                                .with(OneNetDeviceDetailBody::getPsk),
                        new Column().header("enable_status").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
                                s -> s.getEnableStatus() ? "启用" : "禁用"),
                        new Column().header("sec_key").maxWidth(20, OverflowBehaviour.ELLIPSIS_RIGHT)
                                .minWidth(20).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
                                .with(OneNetDeviceDetailBody::getSecKey),
                        new Column().header("createTime").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
                                OneNetDeviceDetailBody::getCreateTime),
                        new Column().header("deviceName").maxWidth(20, OverflowBehaviour.ELLIPSIS_RIGHT)
                                .minWidth(20).headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT)
                                .with(OneNetDeviceDetailBody::getName),
                        new Column().header("status").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
                                s -> OneNetDeviceStatusEnum.getMsgByCode(s.getStatus())),
                        new Column().header("createTime").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
                                OneNetDeviceDetailBody::getCreateTime),
                        new Column().header("activeTime").headerAlign(HorizontalAlign.CENTER).dataAlign(HorizontalAlign.LEFT).with(
                                OneNetDeviceDetailBody::getActivateTime)
                ));
                System.out.format(asciiTable);
                System.out.format(" " + "%n");
            }
        }
    }
}
