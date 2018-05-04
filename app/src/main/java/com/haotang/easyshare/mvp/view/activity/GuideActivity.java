package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.GuideAdapter;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 引导页
 */
public class GuideActivity extends BaseActivity {
    @BindView(R.id.vp_guide_pager)
    ViewPager vpGuidePager;
    @BindView(R.id.ib_guide_next)
    ImageButton ibGuideNext;
    private int[] imagesIds;
    private ArrayList<ImageView> imageList;
    private GuideAdapter adapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setSwipeBackEnable(false);
        DevRing.activityStackManager().pushOneActivity(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        SystemUtil.hideBottomUIMenu(this);
        imagesIds = new int[]{R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3};
        imageList = new ArrayList<ImageView>();
        for (int i = 0; i < imagesIds.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setImageBitmap(SystemUtil.readResToBitMap(this, imagesIds[i]));
            imageList.add(iv);
        }
        adapter = new GuideAdapter(this, imageList);
        vpGuidePager.setAdapter(adapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        vpGuidePager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (position == imagesIds.length - 1) {
                    ibGuideNext.setVisibility(View.VISIBLE);
                } else {
                    ibGuideNext.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @OnClick({R.id.ib_guide_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_guide_next:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                DevRing.activityStackManager().exitActivity(this); //退出activity
                break;
        }
    }
}
