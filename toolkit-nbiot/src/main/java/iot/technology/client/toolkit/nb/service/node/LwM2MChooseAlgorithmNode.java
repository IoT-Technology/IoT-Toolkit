package iot.technology.client.toolkit.nb.service.node;

import iot.technology.client.toolkit.common.constants.*;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class LwM2MChooseAlgorithmNode implements TkNode {

    ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    @Override
    public void prePrompt(NodeContext context) {
        System.out.format(ColorUtils.greenItalic(SecurityAlgorithm.PSK.getCode() + ":" + SecurityAlgorithm.PSK.getValue() + "*") + "%n");
        System.out.format(ColorUtils.greenItalic(SecurityAlgorithm.RPK.getCode() + ":" + SecurityAlgorithm.RPK.getValue()) + "%n");
        System.out.format(ColorUtils.greenItalic(SecurityAlgorithm.X509.getCode() + ":" +SecurityAlgorithm.X509.getValue()) + "%n");
    }

    @Override
    public boolean check(NodeContext context) {
        if (StringUtils.isBlank(context.getData())) {
            context.setCheck(true);
            context.setData(SecurityAlgorithm.PSK.getCode());
            return true;
        }
        if (context.getData().equals(SecurityAlgorithm.PSK.getCode())
                || context.getData().equals(SecurityAlgorithm.RPK.getCode())
                || context.getData().equals(SecurityAlgorithm.X509.getCode())) {
            context.setCheck(true);
            return true;
        }
        System.out.format(ColorUtils.redError("please type psk, rpk, x509"));
        context.setCheck(false);
        return false;
    }

    @Override
    public String nodePrompt() {
        return bundle.getString(NbSettingsCodeEnum.NB_LWM2M_SELECT_ALGORITHM.getCode() + GlobalConstants.promptSuffix) +
                GlobalConstants.promptSeparator;
    }

    @Override
    public String nextNode(NodeContext context) {
        if (!context.isCheck()) {
            return NbSettingsCodeEnum.NB_LWM2M_SELECT_ALGORITHM.getCode();
        }
        if (context.getData().equals(SecurityAlgorithm.PSK.getCode())) {
            return NbSettingsCodeEnum.NB_LWM2M_PSK_IDENTITY.getCode();
        }
        if (context.getData().equals(SecurityAlgorithm.X509.getCode())) {
            return NbSettingsCodeEnum.NB_LWM2M_CLIENT_CERT.getCode();
        }
        if (context.getData().equals(SecurityAlgorithm.RPK.getCode())) {
            return NbSettingsCodeEnum.NB_LWM2M_CLIENT_PUBLIC_KEY.getCode();
        }
        return NbSettingsCodeEnum.NB_LWM2M_PSK_IDENTITY.getCode();
    }

    @Override
    public String getValue(NodeContext context) {
        return context.getData();
    }
}
