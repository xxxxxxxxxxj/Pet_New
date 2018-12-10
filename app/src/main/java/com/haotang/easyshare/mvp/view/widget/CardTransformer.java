package com.haotang.easyshare.mvp.view.widget;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by lenovo on 2017/10/27.
 */

public class CardTransformer implements ViewPager.PageTransformer {
    public static final float MIN_SCALE = 0.8f;

    @Override
    public void transformPage(View page, float position) {
        if (position < -1) { // [-Infinity,-1)
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        } else if (position <= 1) { // [-1,1]
            float scaleFactor = MIN_SCALE
                    + (1 - MIN_SCALE) * (1 - Math.abs(position));
            page.setScaleX(scaleFactor);
            page.setScaleY(scaleFactor);
        } else { // (1,+Infinity]
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        }
    }
}
