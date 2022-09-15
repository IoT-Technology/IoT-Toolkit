package iot.technology.client.toolkit.common.constants;

/**
 * @author mushuwei
 */
public enum CoapCodeEnum {
	HELP("help", "iot.technology.client.toolkit.coap.service.node.HelpProcessor");

	private String code;

	private String clazzName;

	public String getCode() {
		return code;
	}

	public String getClazzName() {
		return clazzName;
	}

	CoapCodeEnum(String code, String clazzName) {
		this.code = code;
		this.clazzName = clazzName;
	}
}
