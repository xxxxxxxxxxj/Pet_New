package com.haotang.easyshare.mvp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.FollowBean;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;

import java.util.List;

import static com.haotang.easyshare.R.id.iv_item_mypost_delete;
import static com.haotang.easyshare.R.id.iv_item_mypost_share;
import static com.haotang.easyshare.R.id.ll_item_mypost_root;

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

    @Override
    protected void convert(final BaseViewHolder helper, FollowBean item) {
        RelativeLayout rl_item_myfollow_root = helper.getView(R.id.rl_item_myfollow_root);
        TextView tv_item_myfollow_jf = helper.getView(R.id.tv_item_myfollow_jf);
        ImageView iv_item_myfollow_img = helper.getView(R.id.iv_item_myfollow_img);
        TextView tv_item_myfollow_name = helper.getView(R.id.tv_item_myfollow_name);
        TextView tv_item_myfollow_fenshu = helper.getView(R.id.tv_item_myfollow_fenshu);
        RecyclerView rv_item_myfollow_star = helper.getView(R.id.rv_item_myfollow_star);
        if (helper.getLayoutPosition() == 0) {
            RecyclerView.LayoutParams layoutParams =
                    (RecyclerView.LayoutParams) rl_item_myfollow_root.getLayoutParams();
            layoutParams.topMargin = DensityUtil.dp2px(mContext, 15);
            rl_item_myfollow_root.setLayoutParams(layoutParams);
        }
        if (item != null) {
            StringUtil.setText(tv_item_myfollow_jf, item.getJifen(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_myfollow_name, item.getName(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tv_item_myfollow_fenshu, item.getFenShu(), "", View.VISIBLE, View.VISIBLE);
            GlideUtil.loadNetCircleImg(mContext, item.getImg(), iv_item_myfollow_img, R.mipmap.ic_image_load);
            if (item.getStarNum() > 0) {
                rv_item_myfollow_star.setVisibility(View.VISIBLE);
            } else {
                rv_item_myfollow_star.setVisibility(View.GONE);
            }
        }
    }
}