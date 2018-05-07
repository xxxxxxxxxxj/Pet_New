package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerAddChargeActivityCommponent;
import com.haotang.easyshare.di.module.activity.AddChargeActivityModule;
import com.haotang.easyshare.mvp.presenter.AddChargePresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.iview.IAddChargeView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.ljy.devring.DevRing;

import javax.inject.Inject;

/**
 * 添加充电站界面
 */
public class AddChargeActivity extends BaseActivity<AddChargePresenter> implements IAddChargeView {
    @Inject
    PermissionDialog permissionDialog;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_add_charge;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerAddChargeActivityCommponent.builder().
                addChargeActivityModule(new AddChargeActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {

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
}
