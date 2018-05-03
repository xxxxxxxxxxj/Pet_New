package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.CollectChargeBean;
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
 * @date XJ on 2018/5/3 18:00
 */
public class CollectChargeListAdapter extends BaseQuickAdapter<CollectChargeBean, BaseViewHolder> {
    public CollectChargeListAdapter(int layoutResId, List<CollectChargeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, CollectChargeBean item) {
        LinearLayout ll_item_collectcharge_root = helper.getView(R.id.ll_item_collectcharge_root);
        ImageView iv_item_collectcharge_img = helper.getView(R.id.iv_item_collectcharge_img);
        TextView tv_item_collectcharge_name = helper.getView(R.id.tv_item_collectcharge_name);
        TextView tv_item_collectcharge_cdf = helper.getView(R.id.tv_item_collectcharge_cdf);
        TextView tv_item_collectcharge_fwf = helper.getView(R.id.tv_item_collectcharge_fwf);
        if (helper.getLayoutPosition() == 0) {
            FrameLayout.LayoutParams layoutParams =
                    (FrameLayout.LayoutParams) ll_item_collectcharge_root.getLayoutParams();
            layoutParams.topMargin = DensityUtil.dp2px(mContext, 15);
            ll_item_collectcharge_root.setLayoutParams(layoutParams);
        }
        if (item != null) {
            StringUtil.setText(tv_item_collectcharge_name, item.getDesc(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_collectcharge_cdf, "充电费：" + item.getCdf(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_collectcharge_fwf, "服务费：" + item.getFwf(), "", View.VISIBLE, View.VISIBLE);
            GlideUtil.loadNetImg(mContext, item.getImg(), iv_item_collectcharge_img, R.mipmap.ic_image_load);
        }
    }
}
