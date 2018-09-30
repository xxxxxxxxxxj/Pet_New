package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.activity.DaggerSelectCouponActivityCommponent;
import com.haotang.easyshare.di.module.activity.SelectCouponActivityModule;
import com.haotang.easyshare.mvp.model.entity.event.SelectCouponEvent;
import com.haotang.easyshare.mvp.model.entity.res.MyCoupon;
import com.haotang.easyshare.mvp.presenter.SelectCouponPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.SelectCouponAdapter;
import com.haotang.easyshare.mvp.view.iview.ISelectCouponView;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 选择优惠券
 */
public class SelectCouponActivity extends BaseActivity<SelectCouponPresenter> implements ISelectCouponView {
    private final static String TAG = MyCouponActivity.class.getSimpleName();
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_selectcoupon)
    RecyclerView rvSelectcoupon;
    @BindView(R.id.mrl_selectcoupon)
    MaterialRefreshLayout mrlSelectcoupon;
    private double price;
    private List<MyCoupon.DataBean.DatasetBean> list = new ArrayList<MyCoupon.DataBean.DatasetBean>();
    private int page = 1;
    private List<MyCoupon.DataBean.DatasetBean> localList = new ArrayList<MyCoupon.DataBean.DatasetBean>();
    private SelectCouponAdapter selectCouponAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_select_coupon;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        price = getIntent().getDoubleExtra("price", 0);
        activityListManager.addActivity(this);
        DaggerSelectCouponActivityCommponent.builder().
                selectCouponActivityModule(new SelectCouponActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("选择优惠券");
        rvSelectcoupon.setHasFixedSize(true);
        rvSelectcoupon.setLayoutManager(new LinearLayoutManager(this));
        selectCouponAdapter = new SelectCouponAdapter(R.layout.item_selectcoupon, list);
        rvSelectcoupon.setAdapter(selectCouponAdapter);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_f8_15));
        rvSelectcoupon.addItemDecoration(divider);
        mrlSelectcoupon.autoRefresh();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        selectCouponAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (list.size() > position) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setSelect(false);
                    }
                    MyCoupon.DataBean.DatasetBean datasetBean = list.get(position);
                    datasetBean.setSelect(true);
                    selectCouponAdapter.notifyDataSetChanged();
                    DevRing.busManager().postEvent(new SelectCouponEvent(datasetBean.getAmount(),datasetBean.getAmountTxt(),datasetBean.getDescription(),datasetBean.getReduceType(),datasetBean.getStartTime(),datasetBean.getId(),datasetBean.getState(),datasetBean.getIsAvali(),datasetBean.getEndTime(),datasetBean.getTitle(),datasetBean.isSelect()));
                    finish();
                }
            }
        });
        mrlSelectcoupon.setMaterialRefreshListener(new MaterialRefreshListener() {

            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                refresh();
            }

            @Override
            public void onfinish() {
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                loadMore();
            }
        });
    }

    private void loadMore() {
        getData();
    }

    private void getData() {
        localList.clear();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("page", page + "");
        builder.addFormDataPart("price", price + "");
        RequestBody build = builder.build();
        mPresenter.match(UrlConstants.getMapHeader(this),build);
    }

    private void refresh() {
        page = 1;
        list.clear();
        getData();
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
    public void matchFail(int code, String msg) {
        disMissDialog();
        mrlSelectcoupon.finishRefresh();
        mrlSelectcoupon.finishRefreshLoadMore();
        selectCouponAdapter.setEmptyView(setEmptyViewBase(1, msg, R.mipmap.no_net_orerror, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        }));
        RingLog.e(TAG, "matchFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }

    @Override
    public void matchSuccess(MyCoupon.DataBean data) {
        mrlSelectcoupon.finishRefresh();
        mrlSelectcoupon.finishRefreshLoadMore();
        if (data != null) {
            List<MyCoupon.DataBean.DatasetBean> dataset = data.getDataset();
            if (dataset != null && dataset.size() > 0) {
                localList.addAll(dataset);
            }
        }
        if (localList.size() > 0) {
            if (page == 1) {
                list.clear();
            }
            list.addAll(localList);
            page++;
        } else {
            if (page == 1) {
                selectCouponAdapter.setEmptyView(setEmptyViewBase(2, "暂无可用优惠券哦~",
                        R.mipmap.no_data, null));
            }
        }
        selectCouponAdapter.notifyDataSetChanged();
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
