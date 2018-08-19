package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/8/19 12:04
 */
public class ReFundExplain {

    /**
     * code : 0
     * data : {"top":["微信充值订单12个月内，支付宝充值订单6个月内，将原路退回，超出时间，请绑定您的支付宝账号进行退款"],"bottom":["1.退款金额仅包含实际充值金额，有系统活动赠送的金额不可退","2.退款过程中，账户将被冻结，无法充值，使用支付宝免密码支付充电不受影响","3.退款后，充电币账户不清零，且可继续正常使用","4.退款最晚将于7个工作日内完成，请耐心等待"]}
     * msg : 操作成功
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        private List<String> top;
        private List<String> bottom;

        public List<String> getTop() {
            return top;
        }

        public void setTop(List<String> top) {
            this.top = top;
        }

        public List<String> getBottom() {
            return bottom;
        }

        public void setBottom(List<String> bottom) {
            this.bottom = bottom;
        }
    }
}
