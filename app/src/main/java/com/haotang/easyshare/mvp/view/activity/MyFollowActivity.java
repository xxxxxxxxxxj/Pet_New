package com.haotang.easyshare.mvp.view.activity;

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
import com.haotang.easyshare.mvp.model.entity.res.FollowBean;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.FollowListAdapter;
import com.ljy.devring.DevRing;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的关注界面
 */
public class MyFollowActivity extends BaseActivity {

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
    private List<FollowBean> list = new ArrayList<FollowBean>();
    private FollowListAdapter followListAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_my_follow;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("关注的人");
        srlMyFollow.setRefreshing(true);
        srlMyFollow.setColorSchemeColors(Color.rgb(47, 223, 189));
        for (int i = 0; i < 20; i++) {
            list.add(new FollowBean("http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433"
                    , "138****6986",
                    "5.0分", 5, "积分2800"));
        }
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

    }

    @Override
    protected void initEvent() {
        followListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        srlMyFollow.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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

    @OnClick({R.id.iv_titlebar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
        }
    }
}
