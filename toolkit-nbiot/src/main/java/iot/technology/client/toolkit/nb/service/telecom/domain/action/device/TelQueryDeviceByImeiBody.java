package iot.technology.client.toolkit.nb.service.telecom.domain.action.device;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class TelQueryDeviceByImeiBody implements Serializable {

	private String deviceId;

	private String deviceName;

	private Integer productId;

	private String imei;

	private String imsi;

	private Integer deviceStatus;

	private Integer autoObserver;

	private Long createTime;

	private String createBy;

	private Long updateTime;

	private String updateBy;

	private Integer netStatus;

	private Long onlineAt;

	private Long offlineAt;

	private String firmwareVersion;


	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}

	public Integer getAutoObserver() {
		return autoObserver;
	}

	public void setAutoObserver(Integer autoObserver) {
		this.autoObserver = autoObserver;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getNetStatus() {
		return netStatus;
	}

	public void setNetStatus(Integer netStatus) {
		this.netStatus = netStatus;
	}

	public Long getOnlineAt() {
		return onlineAt;
	}

	public void setOnlineAt(Long onlineAt) {
		this.onlineAt = onlineAt;
	}

	public Long getOfflineAt() {
		return offlineAt;
	}

	public void setOfflineAt(Long offlineAt) {
		this.offlineAt = offlineAt;
	}

	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
}
