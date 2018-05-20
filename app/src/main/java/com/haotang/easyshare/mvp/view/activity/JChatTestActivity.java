package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
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
    private UserInfo mChateUserInfo;

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
                Intent intent = new Intent(JChatTestActivity.this, ChatActivity.class);
                //创建会话
                intent.putExtra("username", mChateUserInfo.getUserName());
                intent.putExtra("appkey", mChateUserInfo.getAppKey());
                String notename = mChateUserInfo.getNotename();
                if (TextUtils.isEmpty(notename)) {
                    notename = mChateUserInfo.getNickname();
                    if (TextUtils.isEmpty(notename)) {
                        notename = mChateUserInfo.getUserName();
                    }
                }
                intent.putExtra("notename", notename);
                startActivity(intent);
                break;
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.btn_test_jchat_login:
                UserInfo myInfo = JMessageClient.getMyInfo();
                if (myInfo != null) {//说明已登录
                    //登陆成功获取要聊天的人的信息
                    RingLog.e("myInfo = " + myInfo.toString());
                    JMessageClient.getUserInfo(AppConfig.XUJUN_USERNAME, new GetUserInfoCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage, UserInfo info) {
                            if (responseCode == 0) {
                                mChateUserInfo = info;
                                RingLog.e("mChateUserInfo = " + mChateUserInfo.toString());
                            }
                        }
                    });
                } else {
                    JMessageClient.register(AppConfig.ZHOUJUNXIA_USERNAME, AppConfig.ZHOUJUNXIA_PASSWORD, new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String registerDesc) {
                            if (responseCode == 0) {
                                RingLog.e("注册成功");
                                JMessageClient.login(AppConfig.ZHOUJUNXIA_USERNAME, AppConfig.ZHOUJUNXIA_PASSWORD, new RequestCallback<List<DeviceInfo>>() {
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
                                    JMessageClient.login(AppConfig.ZHOUJUNXIA_USERNAME, AppConfig.ZHOUJUNXIA_PASSWORD, new RequestCallback<List<DeviceInfo>>() {
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
