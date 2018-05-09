package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerAddAddressActivityCommponent;
import com.haotang.easyshare.di.module.activity.AddAddressActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.SelectAddress;
import com.haotang.easyshare.mvp.model.entity.res.SerchResult;
import com.haotang.easyshare.mvp.presenter.AddAddressPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.iview.IAddAddressView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.util.RingToast;

import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 添加地址
 */
public class AddAddressActivity extends BaseActivity<AddAddressPresenter> implements IAddAddressView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.iv_titlebar_back)
    ImageView ivTitlebarBack;
    @BindView(R.id.tv_titlebar_other)
    TextView tvTitlebarOther;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.et_addaddress)
    EditText etAddaddress;
    @BindView(R.id.et_addaddress_xxdz)
    EditText etAddaddressXxdz;
    private double lat;
    private double lng;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_add_address;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerAddAddressActivityCommponent.builder().
                addAddressActivityModule(new AddAddressActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("添加地址");
        tvTitlebarOther.setVisibility(View.VISIBLE);
        tvTitlebarOther.setText("添加地址");
        etAddaddress.setFocusable(false);//让EditText失去焦点，然后获取点击事件
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe
    public void getAddress(SerchResult serchResult) {
        if (serchResult != null) {
            lat = serchResult.getLat();
            lng = serchResult.getLng();
            StringUtil.setText(etAddaddress, serchResult.getName() + serchResult.getDesc(), "", View.VISIBLE, View.VISIBLE);
        }
    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_titlebar_other, R.id.et_addaddress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_titlebar_other:
                if (StringUtil.isNotEmpty(etAddaddress.getText().toString())) {
                    RingToast.show("请填写电桩地址");
                    return;
                }
                if (StringUtil.isNotEmpty(etAddaddressXxdz.getText().toString())) {
                    RingToast.show("请填写详细地址");
                    return;
                }
                DevRing.busManager().postEvent(new SelectAddress(etAddaddress.getText().toString().trim()
                        + etAddaddressXxdz.getText().toString().trim(), lat, lng));
                DevRing.activityStackManager().exitActivity(SelectAddressActivity.class);
                finish();
                break;
            case R.id.et_addaddress:
                startActivity(new Intent(AddAddressActivity.this, SerchAddressActivity.class));
                break;
        }
    }
}
