package com.haotang.easyshare.mvp.model.entity.res;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/4 10:55
 */
public class FollowBean {
    private String img;
    private String name;
    private String fenShu;
    private int starNum;
    private String jifen;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFenShu() {
        return fenShu;
    }

    public void setFenShu(String fenShu) {
        this.fenShu = fenShu;
    }

    public int getStarNum() {
        return starNum;
    }

    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }

    public String getJifen() {
        return jifen;
    }

    public void setJifen(String jifen) {
        this.jifen = jifen;
    }

    public FollowBean() {
    }

    public FollowBean(String img, String name, String fenShu, int starNum, String jifen) {
        this.img = img;
        this.name = name;
        this.fenShu = fenShu;
        this.starNum = starNum;
        this.jifen = jifen;
    }
}
