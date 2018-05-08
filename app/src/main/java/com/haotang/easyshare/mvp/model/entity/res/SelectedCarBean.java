package com.haotang.easyshare.mvp.model.entity.res;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/8 15:44
 */
public class SelectedCarBean {
    private String img;
    private String name;
    private String xuhang;
    private double price;

    public SelectedCarBean() {
    }

    public SelectedCarBean(String img, String name, String xuhang, double price) {
        this.img = img;
        this.name = name;
        this.xuhang = xuhang;
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXuhang() {
        return xuhang;
    }

    public void setXuhang(String xuhang) {
        this.xuhang = xuhang;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
