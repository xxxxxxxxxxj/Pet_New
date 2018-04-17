package com.haotang.deving.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.haotang.deving.R;
import com.haotang.deving.mvp.view.activity.base.BaseActivity;
import com.haotang.deving.mvp.view.widget.ShareBottomDialog;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;

import butterknife.OnClick;
import me.shaohui.shareutil.LoginUtil;
import me.shaohui.shareutil.login.LoginListener;
import me.shaohui.shareutil.login.LoginPlatform;
import me.shaohui.shareutil.login.LoginResult;
import me.shaohui.shareutil.login.result.BaseToken;

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
                ShareBottomDialog dialog = new ShareBottomDialog();
                dialog.show(getSupportFragmentManager());
                break;
            case R.id.btn_test_wxlogin:
                LoginUtil.login(TestActivity.this, LoginPlatform.WX, new LoginListener() {
                    @Override
                    public void loginSuccess(LoginResult result) {
                        RingLog.d(TAG, result.getUserInfo().getNickname());
                        RingLog.d(TAG, "登录成功");
                    }

                    @Override
                    public void beforeFetchUserInfo(BaseToken token) {
                        RingLog.d(TAG, "获取用户信息");
                    }

                    @Override
                    public void loginFailure(Exception e) {
                        e.printStackTrace();
                        RingLog.d(TAG, "登录失败");
                    }

                    @Override
                    public void loginCancel() {
                        RingLog.d(TAG, "登录取消");
                    }
                });
                break;
        }
    }
}
