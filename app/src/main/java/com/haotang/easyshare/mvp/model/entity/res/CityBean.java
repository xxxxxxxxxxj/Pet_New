package com.haotang.easyshare.mvp.model.entity.res;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/28 13:33
 */
public class CityBean {
    private int cityCode;
    private String cityName;
    private boolean isSelete;

    public boolean isSelete() {
        return isSelete;
    }

    public void setSelete(boolean selete) {
        isSelete = selete;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public CityBean(int cityCode, String cityName, boolean isSelete) {
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.isSelete = isSelete;
    }

    public CityBean() {
    }
}
