package com.haotang.easyshare.mvp.view.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerRefundResultActivityCommponent;
import com.haotang.easyshare.di.module.activity.RefundResultActivityModule;
import com.haotang.easyshare.mvp.presenter.RefundResultPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.iview.IRefundResultView;
import com.haotang.easyshare.util.SystemUtil;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;

public class RefundResultActivity extends BaseActivity<RefundResultPresenter> implements IRefundResultView {
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.tv_refund_result_desc)
    TextView tvRefundResultDesc;
    @BindView(R.id.tv_refund_result_lxkf)
    TextView tv_refund_result_lxkf;
    private String phone;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_refund_result;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerRefundResultActivityCommponent.builder().
                refundResultActivityModule(new RefundResultActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("申请退款");
        tv_refund_result_lxkf.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tv_refund_result_lxkf.getPaint().setAntiAlias(true);//抗锯齿
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_refund_result_lxkf})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_refund_result_lxkf:
                SystemUtil.cellPhone(RefundResultActivity.this, phone);
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
