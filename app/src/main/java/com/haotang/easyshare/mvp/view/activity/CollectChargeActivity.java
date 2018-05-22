package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerCollectChargeActivityCommponent;
import com.haotang.easyshare.di.module.activity.CollectChargeActivityModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshEvent;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.CollectChargeBean;
import com.haotang.easyshare.mvp.presenter.CollectChargePresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.CollectChargeListAdapter;
import com.haotang.easyshare.mvp.view.iview.ICollectChargeView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 收藏的站点
 */
public class CollectChargeActivity extends BaseActivity<CollectChargePresenter> implements ICollectChargeView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.smrv_collect_charge)
    SwipeMenuRecyclerView smrvCollectCharge;
    @BindView(R.id.srl_collect_charge)
    SwipeRefreshLayout srlCollectCharge;
    private List<CollectChargeBean.DataBean> list = new ArrayList<CollectChargeBean.DataBean>();
    private int mNextRequestPage = 1;
    private CollectChargeListAdapter collectChargeListAdapter;
    private int pageSize;
    private Map<String, String> parmMap = new HashMap<String, String>();
    private int adapterPosition;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_collect_charge;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerCollectChargeActivityCommponent.builder().collectChargeActivityModule(new CollectChargeActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("收藏的站点");
        smrvCollectCharge.setSwipeMenuCreator(swipeMenuCreator);
        smrvCollectCharge.setSwipeMenuItemClickListener(mMenuItemClickListener);
        srlCollectCharge.setRefreshing(true);
        srlCollectCharge.setColorSchemeColors(Color.rgb(47, 223, 189));
        smrvCollectCharge.setHasFixedSize(true);
        smrvCollectCharge.setLayoutManager(new LinearLayoutManager(this));
        collectChargeListAdapter = new CollectChargeListAdapter(R.layout.item_collectcharge, list);
        smrvCollectCharge.setAdapter(collectChargeListAdapter);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_f8_15));
        smrvCollectCharge.addItemDecoration(divider);
    }

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_90);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(CollectChargeActivity.this)
                    .setBackgroundColor(getResources().getColor(R.color.aFF3D33))
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setTextSize(15)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                if (list != null && list.size() > 0 && list.size() > adapterPosition) {
                    CollectChargeBean.DataBean dataBean = list.get(adapterPosition);
                    if (dataBean != null) {
                        parmMap.clear();
                        parmMap.put("uuid", dataBean.getUuid());
                        mPresenter.cancel(parmMap);
                    }
                }
            }
        }
    };

    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter.list();
    }

    @Override
    protected void initEvent() {
        collectChargeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (list != null && list.size() > 0 && list.size() > position) {
                    CollectChargeBean.DataBean collectChargeBean = list.get(position);
                    if (collectChargeBean != null) {
                        startActivity(new Intent(CollectChargeActivity.this, ChargingPileDetailActivity.class).putExtra("uuid", collectChargeBean.getUuid()));
                    }
                }
            }
        });
        /*collectChargeListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });*/
        srlCollectCharge.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        collectChargeListAdapter.setEnableLoadMore(false);
        srlCollectCharge.setRefreshing(true);
        mNextRequestPage = 1;
        mPresenter.list();
    }

    private void loadMore() {
        mPresenter.list();
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

    @Override
    public void listSuccess(List<CollectChargeBean.DataBean> data) {
        if (mNextRequestPage == 1) {
            srlCollectCharge.setRefreshing(false);
            collectChargeListAdapter.setEnableLoadMore(true);
            list.clear();
        }
        collectChargeListAdapter.loadMoreComplete();
        if (data != null && data.size() > 0) {
            if (mNextRequestPage == 1) {
                pageSize = data.size();
            } else {
                if (data.size() < pageSize) {
                    collectChargeListAdapter.loadMoreEnd(false);
                }
            }
            list.addAll(data);
            mNextRequestPage++;
        } else {
            if (mNextRequestPage == 1) {
                collectChargeListAdapter.loadMoreEnd(true);
            } else {
                collectChargeListAdapter.loadMoreEnd(false);
            }
            collectChargeListAdapter.setEmptyView(setEmptyViewBase(2, "暂无收藏的充电桩", R.mipmap.no_data, null));
        }
        collectChargeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void listFail(int code, String msg) {
        if (mNextRequestPage == 1) {
            collectChargeListAdapter.setEnableLoadMore(true);
            srlCollectCharge.setRefreshing(false);
        } else {
            collectChargeListAdapter.loadMoreFail();
        }
        collectChargeListAdapter.setEmptyView(setEmptyViewBase(1, msg, R.mipmap.no_net_orerror, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        }));
        RingLog.e(TAG, "listFail() status = " + code + "---desc = " + msg);
    }

    @Override
    public void cancelSuccess(AddChargeBean data) {
        list.remove(adapterPosition);
        collectChargeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void cancelFail(int code, String msg) {
        RingLog.e(TAG, "listFail() status = " + code + "---desc = " + msg);
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe
    public void refresh(RefreshEvent data) {
        if (data != null && data.getRefreshIndex() == RefreshEvent.COLLECT_OR_CANCEL_CHARGE) {
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
