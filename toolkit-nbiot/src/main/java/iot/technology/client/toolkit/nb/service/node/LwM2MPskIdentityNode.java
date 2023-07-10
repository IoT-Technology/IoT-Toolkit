package iot.technology.client.toolkit.nb.service.node;

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
public class LwM2MPskIdentityNode implements TkNode {

    ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    @Override
    public void prePrompt(NodeContext context) {

    }

    @Override
    public boolean check(NodeContext context) {
        if (StringUtils.isBlank(context.getData())) {
            System.out.format(ColorUtils.redError(bundle.getString("param.error")));
            context.setCheck(false);
            return false;
        }
        context.setCheck(true);
        return true;
    }

    @Override
    public String nodePrompt() {
        return bundle.getString(NbSettingsCodeEnum.NB_LWM2M_PSK_IDENTITY.getCode() + GlobalConstants.promptSuffix) +
                GlobalConstants.promptSeparator;
    }

    @Override
    public String nextNode(NodeContext context) {
        if (!context.isCheck()) {
            return NbSettingsCodeEnum.NB_LWM2M_PSK_IDENTITY.getCode();
        }
        return NbSettingsCodeEnum.NB_LWM2M_PSK_SHARE_KEY.getCode();
    }

    @Override
    public String getValue(NodeContext context) {
        return context.getData();
    }
}
