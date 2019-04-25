package com.haotang.easyshare.mvp.model.entity.event;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/9/4 18:41
 */
public class SelectCouponEvent {
    private double amount;
    private String amountTxt;
    private String description;
    private int reduceType;
    private String startTime;
    private int id;
    private int state;
    private int isAvali;
    private String endTime;
    private String title;
    private boolean isSelect;
    private double maxDiscount;

    public double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public SelectCouponEvent() {
    }

    public SelectCouponEvent(double amount, String amountTxt, String description, int reduceType, String startTime, int id, int state, int isAvali, String endTime, String title, boolean isSelect,double maxDiscount) {
        this.amount = amount;
        this.amountTxt = amountTxt;
        this.description = description;
        this.reduceType = reduceType;
        this.startTime = startTime;
        this.id = id;
        this.state = state;
        this.isAvali = isAvali;
        this.endTime = endTime;
        this.title = title;
        this.isSelect = isSelect;
        this.maxDiscount = maxDiscount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAmountTxt() {
        return amountTxt;
    }

    public void setAmountTxt(String amountTxt) {
        this.amountTxt = amountTxt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReduceType() {
        return reduceType;
    }

    public void setReduceType(int reduceType) {
        this.reduceType = reduceType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIsAvali() {
        return isAvali;
    }

    public void setIsAvali(int isAvali) {
        this.isAvali = isAvali;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
