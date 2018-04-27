package com.haotang.easyshare.mvp.model.entity.res;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/27 15:30
 */
public class CommentTag {
    private String tag;
    private boolean isCheck;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public CommentTag() {
    }

    public CommentTag(String tag, boolean isCheck) {
        this.tag = tag;
        this.isCheck = isCheck;
    }
}
