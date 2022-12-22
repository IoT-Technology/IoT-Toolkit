package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import java.io.Serializable;

/**
 * @author mushuwei
 */
public class MobQueryDeviceByImeiBody implements Serializable {

	private String create_time;

	private String id;

	private boolean online;

	private boolean observe_status;

	private String title;

	private String desc;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public boolean isObserve_status() {
		return observe_status;
	}

	public void setObserve_status(boolean observe_status) {
		this.observe_status = observe_status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
