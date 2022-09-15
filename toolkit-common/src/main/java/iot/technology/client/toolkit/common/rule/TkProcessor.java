package iot.technology.client.toolkit.common.rule;

/**
 * @author mushuwei
 */
public interface TkProcessor {

	boolean supports(String data);

	String handle(String data);
}
