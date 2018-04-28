package com.haotang.easyshare.mvp.view.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.CommentImg;
import com.haotang.easyshare.util.SystemUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/4/28 21:55
 */
class CommentDetailImgAdapter extends BaseQuickAdapter<CommentImg, BaseViewHolder> {
    private List<String> pathList = new ArrayList<String>();

    public CommentDetailImgAdapter(int layoutResId, List<CommentImg> data) {
        super(layoutResId, data);
        pathList.clear();
        for (int i = 0; i < mData.size(); i++) {
            pathList.add(mData.get(i).getImgUrl());
        }
    }

    @Override
    protected void convert(final BaseViewHolder helper, CommentImg item) {
        ImageView iv_item_comment_img = helper.getView(R.id.iv_item_comment_img);
        if (item != null) {
            if (item.isAdd()) {
                iv_item_comment_img.setImageResource(R.mipmap.icon_img_add);
            } else {
                Glide.with(mContext).load(item.getImgUrl()).error(R.mipmap.ic_image_load)
                        .placeholder(R.mipmap.ic_image_load).into(iv_item_comment_img);
            }
            iv_item_comment_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SystemUtil.goPhotoView(mContext, helper.getLayoutPosition(), pathList, false);
                }
            });
        }
    }
}
