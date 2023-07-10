package iot.technology.client.toolkit.nb.service.node;

import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.NbSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.common.utils.security.SecurityUtil;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class LwM2MServerCertNode implements TkNode {

    ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    @Override
    public void prePrompt(NodeContext context) {
        System.out.format(ColorUtils.greenItalic(bundle.getString(NbSettingsCodeEnum.NB_LWM2M_SERVER_CERT.getCode() + GlobalConstants.prePrompt)) + "%n");
    }

    @Override
    public boolean check(NodeContext context) {
        if (StringUtils.isBlank(context.getData())) {
            System.out.format(ColorUtils.redError(bundle.getString("param.error")));
            context.setCheck(false);
            return false;
        }
        try {
            SecurityUtil.certificate.readFromFile(context.getData());
        } catch (IOException | GeneralSecurityException e) {
            System.out.format(ColorUtils.redError(bundle.getString("param.error")));
            context.setCheck(false);
            return false;
        }
        context.setCheck(true);
        return true;
    }

    @Override
    public String nodePrompt() {
        return bundle.getString(NbSettingsCodeEnum.NB_LWM2M_SERVER_CERT.getCode() + GlobalConstants.promptSuffix) +
                GlobalConstants.promptSeparator;
    }

    @Override
    public String nextNode(NodeContext context) {
        if (!context.isCheck()) {
            return NbSettingsCodeEnum.NB_LWM2M_SERVER_CERT.getCode();
        }
        return NbSettingsCodeEnum.NB_LWM2M_CLIENT_PRIVATE_KEY.getCode();
    }

    @Override
    public String getValue(NodeContext context) {
        return context.getData();
    }
}
