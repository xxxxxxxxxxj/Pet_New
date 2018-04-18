package com.haotang.deving.shareutil;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.haotang.deving.mvp.view.activity.base.BaseActivity;
import com.haotang.deving.util.CountdownUtil;
import com.ljy.devring.DevRing;

/**
 * Created by shaohui on 2016/11/19.
 */

public class _ShareActivity extends BaseActivity {
    private int mType;
    private boolean isNew;
    private static final String TYPE = "share_activity_type";

    public static Intent newInstance(Context context, int type) {
        Intent intent = new Intent(context, _ShareActivity.class);
        if (context instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        intent.putExtra(TYPE, type);
        return intent;
    }

    @Override
    protected int getContentLayout() {
        return 0;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        ShareLogger.i(ShareLogger.INFO.ACTIVITY_CREATE);
        isNew = true;
        // init data
        mType = getIntent().getIntExtra(TYPE, 0);
        if (mType == ShareUtil.TYPE) {
            // 分享
            ShareUtil.action(this);
        } else if (mType == LoginUtil.TYPE) {
            // 登录
            LoginUtil.action(this);
        } else {
            // handle 微信回调
            LoginUtil.handleResult(-1, -1, getIntent());
            ShareUtil.handleResult(getIntent());
            finish();
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        ShareLogger.i(ShareLogger.INFO.ACTIVITY_RESUME);
        if (isNew) {
            isNew = false;
        } else {
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ShareLogger.i(ShareLogger.INFO.ACTIVITY_NEW_INTENT);
        // 处理回调
        if (mType == LoginUtil.TYPE) {
            LoginUtil.handleResult(0, 0, intent);
        } else if (mType == ShareUtil.TYPE) {
            ShareUtil.handleResult(intent);
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ShareLogger.i(ShareLogger.INFO.ACTIVITY_RESULT);
        // 处理回调
        if (mType == LoginUtil.TYPE) {
            LoginUtil.handleResult(requestCode, resultCode, data);
        } else if (mType == ShareUtil.TYPE) {
            ShareUtil.handleResult(data);
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

}
