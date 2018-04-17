package com.haotang.deving.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.haotang.deving.R;
import com.haotang.deving.mvp.view.activity.base.BaseActivity;
import com.ljy.devring.DevRing;

import butterknife.OnClick;

/**
 * 测试类
 */
public class TestActivity extends BaseActivity {
    protected final static String TAG = TestActivity.class.getSimpleName();
    @Override
    protected int getContentLayout() {
        return R.layout.activity_test;
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

    @OnClick({R.id.btn_test_webview, R.id.btn_test_share, R.id.btn_test_wxlogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_test_webview:
                startActivity(new Intent(this, WebViewActivity.class).putExtra("url_key", "https://www.duba.com/?f=liebao"));
                break;
            case R.id.btn_test_share:
                break;
            case R.id.btn_test_wxlogin:
                break;
        }
    }
}
