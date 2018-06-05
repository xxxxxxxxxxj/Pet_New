package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerMemberActivityCommponent;
import com.haotang.easyshare.di.module.activity.MemberActivityModule;
import com.haotang.easyshare.mvp.presenter.MemberPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.iview.IMemberView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.ljy.devring.DevRing;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

/**
 * 会员特权界面
 */
public class MemberActivity extends BaseActivity<MemberPresenter> implements IMemberView {
    @Inject
    PermissionDialog permissionDialog;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_member;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerMemberActivityCommponent.builder().memberActivityModule(new MemberActivityModule(this, this)).build().inject(this);
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
        activityListManager.removeActivity(this); //退出activity
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
