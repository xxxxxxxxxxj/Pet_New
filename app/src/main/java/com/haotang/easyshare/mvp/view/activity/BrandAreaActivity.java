package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerBrandAreaActivityCommponent;
import com.haotang.easyshare.di.module.activity.BrandAreaActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.BrandAreaBean;
import com.haotang.easyshare.mvp.model.entity.res.HotPoint;
import com.haotang.easyshare.mvp.model.imageload.GlideImageLoader;
import com.haotang.easyshare.mvp.presenter.BrandAreaPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.BrandAreaAdAdapter;
import com.haotang.easyshare.mvp.view.adapter.BrandAreaHotPointAdapter;
import com.haotang.easyshare.mvp.view.iview.IBrandAreaView;
import com.haotang.easyshare.mvp.view.widget.DividerLinearItemDecoration;
import com.haotang.easyshare.mvp.view.widget.GridSpacingItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.StringUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

import static com.haotang.easyshare.R.id.iv_brand_area_ad_close;
import static com.haotang.easyshare.R.id.rv_brand_area_ad;

/**
 * 品牌专区
 */
public class BrandAreaActivity extends BaseActivity<BrandAreaPresenter> implements IBrandAreaView, OnBannerListener {
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
    private Banner banner_brandarea_top;
    private TextView tv_brandarea_rexiaotv;
    private List<AdvertisementBean.DataBean> bannerList = new ArrayList<AdvertisementBean.DataBean>();
    private String brand;

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
        brand = getIntent().getStringExtra("brand");
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
        View top = getLayoutInflater().inflate(R.layout.brandarea_top_view, (ViewGroup) rvBrandArea.getParent(), false);
        brandAreaHotPointAdapter.addHeaderView(top);
        banner_brandarea_top = (Banner) top.findViewById(R.id.banner_brandarea_top);
        tv_brandarea_rexiaotv = (TextView) top.findViewById(R.id.tv_brandarea_rexiaotv);
        StringUtil.setText(tv_brandarea_rexiaotv, brand + "热销", "", View.VISIBLE, View.VISIBLE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                .addFormDataPart("page", String.valueOf(mNextRequestPage))
                .addFormDataPart("brandId", String.valueOf(brandId))
                .build();
        mPresenter.article(body);

        MultipartBody body1 = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                .addFormDataPart("category", "3").build();
        mPresenter.list(body1);

        MultipartBody body2 = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                .addFormDataPart("category", "4").build();
        mPresenter.list1(body2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @Override
    protected void initEvent() {
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

        MultipartBody body1 = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                .addFormDataPart("category", "3").build();
        mPresenter.list(body1);

        MultipartBody body2 = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                .addFormDataPart("category", "4").build();
        mPresenter.list1(body2);
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
    public void onStart() {
        super.onStart();
        //开始轮播
        if (banner_brandarea_top != null) {
            banner_brandarea_top.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        if (banner_brandarea_top != null) {
            banner_brandarea_top.stopAutoPlay();
        }
    }

    @Override
    public void listSuccess(List<AdvertisementBean.DataBean> data) {
        if (data == null) {
            data = new ArrayList<AdvertisementBean.DataBean>();
        }
        for (int i = 0; i < 5; i++) {
            data.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549072013203471.jpg"
                    , 2, "http://www.sayiyinxiang.com/docs/ad.html"));
        }
        if (data != null && data.size() > 0) {
            bannerList.clear();
            bannerList.addAll(data);
            banner_brandarea_top.setVisibility(View.VISIBLE);
            setBanner(data);
        } else {
            banner_brandarea_top.setVisibility(View.GONE);
        }
    }

    private void setBanner(List<AdvertisementBean.DataBean> data) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < data.size(); i++) {
            list.add(data.get(i).getImg());
        }
        banner_brandarea_top.setImages(list)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
    }

    @Override
    public void listFail(int code, String msg) {
        banner_brandarea_top.setVisibility(View.GONE);
        RingLog.e(TAG, "listFail() status = " + code + "---desc = " + msg);
    }

    @Override
    public void list1Success(List<AdvertisementBean.DataBean> data) {
        if (data == null) {
            data = new ArrayList<AdvertisementBean.DataBean>();
        }
        for (int i = 0; i < 5; i++) {
            data.add(new AdvertisementBean.DataBean("http://img.sayiyinxiang.com/api/brand/imgs/15246549072013203471.jpg"
                    , 2, "http://www.sayiyinxiang.com/docs/ad.html"));
        }
        if (data != null && data.size() > 0) {
            brandAreaHotPointAdapter.setAdData(data);
        }
    }

    @Override
    public void list1Fail(int code, String msg) {
        RingLog.e(TAG, "list1Fail() status = " + code + "---desc = " + msg);
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

    @Override
    public void OnBannerClick(int position) {
        if (bannerList != null && bannerList.size() > 0 && bannerList.size() > position) {
            AdvertisementBean.DataBean dataBean = bannerList.get(position);
            if (dataBean != null) {
                if (dataBean.getDisplay() == 1) {//原生

                } else if (dataBean.getDisplay() == 2) {//H5
                    startActivity(new Intent(BrandAreaActivity.this, WebViewActivity.class).putExtra(WebViewActivity.URL_KEY, dataBean.getDestination()));
                }
            }
        }
    }
}
