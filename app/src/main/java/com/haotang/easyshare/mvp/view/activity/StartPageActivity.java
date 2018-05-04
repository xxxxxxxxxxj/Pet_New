package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.drawee.view.SimpleDraweeView;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.util.CountdownUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 广告页
 */
public class StartPageActivity extends BaseActivity {
    @BindView(R.id.iv_landingpage)
    SimpleDraweeView ivLandingpage;
    @BindView(R.id.btn_landing_tg)
    Button btnLandingTg;
    private String img_url;
    private String jump_url;
    private String backup;
    private int point;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_start_page;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSwipeBackEnable(false);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        SystemUtil.hideBottomUIMenu(this);
        img_url = getIntent().getStringExtra("img_url");
        jump_url = getIntent().getStringExtra("jump_url");
        backup = getIntent().getStringExtra("backup");
        point = getIntent().getIntExtra("point", 0);
        RingLog.d(TAG, "img_url = " + img_url);
        DevRing.imageManager().loadNet(img_url, ivLandingpage);
        CountdownUtil.getInstance().newTimer(3000, 1000, new CountdownUtil.ICountDown() {
            @Override
            public void onTick(long millisUntilFinished) {
                btnLandingTg.setText("跳过  " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(StartPageActivity.this, MainActivity.class));
                finish();
            }
        }, "STARTPAGE_TIMER");
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
        CountdownUtil.getInstance().cancel("STARTPAGE_TIMER");
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @OnClick({R.id.btn_landing_tg, R.id.iv_landingpage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_landing_tg:
                startActivity(new Intent(StartPageActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.iv_landingpage:
                startActivity(new Intent(StartPageActivity.this, MainActivity.class));
                finish();
                break;
        }
    }
}
