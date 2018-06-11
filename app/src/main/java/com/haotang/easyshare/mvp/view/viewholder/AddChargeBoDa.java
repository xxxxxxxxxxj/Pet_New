package com.haotang.easyshare.mvp.view.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.contrarywind.view.WheelView;
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
    @BindView(R.id.rl_addcharge_bottom_selecttime)
    RelativeLayout rlAddchargeBottomSelecttime;
    @BindView(R.id.rll_addcharge_bottom)
    RoundLinearLayout rllAddchargeBottom;
    @BindView(R.id.wv_addcharge_bottom_starttime)
    WheelView wv_addcharge_bottom_starttime;
    @BindView(R.id.wv_addcharge_bottom_endtime)
    WheelView wv_addcharge_bottom_endtime;
    @BindView(R.id.iv_addcharge_bottom_xj)
    ImageView iv_addcharge_bottom_xj;
    @BindView(R.id.rl_addcharge_bottom_xj)
    RelativeLayout rl_addcharge_bottom_xj;

    public ImageView getIv_addcharge_bottom_xj() {
        return iv_addcharge_bottom_xj;
    }

    public void setIv_addcharge_bottom_xj(ImageView iv_addcharge_bottom_xj) {
        this.iv_addcharge_bottom_xj = iv_addcharge_bottom_xj;
    }

    public RelativeLayout getRl_addcharge_bottom_xj() {
        return rl_addcharge_bottom_xj;
    }

    public void setRl_addcharge_bottom_xj(RelativeLayout rl_addcharge_bottom_xj) {
        this.rl_addcharge_bottom_xj = rl_addcharge_bottom_xj;
    }

    public WheelView getWv_addcharge_bottom_starttime() {
        return wv_addcharge_bottom_starttime;
    }

    public void setWv_addcharge_bottom_starttime(WheelView wv_addcharge_bottom_starttime) {
        this.wv_addcharge_bottom_starttime = wv_addcharge_bottom_starttime;
    }

    public WheelView getWv_addcharge_bottom_endtime() {
        return wv_addcharge_bottom_endtime;
    }

    public void setWv_addcharge_bottom_endtime(WheelView wv_addcharge_bottom_endtime) {
        this.wv_addcharge_bottom_endtime = wv_addcharge_bottom_endtime;
    }

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

    public RelativeLayout getRlAddchargeBottomSelecttime() {
        return rlAddchargeBottomSelecttime;
    }

    public void setRlAddchargeBottomSelecttime(RelativeLayout rlAddchargeBottomSelecttime) {
        this.rlAddchargeBottomSelecttime = rlAddchargeBottomSelecttime;
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
