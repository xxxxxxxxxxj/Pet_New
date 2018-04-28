package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.roundview.RoundTextView;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.CommentTag;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/4/28 21:54
 */
class CommentDetailTagAdapter extends BaseQuickAdapter<CommentTag, BaseViewHolder> {
    public CommentDetailTagAdapter(int layoutResId, List<CommentTag> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentTag item) {
        RoundTextView rtv_item_comment_tag = helper.getView(R.id.rtv_item_comment_tag);
        if (item != null) {
            StringUtil.setText(rtv_item_comment_tag, item.getTag(), "", View.VISIBLE, View.VISIBLE);
            if (item.isCheck()) {
                rtv_item_comment_tag.setTextColor(mContext.getResources().getColor(R.color.a0271F0));
                rtv_item_comment_tag.getDelegate().setStrokeColor(mContext.getResources().getColor(R.color.a0271F0));
            } else {
                rtv_item_comment_tag.setTextColor(mContext.getResources().getColor(R.color.a999999));
                rtv_item_comment_tag.getDelegate().setStrokeColor(mContext.getResources().getColor(R.color.a999999));
            }
        }
    }
}
