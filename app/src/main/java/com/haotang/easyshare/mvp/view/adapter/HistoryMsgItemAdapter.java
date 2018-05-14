package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.HistoricalMsg;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/14 15:47
 */
class HistoryMsgItemAdapter extends BaseQuickAdapter<HistoricalMsg.DataBean, BaseViewHolder> {
    public HistoryMsgItemAdapter(int layoutResId, List<HistoricalMsg.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoricalMsg.DataBean item) {
        TextView tv_problem_askorpro = helper.getView(R.id.tv_problem_askorpro);
        TextView tv_problem_date = helper.getView(R.id.tv_problem_date);
        TextView tv_problem = helper.getView(R.id.tv_problem);
        View vw_problem = helper.getView(R.id.vw_problem);
        if (item != null) {
            if (item.getSource() == 1) {//提问
                if (mData.size() > 1) {
                    vw_problem.setVisibility(View.VISIBLE);
                } else {
                    vw_problem.setVisibility(View.GONE);
                }
                StringUtil.setText(tv_problem_askorpro, "我的问题", "", View.VISIBLE, View.VISIBLE);
            } else if (item.getSource() == 2) {//回答
                vw_problem.setVisibility(View.GONE);
                StringUtil.setText(tv_problem_askorpro, "管家回复", "", View.VISIBLE, View.VISIBLE);
            }
            StringUtil.setText(tv_problem_date, item.getCreateTime(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_problem, item.getContent(), "", View.VISIBLE, View.VISIBLE);
        }
    }
}