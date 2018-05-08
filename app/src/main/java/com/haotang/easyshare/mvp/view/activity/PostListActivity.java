package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerPostListActivityCommponent;
import com.haotang.easyshare.di.module.activity.PostListActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.presenter.PostListPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.HotPointAdapter;
import com.haotang.easyshare.mvp.view.iview.IPostListView;
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
 * 帖子列表
 */
public class PostListActivity extends BaseActivity<PostListPresenter> implements IPostListView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.iv_titlebar_back)
    ImageView ivTitlebarBack;
    @BindView(R.id.tv_titlebar_other)
    TextView tvTitlebarOther;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.tv_postlist_zxt)
    TextView tvPostlistZxt;
    @BindView(R.id.tv_postlist_rmt)
    TextView tvPostlistRmt;
    @BindView(R.id.tv_postlist_wtc)
    TextView tvPostlistWtc;
    @BindView(R.id.rv_postlist)
    RecyclerView rvPostlist;
    @BindView(R.id.srl_postlist)
    SwipeRefreshLayout srlPostlist;
    private int mNextRequestPage = 1;
    private List<HotPoint> list = new ArrayList<HotPoint>();
    private HotPointAdapter hotPointAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_post_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerPostListActivityCommponent.builder().
                postListActivityModule(new PostListActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        setClass(1);
        tvTitlebarTitle.setText("帖子列表");
        tvTitlebarOther.setVisibility(View.VISIBLE);
        tvTitlebarOther.setText("发帖");

        srlPostlist.setRefreshing(true);
        srlPostlist.setColorSchemeColors(Color.rgb(47, 223, 189));
        for (int i = 0; i < 20; i++) {
            list.add(new HotPoint("结婚三周年送给媳妇的小电电，大方的么么哒等哈打了客服结婚三周年送给媳妇的小电电，大方的么么哒等哈打了客服结婚三周年送给媳妇的小电电，大方的么么哒等哈打了客服",
                    "http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433",
                    "地方大V", "25分钟前", "28888阅读", "http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433"));
        }
        rvPostlist.setHasFixedSize(true);
        rvPostlist.setLayoutManager(new LinearLayoutManager(this));
        hotPointAdapter = new HotPointAdapter(R.layout.item_hot_point, list);
        rvPostlist.setAdapter(hotPointAdapter);
        //添加自定义分割线
        rvPostlist.addItemDecoration(new DividerLinearItemDecoration(this, LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(this, 15),
                ContextCompat.getColor(this, R.color.af8f8f8)));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        hotPointAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        hotPointAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        srlPostlist.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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

    private void setClass(int flag) {
        if (flag == 1) {
            tvPostlistZxt.setBackgroundResource(R.mipmap.bg_postlist_calss_select);
            tvPostlistRmt.setBackgroundResource(R.mipmap.bg_postlist_calss_unselect);
            tvPostlistWtc.setBackgroundResource(R.mipmap.bg_postlist_calss_unselect);
            tvPostlistZxt.setTextColor(getResources().getColor(R.color.white));
            tvPostlistRmt.setTextColor(getResources().getColor(R.color.a666666));
            tvPostlistWtc.setTextColor(getResources().getColor(R.color.a666666));
        } else if (flag == 2) {
            tvPostlistZxt.setBackgroundResource(R.mipmap.bg_postlist_calss_unselect);
            tvPostlistRmt.setBackgroundResource(R.mipmap.bg_postlist_calss_select);
            tvPostlistWtc.setBackgroundResource(R.mipmap.bg_postlist_calss_unselect);
            tvPostlistZxt.setTextColor(getResources().getColor(R.color.a666666));
            tvPostlistRmt.setTextColor(getResources().getColor(R.color.white));
            tvPostlistWtc.setTextColor(getResources().getColor(R.color.a666666));
        } else if (flag == 3) {
            tvPostlistZxt.setBackgroundResource(R.mipmap.bg_postlist_calss_unselect);
            tvPostlistRmt.setBackgroundResource(R.mipmap.bg_postlist_calss_unselect);
            tvPostlistWtc.setBackgroundResource(R.mipmap.bg_postlist_calss_select);
            tvPostlistZxt.setTextColor(getResources().getColor(R.color.a666666));
            tvPostlistRmt.setTextColor(getResources().getColor(R.color.a666666));
            tvPostlistWtc.setTextColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_titlebar_other, R.id.tv_postlist_zxt, R.id.tv_postlist_rmt, R.id.tv_postlist_wtc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_titlebar_other:
                startActivity(new Intent(PostListActivity.this, SendPostActivity.class));
                break;
            case R.id.tv_postlist_zxt:
                setClass(1);
                break;
            case R.id.tv_postlist_rmt:
                setClass(2);
                break;
            case R.id.tv_postlist_wtc:
                setClass(3);
                break;
        }
    }
}
