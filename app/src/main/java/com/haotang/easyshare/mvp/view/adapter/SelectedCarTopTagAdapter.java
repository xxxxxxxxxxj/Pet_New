package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.model.entity.res.ScreenCarCondition;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/26 16:09
 */
public class SelectedCarTopTagAdapter extends BaseQuickAdapter<ScreenCarCondition, BaseViewHolder> {
    public OnTagDeleteListener onTagDeleteListener = null;

    public interface OnTagDeleteListener {
        public void OnTagDelete(int position);
    }

    public void setOnTagDeleteListener(OnTagDeleteListener onTagDeleteListener) {
        this.onTagDeleteListener = onTagDeleteListener;
    }

    public SelectedCarTopTagAdapter(int layoutResId, List<ScreenCarCondition> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ScreenCarCondition item) {
        TextView tv_item_screencar_toptag = helper.getView(R.id.tv_item_screencar_toptag);
        ImageView tv_item_screencar_toptag_delete = helper.getView(R.id.tv_item_screencar_toptag_delete);
        if (item != null) {
            StringUtil.setText(tv_item_screencar_toptag, item.getName(), "", View.VISIBLE, View.VISIBLE);
            tv_item_screencar_toptag_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTagDeleteListener != null) {
                        onTagDeleteListener.OnTagDelete(helper.getLayoutPosition());
                    }
                }
            });
        }
    }
}