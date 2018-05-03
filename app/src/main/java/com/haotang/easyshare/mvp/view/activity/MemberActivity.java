package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.ljy.devring.DevRing;

/**
 * 会员特权界面
 */
public class MemberActivity extends BaseActivity {

    @Override
    protected int getContentLayout() {
        return R.layout.activity_member;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
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
