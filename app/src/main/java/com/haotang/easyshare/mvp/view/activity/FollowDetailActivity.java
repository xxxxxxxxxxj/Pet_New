package com.haotang.easyshare.mvp.view.activity;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerFollowDetailActivityCommponent;
import com.haotang.easyshare.di.module.activity.FollowDetailActivityModule;
import com.haotang.easyshare.materialratingbar.MaterialRatingBar;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.HomeBean;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.mvp.presenter.FollowDetailPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.PostListAdapter;
import com.haotang.easyshare.mvp.view.iview.IFollowDetailView;
import com.haotang.easyshare.mvp.view.viewholder.FollowDetailBoDa;
import com.haotang.easyshare.mvp.view.viewholder.FollowDetailHeader;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 关注的人详情页
 */
public class FollowDetailActivity extends BaseActivity<FollowDetailPresenter> implements IFollowDetailView, View.OnClickListener {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_followdetail)
    RecyclerView rvFollowdetail;
    @BindView(R.id.srl_followdetail)
    SwipeRefreshLayout srlFollowdetail;
    private int mNextRequestPage = 1;
    private List<PostBean.DataBean> list = new ArrayList<PostBean.DataBean>();
    private PostListAdapter postListAdapter;
    private FollowDetailHeader followDetailHeader;
    private FollowDetailBoDa followDetailBoDa;
    private PopupWindow pWinBottomDialog;
    private String uuid;
    private int pageSize;
    private Map<String, String> parmMap = new HashMap<String, String>();
    private int isCollect;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_follow_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerFollowDetailActivityCommponent.builder().followDetailActivityModule(new FollowDetailActivityModule(this, this)).build().inject(this);
        uuid = getIntent().getStringExtra("uuid");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("TA的详情");
        srlFollowdetail.setRefreshing(true);
        srlFollowdetail.setColorSchemeColors(Color.rgb(47, 223, 189));
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
        mPresenter.info(uuid);
        parmMap.clear();
        parmMap.put("uuid", uuid);
        parmMap.put("page", String.valueOf(mNextRequestPage));
        mPresenter.list(parmMap);
    }

    @Override
    protected void initEvent() {
        followDetailHeader.getIvFollowdetailTopGuanzhu().setOnClickListener(this);
        followDetailHeader.getIvFollowdetailTopPingjia().setOnClickListener(this);
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
                    showBottomDialog(position);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_followdetail_top_guanzhu:
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("uuid", uuid);
                RequestBody build = builder.build();
                if (isCollect == 0) {//是否已关注(0:否、1:是)
                    mPresenter.follow(build);
                } else if (isCollect == 1) {
                    mPresenter.cancel(build);
                }
                break;
            case R.id.iv_followdetail_top_pingjia:
                showBottomDialog(0);
                break;
        }
    }

    private void refresh() {
        postListAdapter.setEnableLoadMore(false);
        srlFollowdetail.setRefreshing(true);
        mNextRequestPage = 1;
        mPresenter.info(uuid);
        parmMap.clear();
        parmMap.put("uuid", uuid);
        parmMap.put("page", String.valueOf(mNextRequestPage));
        mPresenter.list(parmMap);
    }

    private void loadMore() {
        mPresenter.list(parmMap);
    }

    private void showBottomDialog(int position) {
        pWinBottomDialog = null;
        if (pWinBottomDialog == null) {
            ViewGroup customView = (ViewGroup) View.inflate(this, R.layout.followdetail_bottom_dialog
                    , null);
            followDetailBoDa = new FollowDetailBoDa(customView);
            pWinBottomDialog = new PopupWindow(customView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, true);
            pWinBottomDialog.setFocusable(true);// 取得焦点
            //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
            pWinBottomDialog.setBackgroundDrawable(new BitmapDrawable());
            //点击外部消失
            pWinBottomDialog.setOutsideTouchable(true);
            //设置可以点击
            pWinBottomDialog.setTouchable(true);
            //进入退出的动画
            pWinBottomDialog.setAnimationStyle(R.style.mypopwindow_anim_style);
            pWinBottomDialog.setWidth(SystemUtil.getDisplayMetrics(this)[0]);
            pWinBottomDialog.showAtLocation(customView, Gravity.BOTTOM, 0, 0);
            followDetailBoDa.getRll_followdetail_bottom().bringToFront();
            followDetailBoDa.getMrbFollowdetailBottom().setNumStars(5);
            followDetailBoDa.getIv_followdetail_bottom_bg().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pWinBottomDialog.dismiss();
                }
            });
            followDetailBoDa.getIvFollowdetailBottomClose().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pWinBottomDialog.dismiss();
                }
            });
            followDetailBoDa.getRll_followdetail_bottom().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            followDetailBoDa.getMrbFollowdetailBottom().setOnRatingChangeListener(new MaterialRatingBar.OnRatingChangeListener() {
                @Override
                public void onRatingChanged(MaterialRatingBar ratingBar, float rating) {
                    StringUtil.setText(followDetailBoDa.getTvFollowdetailBottomDesc(), String.valueOf(rating), "",
                            View.VISIBLE, View.VISIBLE);
                }
            });
            pWinBottomDialog.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                }
            });
        }
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
    public void infoSuccess(HomeBean data) {
        if (data != null) {
            isCollect = data.getIsCollect();
            StringUtil.setText(followDetailHeader.getIvFollowdetailTopUserpf(), data.getStars() + "", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(followDetailHeader.getIvFollowdetailTopUsername(), data.getUserName(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(followDetailHeader.getIvFollowdetailTopUserpf(), "", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(followDetailHeader.getIvFollowdetailTopVipjf(), data.getCoins() + "", "", View.VISIBLE, View.VISIBLE);
            GlideUtil.loadNetCircleImg(this, data.getHeadImg(), followDetailHeader.getIvFollowdetailTopUserimg(), R.mipmap.ic_image_load_circle);
            if (isCollect == 0) {//未关注
                followDetailHeader.getIvFollowdetailTopGuanzhu().setImageResource(R.mipmap.icon_followdetail_top_guanzhu);
            } else if (isCollect == 1) {//已关注
                followDetailHeader.getIvFollowdetailTopGuanzhu().setImageResource(R.mipmap.icon_followdetail_top_yiguanzhu);
            }
            if (data.getIsEval() == 0) {//未评价
                followDetailHeader.getIvFollowdetailTopPingjia().setImageResource(R.mipmap.icon_followdetail_top_pingjia);
            } else if (data.getIsEval() == 1) {//已评价
                followDetailHeader.getIvFollowdetailTopPingjia().setImageResource(R.mipmap.icon_followdetail_top_yipingjia);
            }
        }
    }

    @Override
    public void infoFail(int code, String msg) {
        RingLog.e(TAG, "infoFail() status = " + code + "---desc = " + msg);
    }

    @Override
    public void listSuccess(List<PostBean.DataBean> data) {
        if (mNextRequestPage == 1) {
            srlFollowdetail.setRefreshing(false);
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
        }
        postListAdapter.notifyDataSetChanged();
    }

    @Override
    public void listFail(int code, String msg) {
        if (mNextRequestPage == 1) {
            postListAdapter.setEnableLoadMore(true);
            srlFollowdetail.setRefreshing(false);
        } else {
            postListAdapter.loadMoreFail();
        }
        RingLog.e(TAG, "listFail() status = " + code + "---desc = " + msg);
    }

    @Override
    public void followSuccess(AddChargeBean data) {
        isCollect = 1;
        followDetailHeader.getIvFollowdetailTopGuanzhu().setImageResource(R.mipmap.icon_followdetail_top_yiguanzhu);
    }

    @Override
    public void followFail(int code, String msg) {
        RingLog.e(TAG, "followFail() status = " + code + "---desc = " + msg);
    }

    @Override
    public void cancelSuccess(AddChargeBean data) {
        isCollect = 0;
        followDetailHeader.getIvFollowdetailTopGuanzhu().setImageResource(R.mipmap.icon_followdetail_top_guanzhu);
    }

    @Override
    public void cancelFail(int code, String msg) {
        RingLog.e(TAG, "cancelFail() status = " + code + "---desc = " + msg);
    }
}
