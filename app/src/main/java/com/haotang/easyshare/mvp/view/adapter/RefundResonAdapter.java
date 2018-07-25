package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.RefundReson;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

import static com.haotang.easyshare.R.id.tv_itemrecharge_temp;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/25 14:30
 */
public class RefundResonAdapter extends BaseQuickAdapter<RefundReson, BaseViewHolder> {
    public RefundResonAdapter(int layoutResId, List<RefundReson> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RefundReson item) {
        TextView tv_item_refund_reson = helper.getView(R.id.tv_item_refund_reson);
        if (item != null) {
            StringUtil.setText(tv_item_refund_reson, item.getName(), "", View.VISIBLE, View.VISIBLE);
            if (item.isSelect()) {
                tv_item_refund_reson.setTextColor(mContext.getResources().getColor(R.color.white));
                tv_item_refund_reson.setBackgroundResource(R.mipmap.bg_btn);
            } else {
                tv_item_refund_reson.setTextColor(mContext.getResources().getColor(R.color.a666666));
                tv_item_refund_reson.setBackgroundResource(R.drawable.bg_round_ddd);
            }
        }
    }
}