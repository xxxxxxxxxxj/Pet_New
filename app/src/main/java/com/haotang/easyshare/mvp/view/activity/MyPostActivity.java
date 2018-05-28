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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerMyPostActivityCommponent;
import com.haotang.easyshare.di.module.activity.MyPostActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.mvp.presenter.MyPostPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.PostListAdapter;
import com.haotang.easyshare.mvp.view.iview.IMyPostView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.mvp.view.widget.ShareBottomDialog;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 我的帖子界面
 */
public class MyPostActivity extends BaseActivity<MyPostPresenter> implements IMyPostView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.iv_titlebar_back)
    ImageView ivTitlebarBack;
    @BindView(R.id.tv_titlebar_other)
    TextView tvTitlebarOther;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_my_post)
    RecyclerView rvMyPost;
    @BindView(R.id.srl_my_post)
    SwipeRefreshLayout srlMyPost;
    private int mNextRequestPage = 1;
    private List<PostBean.DataBean> list = new ArrayList<PostBean.DataBean>();
    private PostListAdapter postListAdapter;
    private int pageSize;
    private String uuid = "";
    private int deletePosition;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_my_post;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerMyPostActivityCommponent.builder().myPostActivityModule(new MyPostActivityModule(this, this)).build().inject(this);
        uuid = getIntent().getStringExtra("uuid");
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("我的帖子");
        srlMyPost.setRefreshing(true);
        srlMyPost.setColorSchemeColors(Color.rgb(47, 223, 189));
        rvMyPost.setHasFixedSize(true);
        rvMyPost.setLayoutManager(new LinearLayoutManager(this));
        postListAdapter = new PostListAdapter(R.layout.item_mypost, list, 0);
        rvMyPost.setAdapter(postListAdapter);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_f8_15));
        rvMyPost.addItemDecoration(divider);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //构建body
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("uuid", uuid);
        builder.addFormDataPart("page", String.valueOf(mNextRequestPage));
        RequestBody build = builder.build();
        mPresenter.list(build);
    }

    @Override
    protected void initEvent() {
        postListAdapter.setOnShareItemListener(new PostListAdapter.OnShareItemListener() {
            @Override
            public void OnShareItem(int position) {
                if (list.size() > 0 && list.size() > position) {
                    PostBean.DataBean dataBean = list.get(position);
                    if (dataBean != null) {
                        PostBean.DataBean.ShareMap shareMap = dataBean.getShareMap();
                        if (shareMap != null) {
                            ShareBottomDialog dialog = new ShareBottomDialog();
                            dialog.setUuid(dataBean.getUuid());
                            dialog.setShareInfo(shareMap.getTitle(), shareMap.getContent(),
                                    shareMap.getUrl(), shareMap.getImg());
                            dialog.show(getSupportFragmentManager());
                        }
                    }
                }
            }
        });
        postListAdapter.setOnDeleteItemListener(new PostListAdapter.OnDeleteItemListener() {
            @Override
            public void OnDeleteItem(int position) {
                if (list.size() > 0 && list.size() > position) {
                    deletePosition = position;
                    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    builder.addFormDataPart("uuid", list.get(position).getUuid());
                    RequestBody body = builder.build();
                    mPresenter.delete(body);
                }
            }
        });
        postListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (list.size() > 0 && list.size() > position) {
                    PostBean.DataBean dataBean = list.get(position);
                    if (dataBean != null) {
                        PostBean.DataBean.ShareMap shareMap = dataBean.getShareMap();
                        if (shareMap != null) {
                            startActivity(new Intent(MyPostActivity.this, WebViewActivity.class).putExtra(WebViewActivity.URL_KEY, shareMap.getUrl()));
                        }
                    }
                }
            }
        });
        postListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        srlMyPost.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        postListAdapter.setEnableLoadMore(false);
        srlMyPost.setRefreshing(true);
        mNextRequestPage = 1;
        //构建body
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("uuid", uuid);
        builder.addFormDataPart("page", String.valueOf(mNextRequestPage));
        RequestBody build = builder.build();
        mPresenter.list(build);
    }

    private void loadMore() {
        //构建body
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("uuid", uuid);
        builder.addFormDataPart("page", String.valueOf(mNextRequestPage));
        RequestBody build = builder.build();
        mPresenter.list(build);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_titlebar_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_titlebar_other:
                startActivity(new Intent(MyPostActivity.this, SendPostActivity.class));
                break;
        }
    }

    @Override
    public void listSuccess(List<PostBean.DataBean> data) {
        if (mNextRequestPage == 1) {
            srlMyPost.setRefreshing(false);
            postListAdapter.setEnableLoadMore(true);
            list.clear();
        }
        postListAdapter.loadMoreComplete();
        if (data != null && data.size() > 0) {
            if (mNextRequestPage == 1) {
                pageSize = data.size();
            } else {
                if (data.size() < pageSize) {
                    postListAdapter.loadMoreEnd(false);
                }
            }
            list.addAll(data);
            mNextRequestPage++;
        } else {
            if (mNextRequestPage == 1) {
                postListAdapter.loadMoreEnd(true);
            } else {
                postListAdapter.loadMoreEnd(false);
            }
            postListAdapter.setEmptyView(setEmptyViewBase(2, "您还没有发布帖子哦", R.mipmap.no_data, null));
        }
        postListAdapter.notifyDataSetChanged();
    }

    @Override
    public void listFail(int code, String msg) {
        if (mNextRequestPage == 1) {
            postListAdapter.setEnableLoadMore(true);
            srlMyPost.setRefreshing(false);
        } else {
            postListAdapter.loadMoreFail();
        }
        postListAdapter.setEmptyView(setEmptyViewBase(1, msg, R.mipmap.no_net_orerror, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        }));
        RingLog.e(TAG, "listFail() status = " + code + "---desc = " + msg);
    }

    @Override
    public void deleteSuccess(AddChargeBean data) {
        list.remove(deletePosition);
        postListAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteFail(int code, String msg) {
        RingLog.e(TAG, "deleteFail() status = " + code + "---desc = " + msg);
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
