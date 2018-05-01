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
import com.haotang.easyshare.mvp.model.entity.res.CommentBean;
import com.haotang.easyshare.mvp.model.entity.res.CommentImg;
import com.haotang.easyshare.mvp.model.entity.res.CommentTag;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.CommentDetailAdapter;
import com.ljy.devring.DevRing;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 评论详情页
 */
public class CommentDetailActivity extends BaseActivity {

    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_comment_detail)
    RecyclerView rvCommentDetail;
    @BindView(R.id.srl_comment_detail)
    SwipeRefreshLayout srlCommentDetail;
    private List<CommentBean> list = new ArrayList<CommentBean>();
    private List<CommentTag> tagList = new ArrayList<CommentTag>();
    private List<CommentImg> imgList = new ArrayList<CommentImg>();
    private CommentDetailAdapter commentDetailAdapter;
    private String[] tags = {"充电便利", "停车免费", "不用排队", "价格便宜", "充电快"};
    private String[] imgs = {"http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433",
            "http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433",
            "http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433",
            "http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433",
            "http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433"};
    private int mNextRequestPage = 1;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_comment_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        srlCommentDetail.setRefreshing(true);
        srlCommentDetail.setColorSchemeColors(Color.rgb(47, 223, 189));
        tvTitlebarTitle.setText("评论详情");
        for (int i = 0; i < 5; i++) {
            tagList.add(new CommentTag("充电便利", false));
        }
        for (int i = 0; i < 5; i++) {
            imgList.add(new CommentImg("http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433", false));
        }
        for (int i = 0; i < 20; i++) {
            list.add(new CommentBean("http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433",
                    "139****9696", "09-20", "目前用过一次，感觉还可以", tagList, imgList, false, false));
        }
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
        mNextRequestPage = 1;
    }

    private void loadMore() {
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
                startActivity(new Intent(CommentDetailActivity.this, CommentActivity.class));
                break;
        }
    }

}
