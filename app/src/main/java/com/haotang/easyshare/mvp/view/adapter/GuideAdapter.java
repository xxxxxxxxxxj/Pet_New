package com.haotang.easyshare.mvp.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/12 17:28
 */
public class GuideAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<ImageView> imageViews;

    public GuideAdapter(Context context, ArrayList<ImageView> imageViews) {
        super();
        this.context = context;
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imageViews.get(position % imageViews.size()));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(imageViews.get(position % imageViews.size()));
        return imageViews.get(position % imageViews.size());
    }
}
