package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.HomeBean;
import com.haotang.easyshare.mvp.view.activity.AddChargeActivity;
import com.haotang.easyshare.mvp.view.activity.ChargingPileDetailActivity;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.other.RingLog;

import org.greenrobot.eventbus.Subscribe;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/5/11 13:41
 */

/**
 * 充电桩
 */
public class ChargeFragment extends BaseFragment implements View.OnClickListener {
    private TextView tvMyfragmentCdcs;
    private ImageView ivMyfragmentImg;
    private RelativeLayout rl_chargefrag_root;
    private RelativeLayout rlMyfragmentImg;
    private TextView tvMyfragmentBjcdz;
    private ImageView ivMyfragmentGgorgr;
    private TextView tvMyfragmentName;
    private TextView tvMyfragmentCdf;
    private TextView tvMyfragmentFwf;
    private String uuid;

    public ChargeFragment() {
    }

    @Override
    protected boolean isLazyLoad() {
        return false;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.chargefragment;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe
    public void getChargeData(HomeBean.StationsBean stationsBean) {
        RingLog.e("stationsBean = " + stationsBean);
        if (stationsBean != null) {
            uuid = stationsBean.getUuid();
            tvMyfragmentCdcs.bringToFront();
            if (stationsBean.getIsPrivate() == 0) {//公共
                ivMyfragmentGgorgr.setImageResource(R.mipmap.icon_gg);
            } else if (stationsBean.getIsPrivate() == 1) {//个人
                ivMyfragmentGgorgr.setImageResource(R.mipmap.icon_gr);
            }
            StringUtil.setText(tvMyfragmentName, stationsBean.getTitle(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentCdcs, "充电" + stationsBean.getTimes() + "次", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentCdf, "充电费：" + stationsBean.getElectricityPrice() + "元/度", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentFwf, "服务费：" + stationsBean.getServiceFee() + "元/度", "", View.VISIBLE, View.VISIBLE);
            GlideUtil.loadNetRoundImg(mActivity, stationsBean.getHeadImg(), ivMyfragmentImg, R.mipmap.charge_default_img_round,5);
        }
    }

    @Override
    protected void initView() {
        View view = getmContentView();
        if (view != null) {
            tvMyfragmentCdcs = (TextView) view.findViewById(R.id.tv_myfragment_cdcs);
            ivMyfragmentImg = (ImageView) view.findViewById(R.id.iv_myfragment_img);
            rlMyfragmentImg = (RelativeLayout) view.findViewById(R.id.rl_myfragment_img);
            tvMyfragmentBjcdz = (TextView) view.findViewById(R.id.tv_myfragment_bjcdz);
            ivMyfragmentGgorgr = (ImageView) view.findViewById(R.id.iv_myfragment_ggorgr);
            tvMyfragmentName = (TextView) view.findViewById(R.id.tv_myfragment_name);
            tvMyfragmentCdf = (TextView) view.findViewById(R.id.tv_myfragment_cdf);
            tvMyfragmentFwf = (TextView) view.findViewById(R.id.tv_myfragment_fwf);
            rl_chargefrag_root = (RelativeLayout) view.findViewById(R.id.rl_chargefrag_root);
        }
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        HomeBean.StationsBean stationsBean = arguments.getParcelable("stationsBean");
        if (stationsBean != null) {
            uuid = stationsBean.getUuid();
            tvMyfragmentCdcs.bringToFront();
            if (stationsBean.getIsPrivate() == 0) {//公共
                ivMyfragmentGgorgr.setImageResource(R.mipmap.icon_gg);
            } else if (stationsBean.getIsPrivate() == 1) {//个人
                ivMyfragmentGgorgr.setImageResource(R.mipmap.icon_gr);
            }
            StringUtil.setText(tvMyfragmentName, stationsBean.getTitle(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentCdcs, "充电" + stationsBean.getTimes() + "次", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentCdf, "充电费：" + stationsBean.getElectricityPrice() + "元/度", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentFwf, "服务费：" + stationsBean.getServiceFee() + "元/度", "", View.VISIBLE, View.VISIBLE);
            GlideUtil.loadNetImg(mActivity, stationsBean.getHeadImg(), ivMyfragmentImg, R.mipmap.ic_image_load);
        }
    }

    @Override
    protected void initEvent() {
        tvMyfragmentBjcdz.setOnClickListener(this);
        rl_chargefrag_root.setOnClickListener(this);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_myfragment_bjcdz:
                startActivity(new Intent(mActivity, AddChargeActivity.class).putExtra("uuid", uuid));
                break;
            case R.id.rl_chargefrag_root:
                startActivity(new Intent(mActivity, ChargingPileDetailActivity.class).putExtra("uuid", uuid));
                break;
        }
    }
}
