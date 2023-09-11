package iot.technology.client.toolkit.mqtt.service.node;

import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.constants.SystemConfigConst;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.FileUtils;
import iot.technology.client.toolkit.common.utils.JsonUtils;
import iot.technology.client.toolkit.mqtt.config.MqttSettings;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Stream;

/**
 * @author mushuwei
 */
public class MqttAppConfigNode implements TkNode {

    ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    @Override
    public void prePrompt(NodeContext context) {
        boolean isNew = FileUtils.notExistOrContents(SystemConfigConst.MQTT_SETTINGS_FILE_NAME);
        if (!isNew) {
            List<String> configList = FileUtils.getDataFromFile(SystemConfigConst.MQTT_SETTINGS_FILE_NAME);
            Stream.iterate(0, i -> i + 1).limit(configList.size()).forEach(i -> {
                MqttSettings settings = JsonUtils.jsonToObject(configList.get(i), MqttSettings.class);
                System.out.format(ColorUtils.greenItalic(i + "   :" + Objects.requireNonNull(settings).getName()) + "%n");
            });
        }
        System.out.format(ColorUtils.greenItalic("new" + " > " + bundle.getString("mqtt.new.config.desc")) + "%n");
    }

    @Override
    public boolean check(NodeContext context) {
        return false;
    }

    @Override
    public String nodePrompt() {
        return null;
    }

    @Override
    public String nextNode(NodeContext context) {
        return null;
    }

    @Override
    public String getValue(NodeContext context) {
        return null;
    }
}
