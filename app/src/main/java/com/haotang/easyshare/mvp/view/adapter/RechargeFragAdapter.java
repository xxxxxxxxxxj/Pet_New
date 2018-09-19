package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.HistoryList;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/25 12:14
 */
public class RechargeFragAdapter extends BaseQuickAdapter<HistoryList.DataBean.DatasetBean, BaseViewHolder> {
    public RechargeFragAdapter(int layoutResId, List<HistoryList.DataBean.DatasetBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final HistoryList.DataBean.DatasetBean item) {
        TextView tv_item_rechargefrag_price = helper.getView(R.id.tv_item_rechargefrag_price);
        TextView tv_item_rechargefrag_name = helper.getView(R.id.tv_item_rechargefrag_name);
        TextView tv_item_rechargefrag_time = helper.getView(R.id.tv_item_rechargefrag_time);
        if (item != null) {
            StringUtil.setText(tv_item_rechargefrag_name, item.getTitle(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_rechargefrag_time, item.getCreateTime(), "", View.VISIBLE, View.VISIBLE);
            if (item.getAmount() >= 0) {
                StringUtil.setText(tv_item_rechargefrag_price, "+" + item.getAmount(), "", View.VISIBLE, View.VISIBLE);
                tv_item_rechargefrag_price.setTextColor(mContext.getResources().getColor(R.color.aD0021B));
            } else {
                StringUtil.setText(tv_item_rechargefrag_price, "" + item.getAmount(), "", View.VISIBLE, View.VISIBLE);
                tv_item_rechargefrag_price.setTextColor(mContext.getResources().getColor(R.color.a333333));
            }
        }
    }
}