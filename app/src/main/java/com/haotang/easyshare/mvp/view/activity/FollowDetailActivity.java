package com.haotang.easyshare.mvp.view.activity;

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
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.PostListAdapter;
import com.haotang.easyshare.mvp.view.viewholder.FollowDetailHeader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关注的人详情页
 */
public class FollowDetailActivity extends BaseActivity {

    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_followdetail)
    RecyclerView rvFollowdetail;
    @BindView(R.id.srl_followdetail)
    SwipeRefreshLayout srlFollowdetail;
    private int mNextRequestPage = 1;
    private List<PostBean> list = new ArrayList<PostBean>();
    private PostListAdapter postListAdapter;
    private FollowDetailHeader followDetailHeader;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_follow_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("TA的详情");
        srlFollowdetail.setRefreshing(true);
        srlFollowdetail.setColorSchemeColors(Color.rgb(47, 223, 189));
        for (int i = 0; i < 20; i++) {
            list.add(new PostBean("http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433"
                    , "e充电的 充电桩速度快，停车方便e充电的 充电桩速度快，停车方便e充电的 充电桩速度快，停车方便e充电的 充电桩速度快，停车方便",
                    "2018年-04-07 11:50:08", "测试", "测试", "https://www.duba.com",
                    UrlConstants.getServiceBaseUrl() + "/static/icon/shouye.png?3"));
        }
        rvFollowdetail.setHasFixedSize(true);
        rvFollowdetail.setLayoutManager(new LinearLayoutManager(this));
        postListAdapter = new PostListAdapter(R.layout.item_mypost, list, 1);

        View top = getLayoutInflater().inflate(R.layout.followdetail_top_view
                , (ViewGroup) rvFollowdetail.getParent(), false);
        followDetailHeader = new FollowDetailHeader(top);
        postListAdapter.addHeaderView(top);

        rvFollowdetail.setAdapter(postListAdapter);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_f8_15));
        rvFollowdetail.addItemDecoration(divider);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        postListAdapter.setOnShareItemListener(new PostListAdapter.OnShareItemListener() {
            @Override
            public void OnShareItem(int position) {//赞
                if (list.size() > 0 && list.size() > position) {
                }
            }
        });
        postListAdapter.setOnDeleteItemListener(new PostListAdapter.OnDeleteItemListener() {
            @Override
            public void OnDeleteItem(int position) {//评论
                if (list.size() > 0 && list.size() > position) {
                }
            }
        });
        postListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        srlFollowdetail.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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

    @OnClick({R.id.iv_titlebar_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
