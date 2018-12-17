package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.activity.DaggerCarDetailActivityCommponent;
import com.haotang.easyshare.di.module.activity.CarDetailActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.CarDetail;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.mvp.model.imageload.GlideImageLoader;
import com.haotang.easyshare.mvp.presenter.CarDetailPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.CarDetailPicAdapter;
import com.haotang.easyshare.mvp.view.iview.ICarDetailView;
import com.haotang.easyshare.mvp.view.widget.ShareBottomDialog;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.haotang.easyshare.util.UmenUtil;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 车辆详情页
 */
public class CarDetailActivity extends BaseActivity<CarDetailPresenter> implements ICarDetailView, OnBannerListener {
    private final static String TAG = CarDetailActivity.class.getSimpleName();
    @BindView(R.id.iv_titlebar_other)
    ImageView ivTitlebarOther;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.banner_car_detail)
    Banner bannerCarDetail;
    @BindView(R.id.tv_car_detail_tgj)
    TextView tvCarDetailTgj;
    @BindView(R.id.tv_car_detail_name)
    TextView tvCarDetailName;
    @BindView(R.id.tv_car_detail_xh)
    TextView tvCarDetailXh;
    @BindView(R.id.tv_car_detail_4sprice)
    TextView tvCarDetail4sprice;
    @BindView(R.id.ll_titlebar_other)
    LinearLayout ll_titlebar_other;
    @BindView(R.id.tv_car_detail_car)
    TextView tvCarDetailCar;
    @BindView(R.id.tv_car_detail_model)
    TextView tvCarDetailModel;
    @BindView(R.id.rv_car_detail_clxq)
    RecyclerView rvCarDetailClxq;
    @BindView(R.id.tfl_car_detail_clbq)
    TagFlowLayout tflCarDetailClbq;
    @BindView(R.id.rv_car_detail_cxcs)
    RecyclerView rvCarDetailCxcs;
    @BindView(R.id.tfl_car_detail_clys)
    TagFlowLayout tflCarDetailClys;
    private int id;
    private PostBean.DataBean.ShareMap shareMap;
    private List<AdvertisementBean.DataBean> bannerList = new ArrayList<AdvertisementBean.DataBean>();
    private ArrayList<AdvertisementBean.DataBean> detailImgs = new ArrayList<AdvertisementBean.DataBean>();
    private ArrayList<AdvertisementBean.DataBean> paramImgs = new ArrayList<AdvertisementBean.DataBean>();
    private CarDetailPicAdapter carDetailPicAdapter;
    private CarDetailPicAdapter carDetailParamPicAdapter;
    private String car;
    private ArrayList<String> imgList = new ArrayList<String>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_car_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        id = getIntent().getIntExtra("carId", 0);
        activityListManager.addActivity(this);
        DaggerCarDetailActivityCommponent.builder().
                carDetailActivityModule(new CarDetailActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("车辆详情");
        ll_titlebar_other.setVisibility(View.VISIBLE);
        ivTitlebarOther.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = ivTitlebarOther.getLayoutParams();
        layoutParams.width = DensityUtil.dp2px(this, 30);
        layoutParams.height = DensityUtil.dp2px(this, 30);
        ivTitlebarOther.setLayoutParams(layoutParams);
        ivTitlebarOther.setImageResource(R.mipmap.icon_share_new);

        detailImgs.clear();
        rvCarDetailClxq.setHasFixedSize(true);
        rvCarDetailClxq.setLayoutManager(new LinearLayoutManager(this));
        carDetailPicAdapter = new CarDetailPicAdapter(R.layout.item_cardetail_pic, detailImgs, this);
        rvCarDetailClxq.setAdapter(carDetailPicAdapter);

        paramImgs.clear();
        rvCarDetailCxcs.setHasFixedSize(true);
        rvCarDetailCxcs.setLayoutManager(new LinearLayoutManager(this));
        carDetailParamPicAdapter = new CarDetailPicAdapter(R.layout.item_cardetail_pic, paramImgs, this);
        rvCarDetailCxcs.setAdapter(carDetailParamPicAdapter);
        UmenUtil.UmengEventStatistics(this, UmenUtil.yxzx10);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showDialog();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("id", id + "");
        RequestBody build = builder.build();
        mPresenter.detail(UrlConstants.getMapHeader(this), build);
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.iv_titlebar_back, R.id.ll_titlebar_other, R.id.tv_car_detail_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.ll_titlebar_other:
                if (shareMap != null) {
                    if (StringUtil.isNotEmpty(shareMap.getUrl())) {
                        if (!shareMap.getUrl().startsWith("http:")
                                && !shareMap.getUrl().startsWith("https:") && !shareMap.getUrl().startsWith("file:///")) {
                            shareMap.setUrl(UrlConstants.getServiceBaseUrl() + shareMap.getUrl());
                        }
                        if (shareMap.getUrl().contains("?")) {
                            shareMap.setUrl(shareMap.getUrl() + "&carid" + id);
                        } else {
                            shareMap.setUrl(shareMap.getUrl() + "?carid" + id);
                        }
                    }
                    ShareBottomDialog dialog = new ShareBottomDialog();
                    dialog.setShareInfo(shareMap.getTitle(), shareMap.getContent(),
                            shareMap.getUrl(), shareMap.getImg());
                    dialog.setType(3);
                    dialog.setUuid(String.valueOf(id));
                    dialog.show(getSupportFragmentManager());
                }
                break;
            case R.id.tv_car_detail_submit:
                if (SystemUtil.checkLogin(CarDetailActivity.this)) {
                    Intent intent = new Intent(CarDetailActivity.this, CarPersonInfoActivity.class);
                    intent.putExtra("carId",id);
                    intent.putExtra("carName",car);
                    intent.putStringArrayListExtra("imgList", imgList);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(CarDetailActivity.this, LoginActivity.class));
                }
                break;
        }
    }

    @Override
    public void detailSuccess(CarDetail.DataBean data) {
        disMissDialog();
        if (data != null) {
            car = data.getCar();
            List<AdvertisementBean.DataBean> banner = data.getBanner();
            List<String> category = data.getCategory();
            List<String> color = data.getColor();
            detailImgs = data.getDetailImgs();
            paramImgs = data.getParamImgs();
            shareMap = data.getShareMap();
            if (detailImgs != null && detailImgs.size() > 0) {
                carDetailPicAdapter.notifyDataSetChanged();
            }
            if (paramImgs != null && paramImgs.size() > 0) {
                carDetailParamPicAdapter.notifyDataSetChanged();
            }
            if (category != null && category.size() > 0) {
                tflCarDetailClbq.setAdapter(new TagAdapter<String>(category) {
                    @Override
                    public View getView(FlowLayout parent, int position, final String s) {
                        View view = (View) View.inflate(CarDetailActivity.this, R.layout.item_cardetail_bq,
                                null);
                        TextView tv_item_cardetail_bq = (TextView) view.findViewById(R.id.tv_item_cardetail_bq);
                        tv_item_cardetail_bq.setText(s);
                        return view;
                    }
                });
            }
            if (color != null && color.size() > 0) {
                tflCarDetailClys.setAdapter(new TagAdapter<String>(color) {
                    @Override
                    public View getView(FlowLayout parent, int position, final String s) {
                        View view = (View) View.inflate(CarDetailActivity.this, R.layout.item_cardetail_bq,
                                null);
                        TextView tv_item_cardetail_bq = (TextView) view.findViewById(R.id.tv_item_cardetail_bq);
                        tv_item_cardetail_bq.setText(s);
                        return view;
                    }
                });
            }
            if (banner != null && banner.size() > 0) {
                bannerCarDetail.setVisibility(View.VISIBLE);
                bannerList.clear();
                bannerList.addAll(banner);
                setBanner(banner);
            } else {
                bannerCarDetail.setVisibility(View.GONE);
            }
            StringUtil.setText(tvCarDetailName, data.getCar(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvCarDetailCar, data.getCar(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvCarDetailModel, data.getCar(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvCarDetailXh, data.getBatteryLife(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvCarDetailTgj, "团购$" + data.getGroupPrice(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvCarDetail4sprice, "4S店$" + data.getShopPrice(), "", View.VISIBLE, View.VISIBLE);
        }
    }

    private void setBanner(List<AdvertisementBean.DataBean> data) {
        imgList.clear();
        for (int i = 0; i < data.size(); i++) {
            imgList.add(data.get(i).getImg());
        }
        bannerCarDetail.setImages(imgList)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .start();
    }

    @Override
    public void detailFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "detailFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
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
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
        SystemUtil.goneJP(this);
    }

    @Override
    public void OnBannerClick(int position) {
        RingLog.e(TAG, "position:" + position);
        if (bannerList != null && bannerList.size() > 0 && bannerList.size() > position) {
            AdvertisementBean.DataBean dataBean = bannerList.get(position);
            if (dataBean != null) {
                if (dataBean.getDisplay() == 1) {//原生

                } else if (dataBean.getDisplay() == 2) {//H5
                    startActivity(new Intent(this, WebViewActivity.class).putExtra(WebViewActivity.URL_KEY, dataBean.getDestination()));
                }
            }
        }
    }
}
