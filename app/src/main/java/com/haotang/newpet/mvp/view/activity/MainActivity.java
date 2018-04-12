package com.haotang.newpet.mvp.view.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.haotang.newpet.R;
import com.haotang.newpet.mvp.model.entity.table.MovieCollect;
import com.haotang.newpet.mvp.view.activity.base.BaseActivity;
import com.ljy.devring.DevRing;
import com.ljy.devring.util.RingToast;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindString;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {
    @BindString(R.string.exit_confirm)
    String mStrExitConfirm;
    private long mExitTime;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        DevRing.cacheManager().spCache().put("guide", true);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Subscribe
    public void onDeleteMovie(MovieCollect movieCollect) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (System.currentTimeMillis() - mExitTime > 2000) {
                    RingToast.show(mStrExitConfirm);
                    mExitTime = System.currentTimeMillis();
                } else {
                    DevRing.activityStackManager().exitApplication();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }
}
