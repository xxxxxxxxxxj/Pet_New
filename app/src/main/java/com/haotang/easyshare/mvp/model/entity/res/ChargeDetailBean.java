package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/5/13 12:02
 */
public class ChargeDetailBean {

    /**
     * address : 中国广东省韶关市曲江区曲江服务区
     * electricityPrice : 未知
     * distance : 0m
     * lng : 113.604547
     * headImg : http://static.evyou.cc/imges/carrierimg/nanfang.jpg
     * fastNum : 4
     * parkingPrice : 免费
     * payWay : 粤易充APP
     * commentTotal : 0
     * isPrivate : 0
     * title : G4曲江服务区（北行）
     * uuid : da892997929a478ea602749cd3c742f2
     * slowNum : 0
     * is_collect : 0
     * times : 0
     * provider : 南方电网
     * phone :
     * openTime : 全天
     * lat : 24.644702
     */

    private String address;
    private String electricityPrice;
    private String distance;
    private double lng;
    private String headImg;
    private int fastNum;
    private String parkingPrice;
    private String payWay;
    private int commentTotal;
    private int isPrivate;
    private String title;
    private String uuid;
    private int slowNum;
    private int isCollect;
    private int times;
    private String provider;
    private String phone;
    private String openTime;
    private double lat;
    private int freeNum;
    private double serviceFee;
    private String remark;
    private int parkingIsUnderground;
    private List<String> detailImgs;
    private List<UseNotices> useNotices;

    public static class UseNotices {
        private String time;
        private String content;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public List<UseNotices> getUseNotices() {
        return useNotices;
    }

    public void setUseNotices(List<UseNotices> useNotices) {
        this.useNotices = useNotices;
    }

    public List<String> getDetailImgs() {
        return detailImgs;
    }

    public void setDetailImgs(List<String> detailImgs) {
        this.detailImgs = detailImgs;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public int getParkingIsUnderground() {
        return parkingIsUnderground;
    }

    public void setParkingIsUnderground(int parkingIsUnderground) {
        this.parkingIsUnderground = parkingIsUnderground;
    }

    public double getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(double serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getFreeNum() {
        return freeNum;
    }

    public void setFreeNum(int freeNum) {
        this.freeNum = freeNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getElectricityPrice() {
        return electricityPrice;
    }

    public void setElectricityPrice(String electricityPrice) {
        this.electricityPrice = electricityPrice;
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

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public int getFastNum() {
        return fastNum;
    }

    public void setFastNum(int fastNum) {
        this.fastNum = fastNum;
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

    public int getCommentTotal() {
        return commentTotal;
    }

    public void setCommentTotal(int commentTotal) {
        this.commentTotal = commentTotal;
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


    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
