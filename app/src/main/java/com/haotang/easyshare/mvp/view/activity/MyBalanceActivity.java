package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.activity.DaggerMyBalanceActivityCommponent;
import com.haotang.easyshare.di.module.activity.MyBalanceActivityModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshBalanceEvent;
import com.haotang.easyshare.mvp.model.entity.res.HomeBean;
import com.haotang.easyshare.mvp.model.entity.res.RedeemCodeBean;
import com.haotang.easyshare.mvp.presenter.MyBalancePresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.MainActivityPagerAdapter;
import com.haotang.easyshare.mvp.view.fragment.RechargeFragment;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IMyBalanceView;
import com.haotang.easyshare.mvp.view.widget.RefundPopupWindow;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MyBalanceActivity extends BaseActivity<MyBalancePresenter> implements IMyBalanceView {
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.ll_titlebar_other)
    LinearLayout ll_titlebar_other;
    @BindView(R.id.stl_my_balance)
    SlidingTabLayout stlMyBalance;
    @BindView(R.id.vp_my_balance)
    ViewPager vpMyBalance;
    @BindView(R.id.tv_my_balance_balance)
    TextView tv_my_balance_balance;
    @BindView(R.id.btn_my_balance_dh)
    Button btn_my_balance_dh;
    @BindView(R.id.et_my_balance_dhm)
    EditText et_my_balance_dhm;
    private String[] mTitles = {"全部记录", "充值记录", "消费明细", "兑换记录"};
    private ArrayList<BaseFragment> mFragments = new ArrayList<>();
    private int currentTabIndex;
    private RefundPopupWindow refundPopupWindow;
    private double balance;

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe
    public void RefreshBalance(RefreshBalanceEvent event) {//充值返回
        if (event != null) {
            showDialog();
            mPresenter.home(UrlConstants.getMapHeader(this));
        }
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_my_balance;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        balance = getIntent().getDoubleExtra("balance", 0);
        activityListManager.addActivity(this);
        DaggerMyBalanceActivityCommponent.builder().
                myBalanceActivityModule(new MyBalanceActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tv_my_balance_balance.setText(String.valueOf(balance));
        tvTitlebarTitle.setText("我的余额");
        ll_titlebar_other.setVisibility(View.VISIBLE);
        for (int i = 0; i < mTitles.length; i++) {
            RechargeFragment rechargeFragment = new RechargeFragment();
            Bundle bundle = new Bundle();
            if (i == 1) {
                bundle.putInt("type", 2);
            } else if (i == 2) {
                bundle.putInt("type", 1);
            } else {
                bundle.putInt("type", i);
            }
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

    @OnClick({R.id.iv_titlebar_back, R.id.ll_titlebar_other, R.id.btn_my_balance_ljcz, R.id.btn_my_balance_dh})
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
            case R.id.btn_my_balance_dh:
                if (StringUtil.isEmpty(StringUtil.checkEditText(et_my_balance_dhm))) {
                    RingToast.show("请输入兑换码");
                    SystemUtil.goneJP(this);
                    return;
                }
                showDialog();
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("code", et_my_balance_dhm.getText().toString().trim());
                RequestBody build = builder.build();
                mPresenter.use(UrlConstants.getMapHeader(this), build);
                break;
        }
    }

    private void showPop() {
        dismissPop();
        refundPopupWindow = new RefundPopupWindow(this, onClickListener);
        refundPopupWindow.showAsDropDown(ll_titlebar_other, -10, -30);
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

    @Override
    public void homeSuccess(HomeBean data) {
        disMissDialog();
        if (data != null) {
            balance = data.getBalance();
            tv_my_balance_balance.setText(String.valueOf(balance));
        }
    }

    @Override
    public void homeFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "homeFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }

    @Override
    public void useSuccess(RedeemCodeBean.RedeemCode data) {
        disMissDialog();
        RingToast.show("兑换成功");
        et_my_balance_dhm.setText("");
        DevRing.busManager().postEvent(new RefreshBalanceEvent());
    }

    @Override
    public void useFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "homeFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
        RingToast.show(msg);
    }
}
