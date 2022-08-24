package iot.technology.client.toolkit.common.rule;

/**
 * @author mushuwei
 */
public interface TkNode {

	void check(String data);

	String nodePrompt();

	String nextNode(String data);

	String getValue(String data);
}
