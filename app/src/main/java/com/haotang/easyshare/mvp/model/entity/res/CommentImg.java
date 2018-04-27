package com.haotang.easyshare.mvp.model.entity.res;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/27 15:29
 */
public class CommentImg {
    private String imgUrl;
    private boolean isAdd;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public CommentImg() {
    }

    public CommentImg(String imgUrl, boolean isAdd) {
        this.imgUrl = imgUrl;
        this.isAdd = isAdd;
    }
}
