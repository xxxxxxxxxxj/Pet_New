package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/5/1 14:53
 */
public class HotPoint {

    /**
     * code : 0
     * data : [{"visitors":0,"carPrice":"29.28-31.28万元","comments":0,"carName":"WEY P8","carIcon":"http://img.sayiyinxiang.com/api/brand/imgs/15246548999678464248.jpg","icon":"http://img.sayiyinxiang.com/api/brand/imgs/15246556172146055316.jpg","title":"红旗L5首款私人定制版新鲜出炉，首位车主是谁大家都没想到！","uuid":"04bc6231b54c49ab961761c9d7751a06","praises":0,"carId":1},{"visitors":0,"carPrice":"29.28-31.28万元","comments":0,"carName":"WEY P8","carIcon":"http://img.sayiyinxiang.com/api/brand/imgs/15246548999678464248.jpg","icon":"http://img.sayiyinxiang.com/api/brand/imgs/15246556222873115841.jpg","title":"油价又上涨，只花1/5燃油车油钱的插电混动P8不了解下？","uuid":"3d95e429efca4369b6b327c1367f3e26","praises":0,"carId":1}]
     * msg : 操作成功
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * visitors : 0
         * carPrice : 29.28-31.28万元
         * comments : 0
         * carName : WEY P8
         * carIcon : http://img.sayiyinxiang.com/api/brand/imgs/15246548999678464248.jpg
         * icon : http://img.sayiyinxiang.com/api/brand/imgs/15246556172146055316.jpg
         * title : 红旗L5首款私人定制版新鲜出炉，首位车主是谁大家都没想到！
         * uuid : 04bc6231b54c49ab961761c9d7751a06
         * praises : 0
         * carId : 1
         */

        private int visitors;
        private String carPrice;
        private int comments;
        private String carName;
        private String carIcon;
        private String icon;
        private String title;
        private String uuid;
        private int praises;
        private int carId;
        private String userName;
        private String createTime;
        private String headImg;
        private String Renewal;

        public String getRenewal() {
            return Renewal;
        }

        public void setRenewal(String renewal) {
            Renewal = renewal;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public int getVisitors() {
            return visitors;
        }

        public void setVisitors(int visitors) {
            this.visitors = visitors;
        }

        public String getCarPrice() {
            return carPrice;
        }

        public void setCarPrice(String carPrice) {
            this.carPrice = carPrice;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public String getCarName() {
            return carName;
        }

        public void setCarName(String carName) {
            this.carName = carName;
        }

        public String getCarIcon() {
            return carIcon;
        }

        public void setCarIcon(String carIcon) {
            this.carIcon = carIcon;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getPraises() {
            return praises;
        }

        public void setPraises(int praises) {
            this.praises = praises;
        }

        public int getCarId() {
            return carId;
        }

        public void setCarId(int carId) {
            this.carId = carId;
        }
    }
}
