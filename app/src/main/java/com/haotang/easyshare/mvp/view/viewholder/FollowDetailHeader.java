package com.haotang.easyshare.mvp.view.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haotang.easyshare.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/4 12:55
 */
public class FollowDetailHeader {
    @BindView(R.id.iv_followdetail_top_bg)
    ImageView ivFollowdetailTopBg;
    @BindView(R.id.iv_followdetail_top_userimg)
    ImageView ivFollowdetailTopUserimg;
    @BindView(R.id.iv_followdetail_top_username)
    TextView ivFollowdetailTopUsername;
    @BindView(R.id.iv_followdetail_top_userpf)
    TextView ivFollowdetailTopUserpf;
    @BindView(R.id.iv_followdetail_top_vipjf)
    TextView ivFollowdetailTopVipjf;
    @BindView(R.id.iv_followdetail_top_guanzhu)
    ImageView ivFollowdetailTopGuanzhu;
    @BindView(R.id.iv_followdetail_top_pingjia)
    ImageView ivFollowdetailTopPingjia;

    public ImageView getIvFollowdetailTopBg() {
        return ivFollowdetailTopBg;
    }

    public void setIvFollowdetailTopBg(ImageView ivFollowdetailTopBg) {
        this.ivFollowdetailTopBg = ivFollowdetailTopBg;
    }

    public ImageView getIvFollowdetailTopUserimg() {
        return ivFollowdetailTopUserimg;
    }

    public void setIvFollowdetailTopUserimg(ImageView ivFollowdetailTopUserimg) {
        this.ivFollowdetailTopUserimg = ivFollowdetailTopUserimg;
    }

    public TextView getIvFollowdetailTopUsername() {
        return ivFollowdetailTopUsername;
    }

    public void setIvFollowdetailTopUsername(TextView ivFollowdetailTopUsername) {
        this.ivFollowdetailTopUsername = ivFollowdetailTopUsername;
    }

    public TextView getIvFollowdetailTopUserpf() {
        return ivFollowdetailTopUserpf;
    }

    public void setIvFollowdetailTopUserpf(TextView ivFollowdetailTopUserpf) {
        this.ivFollowdetailTopUserpf = ivFollowdetailTopUserpf;
    }

    public TextView getIvFollowdetailTopVipjf() {
        return ivFollowdetailTopVipjf;
    }

    public void setIvFollowdetailTopVipjf(TextView ivFollowdetailTopVipjf) {
        this.ivFollowdetailTopVipjf = ivFollowdetailTopVipjf;
    }

    public ImageView getIvFollowdetailTopGuanzhu() {
        return ivFollowdetailTopGuanzhu;
    }

    public void setIvFollowdetailTopGuanzhu(ImageView ivFollowdetailTopGuanzhu) {
        this.ivFollowdetailTopGuanzhu = ivFollowdetailTopGuanzhu;
    }

    public ImageView getIvFollowdetailTopPingjia() {
        return ivFollowdetailTopPingjia;
    }

    public void setIvFollowdetailTopPingjia(ImageView ivFollowdetailTopPingjia) {
        this.ivFollowdetailTopPingjia = ivFollowdetailTopPingjia;
    }

    public FollowDetailHeader(View headerRootView) {
        ButterKnife.bind(this, headerRootView);
    }
}
