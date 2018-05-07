package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerCarInfoActivityCommponent;
import com.haotang.easyshare.di.module.activity.CarInfoActivityModule;
import com.haotang.easyshare.mvp.presenter.CarInfoPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.iview.ICarInfoView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.ljy.devring.DevRing;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 车辆信息界面
 */
public class CarInfoActivity extends BaseActivity<CarInfoPresenter> implements ICarInfoView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.iv_titlebar_back)
    ImageView ivTitlebarBack;
    @BindView(R.id.tv_titlebar_other)
    TextView tvTitlebarOther;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.tv_carinfo_qcpp)
    TextView tvCarinfoQcpp;
    @BindView(R.id.iv_carinfo_qcpp_right)
    ImageView ivCarinfoQcppRight;
    @BindView(R.id.rl_carinfo_qcpp)
    RelativeLayout rlCarinfoQcpp;
    @BindView(R.id.tv_carinfo_cx)
    TextView tvCarinfoCx;
    @BindView(R.id.iv_carinfo_cx_right)
    ImageView ivCarinfoCxRight;
    @BindView(R.id.rl_carinfo_cx)
    RelativeLayout rlCarinfoCx;
    @BindView(R.id.et_cph_qcpp)
    EditText et_cph_qcpp;
    @BindView(R.id.rl_carinfo_cph)
    RelativeLayout rlCarinfoCph;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_car_info;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerCarInfoActivityCommponent.builder().carInfoActivityModule(new CarInfoActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("车辆信息");
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
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @OnClick({R.id.iv_titlebar_back, R.id.rl_carinfo_qcpp, R.id.rl_carinfo_cx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.rl_carinfo_qcpp:
                break;
            case R.id.rl_carinfo_cx:
                break;
        }
    }
}
