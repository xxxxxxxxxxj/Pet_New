package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerSelectAddressActivityCommponent;
import com.haotang.easyshare.di.module.activity.SelectAddressActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.SelectAddress;
import com.haotang.easyshare.mvp.presenter.SelectAddressPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.SelectAddressAdapter;
import com.haotang.easyshare.mvp.view.iview.ISelectAddressView;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.DensityUtil;
import com.ljy.devring.DevRing;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选择地址界面
 */
public class SelectAddressActivity extends BaseActivity<SelectAddressPresenter> implements ISelectAddressView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.iv_titlebar_back)
    ImageView ivTitlebarBack;
    @BindView(R.id.tv_titlebar_other)
    TextView tvTitlebarOther;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_select_address)
    RecyclerView rvSelectAddress;
    private List<SelectAddress> list = new ArrayList<SelectAddress>();
    private SelectAddressAdapter selectAddressAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_select_address;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerSelectAddressActivityCommponent.builder().
                selectAddressActivityModule(new SelectAddressActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("选择地址");
        tvTitlebarOther.setVisibility(View.VISIBLE);
        tvTitlebarOther.setText("新增地址");

        for (int i = 0; i < 20; i++) {
            list.add(new SelectAddress("方恒国际中心",
                    39.989614, 116.481763));
        }
        rvSelectAddress.setHasFixedSize(true);
        rvSelectAddress.setLayoutManager(new LinearLayoutManager(this));
        selectAddressAdapter = new SelectAddressAdapter(R.layout.item_select_address, list);
        rvSelectAddress.setAdapter(selectAddressAdapter);
        //添加自定义分割线
        rvSelectAddress.addItemDecoration(new DividerLinearItemDecoration(this, LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(this, 1),
                ContextCompat.getColor(this, R.color.aEEEEEE)));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        selectAddressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DevRing.busManager().postEvent(list.get(position));
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_titlebar_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_titlebar_other:
                startActivity(new Intent(SelectAddressActivity.this, AddAddressActivity.class));
                break;
        }
    }
}
