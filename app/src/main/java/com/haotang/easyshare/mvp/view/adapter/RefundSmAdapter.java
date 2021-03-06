package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/25 14:39
 */
public class RefundSmAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public RefundSmAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView tv_item_refund_sm = helper.getView(R.id.tv_item_refund_sm);
        StringUtil.setText(tv_item_refund_sm, item, "", View.VISIBLE, View.VISIBLE);
    }
}