package com.haotang.easyshare.mvp.model.entity.res;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/9/4 13:10
 */
public class CarDetail {
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
        private List<String> color;
        private String icon;
        private List<AdvertisementBean.DataBean> banner;
        private int label;
        private ArrayList<AdvertisementBean.DataBean> paramImgs;
        private String car;
        private String price;
        private String groupPrice;
        private String shopPrice;
        private ArrayList<AdvertisementBean.DataBean> detailImgs;
        private List<String> category;
        private String batteryLife;
        private PostBean.DataBean.ShareMap shareMap;

        public String getShopPrice() {
            return shopPrice;
        }

        public void setShopPrice(String shopPrice) {
            this.shopPrice = shopPrice;
        }

        public List<String> getColor() {
            return color;
        }

        public void setColor(List<String> color) {
            this.color = color;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<AdvertisementBean.DataBean> getBanner() {
            return banner;
        }

        public void setBanner(List<AdvertisementBean.DataBean> banner) {
            this.banner = banner;
        }

        public int getLabel() {
            return label;
        }

        public void setLabel(int label) {
            this.label = label;
        }

        public ArrayList<AdvertisementBean.DataBean> getParamImgs() {
            return paramImgs;
        }

        public void setParamImgs(ArrayList<AdvertisementBean.DataBean> paramImgs) {
            this.paramImgs = paramImgs;
        }

        public String getCar() {
            return car;
        }

        public void setCar(String car) {
            this.car = car;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getGroupPrice() {
            return groupPrice;
        }

        public void setGroupPrice(String groupPrice) {
            this.groupPrice = groupPrice;
        }

        public ArrayList<AdvertisementBean.DataBean> getDetailImgs() {
            return detailImgs;
        }

        public void setDetailImgs(ArrayList<AdvertisementBean.DataBean> detailImgs) {
            this.detailImgs = detailImgs;
        }

        public List<String> getCategory() {
            return category;
        }

        public void setCategory(List<String> category) {
            this.category = category;
        }

        public String getBatteryLife() {
            return batteryLife;
        }

        public void setBatteryLife(String batteryLife) {
            this.batteryLife = batteryLife;
        }

        public PostBean.DataBean.ShareMap getShareMap() {
            return shareMap;
        }

        public void setShareMap(PostBean.DataBean.ShareMap shareMap) {
            this.shareMap = shareMap;
        }
    }
}
