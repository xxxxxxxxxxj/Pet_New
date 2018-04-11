package com.haotang.newpet.mvp.view.activity;

import android.os.Bundle;

import com.haotang.newpet.R;
import com.haotang.newpet.mvp.model.entity.table.MovieCollect;
import com.haotang.newpet.mvp.view.activity.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends BaseActivity {

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

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
}
