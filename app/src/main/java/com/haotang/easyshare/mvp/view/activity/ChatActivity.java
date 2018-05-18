package com.haotang.easyshare.mvp.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.chat.keyboard.XhsEmoticonsKeyBoard;
import com.haotang.easyshare.chat.keyboard.widget.EmoticonsEditText;
import com.haotang.easyshare.chat.listview.DropDownListView;
import com.haotang.easyshare.chat.view.ChatView;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.MessageReceiptStatusChangeEvent;
import cn.jpush.im.android.api.event.MessageRetractEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.android.eventbus.EventBus;

/**
 * 聊天页面
 */
public class ChatActivity extends BaseActivity {
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.lv_chat)
    DropDownListView lvChat;
    @BindView(R.id.jmui_at_me_btn)
    Button jmuiAtMeBtn;
    @BindView(R.id.ek_bar)
    XhsEmoticonsKeyBoard ekBar;
    private String username;
    private String appkey;
    private String notename;

    public static final String JPG = ".jpg";
    private static String MsgIDs = "msgIDs";

    private String mTitle;
    private boolean mLongClick = false;

    private static final String MEMBERS_COUNT = "membersCount";
    private static final String GROUP_NAME = "groupName";

    public static final String TARGET_ID = "targetId";
    public static final String TARGET_APP_KEY = "targetAppKey";
    private static final String DRAFT = "draft";
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    public static final int REQUEST_CODE_SELECT = 100;
    private ChatView mChatView;
    private boolean mIsSingle = true;
    private Conversation mConv;
    private String mTargetId;
    private String mTargetAppKey;
    private Activity mContext;
    private ChattingListAdapter mChatAdapter;
    int maxImgCount = 9;
    private List<UserInfo> mAtList;
    private long mGroupId;
    private static final int REFRESH_LAST_PAGE = 0x1023;
    private static final int REFRESH_CHAT_TITLE = 0x1024;
    private static final int REFRESH_GROUP_NAME = 0x1025;
    private static final int REFRESH_GROUP_NUM = 0x1026;
    private Dialog mDialog;

    private GroupInfo mGroupInfo;
    private UserInfo mMyInfo;
    private static final String GROUP_ID = "groupId";
    private int mAtMsgId;
    private int mAtAllMsgId;
    private int mUnreadMsgCnt;
    private boolean mShowSoftInput = false;
    private List<UserInfo> forDel = new ArrayList<>();

    Window mWindow;
    InputMethodManager mImm;
    private final UIHandler mUIHandler = new UIHandler(this);
    private boolean mAtAll = false;

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
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    private void initChatData() {
        SimpleCommonUtils.initEmoticonsEditText(ekBar.getEtChat());
        mMyInfo = JMessageClient.getMyInfo();
        StringUtil.setText(tvTitlebarTitle, notename, "", View.VISIBLE, View.VISIBLE);
        mConv = JMessageClient.getSingleConversation(mTargetId, mTargetAppKey);
        if (mConv == null) {
            mConv = Conversation.createSingleConversation(mTargetId, mTargetAppKey);
        }
        mChatAdapter = new ChattingListAdapter(mContext, mConv, longClickListener);
        mChatView.setChatListAdapter(mChatAdapter);
        mChatView.getListView().setOnDropDownListener(new DropDownListView.OnDropDownListener() {
            @Override
            public void onDropDown() {
                mUIHandler.sendEmptyMessageDelayed(REFRESH_LAST_PAGE, 1000);
            }
        });
        mChatView.setToBottom();
        mChatView.setConversation(mConv);
    }

    private void initChatView() {
        initEmoticonsKeyBoardBar();
        initListView();

        ekBar.getEtChat().addTextChangedListener(new TextWatcher() {
            private CharSequence temp = "";

            @Override
            public void afterTextChanged(Editable arg0) {
                if (temp.length() > 0) {
                    mLongClick = false;
                }

                if (mAtList != null && mAtList.size() > 0) {
                    for (UserInfo info : mAtList) {
                        String name = info.getDisplayName();

                        if (!arg0.toString().contains("@" + name + " ")) {
                            forDel.add(info);
                        }
                    }
                    mAtList.removeAll(forDel);
                }

                if (!arg0.toString().contains("@所有成员 ")) {
                    mAtAll = false;
                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
                if (s.length() > 0 && after >= 1 && s.subSequence(start, start + 1).charAt(0) == '@' && !mLongClick) {
                    if (null != mConv && mConv.getType() == ConversationType.group) {
                        ChooseAtMemberActivity.show(ChatActivity.this, ekBar.getEtChat(), mConv.getTargetId());
                    }
                }
            }
        });
    }

    private void initEmoticonsKeyBoardBar() {
        ekBar.setAdapter(SimpleCommonUtils.getCommonAdapter(this, emoticonClickListener));
        ekBar.addOnFuncKeyBoardListener(this);
        SimpleAppsGridView gridView = new SimpleAppsGridView(this);
        ekBar.addFuncView(gridView);

        ekBar.getEtChat().setOnSizeChangedListener(new EmoticonsEditText.OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int w, int h, int oldw, int oldh) {
                scrollToBottom();
            }
        });
        //发送按钮
        ekBar.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mcgContent = ekBar.getEtChat().getText().toString();
                scrollToBottom();
                if (mcgContent.equals("")) {
                    return;
                }
                Message msg;
                TextContent content = new TextContent(mcgContent);
                if (mAtAll) {
                    msg = mConv.createSendMessageAtAllMember(content, null);
                    mAtAll = false;
                } else if (null != mAtList) {
                    msg = mConv.createSendMessage(content, mAtList, null);
                } else {
                    msg = mConv.createSendMessage(content);
                }
                //设置需要已读回执
                MessageSendingOptions options = new MessageSendingOptions();
                options.setNeedReadReceipt(true);
                JMessageClient.sendMessage(msg, options);
                mChatAdapter.addMsgFromReceiptToList(msg);
                ekBar.getEtChat().setText("");
                if (mAtList != null) {
                    mAtList.clear();
                }
                if (forDel != null) {
                    forDel.clear();
                }
            }
        });
        //切换语音输入
        ekBar.getVoiceOrText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.btn_voice_or_text) {
                    ekBar.setVideoText();
                    ekBar.getBtnVoice().initConv(mConv, mChatAdapter, mChatView);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnBtn();
    }

    private void returnBtn() {
        mConv.resetUnreadCount();
        dismissSoftInput();
        JMessageClient.exitConversation();
        //发送保存为草稿事件到会话列表
        EventBus.getDefault().post(new Event.Builder().setType(EventType.draft)
                .setConversation(mConv)
                .setDraft(ekBar.getEtChat().getText().toString())
                .build());
        finish();
    }

    private void dismissSoftInput() {
        if (mShowSoftInput) {
            if (mImm != null) {
                mImm.hideSoftInputFromWindow(ekBar.getEtChat().getWindowToken(), 0);
                mShowSoftInput = false;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
