package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerMyBalanceActivityCommponent;
import com.haotang.easyshare.di.module.activity.MyBalanceActivityModule;
import com.haotang.easyshare.mvp.presenter.MyBalancePresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.MainActivityPagerAdapter;
import com.haotang.easyshare.mvp.view.fragment.RechargeFragment;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IMyBalanceView;
import com.haotang.easyshare.mvp.view.widget.RefundPopupWindow;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MyBalanceActivity extends BaseActivity<MyBalancePresenter> implements IMyBalanceView {
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.ll_titlebar_other)
    LinearLayout ll_titlebar_other;
    @BindView(R.id.stl_my_balance)
    SlidingTabLayout stlMyBalance;
    @BindView(R.id.vp_my_balance)
    ViewPager vpMyBalance;
    private String[] mTitles = {"全部记录", "消费明细", "充值记录"};
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();
    private int currentTabIndex;
    private RefundPopupWindow refundPopupWindow;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_my_balance;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerMyBalanceActivityCommponent.builder().
                myBalanceActivityModule(new MyBalanceActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("我的余额");
        ll_titlebar_other.setVisibility(View.VISIBLE);
        for (int i = 0; i < mTitles.length; i++) {
            RechargeFragment rechargeFragment = new RechargeFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            rechargeFragment.setArguments(bundle);
            mFragments.add(rechargeFragment);
        }
        vpMyBalance.setAdapter(new MainActivityPagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
        vpMyBalance.setOffscreenPageLimit(mTitles.length);
        stlMyBalance.setViewPager(vpMyBalance);
        stlMyBalance.setCurrentTab(currentTabIndex);
        vpMyBalance.setCurrentItem(currentTabIndex);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
    }

    @OnClick({R.id.iv_titlebar_back, R.id.ll_titlebar_other, R.id.btn_my_balance_ljcz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.ll_titlebar_other:
                showPop();
                break;
            case R.id.btn_my_balance_ljcz:
                startActivity(new Intent(this, RechargeActivity.class));
                break;
        }
    }

    private void showPop() {
        dismissPop();
        refundPopupWindow = new RefundPopupWindow(this, onClickListener);
        refundPopupWindow.showAsDropDown(ll_titlebar_other, -130, -50);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_pop_mybalance:
                    startActivity(new Intent(MyBalanceActivity.this, RefundActivity.class));
                    break;
            }
        }
    };

    private void dismissPop() {
        if (refundPopupWindow != null) {
            if (refundPopupWindow.isShowing()) {
                refundPopupWindow.dismiss();
            }
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
