package com.haotang.easyshare.mvp.view.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.util.GlideUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/12/13 16:41
 */
public class ViewPagerMainAdapter extends PagerAdapter {
    public Context mContext;
    public List<AdvertisementBean.DataBean> bannerList;

    public ViewPagerMainAdapter(Context mContext, List<AdvertisementBean.DataBean> bannerList) {
        this.mContext = mContext;
        this.bannerList = bannerList;
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_viewpager_selectcar, null);
        ImageView iv_item_viewpager_selectcar = (ImageView) view.findViewById(R.id.iv_item_viewpager_selectcar);
        AdvertisementBean.DataBean dataBean = bannerList.get(position);
        if (dataBean != null) {
            GlideUtil.loadNetRoundImg(mContext, dataBean.getImg(), iv_item_viewpager_selectcar, R.mipmap.ic_image_load,10);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}