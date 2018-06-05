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
 * @date XJ on 2018/5/11 12:56
 */
public class HomeBean {

    /**
     * balance : 0
     * car : 宝马i8新能源
     * coins : 0
     * headImg :
     * kf_phone : 400888888
     * level : 0
     * stars : 0
     * stations : [{"electricityPrice":"0.08","headImg":"15258565092619189712.jpg","serviceFee":2,"source":2,"times":0,"title":"远大路1号院A区自用充电桩","uuid":"adb9c744e282402d892db8d8d6de9b48"}]
     * times : 0
     * userName :
     * uuid : c4ca4238a0b923820dcc509a6f75849b
     */

    private double balance;
    private String car;
    private int coins;
    private String headImg;
    private String kf_phone;
    private int level;
    private int stars;
    private int times;
    private String userName;
    private String uuid;
    private int isCollect;
    private int isEval;
    private List<StationsBean> stations;
    private String vipPrivilege;

    public String getVipPrivilege() {
        return vipPrivilege;
    }

    public void setVipPrivilege(String vipPrivilege) {
        this.vipPrivilege = vipPrivilege;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public int getIsEval() {
        return isEval;
    }

    public void setIsEval(int isEval) {
        this.isEval = isEval;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCar() {
        return car;
    }

    public void setCar(String car) {
        this.car = car;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getKf_phone() {
        return kf_phone;
    }

    public void setKf_phone(String kf_phone) {
        this.kf_phone = kf_phone;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<StationsBean> getStations() {
        return stations;
    }

    public void setStations(List<StationsBean> stations) {
        this.stations = stations;
    }

    public static class StationsBean implements Parcelable {
        /**
         * electricityPrice : 0.08
         * headImg : 15258565092619189712.jpg
         * serviceFee : 2
         * source : 2
         * times : 0
         * title : 远大路1号院A区自用充电桩
         * uuid : adb9c744e282402d892db8d8d6de9b48
         */

        private String electricityPrice;
        private String headImg;
        private double serviceFee;
        private int source;
        private int times;
        private String title;
        private String uuid;

        public String getElectricityPrice() {
            return electricityPrice;
        }

        public void setElectricityPrice(String electricityPrice) {
            this.electricityPrice = electricityPrice;
        }

        public String getHeadImg() {
            return headImg;
        }

        public void setHeadImg(String headImg) {
            this.headImg = headImg;
        }

        public double getServiceFee() {
            return serviceFee;
        }

        public void setServiceFee(double serviceFee) {
            this.serviceFee = serviceFee;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(electricityPrice);
            dest.writeString(headImg);
            dest.writeDouble(serviceFee);
            dest.writeInt(source);
            dest.writeInt(times);
            dest.writeString(title);
            dest.writeString(uuid);
        }

        public StationsBean(Parcel in) {
            //顺序要和writeToParcel写的顺序一样
            electricityPrice = in.readString();
            headImg = in.readString();
            serviceFee = in.readDouble();
            source = in.readInt();
            times = in.readInt();
            title = in.readString();
            uuid = in.readString();
        }

        public static final Parcelable.Creator<StationsBean> CREATOR = new Parcelable.Creator<StationsBean>() {
            public StationsBean createFromParcel(Parcel in) {
                return new StationsBean(in);
            }

            public StationsBean[] newArray(int size) {
                return new StationsBean[size];
            }
        };
    }
}
