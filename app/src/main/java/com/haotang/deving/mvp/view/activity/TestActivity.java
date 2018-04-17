package com.haotang.deving.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.haotang.deving.R;
import com.haotang.deving.mvp.view.activity.base.BaseActivity;
import com.ljy.devring.DevRing;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 测试类
 */
public class TestActivity extends BaseActivity {

    @BindView(R.id.btn_test_webview)
    Button btnTestWebview;

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

    @OnClick({R.id.btn_test_webview})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_test_webview:
                startActivity(new Intent(this, WebViewActivity.class).putExtra("url_key", "https://www.duba.com/?f=liebao"));
                break;
        }
    }
}
