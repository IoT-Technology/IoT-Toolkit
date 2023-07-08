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
public class LwM2MPortNode implements TkNode {

    public static final String DEFAULT_COAP_PORT = "" + CoAP.DEFAULT_COAP_PORT;
    public static final String DEFAULT_COAPS_PORT = "" + CoAP.DEFAULT_COAP_SECURE_PORT;

    ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    @Override
    public void prePrompt(NodeContext context) {
        System.out.format(ColorUtils.greenItalic("Default: ") + "%n");
        System.out.format(ColorUtils.greenItalic("  - " + DEFAULT_COAP_PORT + " for coap") + "%n");
        System.out.format(ColorUtils.greenItalic("  - " + DEFAULT_COAPS_PORT + " for coaps") + "%n");
    }

    @Override
    public boolean check(NodeContext context) {
        if (StringUtils.isBlank(context.getData())) {
            context.setCheck(true);
            return true;
        }
        int port = 0;
        try {
            port = Integer.parseInt(context.getData());
        } catch (NumberFormatException e) {
            System.out.format(ColorUtils.redError(bundle.getString("param.error")));
            context.setCheck(false);
            return false;
        }
        if (port < 0 || port > 65535) {
            System.out.format(ColorUtils.redError(bundle.getString("mqtt.port.error")));
            context.setCheck(false);
            return false;
        }
        context.setCheck(true);
        return true;
    }

    @Override
    public String nodePrompt() {
        return bundle.getString(NbSettingsCodeEnum.NB_LWM2M_PORT.getCode() + GlobalConstants.promptSuffix) +
                GlobalConstants.promptSeparator;
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
        return context.getData();
    }
}
