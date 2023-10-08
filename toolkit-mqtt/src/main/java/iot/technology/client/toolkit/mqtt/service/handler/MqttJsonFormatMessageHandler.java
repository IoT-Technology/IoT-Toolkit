package iot.technology.client.toolkit.mqtt.service.handler;

import com.google.gson.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.mqtt.MqttQoS;
import iot.technology.client.toolkit.common.constants.EmojiEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class MqttJsonFormatMessageHandler implements MqttHandler {

    ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();

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

        JsonElement payloadJson;
        String payloadString = new String(ByteBufUtil.getBytes(payload), StandardCharsets.UTF_8);
        try {
            payloadJson = JsonParser.parseString(payloadString);
        } catch (final JsonSyntaxException e) {
            payloadJson = new JsonPrimitive(payloadString);
        }
        sb.append(ColorUtils.colorBold(gson.toJson(payloadJson), "yellow")).append(StringUtils.lineSeparator());
        sb.append(formatter.format(nowDateTime)).append(StringUtils.lineSeparator());
        sb.append(StringUtils.lineSeparator());
        System.out.print(sb);
    }
}
