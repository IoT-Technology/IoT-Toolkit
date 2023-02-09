/*
 * Copyright © 2019-2023 The Toolkit Authors
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

import java.io.Serializable;
import java.util.List;

/**
 * @author mushuwei
 */
public class MobDeviceHisDataBody implements Serializable {

	private String cursor;

	private int count;

	private List<MobDeviceHisDataStreamsBody> datastreams;

	public String getCursor() {
		return cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<MobDeviceHisDataStreamsBody> getDatastreams() {
		return datastreams;
	}

	public void setDatastreams(
			List<MobDeviceHisDataStreamsBody> datastreams) {
		this.datastreams = datastreams;
	}
}
