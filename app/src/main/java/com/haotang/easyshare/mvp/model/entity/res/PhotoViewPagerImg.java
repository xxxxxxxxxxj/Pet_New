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
    private boolean isDeleteAll;

    public int getPagerPosition() {
        return pagerPosition;
    }

    public void setPagerPosition(int pagerPosition) {
        this.pagerPosition = pagerPosition;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isDeleteAll() {
        return isDeleteAll;
    }

    public void setDeleteAll(boolean deleteAll) {
        isDeleteAll = deleteAll;
    }
}
