package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/15 18:27
 */
public class HotSpecialCarBean {

    /**
     * code : 0
     * data : [{"car":"WEY P8","icon":"http://img.sayiyinxiang.com/api/brand/imgs/15246548999678464248.jpg","id":1},{"car":"凯美瑞新能源","icon":"http://img.sayiyinxiang.com/api/brand/imgs/15246549033865297408.jpg","id":3},{"car":"CR-V新能源","icon":"http://img.sayiyinxiang.com/api/brand/imgs/15246549036999268728.JPG","id":5}]
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
         * car : WEY P8
         * icon : http://img.sayiyinxiang.com/api/brand/imgs/15246548999678464248.jpg
         * id : 1
         */

        private String car;
        private String icon;
        private int id;
        private String price;
        private String uuid;
        private String batteryLife;
        private PostBean.DataBean.ShareMap shareMap;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public PostBean.DataBean.ShareMap getShareMap() {
            return shareMap;
        }

        public void setShareMap(PostBean.DataBean.ShareMap shareMap) {
            this.shareMap = shareMap;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBatteryLife() {
            return batteryLife;
        }

        public void setBatteryLife(String batteryLife) {
            this.batteryLife = batteryLife;
        }

        public String getCar() {
            return car;
        }

        public void setCar(String car) {
            this.car = car;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
