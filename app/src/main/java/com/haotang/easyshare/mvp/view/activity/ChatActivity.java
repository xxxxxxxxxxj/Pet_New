package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.MessageReceiptStatusChangeEvent;
import cn.jpush.im.android.api.event.MessageRetractEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

/**
 * 聊天页面
 */
public class ChatActivity extends BaseActivity {
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    private String username;
    private String appkey;
    private String notename;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        //注册sdk的event用于接收各种event事件
        JMessageClient.registerEventReceiver(this);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        appkey = intent.getStringExtra("appkey");
        notename = intent.getStringExtra("notename");
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        StringUtil.setText(tvTitlebarTitle,notename,"",View.VISIBLE,View.VISIBLE);
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

    @OnClick({R.id.iv_titlebar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
        }
    }

    /**
     * 收到消息
     *
     * @param event
     */
    public void onEvent(MessageEvent event) {
        final Message message = event.getMessage();
        if (message != null) {
            RingLog.e("onEvent(MessageEvent event) message = " + message.toString());
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (message.getTargetType() == ConversationType.single) {
                    UserInfo userInfo = (UserInfo) message.getTargetInfo();
                    String targetId = userInfo.getUserName();
                    String appKey = userInfo.getAppKey();
                }
            }
        });
    }

    /**
     * 收到消息
     *
     * @param event
     */
    public void onEventMainThread(MessageRetractEvent event) {
        Message retractedMessage = event.getRetractedMessage();
        if (retractedMessage != null) {
            RingLog.e("retractedMessage = " + retractedMessage.toString());
        }
    }

    /**
     * 当在聊天界面断网再次连接时收离线事件刷新
     */
    public void onEvent(OfflineMessageEvent event) {
        Conversation conv = event.getConversation();
        if (conv != null) {
            RingLog.e("conv = " + conv.toString());
        }
        if (conv.getType().equals(ConversationType.single)) {
            UserInfo userInfo = (UserInfo) conv.getTargetInfo();
            String targetId = userInfo.getUserName();
            String appKey = userInfo.getAppKey();
        }
    }

    /**
     * 消息已读事件
     */
    public void onEventMainThread(MessageReceiptStatusChangeEvent event) {
        List<MessageReceiptStatusChangeEvent.MessageReceiptMeta> messageReceiptMetas = event.getMessageReceiptMetas();
        if (messageReceiptMetas != null && messageReceiptMetas.size() > 0) {
            RingLog.e("messageReceiptMetas = " + messageReceiptMetas.toString());
        }
        for (MessageReceiptStatusChangeEvent.MessageReceiptMeta meta : messageReceiptMetas) {
            long serverMsgId = meta.getServerMsgId();
            int unReceiptCnt = meta.getUnReceiptCnt();
        }
    }
}
