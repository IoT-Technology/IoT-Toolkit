package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import java.io.Serializable;
import java.util.Map;

/**
 * @author mushuwei
 */
public class MobQueryDeviceBody implements Serializable {

	private String protocol;

	private String create_time;

	private String act_time;

	private String last_login;

	private boolean online;

	private String id;

	private Map<String, String> auth_info;

	private String title;

	private String desc;

	private boolean obsv;

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, String> getAuth_info() {
		return auth_info;
	}

	public void setAuth_info(Map<String, String> auth_info) {
		this.auth_info = auth_info;
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

	public String getAct_time() {
		return act_time;
	}

	public void setAct_time(String act_time) {
		this.act_time = act_time;
	}

	public String getLast_login() {
		return last_login;
	}

	public void setLast_login(String last_login) {
		this.last_login = last_login;
	}

	public boolean isObsv() {
		return obsv;
	}

	public void setObsv(boolean obsv) {
		this.obsv = obsv;
	}
}
