package iot.technology.client.toolkit.nb.service.telecom.domain;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class BaseTelResponse implements Serializable {

	private String msg;

	private Integer code;
	
	private boolean success = false;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}