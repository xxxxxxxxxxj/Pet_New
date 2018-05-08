package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerBrandAreaActivityCommponent;
import com.haotang.easyshare.di.module.activity.BrandAreaActivityModule;
import com.haotang.easyshare.mvp.presenter.BrandAreaPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.iview.IBrandAreaView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.ljy.devring.DevRing;

import javax.inject.Inject;

/**
 * 品牌专区
 */
public class BrandAreaActivity extends BaseActivity<BrandAreaPresenter> implements IBrandAreaView {
    @Inject
    PermissionDialog permissionDialog;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_brand_area;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerBrandAreaActivityCommponent.builder().
                brandAreaActivityModule(new BrandAreaActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @Override
    protected void initEvent() {

    }
}
