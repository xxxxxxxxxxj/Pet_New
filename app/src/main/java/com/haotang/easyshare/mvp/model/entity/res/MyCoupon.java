package com.haotang.easyshare.mvp.model.entity.res;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/8/29 11:50
 */
public class MyCoupon {

    /**
     * code : 0
     * data : {"dataset":[{"amount":5,"amountTxt":"5元","description":"登录的时候就赠送哦","reduceType":1,"startTime":"2018-08-23","id":11,"state":0,"endTime":"2018-08-25","title":"新手有礼"}]}
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

        public static class DatasetBean implements Serializable{
            /**
             * amount : 5
             * amountTxt : 5元
             * description : 登录的时候就赠送哦
             * reduceType : 1
             * startTime : 2018-08-23
             * id : 11
             * state : 0
             * endTime : 2018-08-25
             * title : 新手有礼
             */

            private double amount;
            private String amountTxt;
            private String description;
            private int reduceType;
            private String startTime;
            private int id;
            private int state;
            private int isAvali;
            private String endTime;
            private String title;
            private boolean isSelect;
            private double maxDiscount;

            public double getMaxDiscount() {
                return maxDiscount;
            }

            public void setMaxDiscount(double maxDiscount) {
                this.maxDiscount = maxDiscount;
            }

            public boolean isSelect() {
                return isSelect;
            }

            public void setSelect(boolean select) {
                isSelect = select;
            }

            public int getIsAvali() {
                return isAvali;
            }

            public void setIsAvali(int isAvali) {
                this.isAvali = isAvali;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getAmountTxt() {
                return amountTxt;
            }

            public void setAmountTxt(String amountTxt) {
                this.amountTxt = amountTxt;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getReduceType() {
                return reduceType;
            }

            public void setReduceType(int reduceType) {
                this.reduceType = reduceType;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
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
