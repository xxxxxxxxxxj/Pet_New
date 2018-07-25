package com.haotang.easyshare.mvp.view.fragment;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.fragment.DaggerRechargeFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.RechargeFragmentModule;
import com.haotang.easyshare.mvp.model.entity.res.RechargeFrag;
import com.haotang.easyshare.mvp.presenter.RechargeFragmentPresenter;
import com.haotang.easyshare.mvp.view.adapter.RechargeFragAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IRechargeFragmentView;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
    private int mNextRequestPage = 1;
    private int pageSize;
    private List<RechargeFrag> list = new ArrayList<RechargeFrag>();
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
    protected void initView() {
        DaggerRechargeFragmentCommponent.builder()
                .rechargeFragmentModule(new RechargeFragmentModule(this, mActivity))
                .build()
                .inject(this);

        srlRechargefrag.setRefreshing(true);
        srlRechargefrag.setColorSchemeColors(Color.rgb(47, 223, 189));
        rvRechargefrag.setHasFixedSize(true);
        rvRechargefrag.setLayoutManager(new LinearLayoutManager(mActivity));
        for (int i = 0; i < 10; i++) {
            list.add(new RechargeFrag("充电消费", "2018-07-02 23:50", -27.96));
        }
        rechargeFragAdapter = new RechargeFragAdapter(R.layout.item_rechargefrag, list);
        rvRechargefrag.setAdapter(rechargeFragAdapter);
        //添加自定义分割线
        rvRechargefrag.addItemDecoration(new DividerLinearItemDecoration(mActivity, LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(mActivity, 1),
                ContextCompat.getColor(mActivity, R.color.aDDDDDD)));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void requestData() {

    }
}
