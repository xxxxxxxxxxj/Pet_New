package com.haotang.easyshare.mvp.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerLocalChargingActivityCommponent;
import com.haotang.easyshare.di.module.activity.LocalChargingActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.MainFragmentData;
import com.haotang.easyshare.mvp.presenter.LocalChargingPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.MainLocalAdapter;
import com.haotang.easyshare.mvp.view.iview.ILocalChargingView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.ljy.devring.DevRing;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 附近充电桩界面
 */
public class LocalChargingActivity extends BaseActivity<LocalChargingPresenter> implements ILocalChargingView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_local_charging)
    RecyclerView rvLocalCharging;
    @BindView(R.id.srl_local_charging)
    SwipeRefreshLayout srlLocalCharging;
    private List<MainFragmentData> list = new ArrayList<MainFragmentData>();
    private MainLocalAdapter mainLocalAdapter;
    private int mNextRequestPage = 1;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_local_charging;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerLocalChargingActivityCommponent.builder().localChargingActivityModule(new LocalChargingActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("附近的充电桩");
        srlLocalCharging.setRefreshing(true);
        srlLocalCharging.setColorSchemeColors(Color.rgb(47, 223, 189));
        setAdapter();
    }

    private void setAdapter() {
        list.add(new MainFragmentData(0, "测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称",
                "测试距离", 3, 4, 5, "00:30-24:00",
                "北京朝阳区石各庄818号平安建材家东侧东侧500米北京朝阳区石各庄818号平安建材家东侧东侧500米北京朝阳区石各庄818号平安建材家东侧东侧500米",
                39.983456, 116.3154950, "北京",
                "http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433"));

        list.add(new MainFragmentData(0, "测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称",
                "测试距离", 3, 4, 5, "00:30-24:00",
                "北京朝阳区石各庄818号平安建材家东侧东侧500米北京朝阳区石各庄818号平安建材家东侧东侧500米北京朝阳区石各庄818号平安建材家东侧东侧500米",
                31.238068, 121.501654, "上海", "http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433"));

        list.add(new MainFragmentData(0, "测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称",
                "测试距离", 3, 4, 5, "00:30-24:00",
                "北京朝阳区石各庄818号平安建材家东侧东侧500米北京朝阳区石各庄818号平安建材家东侧东侧500米北京朝阳区石各庄818号平安建材家东侧东侧500米",
                30.679879, 104.064855, "成都", "http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433"));

        list.add(new MainFragmentData(0, "测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称",
                "测试距离", 3, 4, 5, "00:30-24:00",
                "北京朝阳区石各庄818号平安建材家东侧东侧500米北京朝阳区石各庄818号平安建材家东侧东侧500米北京朝阳区石各庄818号平安建材家东侧东侧500米",
                34.341568, 108.940174, "西安", "http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433"));

        list.add(new MainFragmentData(0, "测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称测试名称",
                "测试距离", 3, 4, 5, "00:30-24:00",
                "北京朝阳区石各庄818号平安建材家东侧东侧500米北京朝阳区石各庄818号平安建材家东侧东侧500米北京朝阳区石各庄818号平安建材家东侧东侧500米",
                34.7466, 113.625367, "郑州", "http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433"));
        rvLocalCharging.setHasFixedSize(true);
        rvLocalCharging.setLayoutManager(new LinearLayoutManager(this));
        mainLocalAdapter = new MainLocalAdapter(R.layout.item_mainlocal, list,true);
        rvLocalCharging.setAdapter(mainLocalAdapter);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_f8_15));
        rvLocalCharging.addItemDecoration(divider);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        mainLocalAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        srlLocalCharging.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        mNextRequestPage = 1;
    }

    private void loadMore() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
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
