package com.haotang.easyshare.mvp.view.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundLinearLayout;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.MainFragChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.MainFragmentData;
import com.haotang.easyshare.mvp.view.activity.ChargingPileDetailActivity;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/25 14:51
 */
public class MainLocalAdapter extends BaseQuickAdapter<MainFragChargeBean, BaseViewHolder> {
    private boolean isTopDivider;
    private String city;

    public MainLocalAdapter(int layoutResId, List<MainFragChargeBean> data, boolean isTopDivider, String city) {
        super(layoutResId, data);
        this.isTopDivider = isTopDivider;
        this.city = city;
    }

    @Override
    protected void convert(BaseViewHolder helper, final MainFragChargeBean item) {
        RoundLinearLayout rll_item_mainlocal_root = helper.getView(R.id.rll_item_mainlocal_root);
        ImageView iv_item_mainlocal_ggorgr = helper.getView(R.id.iv_item_mainlocal_ggorgr);
        TextView tv_item_mainlocal_juli = helper.getView(R.id.tv_item_mainlocal_juli);
        TextView tv_item_mainlocal_name = helper.getView(R.id.tv_item_mainlocal_name);
        LinearLayout ll_item_mainlocal_kuaichong = helper.getView(R.id.ll_item_mainlocal_kuaichong);
        TextView tv_item_mainlocal_kuaichong_num = helper.getView(R.id.tv_item_mainlocal_kuaichong_num);
        LinearLayout ll_item_mainlocal_manchong = helper.getView(R.id.ll_item_mainlocal_manchong);
        TextView tv_item_mainlocal_manchong_num = helper.getView(R.id.tv_item_mainlocal_manchong_num);
        LinearLayout ll_item_mainlocal_kongxian = helper.getView(R.id.ll_item_mainlocal_kongxian);
        TextView tv_item_mainlocal_kongxian_num = helper.getView(R.id.tv_item_mainlocal_kongxian_num);
        TextView tv_item_mainlocal_kfsj = helper.getView(R.id.tv_item_mainlocal_kfsj);
        ImageView iv_item_mainlocal_daohang = helper.getView(R.id.iv_item_mainlocal_daohang);
        TextView tv_item_mainlocal_address = helper.getView(R.id.tv_item_mainlocal_address);
        if (isTopDivider && helper.getLayoutPosition() == 0) {
            RecyclerView.LayoutParams layoutParams =
                    (RecyclerView.LayoutParams) rll_item_mainlocal_root.getLayoutParams();
            layoutParams.topMargin = DensityUtil.dp2px(mContext, 15);
            rll_item_mainlocal_root.setLayoutParams(layoutParams);
        }
        if (item != null) {
            StringUtil.setText(tv_item_mainlocal_name, item.getTitle(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_mainlocal_juli, item.getDistance(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_mainlocal_address, item.getAddress(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_mainlocal_kfsj, "开放时间：" + item.getOpenTime(), "", View.VISIBLE, View.VISIBLE);
            if (item.getIsPrivate() == 0) {//公共
                iv_item_mainlocal_ggorgr.setImageResource(R.mipmap.icon_gg);
            } else if (item.getIsPrivate() == 1) {//个人
                iv_item_mainlocal_ggorgr.setImageResource(R.mipmap.icon_gr);
            }
            if (item.getFastNum() > 0) {
                StringUtil.setText(tv_item_mainlocal_kuaichong_num, "快充" + item.getFastNum() + "个", "", View.VISIBLE, View.VISIBLE);
                ll_item_mainlocal_kuaichong.setVisibility(View.VISIBLE);
            } else {
                ll_item_mainlocal_kuaichong.setVisibility(View.GONE);
            }
            if (item.getSlowNum() > 0) {
                StringUtil.setText(tv_item_mainlocal_manchong_num, "慢充" + item.getSlowNum() + "个", "", View.VISIBLE, View.VISIBLE);
                ll_item_mainlocal_manchong.setVisibility(View.VISIBLE);
            } else {
                ll_item_mainlocal_manchong.setVisibility(View.GONE);
            }
            if (item.getFreeNum() > 0) {
                StringUtil.setText(tv_item_mainlocal_kongxian_num, "空闲" + item.getFreeNum() + "个", "", View.VISIBLE, View.VISIBLE);
                ll_item_mainlocal_kongxian.setVisibility(View.VISIBLE);
            } else {
                ll_item_mainlocal_kongxian.setVisibility(View.GONE);
            }
            iv_item_mainlocal_daohang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SystemUtil.goNavigation(mContext, item.getLat(), item.getLng(), "我的位置", item.getAddress(), city);
                }
            });
            rll_item_mainlocal_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, ChargingPileDetailActivity.class));
                }
            });
        }
    }
}
