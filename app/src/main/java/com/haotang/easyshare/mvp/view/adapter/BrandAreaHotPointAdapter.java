package com.haotang.easyshare.mvp.view.adapter;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.BrandAreaBean;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.view.activity.ButlerActivity;
import com.haotang.easyshare.mvp.view.activity.CarInfoActivity;
import com.haotang.easyshare.mvp.view.activity.LoginActivity;
import com.haotang.easyshare.mvp.view.widget.GridSpacingItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/15 13:29
 */
public class BrandAreaHotPointAdapter extends BaseQuickAdapter<HotPoint.DataBean, BaseViewHolder> {
    private List<AdvertisementBean.DataBean> adData;
    private List<BrandAreaBean.AdBean> adList = new ArrayList<BrandAreaBean.AdBean>();

    public BrandAreaHotPointAdapter(int layoutResId, List<HotPoint.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotPoint.DataBean item) {
        ImageView iv_item_allbands_lxgj = helper.getView(R.id.iv_item_allbands_lxgj);
        ImageView iv_item_allbands_img = helper.getView(R.id.iv_item_allbands_img);
        TextView tv_item_allbands_name = helper.getView(R.id.tv_item_allbands_name);
        TextView tv_item_allbands_xuhang = helper.getView(R.id.tv_item_allbands_xuhang);
        TextView tv_item_allbands_price = helper.getView(R.id.tv_item_allbands_price);
        ImageView iv_item_hotpoint_img = helper.getView(R.id.iv_item_hotpoint_img);
        TextView tv_item_hotpoint_date = helper.getView(R.id.tv_item_hotpoint_date);
        TextView tv_item_hotpoint_num = helper.getView(R.id.tv_item_hotpoint_num);
        ImageView iv_item_hotpoint_userimg = helper.getView(R.id.iv_item_hotpoint_userimg);
        TextView tv_item_hotpoint_username = helper.getView(R.id.tv_item_hotpoint_username);
        TextView tv_item_hotpoint_name = helper.getView(R.id.tv_item_hotpoint_name);
        LinearLayout ll_item_allbands_root = helper.getView(R.id.ll_item_allbands_root);
        final LinearLayout ll_item_allbands_ad = helper.getView(R.id.ll_item_allbands_ad);

        ImageView iv_brand_area_ad_close = helper.getView(R.id.iv_brand_area_ad_close);
        RecyclerView rv_brand_area_ad = helper.getView(R.id.rv_brand_area_ad);
        if (helper.getLayoutPosition() == 0) {
            RecyclerView.LayoutParams layoutParams =
                    (RecyclerView.LayoutParams) ll_item_allbands_root.getLayoutParams();
            layoutParams.topMargin = DensityUtil.dp2px(mContext, 15);
            ll_item_allbands_root.setLayoutParams(layoutParams);
        }
        if (item != null) {
            GlideUtil.loadNetImg(mContext, item.getCarIcon(), iv_item_allbands_img, R.mipmap.ic_image_load);
            StringUtil.setText(tv_item_allbands_name, item.getCarName(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_allbands_xuhang, item.getBatteryLife(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_allbands_price, "¥" + item.getCarPrice(), "", View.VISIBLE, View.VISIBLE);
            iv_item_allbands_lxgj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (SystemUtil.checkLogin(mContext)) {
                        mContext.startActivity(new Intent(mContext, ButlerActivity.class));
                    } else {
                        mContext.startActivity(new Intent(mContext, LoginActivity.class));
                    }
                }
            });
            GlideUtil.loadNetCircleImg(mContext, item.getHeadImg(), iv_item_hotpoint_userimg, R.mipmap.ic_image_load_circle);
            GlideUtil.loadNetImg(mContext, item.getIcon(), iv_item_hotpoint_img, R.mipmap.ic_image_load);
            StringUtil.setText(tv_item_hotpoint_date, item.getCreateTime(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_hotpoint_num, item.getVisitors() + "阅读", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_hotpoint_username, item.getUserName(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_hotpoint_name, item.getTitle(), "", View.VISIBLE, View.VISIBLE);
            if (helper.getLayoutPosition() == 1 && adData != null && adData.size() > 0) {
                ll_item_allbands_ad.setVisibility(View.VISIBLE);
                adList.clear();
                for (int i = 0; i < adData.size(); i++) {
                    AdvertisementBean.DataBean adsBean = adData.get(i);
                    if (adsBean != null) {
                        adList.add(new BrandAreaBean.AdBean(adsBean.getImg(), adsBean.getDisplay(), adsBean.getDestination()));
                    }
                }
                rv_brand_area_ad.setHasFixedSize(true);
                rv_brand_area_ad.setNestedScrollingEnabled(false);
                NoScollFullGridLayoutManager noScollFullGridLayoutManager = new
                        NoScollFullGridLayoutManager(rv_brand_area_ad, mContext, 3, GridLayoutManager.VERTICAL, false);
                noScollFullGridLayoutManager.setScrollEnabled(false);
                rv_brand_area_ad.setLayoutManager(noScollFullGridLayoutManager);
                if (rv_brand_area_ad.getItemDecorationCount() <= 0) {
                    rv_brand_area_ad.addItemDecoration(new GridSpacingItemDecoration(3,
                            mContext.getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                            mContext.getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                            false));
                }
                rv_brand_area_ad.setAdapter(new BrandAreaAdAdapter(R.layout.item_brandarea_ad
                        , adList));
                iv_brand_area_ad_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ll_item_allbands_ad.setVisibility(View.GONE);
                    }
                });
            } else {
                ll_item_allbands_ad.setVisibility(View.GONE);
            }
        }
    }

    public void setAdData(List<AdvertisementBean.DataBean> adData) {
        this.adData = adData;
        notifyDataSetChanged();
    }
}