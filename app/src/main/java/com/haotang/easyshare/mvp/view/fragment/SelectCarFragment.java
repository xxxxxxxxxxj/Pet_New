package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.fragment.DaggerSelectCarFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.SelectCarFragmentModule;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.HotCarBean;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.mvp.presenter.SelectCarFragmentPresenter;
import com.haotang.easyshare.mvp.view.activity.AllBrandsActivity;
import com.haotang.easyshare.mvp.view.adapter.HotPointCarAdapter;
import com.haotang.easyshare.mvp.view.adapter.SelectCarAdAdapter;
import com.haotang.easyshare.mvp.view.adapter.SelectedCarAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.ISelectCarFragmentView;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullLinearLayoutManager;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:选车主界面</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/20 14:36
 */
public class SelectCarFragment extends BaseFragment<SelectCarFragmentPresenter> implements ISelectCarFragmentView {
    protected final static String TAG = SelectCarFragment.class.getSimpleName();
    @BindView(R.id.rv_selectcar_top)
    RecyclerView rvSelectcarTop;
    @BindView(R.id.rl_selectcar_rmpp_more)
    LinearLayout rlSelectcarRmppMore;
    @BindView(R.id.rl_selectcar_rmpp)
    RelativeLayout rlSelectcarRmpp;
    @BindView(R.id.rv_selectcar_rmpp)
    RecyclerView rvSelectcarRmpp;
    @BindView(R.id.rv_selectcar_jxtj)
    RecyclerView rvSelectcarJxtj;
    private List<HotCarBean.DataBean> carList = new ArrayList<HotCarBean.DataBean>();
    private HotPointCarAdapter hotPointCarAdapter;
    private List<HotSpecialCarBean.DataBean> selectedCarList = new ArrayList<HotSpecialCarBean.DataBean>();
    private SelectedCarAdapter selectedCarAdapter;
    private List<AdvertisementBean.DataBean> adList = new ArrayList<AdvertisementBean.DataBean>();
    private SelectCarAdAdapter selectCarAdAdapter;

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.selectfragment;
    }

    @Override
    protected void initView() {
        DaggerSelectCarFragmentCommponent.builder()
                .selectCarFragmentModule(new SelectCarFragmentModule(this, mActivity))
                .build()
                .inject(this);
        rvSelectcarTop.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mActivity);
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvSelectcarTop.setLayoutManager(linearLayoutManager1);
        selectCarAdAdapter = new SelectCarAdAdapter(R.layout.item_selectcat_ad, adList);
        rvSelectcarTop.setAdapter(selectCarAdAdapter);
        //添加自定义分割线
        rvSelectcarTop.addItemDecoration(new DividerLinearItemDecoration(mActivity, LinearLayoutManager.HORIZONTAL,
                DensityUtil.dp2px(mActivity, 10),
                ContextCompat.getColor(mActivity, R.color.transparent)));

        rvSelectcarRmpp.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvSelectcarRmpp.setLayoutManager(linearLayoutManager);
        hotPointCarAdapter = new HotPointCarAdapter(R.layout.item_hotfrag_top_car, carList);
        rvSelectcarRmpp.setAdapter(hotPointCarAdapter);
        //添加自定义分割线
        rvSelectcarRmpp.addItemDecoration(new DividerLinearItemDecoration(mActivity, LinearLayoutManager.HORIZONTAL, DensityUtil.dp2px(mActivity, 15),
                ContextCompat.getColor(mActivity, R.color.af8f8f8)));

        rvSelectcarJxtj.setHasFixedSize(true);
        rvSelectcarJxtj.setNestedScrollingEnabled(false);
        NoScollFullLinearLayoutManager noScollFullLinearLayoutManager = new NoScollFullLinearLayoutManager(mActivity);
        noScollFullLinearLayoutManager.setScrollEnabled(false);
        rvSelectcarJxtj.setLayoutManager(noScollFullLinearLayoutManager);
        //添加自定义分割线
        rvSelectcarJxtj.addItemDecoration(new DividerLinearItemDecoration(mActivity, LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(mActivity, 15),
                ContextCompat.getColor(mActivity, R.color.af8f8f8)));
        selectedCarList.clear();
        selectedCarAdapter = new SelectedCarAdapter(R.layout.item_allbrands_selectedcar
                , selectedCarList);
        rvSelectcarJxtj.setAdapter(selectedCarAdapter);
    }

    @Override
    protected void initData() {
        showDialog();
        mPresenter.hot();
        mPresenter.special();
    }

    @Override
    public void specialSuccess(List<HotSpecialCarBean.DataBean> data) {
        disMissDialog();
        selectedCarList.clear();
        if (data != null && data.size() > 0) {
            selectedCarList.addAll(data);
            selectedCarAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void specialFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "specialFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
    }

    @Override
    public void hotSuccess(List<HotCarBean.DataBean> data) {
        disMissDialog();
        if (data != null && data.size() > 0) {
            carList.clear();
            carList.addAll(data);
            hotPointCarAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void hotFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "hotFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void requestData() {

    }

    @OnClick(R.id.rl_selectcar_rmpp)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.rl_selectcar_rmpp:
                startActivity(new Intent(mActivity, AllBrandsActivity.class));
                break;
        }
    }
}