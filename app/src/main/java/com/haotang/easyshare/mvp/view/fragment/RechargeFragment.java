package com.haotang.easyshare.mvp.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.fragment.DaggerRechargeFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.RechargeFragmentModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshBalanceEvent;
import com.haotang.easyshare.mvp.model.entity.res.HistoryList;
import com.haotang.easyshare.mvp.presenter.RechargeFragmentPresenter;
import com.haotang.easyshare.mvp.view.adapter.RechargeFragAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IRechargeFragmentView;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/25 12:02
 */
public class RechargeFragment extends BaseFragment<RechargeFragmentPresenter> implements IRechargeFragmentView {
    protected final static String TAG = RechargeFragment.class.getSimpleName();
    @BindView(R.id.rv_rechargefrag)
    RecyclerView rvRechargefrag;
    @BindView(R.id.srl_rechargefrag)
    SwipeRefreshLayout srlRechargefrag;
    private int type = 1;
    private int mNextRequestPage = 1;
    private int pageSize;
    private List<HistoryList.DataBean.DatasetBean> list = new ArrayList<HistoryList.DataBean.DatasetBean>();
    private RechargeFragAdapter rechargeFragAdapter;

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.rechargefragment;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe
    public void RefreshBalance(RefreshBalanceEvent event) {//充值返回
        if (event != null) {
            refresh();
        }
    }

    @Override
    protected void initView() {
        Bundle arguments = getArguments();
        type = arguments.getInt("type", 0);
        Log.e("TAG", "type = " + type);
        DaggerRechargeFragmentCommponent.builder()
                .rechargeFragmentModule(new RechargeFragmentModule(this, mActivity))
                .build()
                .inject(this);

        srlRechargefrag.setRefreshing(true);
        srlRechargefrag.setColorSchemeColors(Color.rgb(47, 223, 189));
        rvRechargefrag.setHasFixedSize(true);
        rvRechargefrag.setLayoutManager(new LinearLayoutManager(mActivity));
        rechargeFragAdapter = new RechargeFragAdapter(R.layout.item_rechargefrag, list);
        rvRechargefrag.setAdapter(rechargeFragAdapter);
        //添加自定义分割线
        rvRechargefrag.addItemDecoration(new DividerLinearItemDecoration(mActivity, LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(mActivity, 1),
                ContextCompat.getColor(mActivity, R.color.aDDDDDD)));
    }

    @Override
    protected void initData() {
        refresh();
    }

    @Override
    protected void initEvent() {
        rechargeFragAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        srlRechargefrag.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    @Override
    public void requestData() {

    }

    private void refresh() {
        rechargeFragAdapter.setEnableLoadMore(false);
        srlRechargefrag.setRefreshing(true);
        mNextRequestPage = 1;
        getData();
    }

    private void getData() {
        showDialog();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("page", mNextRequestPage + "");
        builder.addFormDataPart("type", type + "");
        RequestBody build = builder.build();
        mPresenter.list(UrlConstants.getMapHeader(mActivity),build);
    }

    private void loadMore() {
        getData();
    }

    @Override
    public void listSuccess(HistoryList.DataBean data) {
        disMissDialog();
        if (data != null) {
            List<HistoryList.DataBean.DatasetBean> dataset = data.getDataset();
            if (mNextRequestPage == 1) {
                srlRechargefrag.setRefreshing(false);
                rechargeFragAdapter.setEnableLoadMore(true);
                list.clear();
            }
            rechargeFragAdapter.loadMoreComplete();
            if (dataset != null && dataset.size() > 0) {
                if (mNextRequestPage == 1) {
                    pageSize = dataset.size();
                } else {
                    if (dataset.size() < pageSize) {
                        rechargeFragAdapter.loadMoreEnd(false);
                    }
                }
                list.addAll(dataset);
                mNextRequestPage++;
            } else {
                if (mNextRequestPage == 1) {
                    rechargeFragAdapter.loadMoreEnd(true);
                    rechargeFragAdapter.setEmptyView(setEmptyViewBase(2, "暂无记录", R.mipmap.no_data, DensityUtil.dp2px(mActivity, 200), DensityUtil.dp2px(mActivity, 200), null));
                } else {
                    rechargeFragAdapter.loadMoreEnd(false);
                }
            }
            rechargeFragAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void listFail(int code, String msg) {
        disMissDialog();
        if (mNextRequestPage == 1) {
            rechargeFragAdapter.setEnableLoadMore(true);
            srlRechargefrag.setRefreshing(false);
        } else {
            rechargeFragAdapter.loadMoreFail();
        }
        rechargeFragAdapter.setEmptyView(setEmptyViewBase(1, msg, R.mipmap.no_net_orerror, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        }));
        RingLog.e(TAG, "listFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(getActivity(), code);
    }
}
