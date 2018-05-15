package com.haotang.easyshare.mvp.view.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.view.activity.ButlerActivity;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;

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
                    mContext.startActivity(new Intent(mContext, ButlerActivity.class));
                }
            });
            GlideUtil.loadNetCircleImg(mContext, item.getHeadImg(), iv_item_hotpoint_userimg, R.mipmap.ic_image_load_circle);
            GlideUtil.loadNetImg(mContext, item.getIcon(), iv_item_hotpoint_img, R.mipmap.ic_image_load);
            StringUtil.setText(tv_item_hotpoint_date, item.getCreateTime(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_hotpoint_num, item.getVisitors() + "阅读", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_hotpoint_username, item.getUserName(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_hotpoint_name, item.getTitle(), "", View.VISIBLE, View.VISIBLE);
        }
    }
}