package com.haotang.easyshare.mvp.view.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.roundview.RoundLinearLayout;
import com.haotang.easyshare.R;
import com.haotang.easyshare.materialratingbar.MaterialRatingBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/8 11:24
 */
public class FollowDetailBoDa {
    @BindView(R.id.iv_followdetail_bottom_bg)
    ImageView iv_followdetail_bottom_bg;
    @BindView(R.id.iv_followdetail_bottom_close)
    ImageView ivFollowdetailBottomClose;
    @BindView(R.id.mrb_followdetail_bottom)
    MaterialRatingBar mrbFollowdetailBottom;
    @BindView(R.id.tv_followdetail_bottom_desc)
    TextView tvFollowdetailBottomDesc;
    @BindView(R.id.rll_followdetail_bottom)
    RoundLinearLayout rll_followdetail_bottom;
    @BindView(R.id.tv_followdetail_bottom_submit)
    TextView tv_followdetail_bottom_submit;

    public TextView getTv_followdetail_bottom_submit() {
        return tv_followdetail_bottom_submit;
    }

    public void setTv_followdetail_bottom_submit(TextView tv_followdetail_bottom_submit) {
        this.tv_followdetail_bottom_submit = tv_followdetail_bottom_submit;
    }

    public ImageView getIv_followdetail_bottom_bg() {
        return iv_followdetail_bottom_bg;
    }

    public void setIv_followdetail_bottom_bg(ImageView iv_followdetail_bottom_bg) {
        this.iv_followdetail_bottom_bg = iv_followdetail_bottom_bg;
    }

    public RoundLinearLayout getRll_followdetail_bottom() {
        return rll_followdetail_bottom;
    }

    public void setRll_followdetail_bottom(RoundLinearLayout rll_followdetail_bottom) {
        this.rll_followdetail_bottom = rll_followdetail_bottom;
    }

    public ImageView getIvFollowdetailBottomClose() {
        return ivFollowdetailBottomClose;
    }

    public void setIvFollowdetailBottomClose(ImageView ivFollowdetailBottomClose) {
        this.ivFollowdetailBottomClose = ivFollowdetailBottomClose;
    }

    public MaterialRatingBar getMrbFollowdetailBottom() {
        return mrbFollowdetailBottom;
    }

    public void setMrbFollowdetailBottom(MaterialRatingBar mrbFollowdetailBottom) {
        this.mrbFollowdetailBottom = mrbFollowdetailBottom;
    }

    public TextView getTvFollowdetailBottomDesc() {
        return tvFollowdetailBottomDesc;
    }

    public void setTvFollowdetailBottomDesc(TextView tvFollowdetailBottomDesc) {
        this.tvFollowdetailBottomDesc = tvFollowdetailBottomDesc;
    }

    public FollowDetailBoDa(View headerRootView) {
        ButterKnife.bind(this, headerRootView);
    }
}
