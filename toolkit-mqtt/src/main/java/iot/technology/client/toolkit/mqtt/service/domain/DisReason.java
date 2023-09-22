package iot.technology.client.toolkit.mqtt.service.domain;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class DisReason implements Serializable {

    private Integer actionType;

    private Throwable cause;

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}
