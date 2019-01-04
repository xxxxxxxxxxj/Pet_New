package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/25 14:48
 */
public class MainFragmentData {

    /**
     * ads : [{"img":"http://img.sayiyinxiang.com/api/activity/imgs/fallout.jpg","display":2,"destination":"http://www.baidu.com"}]
     * distanceTip : 附近八公里充电桩
     * publish : [{"img":"http://static.evyou.cc/imges/carrierimg/nanfang.jpg","address":"中国广东省韶关市曲江区曲江服务区","distance":"697m","lng":113.604547,"fastNum":4,"freeNum":1,"isPrivate":1,"title":"G4曲江服务区（北行）","openTime":"全天","uuid":"da892997929a478ea602749cd3c742f2","slowNum":0,"lat":24.644702},{"img":"http://static.evyou.cc/imges/carrierimg/nanfang.jpg","address":"中国广东省韶关市曲江区曲江服务区","distance":"697m","lng":113.604547,"fastNum":4,"isPrivate":1,"title":"G4曲江服务区（北行）","openTime":"全天","uuid":"1bdf1cd70a1d4dba81066003f80baa57","slowNum":0,"lat":24.644702},{"img":"http://static.evyou.cc/imges/carrierimg/chongdianduizhang.jpg","address":"广东省韶关市曲江区广东省韶关市曲江区马坝106国道城东段","distance":"4.62km","lng":113.627747,"fastNum":0,"isPrivate":1,"title":"韶关友好温泉商务酒店","openTime":"不详","uuid":"f91dc636a094408e9b6139bd1d6d8214","slowNum":3,"lat":24.672923},{"img":"http://static.evyou.cc/imges/carrierimg/chongdianduizhang.jpg","address":"广东省韶关市曲江区广东省韶关市曲江区马坝106国道城东段","distance":"4.61km","lng":113.627747,"fastNum":0,"isPrivate":1,"title":"韶关友好温泉商务酒店","openTime":"不详","uuid":"2f9293e68d0348569376b25224401512","slowNum":3,"lat":24.672923}]
     * personal : [{"img":"15258565092619189712.jpg","address":"远大路1号院A区2栋808","distance":"631m","lng":116.438898,"fastNum":0,"freeNum":1,"isPrivate":1,"title":"远大路1号院A区自用充电桩","openTime":"10:00-16:00","uuid":"adb9c744e282402d892db8d8d6de9b48","slowNum":1,"lat":39.9263}]
     */

    private String distanceTip;
    private List<AdsBean> ads;
    private List<PublishBean> publish;
    private List<PersonalBean> personal;

    public String getDistanceTip() {
        return distanceTip;
    }

    public void setDistanceTip(String distanceTip) {
        this.distanceTip = distanceTip;
    }

    public List<AdsBean> getAds() {
        return ads;
    }

    public void setAds(List<AdsBean> ads) {
        this.ads = ads;
    }

    public List<PublishBean> getPublish() {
        return publish;
    }

    public void setPublish(List<PublishBean> publish) {
        this.publish = publish;
    }

    public List<PersonalBean> getPersonal() {
        return personal;
    }

    public void setPersonal(List<PersonalBean> personal) {
        this.personal = personal;
    }

    public static class AdsBean {
        /**
         * img : http://img.sayiyinxiang.com/api/activity/imgs/fallout.jpg
         * display : 2
         * destination : http://www.baidu.com
         */

        private String img;
        private int display;
        private String destination;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getDisplay() {
            return display;
        }

        public void setDisplay(int display) {
            this.display = display;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }
    }

    public static class PublishBean {
        /**
         * img : http://static.evyou.cc/imges/carrierimg/nanfang.jpg
         * address : 中国广东省韶关市曲江区曲江服务区
         * distance : 697m
         * lng : 113.604547
         * fastNum : 4
         * freeNum : 1
         * isPrivate : 1
         * title : G4曲江服务区（北行）
         * openTime : 全天
         * uuid : da892997929a478ea602749cd3c742f2
         * slowNum : 0
         * lat : 24.644702
         */

        private String img;
        private String address;
        private String distance;
        private double lng;
        private int fastNum;
        private int freeNum;
        private int isPrivate;
        private String title;
        private String openTime;
        private String uuid;
        private int slowNum;
        private double lat;
        private String parkingPrice;
        private String payWay;
        private String provider;
        private String headImg;
        private String electricityPrice;

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

        public String getParkingPrice() {
            return parkingPrice;
        }

        public void setParkingPrice(String parkingPrice) {
            this.parkingPrice = parkingPrice;
        }

        public String getPayWay() {
            return payWay;
        }

        public void setPayWay(String payWay) {
            this.payWay = payWay;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public int getFastNum() {
            return fastNum;
        }

        public void setFastNum(int fastNum) {
            this.fastNum = fastNum;
        }

        public int getFreeNum() {
            return freeNum;
        }

        public void setFreeNum(int freeNum) {
            this.freeNum = freeNum;
        }

        public int getIsPrivate() {
            return isPrivate;
        }

        public void setIsPrivate(int isPrivate) {
            this.isPrivate = isPrivate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getSlowNum() {
            return slowNum;
        }

        public void setSlowNum(int slowNum) {
            this.slowNum = slowNum;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }

    public static class PersonalBean {
        /**
         * img : 15258565092619189712.jpg
         * address : 远大路1号院A区2栋808
         * distance : 631m
         * lng : 116.438898
         * fastNum : 0
         * freeNum : 1
         * isPrivate : 1
         * title : 远大路1号院A区自用充电桩
         * openTime : 10:00-16:00
         * uuid : adb9c744e282402d892db8d8d6de9b48
         * slowNum : 1
         * lat : 39.9263
         */

        private String img;
        private String address;
        private String distance;
        private double lng;
        private int fastNum;
        private int freeNum;
        private int isPrivate;
        private String title;
        private String openTime;
        private String uuid;
        private int slowNum;
        private double lat;
        private String parkingPrice;
        private String payWay;
        private String provider;
        private String headImg;
        private String electricityPrice;

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

        public String getParkingPrice() {
            return parkingPrice;
        }

        public void setParkingPrice(String parkingPrice) {
            this.parkingPrice = parkingPrice;
        }

        public String getPayWay() {
            return payWay;
        }

        public void setPayWay(String payWay) {
            this.payWay = payWay;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public int getFastNum() {
            return fastNum;
        }

        public void setFastNum(int fastNum) {
            this.fastNum = fastNum;
        }

        public int getFreeNum() {
            return freeNum;
        }

        public void setFreeNum(int freeNum) {
            this.freeNum = freeNum;
        }

        public int getIsPrivate() {
            return isPrivate;
        }

        public void setIsPrivate(int isPrivate) {
            this.isPrivate = isPrivate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getOpenTime() {
            return openTime;
        }

        public void setOpenTime(String openTime) {
            this.openTime = openTime;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public int getSlowNum() {
            return slowNum;
        }

        public void setSlowNum(int slowNum) {
            this.slowNum = slowNum;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }
}
