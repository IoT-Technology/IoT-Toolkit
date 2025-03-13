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

import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.common.utils.StringUtils;
import iot.technology.client.toolkit.nb.service.mobile.domain.BaseOneNetResponse;

import java.util.List;

/**
 * @author mushuwei
 */
public class OneNetDeviceHisDataResponse extends BaseOneNetResponse {

    private OneNetDeviceHisDataBody data;

    public void printToConsole(String imei, String startTime, String endTime) {
        int count = data.getCount();
        System.out.println(
                ColorUtils.colorBold(String.format("--- %s %s至%s 历史设备数据点 ---", imei, startTime, endTime), "green"));
        System.out.println(String.format("日志数量:    %s", count));
        List<OneNetDeviceHisDataStreamsBody> deviceHisDataStreamsBodies = data.getDatastreams();
        deviceHisDataStreamsBodies.forEach(hds -> {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("数据点:    %s", hds.getId())).append(StringUtils.lineSeparator());
            List<OneNetDeviceHisDataPointsBody> deviceHisDataPointsBodies = hds.getDatapoints();
            deviceHisDataPointsBodies.forEach(dpBody -> {
                sb.append(String.format("数据值: %s", dpBody.getValue())).append(StringUtils.lineSeparator());
                sb.append(String.format("数据上报时间:    %s", dpBody.getAt())).append(StringUtils.lineSeparator());
                sb.append(StringUtils.lineSeparator());
            });
            System.out.println(sb);
        });
    }

    public OneNetDeviceHisDataBody getData() {
        return data;
    }

    public void setData(OneNetDeviceHisDataBody data) {
        this.data = data;
    }
}
