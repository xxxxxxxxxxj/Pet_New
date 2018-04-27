package com.haotang.easyshare.mvp.model.entity.res;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/27 17:21
 */
public class PhotoViewPagerImg {
    private int pagerPosition;
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public PhotoViewPagerImg() {
    }

    public PhotoViewPagerImg(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public PhotoViewPagerImg(int pagerPosition, String imgUrl) {
        this.pagerPosition = pagerPosition;
        this.imgUrl = imgUrl;
    }

    public int getPagerPosition() {
        return pagerPosition;
    }

    public void setPagerPosition(int pagerPosition) {
        this.pagerPosition = pagerPosition;
    }

    public PhotoViewPagerImg(int pagerPosition) {
        this.pagerPosition = pagerPosition;
    }
}
