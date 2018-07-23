package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerRechargeActivityCommponent;
import com.haotang.easyshare.di.module.activity.RechargeActivityModule;
import com.haotang.easyshare.mvp.presenter.RechargePresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.iview.IRechargeView;
import com.ljy.devring.util.RingToast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充值界面
 */
public class RechargeActivity extends BaseActivity<RechargePresenter> implements IRechargeView {
    @BindView(R.id.iv_titlebar_back)
    ImageView ivTitlebarBack;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_recharge_temp)
    RecyclerView rvRechargeTemp;
    @BindView(R.id.iv_recharge_wx)
    ImageView ivRechargeWx;
    @BindView(R.id.iv_recharge_zfb)
    ImageView ivRechargeZfb;
    private int payWay;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerRechargeActivityCommponent.builder().
                rechargeActivityModule(new RechargeActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("立即充值");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }

    private void setPayWay(int flag) {
        if (flag == 1) {//微信
            payWay = 1;
            ivRechargeWx.setImageResource(R.mipmap.icon_addcharge_select);
            ivRechargeZfb.setImageResource(R.mipmap.icon_addcharge_unselect);
        } else if (flag == 2) {//支付宝
            payWay = 2;
            ivRechargeWx.setImageResource(R.mipmap.icon_addcharge_unselect);
            ivRechargeZfb.setImageResource(R.mipmap.icon_addcharge_select);
        }
    }

    @OnClick({R.id.iv_titlebar_back, R.id.rl_recharge_wx, R.id.rl_recharge_zfb, R.id.btn_recharge_ljcz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.rl_recharge_wx:
                setPayWay(1);
                break;
            case R.id.rl_recharge_zfb:
                setPayWay(2);
                break;
            case R.id.btn_recharge_ljcz:
                if (payWay > 0) {

                } else {
                    RingToast.show("请选择支付方式");
                }
                break;
        }
    }
}
