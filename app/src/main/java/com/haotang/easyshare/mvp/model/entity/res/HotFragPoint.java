package com.haotang.easyshare.mvp.model.entity.res;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/12/10 17:12
 */
public class HotFragPoint {
    private String title;
    private int imgFlag;
    private String img;
    private List<String> imgList;
    private String userImg;
    private String userName;
    private String time;
    private String num;

    public HotFragPoint(String title, int imgFlag, String img, List<String> imgList, String userImg, String userName, String time, String num) {
        this.title = title;
        this.imgFlag = imgFlag;
        this.img = img;
        this.imgList = imgList;
        this.userImg = userImg;
        this.userName = userName;
        this.time = time;
        this.num = num;
    }

    public HotFragPoint() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImgFlag() {
        return imgFlag;
    }

    public void setImgFlag(int imgFlag) {
        this.imgFlag = imgFlag;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
