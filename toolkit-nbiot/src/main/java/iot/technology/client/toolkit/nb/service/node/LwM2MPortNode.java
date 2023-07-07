package iot.technology.client.toolkit.nb.service.node;

import iot.technology.client.toolkit.common.rule.NodeContext;
import iot.technology.client.toolkit.common.rule.TkNode;

/**
 * @author mushuwei
 */
public class LwM2MPortNode implements TkNode {

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
        return null;
    }

    @Override
    public String getValue(NodeContext context) {
        return null;
    }
}
