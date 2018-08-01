package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.RechargeTemp;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.RechargeTempAdapter;
import com.haotang.easyshare.mvp.view.widget.GridSpacingItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.SharedPreferenceUtil;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 动画测试
 */
public class AnimationsActivity extends BaseActivity {
    protected final static String TAG = AnimationsActivity.class.getSimpleName();
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.iv_animations)
    ImageView ivAnimations;
    @BindView(R.id.rv_animations)
    RecyclerView rvAnimations;
    private List<RechargeTemp> rechargeTempList = new ArrayList<RechargeTemp>();
    private RechargeTempAdapter rechargeTempAdapter;
    private static final float BASE_TIME = 1500;
    private static final float MIN_DISTANCE = 40;
    private static final float MAX_DISTANCE = 160;
    int screenWidth, screenHeight;
    private int sy;
    private int distance;
    private int dy;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_animations;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        Display dis = this.getWindowManager().getDefaultDisplay();
        screenWidth = dis.getWidth();
        screenHeight = dis.getHeight();
        tvTitlebarTitle.setText("动画");
        rvAnimations.setHasFixedSize(true);
        rvAnimations.setNestedScrollingEnabled(false);
        NoScollFullGridLayoutManager noScollFullGridLayoutManager = new
                NoScollFullGridLayoutManager(rvAnimations, this, 3, GridLayoutManager.VERTICAL, false);
        noScollFullGridLayoutManager.setScrollEnabled(false);
        rvAnimations.setLayoutManager(noScollFullGridLayoutManager);
        rvAnimations.addItemDecoration(new GridSpacingItemDecoration(3,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                true));
        for (int i = 0; i < 10; i++) {
            rechargeTempList.add(new RechargeTemp("充10000", false));
        }
        rechargeTempAdapter = new RechargeTempAdapter(R.layout.item_recharge_temp
                , rechargeTempList);
        rvAnimations.setAdapter(rechargeTempAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        rvAnimations.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // event.getRawX(); //获取手指第一次接触屏幕在x方向的坐标
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:// 获取手指第一次接触屏幕
                        sy = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:// 手指在屏幕上移动对应的事件
                        int y = (int) event.getRawY();
                        // 获取手指移动的距离
                        dy = y - sy;
                        int top = v.getTop() + dy;
                        int left = v.getLeft();
                        if (top <= 0) {
                            top = 0;
                        }
                        if (top >= DensityUtil.dp2px(AnimationsActivity.this, MAX_DISTANCE)) {
                            top = DensityUtil.dp2px(AnimationsActivity.this, MAX_DISTANCE);
                        }
                        if (top <= DensityUtil.dp2px(AnimationsActivity.this, MIN_DISTANCE)) {
                            top = DensityUtil.dp2px(AnimationsActivity.this, MIN_DISTANCE);
                        }
                        // 更改imageView在窗体的位置
                        rvAnimations.layout(left, top, left + rvAnimations.getWidth(), top + rvAnimations.getHeight());
                        // 获取移动后的位置
                        sy = (int) event.getRawY();
                        distance = top;
                        break;
                    case MotionEvent.ACTION_UP:// 手指离开屏幕对应事件
                        RingLog.e("sy = " + sy);
                        RingLog.e("MAX_DISTANCE = " + DensityUtil.dp2px(AnimationsActivity.this, MAX_DISTANCE));
                        RingLog.e("dy = " + dy);
                        RingLog.e("distance = " + distance);
                        if (distance > DensityUtil.dp2px(AnimationsActivity.this, MIN_DISTANCE) &&
                                distance <= DensityUtil.dp2px(AnimationsActivity.this, MAX_DISTANCE)) {
                            final Animation translateAnimation1 = new TranslateAnimation(0, 0, 0, (DensityUtil.dp2px(AnimationsActivity.this, MIN_DISTANCE) - distance));
                            float v1 = (distance - DensityUtil.dp2px(AnimationsActivity.this, MIN_DISTANCE)) * BASE_TIME / (DensityUtil.dp2px(AnimationsActivity.this, (MAX_DISTANCE - MIN_DISTANCE)));
                            RingLog.e("v1 = " + v1);
                            translateAnimation1.setFillAfter(true);
                            translateAnimation1.setDuration((long) v1);

                            final Animation translateAnimation2 = new TranslateAnimation(0, 0, 0, 10);
                            translateAnimation2.setInterpolator(new CycleInterpolator(5));
                            translateAnimation2.setDuration(1000);

                            final Animation translateAnimation3 = new TranslateAnimation(0, 10, 0, 10);
                            translateAnimation3.setInterpolator(new CycleInterpolator(5));
                            translateAnimation3.setDuration(1000);

                            rvAnimations.setAnimation(translateAnimation1);


                            translateAnimation1.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    rvAnimations.setAnimation(translateAnimation2);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                            translateAnimation2.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    ivAnimations.setAnimation(translateAnimation3);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }
                        break;
                }
                return true;// 不会中断触摸事件的返回
            }
        });
    }

    @OnClick({R.id.iv_titlebar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }
}
