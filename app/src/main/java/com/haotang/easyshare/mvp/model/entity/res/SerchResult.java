package com.haotang.easyshare.mvp.model.entity.res;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/26 16:50
 */
public class SerchResult {
    private String name;
    private String desc;
    private double lat;
    private double lng;
    private boolean isFake;

    public boolean isFake() {
        return isFake;
    }

    public void setFake(boolean fake) {
        isFake = fake;
    }

    public SerchResult() {
    }

    public SerchResult(String name, String desc, double lat, double lng, boolean isFake) {
        this.name = name;
        this.desc = desc;
        this.lat = lat;
        this.lng = lng;
        this.isFake = isFake;
    }

    public SerchResult(String name, String desc, double lat, double lng) {
        this.name = name;
        this.desc = desc;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
}
