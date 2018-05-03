package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundLinearLayout;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.HistoricalMessage;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/5/1 13:26
 */
public class HistoricalMessagelAdapter extends BaseQuickAdapter<HistoricalMessage, BaseViewHolder> {
    public HistoricalMessagelAdapter(int layoutResId, List<HistoricalMessage> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoricalMessage item) {
        TextView tv_problem_date = helper.getView(R.id.tv_problem_date);
        TextView tv_problem = helper.getView(R.id.tv_problem);
        TextView tv_reply_date = helper.getView(R.id.tv_reply_date);
        TextView tv_reply = helper.getView(R.id.tv_reply);
        RoundLinearLayout rll_item_historymsg = helper.getView(R.id.rll_item_historymsg);
        if (item != null) {
            if(helper.getLayoutPosition() == 0){
                LinearLayout.LayoutParams layoutParams =
                        (LinearLayout.LayoutParams) rll_item_historymsg.getLayoutParams();
                layoutParams.topMargin = DensityUtil.dp2px(mContext,15);
                rll_item_historymsg.setLayoutParams(layoutParams);
            }
            StringUtil.setText(tv_problem_date, item.getProblemDate(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_problem, item.getProblem(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_reply_date, item.getReplyDate(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_reply, item.getReply(), "", View.VISIBLE, View.VISIBLE);
        }
    }
}
