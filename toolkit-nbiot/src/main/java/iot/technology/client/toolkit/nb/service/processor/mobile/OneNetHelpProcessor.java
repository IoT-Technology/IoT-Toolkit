package iot.technology.client.toolkit.nb.service.processor.mobile;

import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkAbstractProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.List;
import java.util.ResourceBundle;

public class OneNetHelpProcessor extends TkAbstractProcessor {

    ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    @Override
    public boolean supports(ProcessContext context) {
        return context.getData().startsWith("help");
    }

    @Override
    public void handle(ProcessContext context) {
        List<String> arguArgs = List.of(context.getData().split(" "));
        if (arguArgs.size() > 2) {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format(ColorUtils.redError("argument:%s is illegal"), context.getData()))
                    .append(StringUtils.lineSeparator());
            sb.append(ColorUtils.blackBold("usage: help [subCommand]"));
            System.out.println(sb);
            return;
        }
        //user type help
        if (arguArgs.size() == 1) {
            printAllHelpInfo();
            return;
        }
        String subCommand = arguArgs.get(1);
        StringBuilder sb = new StringBuilder();
        switch (subCommand) {
            case "list":
            case "ls":
                sb.append(ColorUtils.colorBold("Usage:  ", "black")
                        + String.format("> %s [%s <name>] [%s <offset>] [%s <limit>]",
                        ColorUtils.colorBold("ls,list", "green"),
                        ColorUtils.colorBold("-name", "green"),
                        ColorUtils.colorBold("-offset", "green"),
                        ColorUtils.colorBold("-limit", "green")));
                sb.append(StringUtils.lineSeparator());
                sb.append(bundle.getString("nb.operation.list.desc")).append(StringUtils.lineSeparator());
                sb.append(StringUtils.lineSeparator());
                sb.append("Options:").append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-name              ", "green"),
                                bundle.getString("nb.cmd.name.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-offset            ", "green"),
                                bundle.getString("nb.cmd.offset.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-limit             ", "green"),
                                bundle.getString("nb.cmd.limit.desc")))
                        .append(StringUtils.lineSeparator());
                System.out.print(sb);
                break;
            case "add":
                sb.append(ColorUtils.colorBold("Usage:  ", "black")
                        + String.format("> %s %s <imei> %s <name> [%s] [%s <imsi>] [%s <psk>]",
                        ColorUtils.colorBold("add", "green"),
                        ColorUtils.colorBold("-imei", "green"),
                        ColorUtils.colorBold("-name", "green"),
                        ColorUtils.colorBold("-imsi", "green"),
                        ColorUtils.colorBold("-psk", "green"),
                        ColorUtils.colorBold("-auth_code", "green"),
                        ColorUtils.colorBold("-lat", "green"),
                        ColorUtils.colorBold("-lon", "green")));
                sb.append(StringUtils.lineSeparator());
                sb.append(bundle.getString("nb.operation.add.desc")).append(StringUtils.lineSeparator());
                sb.append(StringUtils.lineSeparator());
                sb.append("Options:").append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-imei       ", "green"),
                                bundle.getString("nb.cmd.imei.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-name       ", "green"),
                                bundle.getString("nb.cmd.name.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-imsi       ", "green"),
                                bundle.getString("nb.cmd.imsi.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-psk        ", "green"),
                                bundle.getString("nb.cmd.psk.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-auth_code       ", "green"),
                                bundle.getString("nb.cmd.auth_code.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-lat       ", "green"),
                                bundle.getString("nb.cmd.lat.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-lon       ", "green"),
                                bundle.getString("nb.cmd.lon.desc")))
                        .append(StringUtils.lineSeparator());
                System.out.print(sb);
                break;
            case "update":
                sb.append(ColorUtils.colorBold("Usage:  ", "black")
                        + String.format("> %s %s <imei> [%s <name>]",
                        ColorUtils.colorBold("update", "green"),
                        ColorUtils.colorBold("-imei", "green"),
                        ColorUtils.colorBold("-name", "green"),
                        ColorUtils.colorBold("-imsi", "green"),
                        ColorUtils.colorBold("-psk", "green"),
                        ColorUtils.colorBold("-auth_code", "green"),
                        ColorUtils.colorBold("-desc", "green"),
                        ColorUtils.colorBold("-lat", "green"),
                        ColorUtils.colorBold("-lon", "green")));
                sb.append(StringUtils.lineSeparator());
                sb.append(bundle.getString("nb.operation.update.desc")).append(StringUtils.lineSeparator());
                sb.append(StringUtils.lineSeparator());
                sb.append("Options:").append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-imei       ", "green"),
                                bundle.getString("nb.cmd.imei.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-name       ", "green"),
                                bundle.getString("nb.cmd.name.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-imsi       ", "green"),
                                bundle.getString("nb.cmd.imsi.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-psk        ", "green"),
                                bundle.getString("nb.cmd.psk.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-auth_code  ", "green"),
                                bundle.getString("nb.cmd.auth_code.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-lat        ", "green"),
                                bundle.getString("nb.cmd.lat.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-lon        ", "green"),
                                bundle.getString("nb.cmd.lon.desc")))
                        .append(StringUtils.lineSeparator());
                System.out.print(sb);
                break;
            case "del":
            case "delete":
                sb.append(ColorUtils.colorBold("Usage:  ", "black")
                        + String.format("> %s %s",
                        ColorUtils.colorBold("del,delete", "green"),
                        ColorUtils.colorBold("imei", "green")));
                sb.append(StringUtils.lineSeparator());
                sb.append(bundle.getString("nb.operation.del.desc")).append(StringUtils.lineSeparator());
                sb.append(StringUtils.lineSeparator());
                System.out.print(sb);
                break;
            case "show":
                sb.append(ColorUtils.colorBold("Usage:  ", "black")
                        + String.format("> %s %s",
                        ColorUtils.colorBold("show", "green"),
                        ColorUtils.colorBold("imei", "green")));
                sb.append(StringUtils.lineSeparator());
                sb.append(bundle.getString("nb.operation.get.desc")).append(StringUtils.lineSeparator());
                sb.append(StringUtils.lineSeparator());
                System.out.print(sb);
                break;
            case "current-log":
            case "cl":
                sb.append(ColorUtils.colorBold("Usage:  ", "black")
                        + String.format("> %s %s <imei>",
                        ColorUtils.colorBold("cl", "green"),
                        ColorUtils.colorBold("-imei", "green")));
                sb.append(StringUtils.lineSeparator());
                sb.append(bundle.getString("nb.operation.cl.desc")).append(StringUtils.lineSeparator());
                sb.append(StringUtils.lineSeparator());
                sb.append("Options:").append(StringUtils.lineSeparator());

                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-imei        ", "green"),
                                bundle.getString("nb.cmd.imei.desc")))
                        .append(StringUtils.lineSeparator());
                System.out.print(sb);
                break;
            case "history-log":
            case "hl":
                sb.append(ColorUtils.colorBold("Usage:  ", "black")
                        + String.format("> %s %s <imei> [%s <name>] [%s <startTime>] [%s <endTime>]",
                        ColorUtils.colorBold("hl", "green"),
                        ColorUtils.colorBold("-imei", "green"),
                        ColorUtils.colorBold("-limit", "green"),
                        ColorUtils.colorBold("-startTime", "green"),
                        ColorUtils.colorBold("-endTime", "green"),
                        ColorUtils.colorBold("-sort", "green")));
                sb.append(StringUtils.lineSeparator());
                sb.append(bundle.getString("nb.operation.hl.desc")).append(StringUtils.lineSeparator());
                sb.append(StringUtils.lineSeparator());
                sb.append("Options:").append(StringUtils.lineSeparator());

                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-imei        ", "green"),
                                bundle.getString("nb.cmd.imei.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-limit       ", "green"),
                                bundle.getString("nb.cmd.limit.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-startTime   ", "green"),
                                bundle.getString("nb.cmd.startTime.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-endTime     ", "green"),
                                bundle.getString("nb.cmd.endTime.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-sort        ", "green"),
                                bundle.getString("nb.cmd.sort.desc")))
                        .append(StringUtils.lineSeparator());
                System.out.print(sb);
                break;
            case "command":
            case "cmd":
                sb.append(ColorUtils.colorBold("Usage:  ", "black")
                        + String.format("> %s %s <imei> [%s <startTime>] [%s <endTime>] [%s <pageNo>] [%s <pageSize>]",
                        ColorUtils.colorBold("cmd,command", "green"),
                        ColorUtils.colorBold("-imei", "green"),
                        ColorUtils.colorBold("-startTime", "green"),
                        ColorUtils.colorBold("-endTime", "green"),
                        ColorUtils.colorBold("-pn", "green"),
                        ColorUtils.colorBold("-ps", "green")));
                sb.append(StringUtils.lineSeparator());
                sb.append(bundle.getString("nb.operation.command.desc")).append(StringUtils.lineSeparator());
                sb.append(StringUtils.lineSeparator());
                sb.append("Options:").append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-imei           ", "green"),
                                bundle.getString("nb.cmd.sv.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-startTime      ", "green"),
                                bundle.getString("nb.cmd.startTime.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-endTime        ", "green"),
                                bundle.getString("nb.cmd.endTime.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-pn --pageNo    ", "green"),
                                bundle.getString("nb.cmd.pn.desc")))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-ps --pageSize  ", "green"),
                                bundle.getString("nb.cmd.ps.desc")))
                        .append(StringUtils.lineSeparator());
                System.out.print(sb);
                break;
            default:
                break;

        }
    }

    public void printAllHelpInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("").append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("Usage:", "black") + ColorUtils.colorBold(" > ", "green")
                + "{ add | del | update | show | list | cl | hl | command | exit }").append(StringUtils.lineSeparator());
        sb.append("").append(StringUtils.lineSeparator());
        sb.append(bundle.getString("nb.shellmode.help")).append(StringUtils.lineSeparator());
        sb.append("").append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold(bundle.getString("general.commands"), "black"))
                .append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("  help             ", "green")).append(bundle.getString("general.subCommand.help")).append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("  add              ", "green")).append(bundle.getString("nb.operation.add.desc")).append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("  del, delete      ", "green")).append(bundle.getString("nb.operation.del.desc")).append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("  update           ", "green")).append(bundle.getString("nb.operation.update.desc")).append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("  show             ", "green")).append(bundle.getString("nb.operation.get.desc")).append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("  ls, list         ", "green")).append(bundle.getString("nb.operation.list.desc")).append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("  hl, history-log  ", "green")).append(bundle.getString("nb.operation.hl.desc")).append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("  cl, current-log  ", "green")).append(bundle.getString("nb.operation.cl.desc")).append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("  command          ", "green")).append(bundle.getString("nb.operation.command.desc")).append(StringUtils.lineSeparator());
        System.out.println(sb);
    }
}
