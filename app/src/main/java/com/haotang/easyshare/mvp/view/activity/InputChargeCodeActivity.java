package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.activity.DaggerInputChargeCodeActivityCommponent;
import com.haotang.easyshare.di.module.activity.InputChargeCodeActivityModule;
import com.haotang.easyshare.mvp.model.entity.event.StartCodeChargeing;
import com.haotang.easyshare.mvp.model.entity.res.StartChargeing;
import com.haotang.easyshare.mvp.presenter.InputChargeCodePresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.iview.IInputChargeCodeView;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 输入电桩号界面
 */
public class InputChargeCodeActivity extends BaseActivity<InputChargeCodePresenter> implements IInputChargeCodeView {

    @BindView(R.id.tv_inputcharge_code_desc)
    TextView tvInputchargeCodeDesc;
    @BindView(R.id.et_inputcharge_code)
    EditText etInputchargeCode;
    @BindView(R.id.tv_inputcharge_code_btn1)
    TextView tvInputchargeCodeBtn1;
    @BindView(R.id.tv_inputcharge_code_btn2)
    TextView tvInputchargeCodeBtn2;
    @BindView(R.id.ll_inputcharge_code_play)
    LinearLayout llInputchargeCodePlay;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_input_charge_code;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerInputChargeCodeActivityCommponent.builder().
                inputChargeCodeActivityModule(new InputChargeCodeActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        llInputchargeCodePlay.bringToFront();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

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
        SystemUtil.goneJP(this);
        activityListManager.removeActivity(this); //退出activity
    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_inputcharge_code_btn1, R.id.tv_inputcharge_code_btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_inputcharge_code_btn1:
                startActivity(new Intent(InputChargeCodeActivity.this, ScanCodeActivity.class));
                finish();
                break;
            case R.id.tv_inputcharge_code_btn2:
                if (StringUtil.isEmpty(etInputchargeCode.getText().toString().trim())) {
                    RingToast.show("请输入终端编号");
                    return;
                }
                showDialog();
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("code", etInputchargeCode.getText().toString().trim());
                RequestBody body = builder.build();
                mPresenter.start(UrlConstants.getMapHeader(this),body);
                break;
        }
    }

    @Override
    public void startFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "saveFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
        RingToast.show(msg);
        finish();
    }

    @Override
    public void startSuccess(StartChargeing.DataBean data) {
        disMissDialog();
        if (data != null) {
            DevRing.busManager().postEvent(new StartCodeChargeing(data.getOrderId(),data.getTimeout()));
            finish();
        }
    }
}
