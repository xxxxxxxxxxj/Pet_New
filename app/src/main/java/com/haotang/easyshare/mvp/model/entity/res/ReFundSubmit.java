package com.haotang.easyshare.mvp.model.entity.res;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/8/19 12:30
 */
public class ReFundSubmit {

    /**
     * code : 0
     * data : {"desc":"您已与2018年8月13日成功提交退款申请,款项将于7个工作日内,退回至您的付款账户中，请耐心等待"}
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
        /**
         * desc : 您已与2018年8月13日成功提交退款申请,款项将于7个工作日内,退回至您的付款账户中，请耐心等待
         */

        private String desc;

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
