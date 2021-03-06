package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.activity.DaggerMyFollowActivityCommponent;
import com.haotang.easyshare.di.module.activity.MyFollowActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.FollowBean;
import com.haotang.easyshare.mvp.presenter.MyFollowPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.FollowListAdapter;
import com.haotang.easyshare.mvp.view.iview.IMyFollowView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的关注界面
 */
public class MyFollowActivity extends BaseActivity<MyFollowPresenter> implements IMyFollowView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.iv_titlebar_back)
    ImageView ivTitlebarBack;
    @BindView(R.id.tv_titlebar_other)
    TextView tvTitlebarOther;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_my_follow)
    RecyclerView rvMyFollow;
    @BindView(R.id.srl_my_follow)
    SwipeRefreshLayout srlMyFollow;
    private int mNextRequestPage = 1;
    private List<FollowBean.DataBean> list = new ArrayList<FollowBean.DataBean>();
    private FollowListAdapter followListAdapter;
    private int pageSize;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_my_follow;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerMyFollowActivityCommponent.builder().myFollowActivityModule(new MyFollowActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("关注的人");
        srlMyFollow.setRefreshing(true);
        srlMyFollow.setColorSchemeColors(Color.rgb(47, 223, 189));
        rvMyFollow.setHasFixedSize(true);
        rvMyFollow.setLayoutManager(new LinearLayoutManager(this));
        followListAdapter = new FollowListAdapter(R.layout.item_myfollow, list);
        rvMyFollow.setAdapter(followListAdapter);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_f8_15));
        rvMyFollow.addItemDecoration(divider);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showDialog();
        mPresenter.list(UrlConstants.getMapHeader(this));
    }

    @Override
    protected void initEvent() {
        followListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (list != null && list.size() > 0 && list.size() > position) {
                    FollowBean.DataBean dataBean = list.get(position);
                    if (dataBean != null) {
                        startActivity(new Intent(MyFollowActivity.this, FollowDetailActivity.class).putExtra("uuid", dataBean.getUuid()));
                    }
                }
            }
        });
        /*followListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });*/
        srlMyFollow.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        showDialog();
        followListAdapter.setEnableLoadMore(false);
        srlMyFollow.setRefreshing(true);
        mNextRequestPage = 1;
        mPresenter.list(UrlConstants.getMapHeader(this));
    }

    private void loadMore() {
        mPresenter.list(UrlConstants.getMapHeader(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }

    @OnClick({R.id.iv_titlebar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
        }
    }

    @Override
    public void listFail(int code, String msg) {
        disMissDialog();
        if (mNextRequestPage == 1) {
            followListAdapter.setEnableLoadMore(true);
            srlMyFollow.setRefreshing(false);
        } else {
            followListAdapter.loadMoreFail();
        }
        followListAdapter.setEmptyView(setEmptyViewBase(1, msg, R.mipmap.no_net_orerror, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        }));
        RingLog.e(TAG, "listFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this,code);
    }

    @Override
    public void listSuccess(List<FollowBean.DataBean> data) {
        disMissDialog();
        if (mNextRequestPage == 1) {
            srlMyFollow.setRefreshing(false);
            followListAdapter.setEnableLoadMore(true);
            list.clear();
        }
        followListAdapter.loadMoreComplete();
        if (data != null && data.size() > 0) {
            if (mNextRequestPage == 1) {
                pageSize = data.size();
            } else {
                if (data.size() < pageSize) {
                    followListAdapter.loadMoreEnd(false);
                }
            }
            list.addAll(data);
            mNextRequestPage++;
        } else {
            if (mNextRequestPage == 1) {
                followListAdapter.loadMoreEnd(true);
                followListAdapter.setEmptyView(setEmptyViewBase(2, "您还没有关注的人哦", R.mipmap.no_data, null));
            } else {
                followListAdapter.loadMoreEnd(false);
            }
        }
        followListAdapter.notifyDataSetChanged();
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
