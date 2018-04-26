package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.util.CountdownUtil;
import com.ljy.devring.DevRing;

/**
 * 充电桩详情
 */
public class ChargingPileDetailActivity extends BaseActivity {
    private final static String TAG = ChargingPileDetailActivity.class.getSimpleName();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_charging_pile_detail;
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
