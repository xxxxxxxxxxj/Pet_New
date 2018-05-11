package com.haotang.easyshare.mvp.model.entity.res;

import android.graphics.Bitmap;

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
     * stations : [{"img":"http://static.evyou.cc/imges/carrierimg/nanfang.jpg","address":"中国广东省韶关市曲江区曲江服务区","distance":"697m","lng":113.604547,"fastNum":4,"isPrivate":1,"title":"G4曲江服务区（北行）","openTime":"全天","uuid":"da892997929a478ea602749cd3c742f2","slowNum":0,"lat":24.644702},{"img":"http://static.evyou.cc/imges/carrierimg/nanfang.jpg","address":"中国广东省韶关市曲江区曲江服务区","distance":"697m","lng":113.604547,"fastNum":4,"isPrivate":1,"title":"G4曲江服务区（北行）","openTime":"全天","uuid":"1bdf1cd70a1d4dba81066003f80baa57","slowNum":0,"lat":24.644702},{"img":"http://static.evyou.cc/imges/carrierimg/chongdianduizhang.jpg","address":"广东省韶关市曲江区广东省韶关市曲江区马坝106国道城东段","distance":"4.62km","lng":113.627747,"fastNum":0,"isPrivate":1,"title":"韶关友好温泉商务酒店","openTime":"不详","uuid":"f91dc636a094408e9b6139bd1d6d8214","slowNum":3,"lat":24.672923},{"img":"http://static.evyou.cc/imges/carrierimg/chongdianduizhang.jpg","address":"广东省韶关市曲江区广东省韶关市曲江区马坝106国道城东段","distance":"4.61km","lng":113.627747,"fastNum":0,"isPrivate":1,"title":"韶关友好温泉商务酒店","openTime":"不详","uuid":"2f9293e68d0348569376b25224401512","slowNum":3,"lat":24.672923}]
     * articles : [{"comments":0,"icon":"http://img.sayiyinxiang.com/api/brand/imgs/15246586331239576679.jpg","title":"曾是英国皇室及罗马教皇御用车, 现出混动车, 百公里油耗1.5L!","uuid":"90ad5b8dfddf4d569bbdab7653307de0","praises":0},{"comments":0,"icon":"http://img.sayiyinxiang.com/api/brand/imgs/15246594919442574355.jpg","title":"迈凯伦P一接替者, 2.5秒破百, 销售价格一675万, bugatti怕了!","uuid":"8d5b177af2c94e74826e829157b0ef6f","praises":0},{"comments":0,"icon":"http://img.sayiyinxiang.com/api/brand/imgs/15246600861069329369.jpg","title":"宝骏向老年代步车开炮，裸车3万多，最高可飙100Km/h","uuid":"2bf782d85fc94861b0c69a520a663d75","praises":0},{"comments":0,"icon":"http://img.sayiyinxiang.com/api/brand/imgs/15246608323257700588.jpg","title":"\u201c奇瑞eQ们\u201d 要抢 \u201c雷丁们\u201d 的饭碗？真相是\u2026\u2026","uuid":"21d4d2146c3f48a39d4d703e32441ae2","praises":0},{"comments":0,"icon":"http://img.sayiyinxiang.com/api/brand/imgs/15246556172146055316.jpg","title":"红旗L5首款私人定制版新鲜出炉，首位车主是谁大家都没想到！","uuid":"04bc6231b54c49ab961761c9d7751a06","praises":0}]
     */

    private String distanceTip;
    private List<AdsBean> ads;
    private List<StationsBean> stations;
    private List<ArticlesBean> articles;

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

    public List<StationsBean> getStations() {
        return stations;
    }

    public void setStations(List<StationsBean> stations) {
        this.stations = stations;
    }

    public List<ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticlesBean> articles) {
        this.articles = articles;
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

    public static class StationsBean {
        /**
         * img : http://static.evyou.cc/imges/carrierimg/nanfang.jpg
         * address : 中国广东省韶关市曲江区曲江服务区
         * distance : 697m
         * lng : 113.604547
         * fastNum : 4
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
        private int isPrivate;
        private String title;
        private String openTime;
        private String uuid;
        private int slowNum;
        private double lat;
        private Bitmap bitmap;

        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
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

    public static class ArticlesBean {
        /**
         * comments : 0
         * icon : http://img.sayiyinxiang.com/api/brand/imgs/15246586331239576679.jpg
         * title : 曾是英国皇室及罗马教皇御用车, 现出混动车, 百公里油耗1.5L!
         * uuid : 90ad5b8dfddf4d569bbdab7653307de0
         * praises : 0
         */

        private int comments;
        private String icon;
        private String title;
        private String uuid;
        private int praises;

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
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
    }
}
