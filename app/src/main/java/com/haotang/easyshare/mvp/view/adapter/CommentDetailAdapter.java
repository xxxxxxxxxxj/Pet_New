package com.haotang.easyshare.mvp.view.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.CommentBean;
import com.haotang.easyshare.mvp.model.entity.res.CommentImg;
import com.haotang.easyshare.mvp.model.entity.res.CommentTag;
import com.haotang.easyshare.mvp.view.widget.GridSpacingItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.other.RingLog;

import java.util.List;

import static com.haotang.easyshare.R.id.ll_item_comment_root;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/28 18:00
 */
public class CommentDetailAdapter extends BaseQuickAdapter<CommentBean, BaseViewHolder> {

    public CommentDetailAdapter(int layoutResId, List<CommentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final CommentBean item) {
        LinearLayout ll_item_comment_root = helper.getView(R.id.ll_item_comment_root);
        ImageView iv_item_comment = helper.getView(R.id.iv_item_comment);
        TextView tv_item_comment_name = helper.getView(R.id.tv_item_comment_name);
        TextView tv_item_comment_date = helper.getView(R.id.tv_item_comment_date);
        TextView tv_item_comment_desc = helper.getView(R.id.tv_item_comment_desc);
        RecyclerView rv_item_comment_tag = helper.getView(R.id.rv_item_comment_tag);
        RecyclerView rv_item_comment_img = helper.getView(R.id.rv_item_comment_img);
        if (helper.getLayoutPosition() == 0) {
            RecyclerView.LayoutParams layoutParams =
                    (RecyclerView.LayoutParams) ll_item_comment_root.getLayoutParams();
            layoutParams.topMargin = DensityUtil.dp2px(mContext,15);
            ll_item_comment_root.setLayoutParams(layoutParams);
        }
        if (item != null) {
            GlideUtil.loadNetCircleImg(mContext, item.getImgUrl(), iv_item_comment, R.mipmap.ic_image_load_circle);
            StringUtil.setText(tv_item_comment_name, item.getName(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_comment_desc, item.getDesc(), "", View.VISIBLE, View.GONE);
            StringUtil.setText(tv_item_comment_date, item.getDate(), "", View.VISIBLE, View.VISIBLE);
            List<CommentTag> tagLIst = item.getTagList();
            List<CommentImg> imgList = item.getImgList();
            if (tagLIst != null && tagLIst.size() > 0) {
                rv_item_comment_tag.setVisibility(View.VISIBLE);
                rv_item_comment_tag.setHasFixedSize(true);
                rv_item_comment_tag.setNestedScrollingEnabled(false);
                NoScollFullGridLayoutManager noScollFullGridLayoutManager1 = new NoScollFullGridLayoutManager(rv_item_comment_tag, mContext, 4, GridLayoutManager.VERTICAL, false);
                noScollFullGridLayoutManager1.setScrollEnabled(false);
                rv_item_comment_tag.setLayoutManager(noScollFullGridLayoutManager1);
                if (rv_item_comment_tag.getItemDecorationCount() <= 0) {
                    rv_item_comment_tag.addItemDecoration(new GridSpacingItemDecoration(4,
                            mContext.getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                            mContext.getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                            false));
                }
                CommentDetailTagAdapter commentDetailTagAdapter = new CommentDetailTagAdapter(R.layout.item_comment_tag, tagLIst);
                rv_item_comment_tag.setAdapter(commentDetailTagAdapter);
            } else {
                rv_item_comment_tag.setVisibility(View.GONE);
            }
            if (imgList != null && imgList.size() > 0) {
                rv_item_comment_img.setVisibility(View.VISIBLE);
                rv_item_comment_img.setHasFixedSize(true);
                rv_item_comment_img.setNestedScrollingEnabled(false);
                NoScollFullGridLayoutManager noScollFullGridLayoutManager = new NoScollFullGridLayoutManager(rv_item_comment_img, mContext, 3, GridLayoutManager.VERTICAL, false);
                noScollFullGridLayoutManager.setScrollEnabled(false);
                rv_item_comment_img.setLayoutManager(noScollFullGridLayoutManager);
                if (rv_item_comment_img.getItemDecorationCount() <= 0) {
                    rv_item_comment_img.addItemDecoration(new GridSpacingItemDecoration(3,
                            mContext.getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                            mContext.getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                            false));
                }
                CommentDetailImgAdapter commentDetailImgAdapter = new CommentDetailImgAdapter(R.layout.item_comment_img
                        , imgList);
                rv_item_comment_img.setAdapter(commentDetailImgAdapter);
            } else {
                rv_item_comment_img.setVisibility(View.GONE);
            }
        }
    }
}