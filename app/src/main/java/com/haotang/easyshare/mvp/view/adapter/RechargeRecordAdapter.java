package com.haotang.easyshare.mvp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.ChargeList;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/24 14:33
 */
public class RechargeRecordAdapter extends BaseQuickAdapter<ChargeList.DataBean.DatasetBean, BaseViewHolder> {
    private final int flag;

    public RechargeRecordAdapter(int layoutResId, List<ChargeList.DataBean.DatasetBean> data, int flag) {
        super(layoutResId, data);
        this.flag = flag;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ChargeList.DataBean.DatasetBean item) {
        TextView tv_item_rechargerecord_price = helper.getView(R.id.tv_item_rechargerecord_price);
        TextView tv_item_rechargerecord_name = helper.getView(R.id.tv_item_rechargerecord_name);
        TextView tv_item_rechargerecord_cdl = helper.getView(R.id.tv_item_rechargerecord_cdl);
        TextView tv_item_rechargerecord_kssj = helper.getView(R.id.tv_item_rechargerecord_kssj);
        TextView tv_item_rechargerecord_jssj = helper.getView(R.id.tv_item_rechargerecord_jssj);
        TextView tv_item_rechargerecord_cdsj = helper.getView(R.id.tv_item_rechargerecord_cdsj);
        TextView tv_item_rechargerecord_couponprice = helper.getView(R.id.tv_item_rechargerecord_couponprice);
        LinearLayout ll_item_rechargerecord_root = helper.getView(R.id.ll_item_rechargerecord_root);
        LinearLayout ll_item_rechargerecord = helper.getView(R.id.ll_item_rechargerecord);
        if (item != null) {
            if (helper.getLayoutPosition() == 0) {
                RecyclerView.LayoutParams layoutParams =
                        (RecyclerView.LayoutParams) ll_item_rechargerecord_root.getLayoutParams();
                layoutParams.topMargin = DensityUtil.dp2px(mContext, 15);
                ll_item_rechargerecord_root.setLayoutParams(layoutParams);
                if (flag == 1) {
                    ll_item_rechargerecord.setBackgroundResource(R.drawable.bg_round_blue);
                } else {
                    ll_item_rechargerecord.setBackgroundResource(R.drawable.bg_round_white);
                }
            }
            StringUtil.setText(tv_item_rechargerecord_price, "¥" + item.getTotalPrice(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_rechargerecord_couponprice, "", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_rechargerecord_name, item.getStationName(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_rechargerecord_cdl, "充电量：" + item.getTotalPower() + "度", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_rechargerecord_kssj, "开始时间：" + item.getStartTime(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_rechargerecord_jssj, "结束时间：" + item.getEndTime(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_rechargerecord_cdsj, "充电时间：" + item.getTotalTime() + "分钟", "", View.VISIBLE, View.VISIBLE);
        }
    }
}