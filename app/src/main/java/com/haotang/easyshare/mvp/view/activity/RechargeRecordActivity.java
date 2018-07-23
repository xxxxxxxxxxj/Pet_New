package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerRechargeRecordActivityCommponent;
import com.haotang.easyshare.di.module.activity.RechargeRecordActivityModule;
import com.haotang.easyshare.mvp.presenter.RechargeRecordPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.iview.IRechargeRecordView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充电记录界面
 */
public class RechargeRecordActivity extends BaseActivity<RechargeRecordPresenter> implements IRechargeRecordView {

    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_recharge_record;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerRechargeRecordActivityCommponent.builder().
                rechargeRecordActivityModule(new RechargeRecordActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("充电记录");
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
        activityListManager.removeActivity(this); //退出activity
    }

    @OnClick(R.id.iv_titlebar_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
        }
    }
}
