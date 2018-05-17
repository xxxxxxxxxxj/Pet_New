package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.ljy.devring.DevRing;

import cn.jpush.im.android.api.JMessageClient;

/**
 * 聊天页面
 */
public class ChatActivity extends BaseActivity {

    @Override
    protected int getContentLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        //注册sdk的event用于接收各种event事件
        JMessageClient.registerEventReceiver(this);
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
        //注销消息接收
        JMessageClient.unRegisterEventReceiver(this);
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }
}
