/*
 * Copyright © 2019-2025 The Toolkit Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package iot.technology.client.toolkit.nb.service.mobile.domain.action.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class OneNetCachedCommandItem implements Serializable {

	@JsonProperty("cmd_uuid")
	private String cmdUuid;

	private String type;

	@JsonProperty("create_time")
	private String createTime;

	@JsonProperty("valid_time")
	private String validTime;

	@JsonProperty("expired_time")
	private String expiredTime;

	@JsonProperty("send_time")
	private String sendTime;

	@JsonProperty("send_status")
	private int sendStatus;

	@JsonProperty("confirm_time")
	private String confirmTime;

	@JsonProperty("confirm_status")
	private String confirmStatus;

	private int remain;


	public String getCmdUuid() {
		return cmdUuid;
	}

	public void setCmdUuid(String cmdUuid) {
		this.cmdUuid = cmdUuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public int getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(String confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getConfirmStatus() {
		return confirmStatus;
	}

	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}

	public int getRemain() {
		return remain;
	}

	public void setRemain(int remain) {
		this.remain = remain;
	}
}
