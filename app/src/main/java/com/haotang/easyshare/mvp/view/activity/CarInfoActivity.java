package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerCarInfoActivityCommponent;
import com.haotang.easyshare.di.module.activity.CarInfoActivityModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.MyCarBean;
import com.haotang.easyshare.mvp.presenter.CarInfoPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.iview.ICarInfoView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 车辆信息界面
 */
public class CarInfoActivity extends BaseActivity<CarInfoPresenter> implements ICarInfoView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.et_carinfo_qcpp)
    EditText etCarinfoQcpp;
    @BindView(R.id.et_carinfo_cx)
    EditText etCarinfoCx;
    @BindView(R.id.et_carinfo_cph)
    EditText etCarinfoCph;
    private int id;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_car_info;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerCarInfoActivityCommponent.builder().carInfoActivityModule(new CarInfoActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("车辆信息");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showDialog();
        mPresenter.my();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }

    @OnClick({R.id.iv_titlebar_back, R.id.iv_carinfo_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.iv_carinfo_submit:
                showDialog();
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("brand", etCarinfoQcpp.getText().toString().trim());
                builder.addFormDataPart("car", etCarinfoCx.getText().toString().trim());
                builder.addFormDataPart("number", etCarinfoCph.getText().toString().trim());
                if (id > 0) {
                    builder.addFormDataPart("id", String.valueOf(id));
                }
                RequestBody build = builder.build();
                mPresenter.save(build);
                break;
        }
    }

    @Override
    public void mySuccess(List<MyCarBean.DataBean> data) {
        disMissDialog();
        if (data != null && data.size() > 0) {
            MyCarBean.DataBean dataBean = data.get(0);
            if (dataBean != null) {
                id = dataBean.getId();
                StringUtil.setText(etCarinfoQcpp, dataBean.getBrand(), "", View.VISIBLE, View.VISIBLE);
                StringUtil.setText(etCarinfoCx, dataBean.getCar(), "", View.VISIBLE, View.VISIBLE);
                StringUtil.setText(etCarinfoCph, dataBean.getNumber(), "", View.VISIBLE, View.VISIBLE);
            }
        }
    }

    @Override
    public void myFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "myFail() status = " + code + "---desc = " + msg);
    }

    @Override
    public void saveSuccess(AddChargeBean data) {
        disMissDialog();
        RingLog.e(TAG, "saveSuccess");
        DevRing.busManager().postEvent(new RefreshFragmentEvent(RefreshFragmentEvent.REFRESH_MYFRAGMET));
        finish();
    }

    @Override
    public void saveFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "saveFail() status = " + code + "---desc = " + msg);
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
