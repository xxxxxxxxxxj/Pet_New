package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/28 17:57
 */
public class CommentBean {
    private String imgUrl;
    private String name;
    private String date;
    private String desc;
    private List<CommentTag> tagList;
    private List<CommentImg> imgList;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<CommentTag> getTagList() {
        return tagList;
    }

    public void setTagList(List<CommentTag> tagList) {
        this.tagList = tagList;
    }

    public List<CommentImg> getImgList() {
        return imgList;
    }

    public void setImgList(List<CommentImg> imgList) {
        this.imgList = imgList;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public CommentBean(String imgUrl, String name, String date, String desc, List<CommentTag> tagList, List<CommentImg> imgList) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.date = date;
        this.desc = desc;
        this.tagList = tagList;
        this.imgList = imgList;
    }

    public CommentBean() {
    }
}
