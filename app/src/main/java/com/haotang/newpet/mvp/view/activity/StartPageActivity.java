package com.haotang.newpet.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.haotang.newpet.R;
import com.haotang.newpet.mvp.view.activity.base.BaseActivity;
import com.haotang.newpet.util.CountdownUtil;
import com.haotang.newpet.util.SystemUtil;
import com.ljy.devring.DevRing;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 广告页
 */
public class StartPageActivity extends BaseActivity {
    @BindView(R.id.iv_landingpage)
    ImageView ivLandingpage;
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

    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        SystemUtil.hideBottomUIMenu(this);
        img_url = getIntent().getStringExtra("img_url");
        jump_url = getIntent().getStringExtra("jump_url");
        backup = getIntent().getStringExtra("backup");
        point = getIntent().getIntExtra("point", 0);
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
