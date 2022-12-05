package iot.technology.client.toolkit.nb.service.telecom.domain.settings;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class TelProjectSettings implements Serializable {

	private String projectName;

	private String appKey;

	private String appSecret;

	private Integer serialNumber;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public Integer getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Integer serialNumber) {
		this.serialNumber = serialNumber;
	}
}
