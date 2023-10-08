package iot.technology.client.toolkit.mqtt.service.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.mqtt.MqttQoS;
import iot.technology.client.toolkit.common.constants.EmojiEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import org.eclipse.leshan.core.util.Hex;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class MqttHexMessageHandler implements MqttHandler {

    ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    @Override
    public void onMessage(String topic, MqttQoS qos, ByteBuf payload) {
        LocalDateTime nowDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.lineSeparator());
        System.out.format("%n" + "------"+ bundle.getString("mqtt.received.desc") + String.format(EmojiEnum.subscribeEmoji) + "------" + "%n");
        String qosConsoleString = "";
        if (qos.equals(MqttQoS.AT_MOST_ONCE)) {
            qosConsoleString = bundle.getString("mqtt.qos0.prompt");
        }
        if (qos.equals(MqttQoS.AT_LEAST_ONCE)) {
            qosConsoleString = bundle.getString("mqtt.qos1.prompt");
        }
        if (qos.equals(MqttQoS.EXACTLY_ONCE)) {
            qosConsoleString = bundle.getString("mqtt.qos2.prompt");
        }
        sb.append(String.format("Topic:%s   QoS:%s", topic, qosConsoleString)).append(StringUtils.lineSeparator());
        sb.append(ColorUtils.colorBold(Hex.encodeHexString(ByteBufUtil.getBytes(payload)), "yellow"))
                .append(StringUtils.lineSeparator());
        sb.append(formatter.format(nowDateTime)).append(StringUtils.lineSeparator());
        sb.append(StringUtils.lineSeparator());
        System.out.print(sb);

    }
}
