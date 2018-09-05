package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerCarDetailActivityCommponent;
import com.haotang.easyshare.di.module.activity.CarDetailActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.CarDetail;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.mvp.model.imageload.GlideImageLoader;
import com.haotang.easyshare.mvp.presenter.CarDetailPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.CarDetailPicAdapter;
import com.haotang.easyshare.mvp.view.adapter.CarDetailTagAdapter;
import com.haotang.easyshare.mvp.view.iview.ICarDetailView;
import com.haotang.easyshare.mvp.view.widget.GridSpacingItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.haotang.easyshare.mvp.view.widget.ShareBottomDialog;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

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
    @BindView(R.id.tv_car_detail_gfj)
    TextView tvCarDetailGfj;
    @BindView(R.id.tv_car_detail_tgj)
    TextView tvCarDetailTgj;
    @BindView(R.id.ll_car_detail_price)
    LinearLayout llCarDetailPrice;
    @BindView(R.id.tv_car_detail_name)
    TextView tvCarDetailName;
    @BindView(R.id.tv_car_detail_xh)
    TextView tvCarDetailXh;
    @BindView(R.id.tv_car_detail_4sprice)
    TextView tvCarDetail4sprice;
    @BindView(R.id.tv_car_detail_tgprice)
    TextView tvCarDetailTgprice;
    @BindView(R.id.rv_car_detail_tag)
    RecyclerView rvCarDetailTag;
    @BindView(R.id.tv_car_detail_color)
    TextView tvCarDetailColor;
    @BindView(R.id.ll_car_detail_color)
    LinearLayout ll_car_detail_color;
    @BindView(R.id.ll_car_detail_tag)
    LinearLayout ll_car_detail_tag;
    @BindView(R.id.ll_titlebar_other)
    LinearLayout ll_titlebar_other;
    @BindView(R.id.vw_cardetail_clxq)
    View vwCardetailClxq;
    @BindView(R.id.tv_cardetail_clxq)
    TextView tvCardetailClxq;
    @BindView(R.id.rl_cardetail_clxq)
    RelativeLayout rlCardetailClxq;
    @BindView(R.id.vw_cardetail_cxcs)
    View vwCardetailCxcs;
    @BindView(R.id.tv_cardetail_cxcs)
    TextView tvCardetailCxcs;
    @BindView(R.id.rl_cardetail_cxcs)
    RelativeLayout rlCardetailCxcs;
    @BindView(R.id.rv_car_detail_pic)
    RecyclerView rvCarDetailPic;
    private int id;
    private PostBean.DataBean.ShareMap shareMap;
    private List<AdvertisementBean.DataBean> bannerList = new ArrayList<AdvertisementBean.DataBean>();
    private int currentTabIndex;
    private int flag = 1;
    private List<AdvertisementBean.DataBean> imgs = new ArrayList<AdvertisementBean.DataBean>();
    private ArrayList<AdvertisementBean.DataBean> detailImgs;
    private ArrayList<AdvertisementBean.DataBean> paramImgs;
    private CarDetailPicAdapter carDetailPicAdapter;
    private HotSpecialCarBean.DataBean carDetailData;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_car_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        carDetailData = (HotSpecialCarBean.DataBean) getIntent().getSerializableExtra("carDetailData");
        if (carDetailData != null) {
            id = carDetailData.getId();
        }
        activityListManager.addActivity(this);
        DaggerCarDetailActivityCommponent.builder().
                carDetailActivityModule(new CarDetailActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("车辆详情页");
        ll_titlebar_other.setVisibility(View.VISIBLE);
        ivTitlebarOther.setVisibility(View.VISIBLE);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        float density = metric.density; // 屏幕密度（0.75 / 1.0 / 1.5）
        ViewGroup.LayoutParams layoutParams = ivTitlebarOther.getLayoutParams();
        layoutParams.width = DensityUtil.dp2px(this, 18);
        layoutParams.height = DensityUtil.dp2px(this, 18);
        ivTitlebarOther.setLayoutParams(layoutParams);
        ivTitlebarOther.setImageResource(R.mipmap.share);
        imgs.clear();
        rvCarDetailPic.setHasFixedSize(true);
        rvCarDetailPic.setLayoutManager(new LinearLayoutManager(this));
        carDetailPicAdapter = new CarDetailPicAdapter(R.layout.item_cardetail_pic, imgs, this);
        rvCarDetailPic.setAdapter(carDetailPicAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showDialog();
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder.addFormDataPart("id", id + "");
        RequestBody build = builder.build();
        mPresenter.detail(build);
    }

    @Override
    protected void initEvent() {

    }

    @OnClick({R.id.iv_titlebar_back, R.id.ll_titlebar_other, R.id.tv_car_detail_submit, R.id.rl_cardetail_cxcs, R.id.rl_cardetail_clxq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.ll_titlebar_other:
                if (shareMap != null) {
                    ShareBottomDialog dialog = new ShareBottomDialog();
                    dialog.setShareInfo(shareMap.getTitle(), shareMap.getContent(),
                            shareMap.getUrl(), shareMap.getImg());
                    dialog.completeUrl(CarDetailActivity.this);
                    dialog.show(getSupportFragmentManager());
                }
                break;
            case R.id.tv_car_detail_submit:
                if (SystemUtil.checkLogin(CarDetailActivity.this)) {
                    Intent intent = new Intent(CarDetailActivity.this, CarPersonInfoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("carDetailData", carDetailData);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(CarDetailActivity.this, LoginActivity.class));
                }
                break;
            case R.id.rl_cardetail_clxq:
                flag = 1;
                setTab();
                break;
            case R.id.rl_cardetail_cxcs:
                flag = 2;
                setTab();
                break;
        }
    }

    @Override
    public void detailSuccess(CarDetail.DataBean data) {
        disMissDialog();
        if (data != null) {
            List<AdvertisementBean.DataBean> banner = data.getBanner();
            List<String> category = data.getCategory();
            List<String> color = data.getColor();
            detailImgs = data.getDetailImgs();
            paramImgs = data.getParamImgs();
            flag = 1;
            setTab();
            shareMap = data.getShareMap();
            if (category != null && category.size() > 0) {
                ll_car_detail_tag.setVisibility(View.VISIBLE);
                rvCarDetailTag.setHasFixedSize(true);
                rvCarDetailTag.setNestedScrollingEnabled(false);
                NoScollFullGridLayoutManager noScollFullGridLayoutManager = new
                        NoScollFullGridLayoutManager(rvCarDetailTag, this, 2, GridLayoutManager.VERTICAL, false);
                noScollFullGridLayoutManager.setScrollEnabled(false);
                rvCarDetailTag.setLayoutManager(noScollFullGridLayoutManager);
                rvCarDetailTag.addItemDecoration(new GridSpacingItemDecoration(2,
                        getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                        getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                        false));
                rvCarDetailTag.setAdapter(new CarDetailTagAdapter(R.layout.item_cardetail_tag, category));
            } else {
                ll_car_detail_tag.setVisibility(View.GONE);
            }
            if (color != null && color.size() > 0) {
                ll_car_detail_color.setVisibility(View.VISIBLE);
                String colorStr = "";
                for (int i = 0; i < color.size(); i++) {
                    if (i == color.size() - 1) {
                        colorStr = colorStr + color.get(i);
                    } else {
                        colorStr = colorStr + color.get(i) + "  ";
                    }
                }
                tvCarDetailColor.setText(colorStr);
            } else {
                ll_car_detail_color.setVisibility(View.GONE);
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
            StringUtil.setText(tvCarDetailXh, "续航" + data.getCar(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvCarDetailGfj, "官方价:¥" + data.getPrice() + "起", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvCarDetail4sprice, "¥" + data.getPrice() + "起", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvCarDetailTgj, "团购价:¥" + data.getGroupPrice() + "起", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvCarDetailTgprice, "¥" + data.getGroupPrice() + "起", "", View.VISIBLE, View.VISIBLE);
        }
    }

    private void setBanner(List<AdvertisementBean.DataBean> data) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < data.size(); i++) {
            list.add(data.get(i).getImg());
        }
        bannerCarDetail.setImages(list)
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

    private void setTab() {
        if (flag == 1) {
            vwCardetailClxq.setVisibility(View.VISIBLE);
            vwCardetailCxcs.setVisibility(View.GONE);
            tvCardetailClxq.setTextColor(getResources().getColor(R.color.a0271F0));
            tvCardetailCxcs.setTextColor(getResources().getColor(R.color.a333333));
            if (detailImgs != null && detailImgs.size() > 0) {
                imgs.clear();
                imgs.addAll(detailImgs);
                carDetailPicAdapter.notifyDataSetChanged();
            }
        } else if (flag == 2) {
            vwCardetailClxq.setVisibility(View.GONE);
            vwCardetailCxcs.setVisibility(View.VISIBLE);
            tvCardetailClxq.setTextColor(getResources().getColor(R.color.a333333));
            tvCardetailCxcs.setTextColor(getResources().getColor(R.color.a0271F0));
            if (paramImgs != null && paramImgs.size() > 0) {
                imgs.clear();
                imgs.addAll(paramImgs);
                carDetailPicAdapter.notifyDataSetChanged();
            }
        }
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
