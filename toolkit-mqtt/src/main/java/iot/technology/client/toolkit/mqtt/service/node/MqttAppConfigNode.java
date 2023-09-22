package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.*;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.FileUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.mqtt.config.MqttSettings;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author mushuwei
 */
public class MqttAppConfigNode implements TkNode {

    ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    @Override
    public void prePrompt(NodeContext context) {
        boolean isNew = FileUtils.notExistOrContents(SystemConfigConst.MQTT_SETTINGS_FILE_NAME);
        StringBuilder sb = new StringBuilder();
        if (!isNew) {
            sb.append(ColorUtils.colorBold(bundle.getString("general.used"), "black") + ": " + String.format(EmojiEnum.usedEmoji) + "  "
                    + ColorUtils.colorBold(bundle.getString("general.unused"), "black") + ": " + String.format(EmojiEnum.unusedEmoji))
                    .append(StringUtils.lineSeparator);
            sb.append(StringUtils.lineSeparator());
            List<String> configList = FileUtils.getDataFromFile(SystemConfigConst.MQTT_SETTINGS_FILE_NAME);
            context.setPromptData(configList);
            Stream.iterate(0, i -> i + 1).limit(configList.size()).forEach(i -> {
                MqttSettings settings = JsonUtils.jsonToObject(configList.get(i), MqttSettings.class);
                String emoji = settings.getUsage().equals(0) ? EmojiEnum.unusedEmoji : EmojiEnum.usedEmoji;
                sb.append(String.format(emoji) + " "+ ColorUtils.greenItalic(i + ": " + Objects.requireNonNull(settings).getName()))
                        .append(StringUtils.lineSeparator());
            });
        }
        sb.append(ColorUtils.greenItalic("new" + " > " + bundle.getString("mqtt.new.config.desc")))
                .append(StringUtils.lineSeparator());
        System.out.print(sb);
    }

    @Override
    public boolean check(NodeContext context) {
        List<String> configList = FileUtils.getDataFromFile(SystemConfigConst.MQTT_SETTINGS_FILE_NAME);
        List<String> indexList = Stream.iterate(0, i -> i + 1)
                .limit(configList.size())
                .map(String::valueOf)
                .collect(Collectors.toList());
        boolean matchIndex = indexList.stream().anyMatch(index -> index.equals(context.getData()));
        if (matchIndex || context.getData().equals("new")) {
            context.setCheck(true);
            return true;
        }
        System.out.format(ColorUtils.redError(bundle.getString("mqtt.select.config.error")) + "%n");
        context.setCheck(false);
        return false;
    }

    @Override
    public String nodePrompt() {
        return bundle.getString(MqttSettingsCodeEnum.MQTT_APP_CONFIG.getCode() + GlobalConstants.promptSuffix) +
                GlobalConstants.promptSeparator;
    }

    @Override
    public String nextNode(NodeContext context) {
        if (!context.isCheck()) {
            return MqttSettingsCodeEnum.MQTT_APP_CONFIG.getCode();
        }
        if (context.getData().equals("new")) {
            return MqttSettingsCodeEnum.SETTINGS_NAME.getCode();
        }
        return MqttSettingsCodeEnum.MQTT_APP_CONFIG.getCode();
    }

    @Override
    public String getValue(NodeContext context) {
        if (!context.isCheck()) {
            return context.getData();
        }
        if (context.getData().equals("new")) {
            return context.getData();
        }
        return context.getPromptData().get(Integer.parseInt(context.getData()));
    }
}
