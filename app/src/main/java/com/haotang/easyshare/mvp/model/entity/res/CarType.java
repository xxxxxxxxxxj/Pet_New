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
        private String car;
        private String icon;
        private int id;
        private String desc;

        @Override
        public String toString() {
            return "DataBean{" +
                    "car='" + car + '\'' +
                    ", icon='" + icon + '\'' +
                    ", id=" + id +
                    ", desc='" + desc + '\'' +
                    '}';
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

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(car);
            dest.writeString(icon);
            dest.writeInt(id);
            dest.writeString(desc);
        }

        public DataBean(Parcel in) {
            //顺序要和writeToParcel写的顺序一样
            car = in.readString();
            icon = in.readString();
            id = in.readInt();
            desc = in.readString();
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
