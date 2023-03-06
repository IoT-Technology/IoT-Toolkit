package iot.technology.client.toolkit.nb.service.telecom.domain.action.data;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class TelQueryDeviceCommandBody implements Serializable {

	private String commandId;

	private String command;

	private String createBy;

	private String commandStatus;

	private Long createTime;

	private Long finishTime;

	private String resultCode;

	private String resultMessage;

	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCommandStatus() {
		return commandStatus;
	}

	public void setCommandStatus(String commandStatus) {
		this.commandStatus = commandStatus;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
}
