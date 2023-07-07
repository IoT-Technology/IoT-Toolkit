package iot.technology.client.toolkit.nb.service.node;

import iot.technology.client.toolkit.common.constants.NbSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class LwM2MPortNode implements TkNode {

    ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    @Override
    public void prePrompt(NodeContext context) {

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
        if (!context.isCheck()) {
            return NbSettingsCodeEnum.NB_LWM2M_PORT.getCode();
        }
        return NbSettingsCodeEnum.NB_LWM2M_BOOTSTRAP.getCode();
    }

    @Override
    public String getValue(NodeContext context) {
        return null;
    }
}
