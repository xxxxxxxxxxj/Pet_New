package com.haotang.easyshare.mvp.model.entity.res;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/3 17:57
 */
public class CollectChargeBean {
    private String img;
    private String desc;
    private String cdf;
    private String fwf;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCdf() {
        return cdf;
    }

    public void setCdf(String cdf) {
        this.cdf = cdf;
    }

    public String getFwf() {
        return fwf;
    }

    public void setFwf(String fwf) {
        this.fwf = fwf;
    }

    public CollectChargeBean() {
    }

    public CollectChargeBean(String img, String desc, String cdf, String fwf) {
        this.img = img;
        this.desc = desc;
        this.cdf = cdf;
        this.fwf = fwf;
    }
}
