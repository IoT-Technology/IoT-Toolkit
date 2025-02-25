package iot.technology.client.toolkit.nb.service.mobile.domain.action.device;

import iot.technology.client.toolkit.common.utils.ColorUtils;
import iot.technology.client.toolkit.nb.service.mobile.domain.BaseOneNetResponse;
import iot.technology.client.toolkit.nb.service.mobile.domain.settings.OneNetDeviceStatusEnum;

public class OneNetDeviceDetailResponse extends BaseOneNetResponse {

    private OneNetDeviceDetailBody data;

    public void printToConsole() {
        System.out.format("deviceId:      " + ColorUtils.blackBold(data.getDid()) + "%n");
        System.out.format("productId:     " + ColorUtils.blackBold(data.getPid()) + "%n");
        System.out.format("name:          " + ColorUtils.blackBold(data.getName()) + "%n");
        System.out.format("description:   " + ColorUtils.blackBold(data.getDesc()) + "%n");
        System.out.format("status:        " + ColorUtils.blackBold(OneNetDeviceStatusEnum.getMsgByCode(data.getStatus())) + "%n");
        System.out.format("createTime:    " + ColorUtils.blackBold(data.getCreateTime()) + "%n");
        System.out.format("updateTime:    " + ColorUtils.blackBold(data.getActivateTime()) + "%n");
        System.out.format("lastTime:      " + ColorUtils.blackBold(data.getLastTime()) + "%n");
        System.out.format("secKey:        " + ColorUtils.blackBold(data.getSecKey()) + "%n");
        System.out.format("imei:          " + ColorUtils.blackBold(data.getImei()) + "%n");
        System.out.format("imsi:          " + ColorUtils.blackBold(data.getImsi()) + "%n");
        System.out.format("psk:           " + ColorUtils.blackBold(data.getPsk()) + "%n");
        System.out.format("obsv:          " + ColorUtils.blackBold(data.getObsv().toString()) + "%n");
        System.out.format("obsv_st:       " + ColorUtils.blackBold(data.getObsvSt().toString()) + "%n");
    }

    public OneNetDeviceDetailBody getData() {
        return data;
    }

    public void setData(OneNetDeviceDetailBody data) {
        this.data = data;
    }
}
