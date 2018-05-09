package com.haotang.easyshare.mvp.view.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundLinearLayout;
import com.haotang.easyshare.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/9 15:25
 */
public class AddChargeBoDa {
    @BindView(R.id.iv_addcharge_bottom_bg)
    ImageView ivAddchargeBottomBg;
    @BindView(R.id.tv_addcharge_bottom_other)
    TextView tvAddchargeBottomOther;
    @BindView(R.id.iv_addcharge_bottom_close)
    ImageView ivAddchargeBottomClose;
    @BindView(R.id.tv_addcharge_bottom_title)
    TextView tvAddchargeBottomTitle;
    @BindView(R.id.iv_addcharge_bottom_wx)
    ImageView ivAddchargeBottomWx;
    @BindView(R.id.iv_addcharge_bottom_wxicon)
    ImageView ivAddchargeBottomWxicon;
    @BindView(R.id.rl_addcharge_bottom_wx)
    RelativeLayout rlAddchargeBottomWx;
    @BindView(R.id.iv_addcharge_bottom_zfb)
    ImageView ivAddchargeBottomZfb;
    @BindView(R.id.iv_addcharge_bottom_zfbicon)
    ImageView ivAddchargeBottomZfbicon;
    @BindView(R.id.rl_addcharge_bottom_zfb)
    RelativeLayout rlAddchargeBottomZfb;
    @BindView(R.id.ll_addcharge_bottom_selectpayway)
    LinearLayout llAddchargeBottomSelectpayway;
    @BindView(R.id.ll_addcharge_bottom_selecttime)
    LinearLayout llAddchargeBottomSelecttime;
    @BindView(R.id.rll_addcharge_bottom)
    RoundLinearLayout rllAddchargeBottom;

    public ImageView getIvAddchargeBottomBg() {
        return ivAddchargeBottomBg;
    }

    public void setIvAddchargeBottomBg(ImageView ivAddchargeBottomBg) {
        this.ivAddchargeBottomBg = ivAddchargeBottomBg;
    }

    public TextView getTvAddchargeBottomOther() {
        return tvAddchargeBottomOther;
    }

    public void setTvAddchargeBottomOther(TextView tvAddchargeBottomOther) {
        this.tvAddchargeBottomOther = tvAddchargeBottomOther;
    }

    public ImageView getIvAddchargeBottomClose() {
        return ivAddchargeBottomClose;
    }

    public void setIvAddchargeBottomClose(ImageView ivAddchargeBottomClose) {
        this.ivAddchargeBottomClose = ivAddchargeBottomClose;
    }

    public TextView getTvAddchargeBottomTitle() {
        return tvAddchargeBottomTitle;
    }

    public void setTvAddchargeBottomTitle(TextView tvAddchargeBottomTitle) {
        this.tvAddchargeBottomTitle = tvAddchargeBottomTitle;
    }

    public ImageView getIvAddchargeBottomWx() {
        return ivAddchargeBottomWx;
    }

    public void setIvAddchargeBottomWx(ImageView ivAddchargeBottomWx) {
        this.ivAddchargeBottomWx = ivAddchargeBottomWx;
    }

    public ImageView getIvAddchargeBottomWxicon() {
        return ivAddchargeBottomWxicon;
    }

    public void setIvAddchargeBottomWxicon(ImageView ivAddchargeBottomWxicon) {
        this.ivAddchargeBottomWxicon = ivAddchargeBottomWxicon;
    }

    public RelativeLayout getRlAddchargeBottomWx() {
        return rlAddchargeBottomWx;
    }

    public void setRlAddchargeBottomWx(RelativeLayout rlAddchargeBottomWx) {
        this.rlAddchargeBottomWx = rlAddchargeBottomWx;
    }

    public ImageView getIvAddchargeBottomZfb() {
        return ivAddchargeBottomZfb;
    }

    public void setIvAddchargeBottomZfb(ImageView ivAddchargeBottomZfb) {
        this.ivAddchargeBottomZfb = ivAddchargeBottomZfb;
    }

    public ImageView getIvAddchargeBottomZfbicon() {
        return ivAddchargeBottomZfbicon;
    }

    public void setIvAddchargeBottomZfbicon(ImageView ivAddchargeBottomZfbicon) {
        this.ivAddchargeBottomZfbicon = ivAddchargeBottomZfbicon;
    }

    public RelativeLayout getRlAddchargeBottomZfb() {
        return rlAddchargeBottomZfb;
    }

    public void setRlAddchargeBottomZfb(RelativeLayout rlAddchargeBottomZfb) {
        this.rlAddchargeBottomZfb = rlAddchargeBottomZfb;
    }

    public LinearLayout getLlAddchargeBottomSelectpayway() {
        return llAddchargeBottomSelectpayway;
    }

    public void setLlAddchargeBottomSelectpayway(LinearLayout llAddchargeBottomSelectpayway) {
        this.llAddchargeBottomSelectpayway = llAddchargeBottomSelectpayway;
    }

    public LinearLayout getLlAddchargeBottomSelecttime() {
        return llAddchargeBottomSelecttime;
    }

    public void setLlAddchargeBottomSelecttime(LinearLayout llAddchargeBottomSelecttime) {
        this.llAddchargeBottomSelecttime = llAddchargeBottomSelecttime;
    }

    public RoundLinearLayout getRllAddchargeBottom() {
        return rllAddchargeBottom;
    }

    public void setRllAddchargeBottom(RoundLinearLayout rllAddchargeBottom) {
        this.rllAddchargeBottom = rllAddchargeBottom;
    }

    public AddChargeBoDa(View headerRootView) {
        ButterKnife.bind(this, headerRootView);
    }
}
