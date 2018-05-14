package com.haotang.easyshare.mvp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.HistoricalMsg;
import com.haotang.easyshare.mvp.view.widget.NoScollFullLinearLayoutManager;
import com.haotang.easyshare.util.DensityUtil;

import java.util.List;

import static android.R.attr.data;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/5/1 13:26
 */
public class HistoricalMessagelAdapter extends BaseQuickAdapter<List<HistoricalMsg.DataBean>, BaseViewHolder> {
    public HistoricalMessagelAdapter(int layoutResId, List<List<HistoricalMsg.DataBean>> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, List<HistoricalMsg.DataBean> item) {
        RecyclerView rv_item_problem = helper.getView(R.id.rv_item_problem);
        LinearLayout ll_item_problem_root = helper.getView(R.id.ll_item_problem_root);
        if (item != null) {
            if (helper.getLayoutPosition() == 0) {
                RecyclerView.LayoutParams layoutParams =
                        (RecyclerView.LayoutParams) ll_item_problem_root.getLayoutParams();
                layoutParams.topMargin = DensityUtil.dp2px(mContext, 15);
                ll_item_problem_root.setLayoutParams(layoutParams);
            }
            if (item != null && item.size() > 0) {
                rv_item_problem.setHasFixedSize(true);
                rv_item_problem.setNestedScrollingEnabled(false);
                NoScollFullLinearLayoutManager noScollFullLinearLayoutManager = new NoScollFullLinearLayoutManager(mContext);
                noScollFullLinearLayoutManager.setScrollEnabled(false);
                rv_item_problem.setLayoutManager(noScollFullLinearLayoutManager);
                rv_item_problem.setAdapter(new HistoryMsgItemAdapter(R.layout.item_historymsg_item, item));
            }
        }
    }
}
