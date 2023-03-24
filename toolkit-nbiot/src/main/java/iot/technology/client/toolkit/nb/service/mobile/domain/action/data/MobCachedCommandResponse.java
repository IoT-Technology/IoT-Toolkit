package iot.technology.client.toolkit.nb.service.mobile.domain.action.data;

import iot.technology.client.toolkit.nb.service.mobile.domain.BaseMobileResponse;

/**
 * @author mushuwei
 */
public class MobCachedCommandResponse extends BaseMobileResponse {

	private MobCachedCommandBody data;

	public MobCachedCommandBody getData() {
		return data;
	}

	public void setData(MobCachedCommandBody data) {
		this.data = data;
	}
}
