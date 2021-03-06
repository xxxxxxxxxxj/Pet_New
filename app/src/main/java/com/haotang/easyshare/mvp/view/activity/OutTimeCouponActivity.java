package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.activity.DaggerOutTimeCouponActivityCommponent;
import com.haotang.easyshare.di.module.activity.OutTimeCouponActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.MyCoupon;
import com.haotang.easyshare.mvp.presenter.OutTimeCouponPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.MyCouponAdapter;
import com.haotang.easyshare.mvp.view.iview.IOutTimeCouponView;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 过期优惠券界面
 */
public class OutTimeCouponActivity extends BaseActivity<OutTimeCouponPresenter> implements IOutTimeCouponView {
    private final static String TAG = OutTimeCouponActivity.class.getSimpleName();
    private List<MyCoupon.DataBean.DatasetBean> list = new ArrayList<MyCoupon.DataBean.DatasetBean>();
    private MyCouponAdapter myCouponAdapter;
    private int page = 1;
    private List<MyCoupon.DataBean.DatasetBean> localList = new ArrayList<MyCoupon.DataBean.DatasetBean>();
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_mycoupon)
    RecyclerView rvMycoupon;
    @BindView(R.id.mrl_mycoupon)
    MaterialRefreshLayout mrlMycoupon;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_out_time_coupon;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerOutTimeCouponActivityCommponent.builder().
                outTimeCouponActivityModule(new OutTimeCouponActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("过期的优惠券");
        rvMycoupon.setHasFixedSize(true);
        rvMycoupon.setLayoutManager(new LinearLayoutManager(this));
        myCouponAdapter = new MyCouponAdapter(R.layout.item_mycoupon, list, 1);
        rvMycoupon.setAdapter(myCouponAdapter);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_f8_15));
        rvMycoupon.addItemDecoration(divider);
        mrlMycoupon.autoRefresh();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        mrlMycoupon.setMaterialRefreshListener(new MaterialRefreshListener() {

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
        builder.addFormDataPart("state", "0");
        RequestBody build = builder.build();
        mPresenter.list(UrlConstants.getMapHeader(this),build);
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
    public void listSuccess(MyCoupon.DataBean data) {
        mrlMycoupon.finishRefresh();
        mrlMycoupon.finishRefreshLoadMore();
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
                myCouponAdapter.setEmptyView(setEmptyViewBase(2, "暂无过期优惠券哦~",
                        R.mipmap.no_data, null));
            }
        }
        myCouponAdapter.notifyDataSetChanged();
    }

    @Override
    public void listFail(int code, String msg) {
        disMissDialog();
        mrlMycoupon.finishRefresh();
        mrlMycoupon.finishRefreshLoadMore();
        myCouponAdapter.setEmptyView(setEmptyViewBase(1, msg, R.mipmap.no_net_orerror, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        }));
        RingLog.e(TAG, "queryFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }
}
