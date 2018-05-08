package com.haotang.easyshare.mvp.view.adapter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.materialratingbar.MaterialRatingBar;
import com.haotang.easyshare.mvp.model.entity.res.FollowBean;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/4 10:59
 */
public class FollowListAdapter extends BaseQuickAdapter<FollowBean, BaseViewHolder> {
    public FollowListAdapter(int layoutResId, List<FollowBean> data) {
        super(layoutResId, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void convert(final BaseViewHolder helper, FollowBean item) {
        RelativeLayout rl_item_myfollow_root = helper.getView(R.id.rl_item_myfollow_root);
        TextView tv_item_myfollow_jf = helper.getView(R.id.tv_item_myfollow_jf);
        ImageView iv_item_myfollow_img = helper.getView(R.id.iv_item_myfollow_img);
        TextView tv_item_myfollow_name = helper.getView(R.id.tv_item_myfollow_name);
        TextView tv_item_myfollow_fenshu = helper.getView(R.id.tv_item_myfollow_fenshu);
        MaterialRatingBar mrb_item_myfollow_fenshu = helper.getView(R.id.mrb_item_myfollow_fenshu);
        if (helper.getLayoutPosition() == 0) {
            RecyclerView.LayoutParams layoutParams =
                    (RecyclerView.LayoutParams) rl_item_myfollow_root.getLayoutParams();
            layoutParams.topMargin = DensityUtil.dp2px(mContext, 15);
            rl_item_myfollow_root.setLayoutParams(layoutParams);
        }
        if (item != null) {
            mrb_item_myfollow_fenshu.setNumStars(5);
            mrb_item_myfollow_fenshu.setProgress(item.getStarNum());
            StringUtil.setText(tv_item_myfollow_jf, item.getJifen(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_myfollow_name, item.getName(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_myfollow_fenshu, item.getFenShu(), "", View.VISIBLE, View.VISIBLE);
            GlideUtil.loadNetCircleImg(mContext, item.getImg(), iv_item_myfollow_img, R.mipmap.ic_image_load);
        }
    }
}