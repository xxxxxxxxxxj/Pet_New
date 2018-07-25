package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.RechargeTemp;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/25 10:02
 */
public class RechargeTempAdapter extends BaseQuickAdapter<RechargeTemp, BaseViewHolder> {
    public RechargeTempAdapter(int layoutResId, List<RechargeTemp> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeTemp item) {
        TextView tv_itemrecharge_temp = helper.getView(R.id.tv_itemrecharge_temp);
        if (item != null) {
            StringUtil.setText(tv_itemrecharge_temp, item.getName(), "", View.VISIBLE, View.VISIBLE);
            if (item.isSelect()) {
                tv_itemrecharge_temp.setTextColor(mContext.getResources().getColor(R.color.a0271F0));
                tv_itemrecharge_temp.setBackgroundResource(R.drawable.bg_round_027);
            } else {
                tv_itemrecharge_temp.setTextColor(mContext.getResources().getColor(R.color.a666666));
                tv_itemrecharge_temp.setBackgroundResource(R.drawable.bg_round_ddd);
            }
        }
    }
}