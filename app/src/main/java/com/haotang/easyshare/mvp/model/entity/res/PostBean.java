package com.haotang.easyshare.mvp.model.entity.res;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/3 14:23
 */
public class PostBean {
    private String img;
    private String desc;
    private String date;
    private String shareTitle;
    private String shareSummary;
    private String shareTargetUrl;
    private String shareThumbUrlOrPath;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareSummary() {
        return shareSummary;
    }

    public void setShareSummary(String shareSummary) {
        this.shareSummary = shareSummary;
    }

    public String getShareTargetUrl() {
        return shareTargetUrl;
    }

    public void setShareTargetUrl(String shareTargetUrl) {
        this.shareTargetUrl = shareTargetUrl;
    }

    public String getShareThumbUrlOrPath() {
        return shareThumbUrlOrPath;
    }

    public void setShareThumbUrlOrPath(String shareThumbUrlOrPath) {
        this.shareThumbUrlOrPath = shareThumbUrlOrPath;
    }

    public PostBean() {
    }

    public PostBean(String img, String desc, String date, String shareTitle, String shareSummary, String shareTargetUrl, String shareThumbUrlOrPath) {
        this.img = img;
        this.desc = desc;
        this.date = date;
        this.shareTitle = shareTitle;
        this.shareSummary = shareSummary;
        this.shareTargetUrl = shareTargetUrl;
        this.shareThumbUrlOrPath = shareThumbUrlOrPath;
    }
}
