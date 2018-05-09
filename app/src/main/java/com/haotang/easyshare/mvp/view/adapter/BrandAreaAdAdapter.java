package com.haotang.easyshare.mvp.view.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.BrandAreaBean;
import com.haotang.easyshare.mvp.model.entity.res.CommentImg;
import com.haotang.easyshare.util.GlideUtil;

import java.util.List;

import static com.haotang.easyshare.R.id.iv_item_comment_img;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/9 12:58
 */
class BrandAreaAdAdapter extends BaseQuickAdapter<BrandAreaBean.AdBean, BaseViewHolder> {
    public BrandAreaAdAdapter(int layoutResId, List<BrandAreaBean.AdBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrandAreaBean.AdBean item) {
        ImageView iv_item_brandareaad_img = helper.getView(R.id.iv_item_brandareaad_img);
        if (item != null) {
            GlideUtil.loadNetImg(mContext,item.getImg(),iv_item_brandareaad_img,R.mipmap.ic_image_load);
        }
    }
}