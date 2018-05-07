package com.haotang.easyshare.mvp.view.fragment;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.fragment.DaggerHistoricalMessageFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.HistoricalMessageFragmentModule;
import com.haotang.easyshare.mvp.model.entity.res.HistoricalMessage;
import com.haotang.easyshare.mvp.presenter.HistoricalMessageFragmentPresenter;
import com.haotang.easyshare.mvp.view.adapter.HistoricalMessagelAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IHistoricalMessageFragmentView;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.DensityUtil;

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
    private List<HistoricalMessage> list = new ArrayList<HistoricalMessage>();
    private HistoricalMessagelAdapter historicalMessagelAdapter;

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
        for (int i = 0; i < 20; i++) {
            list.add(new HistoricalMessage("2013年的保时捷Boxster，一般人找不到发动机在哪",
                    "04-07  01:55", "04-10  01:55", "这一看就是二十多年，家里的汽车杂志堆积如山。长大后修过车，玩过车，倒过车，年过三旬，而立之年，检车车让老司机的一技之长有了发挥的余地，这里要感谢下检车车和一直以来信赖老司机的车友们。"));
        }
        rvHistorymsg.setHasFixedSize(true);
        rvHistorymsg.setLayoutManager(new LinearLayoutManager(mActivity));
        historicalMessagelAdapter = new HistoricalMessagelAdapter(R.layout.item_historymsg, list);
        rvHistorymsg.setAdapter(historicalMessagelAdapter);
        //添加自定义分割线
        rvHistorymsg.addItemDecoration(new DividerLinearItemDecoration(mActivity, LinearLayoutManager.VERTICAL, DensityUtil.dp2px(mActivity,15),
                ContextCompat.getColor(mActivity, R.color.af8f8f8)));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        historicalMessagelAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        srlHistorymsg.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
}