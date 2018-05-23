package com.haotang.easyshare.mvp.view.fragment;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.fragment.DaggerHistoricalMessageFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.HistoricalMessageFragmentModule;
import com.haotang.easyshare.mvp.model.entity.res.HistoricalMsg;
import com.haotang.easyshare.mvp.presenter.HistoricalMessageFragmentPresenter;
import com.haotang.easyshare.mvp.view.adapter.HistoricalMessagelAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IHistoricalMessageFragmentView;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.DensityUtil;
import com.ljy.devring.other.RingLog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/28 15:37
 */
public class HistoricalMessageFragment extends BaseFragment<HistoricalMessageFragmentPresenter> implements IHistoricalMessageFragmentView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.rv_historymsg)
    RecyclerView rvHistorymsg;
    @BindView(R.id.srl_historymsg)
    SwipeRefreshLayout srlHistorymsg;
    private int mNextRequestPage = 1;
    private List<List<HistoricalMsg.DataBean>> list = new ArrayList<List<HistoricalMsg.DataBean>>();
    private HistoricalMessagelAdapter historicalMessagelAdapter;
    private int pageSize;

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.historicalmessagefragment;
    }

    @Override
    protected void initView() {
        DaggerHistoricalMessageFragmentCommponent.builder()
                .historicalMessageFragmentModule(new HistoricalMessageFragmentModule(this, mActivity))
                .build()
                .inject(this);
        srlHistorymsg.setRefreshing(true);
        srlHistorymsg.setColorSchemeColors(Color.rgb(47, 223, 189));
        rvHistorymsg.setHasFixedSize(true);
        rvHistorymsg.setLayoutManager(new LinearLayoutManager(mActivity));
        historicalMessagelAdapter = new HistoricalMessagelAdapter(R.layout.item_historymsg, list);
        rvHistorymsg.setAdapter(historicalMessagelAdapter);
        //添加自定义分割线
        rvHistorymsg.addItemDecoration(new DividerLinearItemDecoration(mActivity, LinearLayoutManager.VERTICAL, DensityUtil.dp2px(mActivity, 15),
                ContextCompat.getColor(mActivity, R.color.af8f8f8)));
    }

    @Override
    protected void initData() {
        refresh();
    }

    @Override
    protected void initEvent() {
       /* historicalMessagelAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });*/
        srlHistorymsg.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void refresh() {
        historicalMessagelAdapter.setEnableLoadMore(false);
        srlHistorymsg.setRefreshing(true);
        mNextRequestPage = 1;
        mPresenter.history();
    }

    private void loadMore() {
        mPresenter.history();
    }

    @Override
    public void historySuccess(List<List<HistoricalMsg.DataBean>> data) {
        if (mNextRequestPage == 1) {
            srlHistorymsg.setRefreshing(false);
            historicalMessagelAdapter.setEnableLoadMore(true);
            list.clear();
        }
        historicalMessagelAdapter.loadMoreComplete();
        if (data != null && data.size() > 0) {
            if (mNextRequestPage == 1) {
                pageSize = data.size();
            } else {
                if (data.size() < pageSize) {
                    historicalMessagelAdapter.loadMoreEnd(false);
                }
            }
            list.addAll(data);
            mNextRequestPage++;
        } else {
            if (mNextRequestPage == 1) {
                historicalMessagelAdapter.loadMoreEnd(true);
            } else {
                historicalMessagelAdapter.loadMoreEnd(false);
            }
            historicalMessagelAdapter.setEmptyView(setEmptyViewBase(2, "暂无留言", R.mipmap.no_data, null));
        }
        historicalMessagelAdapter.notifyDataSetChanged();
    }

    @Override
    public void historyFail(int code, String msg) {
        if (mNextRequestPage == 1) {
            historicalMessagelAdapter.setEnableLoadMore(true);
            srlHistorymsg.setRefreshing(false);
        } else {
            historicalMessagelAdapter.loadMoreFail();
        }
        historicalMessagelAdapter.setEmptyView(setEmptyViewBase(1, msg, R.mipmap.no_net_orerror, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        }));
        RingLog.e(TAG, "historyFail() status = " + code + "---desc = " + msg);
    }
}