package iot.technology.client.toolkit.nb.service.node;

import iot.technology.client.toolkit.common.constants.CertUsageEnum;
import iot.technology.client.toolkit.common.constants.GlobalConstants;
import iot.technology.client.toolkit.common.constants.NbSettingsCodeEnum;
import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class LwM2MCertUsageNode implements TkNode {

    ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    @Override
    public void prePrompt(NodeContext context) {
        System.out.format(ColorUtils.greenItalic("Certificate Usage (as integer) defining how to use server certificate") + "%n");
        System.out.format(ColorUtils.greenItalic(" - 0 : CA constraint") + "%n");
        System.out.format(ColorUtils.greenItalic(" - 1 : service certificate constraint") + "%n");
        System.out.format(ColorUtils.greenItalic(" - 2 : trust anchor assertion") + "%n");
        System.out.format(ColorUtils.greenItalic(" - 3 : domain issued certificate (Default value)") + "%n");
    }

    @Override
    public boolean check(NodeContext context) {
        if (StringUtils.isBlank(context.getData())) {
            context.setCheck(true);
            context.setData(CertUsageEnum.DOMAIN_ISSUED_CERT.getCode());
            return true;
        }
        context.setCheck(true);
        return true;
    }

    @Override
    public String nodePrompt() {
        return bundle.getString(NbSettingsCodeEnum.NB_LWM2M_CERT_USAGE.getCode() + GlobalConstants.promptSuffix) +
                GlobalConstants.promptSeparator;
    }

    @Override
    public String nextNode(NodeContext context) {
        if (!context.isCheck()) {
            return NbSettingsCodeEnum.NB_LWM2M_CERT_USAGE.getCode();
        }
        return NbSettingsCodeEnum.NB_LWM2M_CLIENT_CERT.getCode();
    }

    @Override
    public String getValue(NodeContext context) {
        return context.getData();
    }
}
