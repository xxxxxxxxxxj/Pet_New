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
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerCommentDetailActivityCommponent;
import com.haotang.easyshare.di.module.activity.CommentDetailActivityModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshEvent;
import com.haotang.easyshare.mvp.model.entity.res.CommentBean;
import com.haotang.easyshare.mvp.model.entity.res.CommentImg;
import com.haotang.easyshare.mvp.model.entity.res.CommentTag;
import com.haotang.easyshare.mvp.presenter.CommentDetailPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.CommentDetailAdapter;
import com.haotang.easyshare.mvp.view.iview.ICommentDetailView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 充电桩评论列表页
 */
public class CommentDetailActivity extends BaseActivity<CommentDetailPresenter> implements ICommentDetailView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.tv_comment_plnum)
    TextView tv_comment_plnum;
    @BindView(R.id.rv_comment_detail)
    RecyclerView rvCommentDetail;
    @BindView(R.id.srl_comment_detail)
    SwipeRefreshLayout srlCommentDetail;
    private List<CommentBean.Comment> list = new ArrayList<CommentBean.Comment>();
    private CommentDetailAdapter commentDetailAdapter;
    private int mNextRequestPage = 1;
    private String uuid;
    private int pageSize;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_comment_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerCommentDetailActivityCommponent.builder().commentDetailActivityModule(new CommentDetailActivityModule(this, this)).build().inject(this);
        uuid = getIntent().getStringExtra("uuid");
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        srlCommentDetail.setRefreshing(true);
        srlCommentDetail.setColorSchemeColors(Color.rgb(47, 223, 189));
        tvTitlebarTitle.setText("评论详情");
        rvCommentDetail.setHasFixedSize(true);
        rvCommentDetail.setLayoutManager(new LinearLayoutManager(this));
        commentDetailAdapter = new CommentDetailAdapter(R.layout.item_comment, list);
        rvCommentDetail.setAdapter(commentDetailAdapter);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_f8_15));
        rvCommentDetail.addItemDecoration(divider);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter.list(uuid, mNextRequestPage);
    }

    @Override
    protected void initEvent() {
        commentDetailAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        srlCommentDetail.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        commentDetailAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        srlCommentDetail.setRefreshing(true);
        mNextRequestPage = 1;
        mPresenter.list(uuid, mNextRequestPage);
    }

    private void loadMore() {
        mPresenter.list(uuid, mNextRequestPage);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @OnClick({R.id.iv_titlebar_back, R.id.ll_comment_detail_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.ll_comment_detail_add:
                startActivity(new Intent(CommentDetailActivity.this, CommentActivity.class).putExtra("uuid", uuid));
                break;
        }
    }

    @Override
    public void listSuccess(CommentBean data) {
        if (mNextRequestPage == 1) {
            srlCommentDetail.setRefreshing(false);
            commentDetailAdapter.setEnableLoadMore(true);
            list.clear();
        }
        commentDetailAdapter.loadMoreComplete();
        if (data != null) {
            StringUtil.setText(tv_comment_plnum, "添加评论(" + data.getTotal() + ")", "", View.VISIBLE, View.GONE);
            List<CommentBean.Comment> comments = data.getComments();
            if (comments != null && comments.size() > 0) {
                if (mNextRequestPage == 1) {
                    pageSize = comments.size();
                } else {
                    if (comments.size() < pageSize) {
                        commentDetailAdapter.loadMoreEnd(false);
                    }
                }
                for (int i = 0; i < comments.size(); i++) {
                    CommentBean.Comment comment = comments.get(i);
                    if (comment != null) {
                        List<String> media = comment.getMedia();
                        List<String> tags = comment.getTags();
                        if (media != null && media.size() > 0) {
                            List<CommentImg> mediaList = new ArrayList<CommentImg>();
                            mediaList.clear();
                            for (int k = 0; k < media.size(); k++) {
                                mediaList.add(new CommentImg(media.get(k), false));
                            }
                            comment.setMediaList(mediaList);
                        }
                        if (tags != null && tags.size() > 0) {
                            List<CommentTag> tagList = new ArrayList<CommentTag>();
                            for (int k = 0; k < tags.size(); k++) {
                                tagList.add(new CommentTag(tags.get(k), false));
                            }
                            comment.setTagList(tagList);
                        }
                    }
                }
                list.addAll(comments);
                mNextRequestPage++;
            } else {
                if (mNextRequestPage == 1) {
                    commentDetailAdapter.loadMoreEnd(true);
                } else {
                    commentDetailAdapter.loadMoreEnd(false);
                }
                commentDetailAdapter.setEmptyView(setEmptyViewBase(2, "暂无评论", R.mipmap.no_data, null));
            }
        }
        commentDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void listFail(int code, String msg) {
        RingLog.e(TAG, "listFail() status = " + code + "---desc = " + msg);
        if (mNextRequestPage == 1) {
            commentDetailAdapter.setEnableLoadMore(true);
            srlCommentDetail.setRefreshing(false);
        } else {
            commentDetailAdapter.loadMoreFail();
        }
        commentDetailAdapter.setEmptyView(setEmptyViewBase(1, msg, R.mipmap.no_net_orerror, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        }));
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe
    public void refresh(RefreshEvent data) {
        if (data != null && data.getRefreshIndex() == RefreshEvent.SAVE_CHARGE_COMMENT) {
            refresh();
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
