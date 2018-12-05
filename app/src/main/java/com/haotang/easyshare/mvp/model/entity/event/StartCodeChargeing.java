package com.haotang.easyshare.mvp.model.entity.event;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/9/27 18:37
 */
public class StartCodeChargeing {
    private int orderId;
    private int timeOut;
    private int unit;
    private List<String> dialogTips;

    public StartCodeChargeing() {
    }

    public StartCodeChargeing(int orderId, int timeOut, int unit, List<String> dialogTips) {
        this.orderId = orderId;
        this.timeOut = timeOut;
        this.unit = unit;
        this.dialogTips = dialogTips;
    }

    public List<String> getDialogTips() {
        return dialogTips;
    }

    public void setDialogTips(List<String> dialogTips) {
        this.dialogTips = dialogTips;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
