package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.ljy.devring.other.RingLog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.RequestCallback;
import cn.jpush.im.android.api.model.DeviceInfo;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * JChat测试界面
 */
public class JChatTestActivity extends BaseActivity {

    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_jchat_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("JChat测试界面");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.iv_titlebar_back, R.id.btn_test_jchat_login, R.id.btn_test_jchat_chat})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_test_jchat_chat:
                startActivity(new Intent(JChatTestActivity.this,ChatActivity.class));
                break;
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.btn_test_jchat_login:
                UserInfo myInfo = JMessageClient.getMyInfo();
                if (myInfo != null) {//说明已登录
                    RingLog.e("myInfo = " + myInfo.toString());
                } else {
                    JMessageClient.register(AppConfig.XUJUN_USERNAME, AppConfig.XUJUN_PASSWORD, new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String registerDesc) {
                            if (responseCode == 0) {
                                RingLog.e("注册成功");
                                JMessageClient.login(AppConfig.XUJUN_USERNAME, AppConfig.XUJUN_PASSWORD, new RequestCallback<List<DeviceInfo>>() {
                                    @Override
                                    public void gotResult(int responseCode, String responseMessage, List<DeviceInfo> result) {
                                        if (responseCode == 0) {
                                            RingLog.e("登陆成功");
                                            RingLog.e("JMessageClient.login" + ", responseCode = " + responseCode + " ; LoginDesc = " + responseMessage);
                                            RingLog.e("result = " + result.toString());
                                        } else {
                                            RingLog.e("登陆失败 JMessageClient.login" + ", responseCode = " + responseCode + " ; LoginDesc = " + responseMessage);
                                        }
                                    }
                                });
                            } else {
                                RingLog.e("注册失败 JMessageClient.register " + ", responseCode = " + responseCode + " ; registerDesc = " + registerDesc);
                                if (responseCode == 898001) {
                                    JMessageClient.login(AppConfig.XUJUN_USERNAME, AppConfig.XUJUN_PASSWORD, new RequestCallback<List<DeviceInfo>>() {
                                        @Override
                                        public void gotResult(int responseCode, String responseMessage, List<DeviceInfo> result) {
                                            if (responseCode == 0) {
                                                RingLog.e("登陆成功");
                                                RingLog.e("JMessageClient.login" + ", responseCode = " + responseCode + " ; LoginDesc = " + responseMessage);
                                                RingLog.e("result = " + result.toString());
                                            } else {
                                                RingLog.e("登陆失败 JMessageClient.login" + ", responseCode = " + responseCode + " ; LoginDesc = " + responseMessage);
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }
                break;
        }
    }
}
