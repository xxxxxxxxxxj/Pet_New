package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerBrandAreaActivityCommponent;
import com.haotang.easyshare.di.module.activity.BrandAreaActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.presenter.BrandAreaPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.BrandAreaHotPointAdapter;
import com.haotang.easyshare.mvp.view.iview.IBrandAreaView;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.DensityUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

/**
 * 品牌专区
 */
public class BrandAreaActivity extends BaseActivity<BrandAreaPresenter> implements IBrandAreaView {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.iv_titlebar_back)
    ImageView ivTitlebarBack;
    @BindView(R.id.tv_titlebar_other)
    TextView tvTitlebarOther;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_brand_area)
    RecyclerView rvBrandArea;
    @BindView(R.id.srl_brand_area)
    SwipeRefreshLayout srl_brand_area;
    private List<HotPoint.DataBean> list = new ArrayList<HotPoint.DataBean>();
    private BrandAreaHotPointAdapter brandAreaHotPointAdapter;
    private int brandId;
    private int mNextRequestPage = 1;
    private int pageSize;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_brand_area;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerBrandAreaActivityCommponent.builder().
                brandAreaActivityModule(new BrandAreaActivityModule(this, this)).build().inject(this);
        brandId = getIntent().getIntExtra("brandId", 0);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        srl_brand_area.setRefreshing(true);
        srl_brand_area.setColorSchemeColors(Color.rgb(47, 223, 189));
        tvTitlebarTitle.setText("品牌专区");
        tvTitlebarOther.setText("发帖");
        tvTitlebarOther.setVisibility(View.VISIBLE);
        rvBrandArea.setHasFixedSize(true);
        rvBrandArea.setLayoutManager(new LinearLayoutManager(this));
        //添加自定义分割线
        rvBrandArea.addItemDecoration(new DividerLinearItemDecoration(this, LinearLayoutManager.VERTICAL,
                DensityUtil.dp2px(this, 15),
                ContextCompat.getColor(this, R.color.af8f8f8)));
        brandAreaHotPointAdapter = new BrandAreaHotPointAdapter(R.layout.brandarea_rexiao_ll, list);
        rvBrandArea.setAdapter(brandAreaHotPointAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                .addFormDataPart("page", String.valueOf(mNextRequestPage))
                .addFormDataPart("brandId", String.valueOf(brandId))
                .build();
        mPresenter.article(body);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @Override
    protected void initEvent() {
        /*brandAreaHotPointAdapter.setOnBannerItemListener(new brandAreaHotPointAdapter.OnBannerItemListener() {
            @Override
            public void OnBannerItem(int position) {
                RingLog.d("position = " + position);
                brandAreaList.remove(position);
                brandAreaHotPointAdapter.notifyDataSetChanged();
            }
        });*/
        brandAreaHotPointAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        srl_brand_area.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        brandAreaHotPointAdapter.setEnableLoadMore(false);
        srl_brand_area.setRefreshing(true);
        mNextRequestPage = 1;
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                .addFormDataPart("page", String.valueOf(mNextRequestPage))
                .addFormDataPart("brandId", String.valueOf(brandId))
                .build();
        mPresenter.article(body);
    }

    private void loadMore() {
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                .addFormDataPart("page", String.valueOf(mNextRequestPage))
                .addFormDataPart("brandId", String.valueOf(brandId))
                .build();
        mPresenter.article(body);
    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_titlebar_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_titlebar_other:
                startActivity(new Intent(BrandAreaActivity.this, SendPostActivity.class).putExtra("brandId", brandId));
                break;
        }
    }

    @Override
    public void articleSuccess(List<HotPoint.DataBean> data) {
        if (mNextRequestPage == 1) {
            srl_brand_area.setRefreshing(false);
            brandAreaHotPointAdapter.setEnableLoadMore(true);
            list.clear();
        }
        brandAreaHotPointAdapter.loadMoreComplete();
        if (data != null && data.size() > 0) {
            if (mNextRequestPage == 1) {
                pageSize = data.size();
            } else {
                if (data.size() < pageSize) {
                    brandAreaHotPointAdapter.loadMoreEnd(false);
                }
            }
            list.addAll(data);
            mNextRequestPage++;
        } else {
            if (mNextRequestPage == 1) {
                brandAreaHotPointAdapter.loadMoreEnd(true);
            } else {
                brandAreaHotPointAdapter.loadMoreEnd(false);
            }
        }
        brandAreaHotPointAdapter.notifyDataSetChanged();
    }

    @Override
    public void articleFail(int code, String msg) {
        if (mNextRequestPage == 1) {
            brandAreaHotPointAdapter.setEnableLoadMore(true);
            srl_brand_area.setRefreshing(false);
        } else {
            brandAreaHotPointAdapter.loadMoreFail();
        }
        RingLog.e(TAG, "articleFail() status = " + code + "---desc = " + msg);
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
