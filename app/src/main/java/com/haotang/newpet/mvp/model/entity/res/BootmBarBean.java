package com.haotang.newpet.mvp.model.entity.res;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/13 14:59
 */
public class BootmBarBean implements Serializable {
    private int nToBeComment;
    private String mallRedPoint;
    private IndexBean index;

    public int getNToBeComment() {
        return nToBeComment;
    }

    public void setNToBeComment(int nToBeComment) {
        this.nToBeComment = nToBeComment;
    }

    public String getMallRedPoint() {
        return mallRedPoint;
    }

    public void setMallRedPoint(String mallRedPoint) {
        this.mallRedPoint = mallRedPoint;
    }

    public IndexBean getIndex() {
        return index;
    }

    public void setIndex(IndexBean index) {
        this.index = index;
    }

    public static class IndexBean {
        /**
         * bottom : {"list":[{"title":"宠物家","pic":"/static/icon/shouye.png?3","picH":"/static/icon/shouye_h.png?3"},{"title":"商城","pic":"/static/icon/shangcheng.png?v=15","picH":"/static/icon/shangcheng_h.png"},{"title":"宠圈","pic":"/static/icon/chongquan.png?3","picH":"/static/icon/chongquan_h.png?3"},{"title":"我的","pic":"/static/icon/wode.png?3","picH":"/static/icon/wode_h.png?3"}]}
         */

        private BottomBean bottom;

        public BottomBean getBottom() {
            return bottom;
        }

        public void setBottom(BottomBean bottom) {
            this.bottom = bottom;
        }

        public static class BottomBean {
            private List<ListBean> list;

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class ListBean {
                /**
                 * title : 宠物家
                 * pic : /static/icon/shouye.png?3
                 * picH : /static/icon/shouye_h.png?3
                 */

                private String title;
                private String pic;
                private String picH;

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getPic() {
                    return pic;
                }

                public void setPic(String pic) {
                    this.pic = pic;
                }

                public String getPicH() {
                    return picH;
                }

                public void setPicH(String picH) {
                    this.picH = picH;
                }
            }
        }
    }
}
