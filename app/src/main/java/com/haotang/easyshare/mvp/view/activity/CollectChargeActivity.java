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
import com.haotang.easyshare.mvp.model.entity.res.CollectChargeBean;
import com.haotang.easyshare.mvp.presenter.CollectChargePresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.CollectChargeListAdapter;
import com.haotang.easyshare.mvp.view.iview.ICollectChargeView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.ljy.devring.DevRing;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

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
    private List<CollectChargeBean> list = new ArrayList<CollectChargeBean>();
    private int mNextRequestPage = 1;
    private CollectChargeListAdapter collectChargeListAdapter;

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
        for (int i = 0; i < 20; i++) {
            list.add(new CollectChargeBean("http://dev-pet-avatar.oss-cn-beijing.aliyuncs.com/shop/imgs/shopyyc.png?v=433"
                    , "北京市西城区晚风珠宝充电站",
                    "0.36～0.98元/度", "0.8～0.8元/度"));
        }
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
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                list.remove(adapterPosition);
                collectChargeListAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        collectChargeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(CollectChargeActivity.this, ChargingPileDetailActivity.class).putExtra("uuid",""));
            }
        });
        collectChargeListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        srlCollectCharge.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
