package com.haotang.easyshare.mvp.model.entity.res;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/24 14:34
 */
public class RechargeRecord {
    private String name;
    private int cdl;
    private double price;
    private String kssj;
    private String jssj;
    private String cdsj;

    public RechargeRecord() {
    }

    public RechargeRecord(String name, int cdl, double price, String kssj, String jssj, String cdsj) {
        this.name = name;
        this.cdl = cdl;
        this.price = price;
        this.kssj = kssj;
        this.jssj = jssj;
        this.cdsj = cdsj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCdl() {
        return cdl;
    }

    public void setCdl(int cdl) {
        this.cdl = cdl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJssj() {
        return jssj;
    }

    public void setJssj(String jssj) {
        this.jssj = jssj;
    }

    public String getCdsj() {
        return cdsj;
    }

    public void setCdsj(String cdsj) {
        this.cdsj = cdsj;
    }
}
