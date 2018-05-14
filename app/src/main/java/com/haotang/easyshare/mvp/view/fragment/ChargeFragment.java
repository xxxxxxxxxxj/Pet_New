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

import butterknife.BindView;
import butterknife.OnClick;

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
public class ChargeFragment extends BaseFragment {
    @BindView(R.id.tv_myfragment_cdcs)
    TextView tvMyfragmentCdcs;
    @BindView(R.id.iv_myfragment_img)
    ImageView ivMyfragmentImg;
    @BindView(R.id.rl_myfragment_img)
    RelativeLayout rlMyfragmentImg;
    @BindView(R.id.tv_myfragment_bjcdz)
    TextView tvMyfragmentBjcdz;
    @BindView(R.id.iv_myfragment_ggorgr)
    ImageView ivMyfragmentGgorgr;
    @BindView(R.id.tv_myfragment_name)
    TextView tvMyfragmentName;
    @BindView(R.id.tv_myfragment_cdf)
    TextView tvMyfragmentCdf;
    @BindView(R.id.tv_myfragment_fwf)
    TextView tvMyfragmentFwf;
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
    protected void initView() {
        Bundle arguments = getArguments();
        HomeBean.StationsBean stationsBean = arguments.getParcelable("stationsBean");
        if (stationsBean != null) {
            uuid = stationsBean.getUuid();
            tvMyfragmentCdcs.bringToFront();
            StringUtil.setText(tvMyfragmentName, stationsBean.getTitle(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentCdcs, "充电" + stationsBean.getTimes() + "次", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentCdf, "充电费：" + stationsBean.getElectricityPrice() + "元/度", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvMyfragmentFwf, "服务费：" + stationsBean.getServiceFee() + "元/度", "", View.VISIBLE, View.VISIBLE);
            GlideUtil.loadNetImg(mActivity, stationsBean.getHeadImg(), ivMyfragmentImg, R.mipmap.ic_image_load);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.tv_myfragment_bjcdz, R.id.rl_chargefrag_root})
    public void onViewClicked(View view) {
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
