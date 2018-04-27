package com.haotang.easyshare.mvp.model.entity.res;

import android.graphics.Bitmap;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/25 14:48
 */
public class MainFragmentData {
    private int ggorgr;
    private String name;
    private String juli;
    private int kuaichongnum;
    private int manchongnum;
    private int kongxiannum;
    private String kfsj;
    private String address;
    private double lat;
    private double lng;
    private String city;
    private String img;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getGgorgr() {
        return ggorgr;
    }

    public void setGgorgr(int ggorgr) {
        this.ggorgr = ggorgr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJuli() {
        return juli;
    }

    public void setJuli(String juli) {
        this.juli = juli;
    }

    public int getKuaichongnum() {
        return kuaichongnum;
    }

    public void setKuaichongnum(int kuaichongnum) {
        this.kuaichongnum = kuaichongnum;
    }

    public int getManchongnum() {
        return manchongnum;
    }

    public void setManchongnum(int manchongnum) {
        this.manchongnum = manchongnum;
    }

    public int getKongxiannum() {
        return kongxiannum;
    }

    public void setKongxiannum(int kongxiannum) {
        this.kongxiannum = kongxiannum;
    }

    public String getKfsj() {
        return kfsj;
    }

    public void setKfsj(String kfsj) {
        this.kfsj = kfsj;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public MainFragmentData() {
    }

    public MainFragmentData(int ggorgr, String name, String juli, int kuaichongnum, int manchongnum,
                            int kongxiannum, String kfsj, String address, double lat, double lng, String city, String img) {
        this.ggorgr = ggorgr;
        this.name = name;
        this.juli = juli;
        this.kuaichongnum = kuaichongnum;
        this.manchongnum = manchongnum;
        this.kongxiannum = kongxiannum;
        this.kfsj = kfsj;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.city = city;
        this.img = img;
    }
}
