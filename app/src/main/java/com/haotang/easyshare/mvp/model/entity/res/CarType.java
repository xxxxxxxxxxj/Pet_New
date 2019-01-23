package com.haotang.easyshare.mvp.model.entity.res;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/12/15 16:54
 */
public class CarType {
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

    public static class DataBean implements Parcelable {
        private String brand;
        private String car;
        private int id;
        private String category;
        private String groupPrice;
        private String icon;
        private String price;
        private List<AdvertisementBean.DataBean> banner;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "brand='" + brand + '\'' +
                    ", car='" + car + '\'' +
                    ", id=" + id +
                    ", category='" + category + '\'' +
                    ", groupPrice='" + groupPrice + '\'' +
                    ", banner=" + banner +
                    '}';
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public List<AdvertisementBean.DataBean> getBanner() {
            return banner;
        }

        public void setBanner(List<AdvertisementBean.DataBean> banner) {
            this.banner = banner;
        }

        public String getGroupPrice() {
            return groupPrice;
        }

        public void setGroupPrice(String groupPrice) {
            this.groupPrice = groupPrice;
        }

        public String getCar() {
            return car;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setCar(String car) {
            this.car = car;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(car);
            dest.writeInt(id);
            dest.writeString(category);
            dest.writeString(groupPrice);
        }

        public DataBean(Parcel in) {
            //顺序要和writeToParcel写的顺序一样
            car = in.readString();
            id = in.readInt();
            category = in.readString();
            groupPrice = in.readString();
        }

        public static final Parcelable.Creator<DataBean> CREATOR = new Parcelable.Creator<DataBean>() {
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }
}
