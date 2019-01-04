package com.haotang.easyshare.mvp.model.entity.res;

import android.graphics.Bitmap;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/11 17:09
 */
public class MainFragChargeBean {
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
    private Bitmap bitmap;
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

    public MainFragChargeBean() {
    }

    public MainFragChargeBean(String img, String address, String distance, double lng, int fastNum, int freeNum,
                              int isPrivate, String title, String openTime, String uuid, int slowNum, double lat
            , String parkingPrice, String payWay, String provider) {
        this.img = img;
        this.address = address;
        this.distance = distance;
        this.lng = lng;
        this.fastNum = fastNum;
        this.freeNum = freeNum;
        this.isPrivate = isPrivate;
        this.title = title;
        this.openTime = openTime;
        this.uuid = uuid;
        this.slowNum = slowNum;
        this.lat = lat;
        this.parkingPrice = parkingPrice;
        this.payWay = payWay;
        this.provider = provider;
    }

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
