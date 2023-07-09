package iot.technology.client.toolkit.nb.service.node;

import iot.technology.client.toolkit.common.constants.StorageConstants;
import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;
import iot.technology.client.toolkit.common.utils.ColorUtils;

import java.util.ResourceBundle;

/**
 * @author mushuwei
 */
public class LwM2MDtlsNode implements TkNode {

    ResourceBundle bundle = ResourceBundle.getBundle(StorageConstants.LANG_MESSAGES);

    @Override
    public void prePrompt(NodeContext context) {
        System.out.format(ColorUtils.greenItalic("  - N *") + "%n");
        System.out.format(ColorUtils.greenItalic("  - Y  ") + "%n");
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
        return null;
    }

    @Override
    public String getValue(NodeContext context) {
        return null;
    }
}
