package com.haotang.easyshare.mvp.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerRechargeRecordActivityCommponent;
import com.haotang.easyshare.di.module.activity.RechargeRecordActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.entity.res.RechargeRecord;
import com.haotang.easyshare.mvp.presenter.RechargeRecordPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.RechargeRecordAdapter;
import com.haotang.easyshare.mvp.view.iview.IRechargeRecordView;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.util.DensityUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充电记录界面
 */
public class RechargeRecordActivity extends BaseActivity<RechargeRecordPresenter> implements IRechargeRecordView {

    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_recharge_record)
    RecyclerView rv_recharge_record;
    @BindView(R.id.srl_recharge_record)
    SwipeRefreshLayout srl_recharge_record;
    private int mNextRequestPage = 1;
    private int pageSize;
    private List<RechargeRecord> list = new ArrayList<RechargeRecord>();
    private RechargeRecordAdapter rechargeRecordAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_recharge_record;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerRechargeRecordActivityCommponent.builder().
                rechargeRecordActivityModule(new RechargeRecordActivityModule(this, this)).build().inject(this);

        srl_recharge_record.setRefreshing(true);
        srl_recharge_record.setColorSchemeColors(Color.rgb(47, 223, 189));
        rv_recharge_record.setHasFixedSize(true);
        rv_recharge_record.setLayoutManager(new LinearLayoutManager(this));
        rechargeRecordAdapter = new RechargeRecordAdapter(R.layout.item_rechargerecord, list);
        rv_recharge_record.setAdapter(rechargeRecordAdapter);
        //添加自定义分割线
        rv_recharge_record.addItemDecoration(new DividerLinearItemDecoration(this, LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(this, 15),
                ContextCompat.getColor(this, R.color.af8f8f8)));
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
        rechargeRecordAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        srl_recharge_record.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        rechargeRecordAdapter.setEnableLoadMore(false);
        srl_recharge_record.setRefreshing(true);
        mNextRequestPage = 1;
    }

    private void loadMore() {
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
