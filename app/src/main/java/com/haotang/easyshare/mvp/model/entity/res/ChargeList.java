package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/8/19 13:01
 */
public class ChargeList {

    /**
     * code : 0
     * data : {"dataset":[{"totalPrice":10,"totalTime":0,"stationName":"青年路22号院(东门)","startTime":"1000-10-10 00:00:00","endTime":"1000-10-10 00:00:00","id":1,"state":4,"totalPower":0}]}
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
             * totalPrice : 10
             * totalTime : 0
             * stationName : 青年路22号院(东门)
             * startTime : 1000-10-10 00:00:00
             * endTime : 1000-10-10 00:00:00
             * id : 1
             * state : 4
             * totalPower : 0
             */

            private double totalPrice;
            private double payPrice;
            private double totalTime;
            private String stationName;
            private String startTime;
            private String endTime;
            private int id;
            private int state;
            private double totalPower;

            public double getPayPrice() {
                return payPrice;
            }

            public void setPayPrice(double payPrice) {
                this.payPrice = payPrice;
            }

            public double getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(double totalPrice) {
                this.totalPrice = totalPrice;
            }

            public double getTotalTime() {
                return totalTime;
            }

            public void setTotalTime(double totalTime) {
                this.totalTime = totalTime;
            }

            public String getStationName() {
                return stationName;
            }

            public void setStationName(String stationName) {
                this.stationName = stationName;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
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

            public double getTotalPower() {
                return totalPower;
            }

            public void setTotalPower(double totalPower) {
                this.totalPower = totalPower;
            }
        }
    }
}
