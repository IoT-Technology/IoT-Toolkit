package iot.technology.client.toolkit.nb.service.mobile.domain;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class BaseMobileResponse implements Serializable {

	private String error;

	private Integer errno;

	private boolean success = false;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getErrno() {
		return errno;
	}

	public void setErrno(Integer errno) {
		this.errno = errno;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
