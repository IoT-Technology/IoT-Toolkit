package iot.technology.client.toolkit.mqtt.config;

import iot.technology.client.toolkit.mqtt.service.core.MqttClientService;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class MqttShellModeDomain implements Serializable {

    private String name;

    private MqttClientService client;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MqttClientService getClient() {
        return client;
    }

    public void setClient(MqttClientService client) {
        this.client = client;
    }
}
