package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.ScreenCarCondition;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

import static com.haotang.easyshare.R.id.tv_itemrecharge_temp;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/26 14:08
 */
public class ScreenCarCategoryAdapter extends BaseQuickAdapter<ScreenCarCondition, BaseViewHolder> {
    public ScreenCarCategoryAdapter(int layoutResId, List<ScreenCarCondition> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ScreenCarCondition item) {
        TextView tv_item_screencar_condition = helper.getView(R.id.tv_item_screencar_condition);
        if (item != null) {
            StringUtil.setText(tv_item_screencar_condition, item.getName(), "", View.VISIBLE, View.VISIBLE);
            if (item.isSelect()) {
                tv_item_screencar_condition.setTextColor(mContext.getResources().getColor(R.color.a0271F0));
                tv_item_screencar_condition.setBackgroundResource(R.drawable.bg_round_027);
            } else {
                tv_item_screencar_condition.setTextColor(mContext.getResources().getColor(R.color.a666666));
                tv_item_screencar_condition.setBackgroundResource(R.drawable.bg_round_f8);
            }
        }
    }
}