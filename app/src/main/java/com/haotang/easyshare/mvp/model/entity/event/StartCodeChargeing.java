package com.haotang.easyshare.mvp.model.entity.event;

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

    public StartCodeChargeing() {
    }

    public StartCodeChargeing(int orderId, int timeOut) {
        this.orderId = orderId;
        this.timeOut = timeOut;
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
