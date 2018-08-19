package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/8/19 13:44
 */
public class HistoryList {

    /**
     * code : 0
     * data : {"dataset":[{"amount":0.01,"createTime":"2018-08-13 10:15:34","title":"充值退款"},{"amount":0.01,"createTime":"2018-08-13 10:13:18","title":"充值退款"},{"amount":0.01,"createTime":"2018-08-13 10:10:02","title":"充值退款"},{"amount":-10,"createTime":"2018-08-02 11:21:07","title":"支付充电费用"}]}
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
        private List<DatasetBean> dataset;

        public List<DatasetBean> getDataset() {
            return dataset;
        }

        public void setDataset(List<DatasetBean> dataset) {
            this.dataset = dataset;
        }

        public static class DatasetBean {
            /**
             * amount : 0.01
             * createTime : 2018-08-13 10:15:34
             * title : 充值退款
             */

            private double amount;
            private String createTime;
            private String title;

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
