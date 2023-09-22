package iot.technology.client.toolkit.mqtt.service.processor.shellmode;

import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.ProcessContext;
import iot.technology.client.toolkit.common.rule.TkProcessor;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.List;
import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class HelpProcessor implements TkProcessor {

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
                                + String.format("%s ", ColorUtils.colorBold("ls", "green")))
                        .append(StringUtils.lineSeparator());
                sb.append(bundle.getString("mqtt.subCmd.ls.help")).append(StringUtils.lineSeparator());
                System.out.print(sb);
                break;
            case "pub":
            case "publish":
                sb.append(ColorUtils.colorBold("Usage: ", "black")
                        + String.format("> %s %s <topic> %s <qos>  %s <messageFromCommandline> ",
                        ColorUtils.colorBold("pub", "green"),
                        ColorUtils.colorBold("-t", "green"),
                        ColorUtils.colorBold("-q", "green"),
                        ColorUtils.colorBold("-m", "green")));
                sb.append(StringUtils.lineSeparator());
                sb.append(bundle.getString("mqtt.subCmd.pub.help")).append(StringUtils.lineSeparator());
                sb.append(StringUtils.lineSeparator());
                sb.append("Options:").append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                        ColorUtils.colorBold("-m, --message ", "green"),
                        "The message to publish"))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                        ColorUtils.colorBold("-t --topic    ", "green"),
                        "The topics to publish to"))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                        ColorUtils.colorBold("-q --qos      ", "green"),
                        "Quality of service for the corresponding topic (default for all: 0)"))
                        .append(StringUtils.lineSeparator());
                System.out.print(sb);
                break;
            case "sub":
            case "subscribe":
                sb.append(ColorUtils.colorBold("Usage: ", "black")
                        + String.format("> %s %s <topic> %s <qos>",
                        ColorUtils.colorBold("sub", "green"),
                        ColorUtils.colorBold("-t", "green"),
                        ColorUtils.colorBold("-q", "green")));
                sb.append(StringUtils.lineSeparator());
                sb.append(bundle.getString("mqtt.subCmd.sub.help")).append(StringUtils.lineSeparator());
                sb.append(StringUtils.lineSeparator());
                sb.append("Options:").append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-t --topic ", "green"),
                                "The topics to publish to"))
                        .append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-q --qos   ", "green"),
                                "Quality of service for the corresponding topic (default for all: 0)"))
                        .append(StringUtils.lineSeparator());
                System.out.print(sb);
                break;
            case "unsub":
            case "unsubscribe":
                sb.append(ColorUtils.colorBold("Usage: ", "black")
                        + String.format("> %s %s <topic>",
                        ColorUtils.colorBold("unsub", "green"),
                        ColorUtils.colorBold("-t", "green")));
                sb.append(StringUtils.lineSeparator());
                sb.append(bundle.getString("mqtt.subCmd.unsub.help")).append(StringUtils.lineSeparator());
                sb.append(StringUtils.lineSeparator());
                sb.append("Options:").append(StringUtils.lineSeparator());
                sb.append(String.format("%s %s",
                                ColorUtils.colorBold("-t --topic", "green"),
                                "The topics to publish to"))
                        .append(StringUtils.lineSeparator());
                System.out.print(sb);
                break;
            case "dis":
            case "disconnect":
                sb.append(ColorUtils.colorBold("Usage:  ", "black")
                                + String.format("%s ", ColorUtils.colorBold("dis", "green")))
                        .append(StringUtils.lineSeparator());
                sb.append(bundle.getString("mqtt.subCmd.dis.help")).append(StringUtils.lineSeparator());
                System.out.print(sb);
                break;
            default:
                break;
        }

    }

    public void printAllHelpInfo() {
        StringBuilder sb = new StringBuilder();
        // list telecom nb-iot devices
        sb.append("").append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("Usage:", "black") + ColorUtils.colorBold(" > ", "green")
                + "{ pub | sub | unsub | dis | list | exit }").append(StringUtils.lineSeparator());
        sb.append("").append(StringUtils.lineSeparator());
        sb.append(bundle.getString("mqtt.shellmode.help")).append(StringUtils.lineSeparator());
        sb.append("").append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold(bundle.getString("general.commands"), "black"))
                .append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("  help                ", "green")).append(bundle.getString("general.subCommand.help")).append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("  pub, publish        ", "green")).append(bundle.getString("mqtt.subCmd.pub.help")).append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("  sub, subscribe      ", "green")).append(bundle.getString("mqtt.subCmd.sub.help")).append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("  unsub, unsubscribe  ", "green")).append(bundle.getString("mqtt.subCmd.unsub.help")).append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("  dis, disconnect     ", "green")).append(bundle.getString("mqtt.subCmd.dis.help")).append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("  ls, list            ", "green")).append(bundle.getString("mqtt.subCmd.ls.help")).append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold("  exit                ", "green")).append(bundle.getString("general.subCommand.exit")).append(StringUtils.lineSeparator());
        System.out.println(sb);
    }
}
