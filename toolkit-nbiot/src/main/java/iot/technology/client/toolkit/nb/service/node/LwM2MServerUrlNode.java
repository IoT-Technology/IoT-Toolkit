package iot.technology.client.toolkit.nb.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.NbSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import org.eclipse.californium.core.coap.CoAP;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class LwM2MServerUrlNode implements TkNode {

    public static final String DEFAULT_COAP_URL = "localhost";

    ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    @Override
    public void prePrompt(NodeContext context) {
        System.out.format(ColorUtils.greenItalic("Default: " + DEFAULT_COAP_URL) + "%n");
    }

    @Override
    public boolean check(NodeContext context) {
        context.setCheck(true);
        return true;
    }

    @Override
    public String nodePrompt() {
        return bundle.getString(NbSettingsCodeEnum.NB_LWM2M_URL.getCode() + GlobalConstants.promptSuffix) +
                GlobalConstants.promptSeparator;
    }

    @Override
    public String nextNode(NodeContext context) {
        if (!context.isCheck()) {
            return NbSettingsCodeEnum.NB_LWM2M_URL.getCode();
        }
        return NbSettingsCodeEnum.NB_LWM2M_PORT.getCode();
    }

    @Override
    public String getValue(NodeContext context) {
        return context.getData();
    }
}
