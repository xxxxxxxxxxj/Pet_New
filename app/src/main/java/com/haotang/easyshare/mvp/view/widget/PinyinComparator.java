package com.haotang.easyshare.mvp.view.widget;

import com.haotang.easyshare.mvp.model.entity.res.HotCarBean;

import java.util.Comparator;

/**
 * @author xiaanming
 */
public class PinyinComparator implements Comparator<HotCarBean.DataBean> {

    public int compare(HotCarBean.DataBean o1, HotCarBean.DataBean o2) {
        if (o1.getSortLetters().equals("@") || o2.getSortLetters().equals("#")) {
            return -1;
        } else if (o1.getSortLetters().equals("#") || o2.getSortLetters().equals("@")) {
            return 1;
        } else {
            return o1.getSortLetters().compareTo(o2.getSortLetters());
        }
    }
}
