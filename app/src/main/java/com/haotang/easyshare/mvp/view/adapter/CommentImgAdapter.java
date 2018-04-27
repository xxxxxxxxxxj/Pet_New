package com.haotang.easyshare.mvp.view.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.CommentImg;
import com.haotang.easyshare.mvp.view.activity.PhotoViewPagerActivity;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/27 15:22
 */
public class CommentImgAdapter extends BaseQuickAdapter<CommentImg, BaseViewHolder> {
    public CommentImgAdapter(int layoutResId, List<CommentImg> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentImg item) {
        ImageView iv_item_comment_img = helper.getView(R.id.iv_item_comment_img);
        if (item != null) {
            if (item.isAdd()) {
                iv_item_comment_img.setImageResource(R.mipmap.icon_img_add);
            } else {
                Glide.with(mContext).load(item.getImgUrl()).error(R.mipmap.ic_image_load)
                        .placeholder(R.mipmap.ic_image_load).into(iv_item_comment_img);
            }
        }
    }
}
