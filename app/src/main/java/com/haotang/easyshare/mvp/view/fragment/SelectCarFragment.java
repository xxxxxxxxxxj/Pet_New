package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.fragment.DaggerSelectCarFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.SelectCarFragmentModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.HotCarBean;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.mvp.presenter.SelectCarFragmentPresenter;
import com.haotang.easyshare.mvp.view.activity.AllBrandsActivity;
import com.haotang.easyshare.mvp.view.activity.CarDetailActivity;
import com.haotang.easyshare.mvp.view.activity.ScreenCarActivity;
import com.haotang.easyshare.mvp.view.activity.WebViewActivity;
import com.haotang.easyshare.mvp.view.adapter.HotPointCarAdapter;
import com.haotang.easyshare.mvp.view.adapter.ScreenCarAdapter;
import com.haotang.easyshare.mvp.view.adapter.SelectCarAdAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.ISelectCarFragmentView;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullLinearLayoutManager;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.haotang.easyshare.util.UmenUtil;
import com.ljy.devring.other.RingLog;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

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
    @BindView(R.id.rl_selectcar_top)
    RelativeLayout rl_selectcar_top;
    private List<HotCarBean.DataBean> carList = new ArrayList<HotCarBean.DataBean>();
    private HotPointCarAdapter hotPointCarAdapter;
    private List<HotSpecialCarBean.DataBean> selectedCarList = new ArrayList<HotSpecialCarBean.DataBean>();
    private ScreenCarAdapter screenCarAdapter;
    private List<AdvertisementBean.DataBean> bannerList = new ArrayList<AdvertisementBean.DataBean>();
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
        selectCarAdAdapter = new SelectCarAdAdapter(R.layout.item_selectcat_ad, bannerList);
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
        screenCarAdapter = new ScreenCarAdapter(R.layout.item_screencar
                , selectedCarList);
        rvSelectcarJxtj.setAdapter(screenCarAdapter);
    }

    @Override
    protected void initData() {
        refresh();
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe
    public void RefreshFragment(RefreshFragmentEvent refreshFragmentEvent) {
        if (refreshFragmentEvent != null && refreshFragmentEvent.getRefreshIndex() ==
                RefreshFragmentEvent.REFRESH_SELECTCARFRAGMET && mPresenter != null) {
            RingLog.e("REFRESH_SELECTCARFRAGMET");
            refresh();
            UmenUtil.UmengEventStatistics(getActivity(),UmenUtil.yxzx8);
        }
    }

    private void refresh() {
        showDialog();
        mPresenter.hot(UrlConstants.getMapHeader(mActivity));
        mPresenter.special(UrlConstants.getMapHeader(mActivity));
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                .addFormDataPart("category", "3").build();
        mPresenter.list(UrlConstants.getMapHeader(mActivity),body);
    }

    @Override
    public void specialSuccess(List<HotSpecialCarBean.DataBean> data) {
        disMissDialog();
        selectedCarList.clear();
        if (data != null && data.size() > 0) {
            selectedCarList.addAll(data);
            screenCarAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void specialFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "specialFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
    }

    @Override
    public void listFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "specialFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
    }

    @Override
    public void listSuccess(List<AdvertisementBean.DataBean> data) {
        disMissDialog();
        if (data != null && data.size() > 0) {
            bannerList.clear();
            bannerList.addAll(data);
            rl_selectcar_top.setVisibility(View.VISIBLE);
            selectCarAdAdapter.notifyDataSetChanged();
        } else {
            rl_selectcar_top.setVisibility(View.GONE);
        }
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
        screenCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(selectedCarList.size() > position){
                    HotSpecialCarBean.DataBean dataBean = selectedCarList.get(position);
                    Intent intent = new Intent(getActivity(), CarDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("carDetailData", dataBean);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        selectCarAdAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (bannerList != null && bannerList.size() > 0 && bannerList.size() > position) {
                    AdvertisementBean.DataBean dataBean = bannerList.get(position);
                    if (dataBean != null) {
                        if (dataBean.getDisplay() == 1) {//原生

                        } else if (dataBean.getDisplay() == 2) {//H5
                            mActivity.startActivity(new Intent(mActivity, WebViewActivity.class).putExtra(WebViewActivity.URL_KEY, dataBean.getDestination()));
                        }
                    }
                }
            }
        });
        hotPointCarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (carList != null && carList.size() > 0 && carList.size() > position) {
                    HotCarBean.DataBean dataBean = carList.get(position);
                    if (dataBean != null) {
                        Intent intent = new Intent(mActivity, ScreenCarActivity.class);
                        intent.putExtra("brandId", dataBean.getId());
                        intent.putExtra("brand", dataBean.getBrand());
                        startActivity(intent);
                    }
                }
            }
        });
    }

    @Override
    public void requestData() {

    }

    @OnClick(R.id.rl_selectcar_rmpp)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.rl_selectcar_rmpp:
                startActivity(new Intent(mActivity, AllBrandsActivity.class).putExtra("flag",1));
                UmenUtil.UmengEventStatistics(getActivity(),UmenUtil.yxzx9);
                break;
        }
    }
}