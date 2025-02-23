package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import java.io.Serializable;
import java.util.List;

public class OneNetDevicePageResponse implements Serializable {

    private Integer offset;

    private Integer limit;

    private List<OneNetDeviceDetailBody> list;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<OneNetDeviceDetailBody> getList() {
        return list;
    }

    public void setList(List<OneNetDeviceDetailBody> list) {
        this.list = list;
    }
}
