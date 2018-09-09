package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerButlerActivityCommponent;
import com.haotang.easyshare.di.module.activity.ButlerActivityModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.presenter.ButlerPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.MainActivityPagerAdapter;
import com.haotang.easyshare.mvp.view.fragment.CurrentMessageFragment;
import com.haotang.easyshare.mvp.view.fragment.HistoricalMessageFragment;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IButlerView;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 私人管家页面
 */
public class ButlerActivity extends BaseActivity<ButlerPresenter> implements IButlerView {
    @BindView(R.id.tv_titlebar_other)
    TextView tvTitlebarOther;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.stl_butler)
    SlidingTabLayout stlButler;
    @BindView(R.id.vp_butler)
    ViewPager vpButler;
    @Inject
    CurrentMessageFragment currentMessageFragment;
    @Inject
    HistoricalMessageFragment historicalMessageFragment;
    private ArrayList<BaseFragment> mFragments = new ArrayList<BaseFragment>();
    private final String[] mTitles = {"当前留言", "历史留言"};
    private int currentTabIndex;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_butler;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe
    public void RefreshFragment(RefreshFragmentEvent refreshFragmentEvent) {
        if (refreshFragmentEvent != null && refreshFragmentEvent.getRefreshIndex() == RefreshFragmentEvent.REFRESH_HISTORYMESSAGEFRAGMET) {
            RingLog.e("REFRESH_HISTORYMESSAGEFRAGMET");
            currentTabIndex = 1;
            vpButler.setCurrentItem(currentTabIndex);
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        //使用Dagger2对本类中相关变量进行初始化
        DaggerButlerActivityCommponent.builder().butlerActivityModule(new ButlerActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarOther.setText("发送");
        tvTitlebarOther.setVisibility(View.VISIBLE);
        tvTitlebarTitle.setText("您的私人管家");
        mFragments.add(currentMessageFragment);
        mFragments.add(historicalMessageFragment);
        vpButler.setAdapter(new MainActivityPagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
        vpButler.setOffscreenPageLimit(2);
        stlButler.setViewPager(vpButler);
        vpButler.setCurrentItem(currentTabIndex);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        vpButler.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                SystemUtil.goneJP(ButlerActivity.this);
                RingLog.e("position = " + position);
                if (position == 0) {
                    tvTitlebarOther.setVisibility(View.VISIBLE);
                } else {
                    tvTitlebarOther.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_titlebar_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_titlebar_other:
                currentMessageFragment.saveMsg();
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
}
