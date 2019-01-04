package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.activity.DaggerChargingPileDetailActivityCommponent;
import com.haotang.easyshare.di.module.activity.ChargingPileDetailActivityModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshEvent;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.ChargeDetailBean;
import com.haotang.easyshare.mvp.model.entity.res.CommentBean;
import com.haotang.easyshare.mvp.model.entity.res.CommentImg;
import com.haotang.easyshare.mvp.model.entity.res.CommentTag;
import com.haotang.easyshare.mvp.model.entity.res.PostBean;
import com.haotang.easyshare.mvp.model.imageload.GlideImageLoader;
import com.haotang.easyshare.mvp.presenter.ChargingPileDetailPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.CommentDetailAdapter;
import com.haotang.easyshare.mvp.view.adapter.UseNoticesAdapter;
import com.haotang.easyshare.mvp.view.iview.IChargingPileDetailView;
import com.haotang.easyshare.mvp.view.widget.DragView;
import com.haotang.easyshare.mvp.view.widget.NoScollFullLinearLayoutManager;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.mvp.view.widget.ShareBottomDialog;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.SharedPreferenceUtil;
import com.haotang.easyshare.util.SignUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.haotang.easyshare.util.UmenUtil;
import com.haotang.easyshare.verticalbanner.VerticalBannerView;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;
import com.umeng.analytics.MobclickAgent;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;


/**
 * 充电桩详情
 */
public class ChargingPileDetailActivity extends BaseActivity<ChargingPileDetailPresenter> implements IChargingPileDetailView, AMapLocationListener, OnBannerListener {
    private final static String TAG = ChargingPileDetailActivity.class.getSimpleName();
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.iv_chargingdetail_sc)
    ImageView ivChargingdetailSc;
    @BindView(R.id.tv_chargingdetail_pl)
    TextView tvChargingdetailPl;
    @BindView(R.id.tv_titlebar_title)
    TextView tv_titlebar_title;
    @BindView(R.id.tv_chargingdetail_ggorgr)
    TextView tvChargingdetailGgorgr;
    @BindView(R.id.tv_chargingdetail_name)
    TextView tvChargingdetailName;
    @BindView(R.id.tv_chargingdetail_cdf)
    TextView tvChargingdetailCdf;
    @BindView(R.id.tv_chargingdetail_fwf)
    TextView tvChargingdetailFwf;
    @BindView(R.id.tv_chargingdetail_kuaichong_num)
    TextView tvChargingdetailKuaichongNum;
    @BindView(R.id.tv_chargingdetail_manchong_num)
    TextView tvChargingdetailManchongNum;
    @BindView(R.id.tv_chargingdetail_kongxian_num)
    TextView tvChargingdetailKongxianNum;
    @BindView(R.id.vbv_chargingdetail)
    VerticalBannerView vbvChargingdetail;
    @BindView(R.id.tv_chargingdetail_juli)
    TextView tvChargingdetailJuli;
    @BindView(R.id.ll_chargingdetail_daohang)
    LinearLayout llChargingdetailDaohang;
    @BindView(R.id.tv_chargingdetail_address)
    TextView tvChargingdetailAddress;
    @BindView(R.id.tv_chargingdetail_yys)
    TextView tvChargingdetailYys;
    @BindView(R.id.tv_chargingdetail_kfsj)
    TextView tvChargingdetailKfsj;
    @BindView(R.id.tv_chargingdetail_zffs)
    TextView tvChargingdetailZffs;
    @BindView(R.id.tv_chargingdetail_tcf)
    TextView tvChargingdetailTcf;
    @BindView(R.id.tv_chargingdetail_zdbz)
    TextView tvChargingdetailZdbz;
    @BindView(R.id.iv_chargedetail_wegit)
    DragView iv_chargedetail_wegit;
    @BindView(R.id.banner_chargingdetail)
    Banner banner_chargingdetail;
    @BindView(R.id.ll_chargingdetail_pl)
    LinearLayout ll_chargingdetail_pl;
    @BindView(R.id.tv_chargingdetail_qbpl)
    TextView tv_chargingdetail_qbpl;
    @BindView(R.id.rv_chargingdetail_pl)
    RecyclerView rv_chargingdetail_pl;
    private String uuid;
    private String city;
    private double lat;
    private double lng;
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;
    private String address;
    private double chargeLng;
    private double chargeLat;
    private int is_collect;
    private Map<String, String> parmMap = new HashMap<String, String>();
    private PostBean.DataBean.ShareMap shareMap;
    private double serchLat;
    private double serchLng;
    private AdvertisementBean.DataBean dataBean;
    private List<CommentBean.Comment> list = new ArrayList<CommentBean.Comment>();
    private CommentDetailAdapter commentDetailAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_charging_pile_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerChargingPileDetailActivityCommponent.builder().
                chargingPileDetailActivityModule(new ChargingPileDetailActivityModule(this, this)).build().inject(this);
        uuid = getIntent().getStringExtra("uuid");
        serchLat = getIntent().getDoubleExtra("serchLat", 0);
        serchLng = getIntent().getDoubleExtra("serchLng", 0);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tv_titlebar_title.setText("站点详情");
        if (serchLat > 0 && serchLng > 0) {
            refresh();
        } else {
            setLocation();
        }
        UmenUtil.UmengEventStatistics(this, UmenUtil.yxzx3);
        rv_chargingdetail_pl.setHasFixedSize(true);
        rv_chargingdetail_pl.setNestedScrollingEnabled(false);
        NoScollFullLinearLayoutManager noScollFullLinearLayoutManager = new NoScollFullLinearLayoutManager(this);
        noScollFullLinearLayoutManager.setScrollEnabled(false);
        rv_chargingdetail_pl.setLayoutManager(noScollFullLinearLayoutManager);
        commentDetailAdapter = new CommentDetailAdapter(R.layout.item_comment, list);
        rv_chargingdetail_pl.setAdapter(commentDetailAdapter);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_f8_15));
        rv_chargingdetail_pl.addItemDecoration(divider);
    }

    private void setLocation() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                lat = amapLocation.getLatitude();//获取纬度
                lng = amapLocation.getLongitude();//获取经度
                city = amapLocation.getCity();
                amapLocation.getAddress();
                RingLog.d(TAG, "定位成功lat = "
                        + lat + ", lng = "
                        + lng + ",city = " + city + ",address = " + amapLocation.getAddress());
                if (lat > 0 && lng > 0) {
                    refresh();
                    mlocationClient.stopLocation();
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                RingLog.d(TAG, "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    private void refresh() {
        showDialog();
        Map<String, String> mapHeader = UrlConstants.getMapHeader(ChargingPileDetailActivity.this);
        if (serchLat > 0 && serchLng > 0) {
            mapHeader.put("lng", String.valueOf(serchLng));
            mapHeader.put("lat", String.valueOf(serchLat));
            mapHeader.put("key", AppConfig.SERVER_KEY);
            mapHeader.put("uuid", uuid);
            RingLog.d(TAG, "mapHeader =  " + mapHeader.toString());
            String md5 = SignUtil.sign(mapHeader, "MD5");
            RingLog.d(TAG, "md5 =  " + md5);
            mPresenter.detail(UrlConstants.getMapHeader(this), serchLng, serchLat, uuid, md5);
        } else if (lat > 0 && lng > 0) {
            mapHeader.put("lng", String.valueOf(lng));
            mapHeader.put("lat", String.valueOf(lat));
            mapHeader.put("key", AppConfig.SERVER_KEY);
            mapHeader.put("uuid", uuid);
            RingLog.d(TAG, "mapHeader =  " + mapHeader.toString());
            String md5 = SignUtil.sign(mapHeader, "MD5");
            RingLog.d(TAG, "md5 =  " + md5);
            mPresenter.detail(UrlConstants.getMapHeader(this), lng, lat, uuid, md5);
        }
        showDialog();
        mPresenter.commentList(UrlConstants.getMapHeader(this), uuid, 1);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                .addFormDataPart("category", "6").build();
        mPresenter.list(UrlConstants.getMapHeader(this), body);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }

    @OnClick({R.id.iv_titlebar_back, R.id.ll_chargingdetail_sc, R.id.ll_chargingdetail_share,
            R.id.rl_chargingdetail_pl, R.id.ll_chargingdetail_call,
            R.id.ll_chargingdetail_daohang, R.id.iv_chargedetail_wegit, R.id.rl_chargingdetail_pl_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_chargingdetail_pl_more:
                startActivity(new Intent(ChargingPileDetailActivity.this, CommentDetailActivity.class).putExtra("uuid", uuid));
                break;
            case R.id.iv_chargedetail_wegit:
                if (dataBean != null) {
                    if (dataBean.getDisplay() == 1) {//原生

                    } else if (dataBean.getDisplay() == 2) {//H5
                        startActivity(new Intent(ChargingPileDetailActivity.this, WebViewActivity.class).putExtra(WebViewActivity.URL_KEY, dataBean.getDestination()));
                    }
                }
                break;
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.ll_chargingdetail_sc:
                if (SystemUtil.checkLogin(this)) {
                    showDialog();
                    parmMap.clear();
                    parmMap.put("uuid", uuid);
                    if (is_collect == 0) {//是否已收藏(0:否、1:是)
                        mPresenter.follow(UrlConstants.getMapHeader(this), parmMap);
                    } else if (is_collect == 1) {
                        mPresenter.cancel(UrlConstants.getMapHeader(this), parmMap);
                    }
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.ll_chargingdetail_share:
                if (shareMap != null) {
                    if (shareMap.getUrl() != null && !TextUtils.isEmpty(shareMap.getUrl())) {
                        if (!shareMap.getUrl().startsWith("http:")
                                && !shareMap.getUrl().startsWith("https:") && !shareMap.getUrl().startsWith("file:///")) {
                            shareMap.setUrl(UrlConstants.getServiceBaseUrl() + shareMap.getUrl());
                        }
                        if (shareMap.getUrl().contains("?")) {
                            shareMap.setUrl(shareMap.getUrl() + "&system=android_" + SystemUtil.getCurrentVersion(this)
                                    + "&imei="
                                    + SystemUtil.getIMEI(this)
                                    + "&phone="
                                    + SharedPreferenceUtil.getInstance(ChargingPileDetailActivity.this).getString("cellphone", "") + "&phoneModel="
                                    + android.os.Build.BRAND + " " + android.os.Build.MODEL
                                    + "&phoneSystemVersion=" + "Android "
                                    + android.os.Build.VERSION.RELEASE + "&petTimeStamp="
                                    + System.currentTimeMillis());
                        } else {
                            shareMap.setUrl(shareMap.getUrl() + "?system=android_" + SystemUtil.getCurrentVersion(this)
                                    + "&imei="
                                    + SystemUtil.getIMEI(this)
                                    + "&phone="
                                    + SharedPreferenceUtil.getInstance(ChargingPileDetailActivity.this).getString("cellphone", "") + "&phoneModel="
                                    + android.os.Build.BRAND + " " + android.os.Build.MODEL
                                    + "&phoneSystemVersion=" + "Android "
                                    + android.os.Build.VERSION.RELEASE + "&petTimeStamp="
                                    + System.currentTimeMillis());
                        }
                        if (serchLat > 0 && serchLng > 0) {
                            shareMap.setUrl(shareMap.getUrl() + "&lat=" + serchLat + "&lng=" + serchLng + "&uuid=" + uuid);
                        } else if (lat > 0 && lng > 0) {
                            shareMap.setUrl(shareMap.getUrl() + "&lat=" + lat + "&lng=" + lng + "&uuid=" + uuid);
                        }
                    }
                    ShareBottomDialog dialog = new ShareBottomDialog();
                    dialog.setShareInfo(shareMap.getTitle(), shareMap.getContent(),
                            shareMap.getUrl(), shareMap.getImg());
                    dialog.setType(2);
                    dialog.setUuid(uuid);
                    dialog.show(getSupportFragmentManager());
                }
                break;
            case R.id.rl_chargingdetail_pl:
                startActivity(new Intent(ChargingPileDetailActivity.this, CommentDetailActivity.class).putExtra("uuid", uuid));
                break;
            case R.id.ll_chargingdetail_call:
                SystemUtil.cellPhone(ChargingPileDetailActivity.this, "");
                break;
            case R.id.ll_chargingdetail_daohang:
                if (serchLat > 0 && serchLng > 0) {
                    SystemUtil.goNavigation(this, chargeLat, chargeLng, "", address, serchLat, serchLng, uuid);
                } else if (lat > 0 && lng > 0) {
                    SystemUtil.goNavigation(this, chargeLat, chargeLng, "", address, lat, lng, uuid);
                }
                break;
        }
    }

    @Override
    public void detailSuccess(ChargeDetailBean data) {
        disMissDialog();
        if (data != null) {
            shareMap = data.getShareMap();
            is_collect = data.getIsCollect();
            uuid = data.getUuid();
            address = data.getAddress();
            chargeLat = data.getLat();
            chargeLng = data.getLng();
            StringUtil.setText(tvChargingdetailName, data.getTitle(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailJuli, "距您"+data.getDistance(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailAddress, data.getAddress(), "", View.VISIBLE, View.VISIBLE);
            String kfsj = "";
            if (data.getOpenTime() != null && StringUtil.isNotEmpty(data.getOpenTime())) {
                if (data.getOpenTime().contains("开放时间:")) {
                    String[] split = data.getOpenTime().split("开放时间:");
                    if (split != null && split.length > 1) {
                        kfsj = split[1];
                    }
                } else {
                    kfsj = data.getOpenTime();
                }
            }
            StringUtil.setText(tvChargingdetailKfsj, kfsj, "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailCdf, data.getElectricityPrice(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailFwf, data.getServiceFee(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailYys, data.getProvider(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailZffs, data.getPayWay(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailTcf, data.getParkingPrice(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailZdbz, data.getRemark(), "", View.VISIBLE, View.VISIBLE);
            if (data.getCommentTotal() > 0) {
                ll_chargingdetail_pl.setVisibility(View.VISIBLE);
                tvChargingdetailPl.setVisibility(View.VISIBLE);
                StringUtil.setText(tvChargingdetailPl, data.getCommentTotal() + "", "", View.VISIBLE, View.VISIBLE);
                StringUtil.setText(tv_chargingdetail_qbpl, "全部评论（" + data.getCommentTotal() + "）", "", View.VISIBLE, View.VISIBLE);
            } else {
                tvChargingdetailPl.setVisibility(View.GONE);
                ll_chargingdetail_pl.setVisibility(View.GONE);
            }
            if (is_collect == 0) {//是否已收藏(0:否、1:是)
                ivChargingdetailSc.setImageResource(R.mipmap.sc_not);
            } else if (is_collect == 1) {
                ivChargingdetailSc.setImageResource(R.mipmap.sc);
            }
            if (data.getIsPrivate() == 0) {//公共
                tvChargingdetailGgorgr.setText("公共");
                tvChargingdetailGgorgr.setBackgroundResource(R.drawable.bg_round_yew5);
            } else if (data.getIsPrivate() == 1) {//个人
                tvChargingdetailGgorgr.setText("个人");
                tvChargingdetailGgorgr.setBackgroundResource(R.drawable.bg_round_red5);
            }
            StringUtil.setText(tvChargingdetailKuaichongNum, "快充" + data.getFastNum() + "个", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailManchongNum, "慢充" + data.getSlowNum() + "个", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailKongxianNum, "空闲" + data.getFreeNum() + "个", "", View.VISIBLE, View.VISIBLE);
            List<ChargeDetailBean.UseNotices> useNotices = data.getUseNotices();
            if (useNotices != null && useNotices.size() > 0) {
                vbvChargingdetail
                        .setVisibility(View.VISIBLE);
                vbvChargingdetail.bringToFront();
                try {
                    vbvChargingdetail.setAdapter(new UseNoticesAdapter(useNotices));
                    vbvChargingdetail.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                vbvChargingdetail
                        .setVisibility(View.GONE);
            }
            if (data.getDetailImgs() != null && data.getDetailImgs().size() > 0) {
                banner_chargingdetail.setImages(data.getDetailImgs())
                        .setImageLoader(new GlideImageLoader())
                        .setOnBannerListener(this)
                        .start();
            }
        }
    }

    @Override
    public void detailFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "detailFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }

    @Override
    public void followSuccess(AddChargeBean data) {
        disMissDialog();
        is_collect = 1;
        ivChargingdetailSc.setImageResource(R.mipmap.sc);
        DevRing.busManager().postEvent(new RefreshEvent(RefreshEvent.COLLECT_OR_CANCEL_CHARGE));
        RingToast.show("收藏站点成功");
    }

    @Override
    public void followFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "detailFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }

    @Override
    public void cancelSuccess(AddChargeBean data) {
        disMissDialog();
        is_collect = 0;
        ivChargingdetailSc.setImageResource(R.mipmap.sc_not);
        DevRing.busManager().postEvent(new RefreshEvent(RefreshEvent.COLLECT_OR_CANCEL_CHARGE));
    }

    @Override
    public void cancelFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "detailFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }

    @Override
    public void listSuccess(List<AdvertisementBean.DataBean> data) {
        if (data != null && data.size() > 0) {
            dataBean = data.get(0);
            if (dataBean != null) {
                final String img = dataBean.getImg();
                if (StringUtil.isNotEmpty(img)) {
                    Glide.with(ChargingPileDetailActivity.this)
                            .load(img)
                            .asBitmap()//强制Glide返回一个Bitmap对象
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                    int width = bitmap.getWidth();
                                    int height = bitmap.getHeight();
                                    int dp2pxWidth = DensityUtil.dp2px(ChargingPileDetailActivity.this, width / 2);
                                    int dp2pxHeight = DensityUtil.dp2px(ChargingPileDetailActivity.this, height / 2);
                                    iv_chargedetail_wegit.setVisibility(View.VISIBLE);
                                    iv_chargedetail_wegit.setImageWidth(dp2pxWidth);
                                    iv_chargedetail_wegit.setImageHeight(dp2pxHeight);
                                    GlideUtil.loadNetImg(ChargingPileDetailActivity.this, img, iv_chargedetail_wegit, 0,
                                            dp2pxWidth, dp2pxHeight);
                                }
                            });
                }
            }
        }
    }

    @Override
    public void listFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "detailFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }

    @Override
    public void commentListSuccess(CommentBean data) {
        list.clear();
        if (data != null) {
            List<CommentBean.Comment> comments = data.getComments();
            if (comments != null && comments.size() > 0) {
                for (int i = 0; i < comments.size(); i++) {
                    CommentBean.Comment comment = comments.get(i);
                    if (comment != null) {
                        List<String> media = comment.getMedia();
                        List<String> tags = comment.getTags();
                        if (media != null && media.size() > 0) {
                            List<CommentImg> mediaList = new ArrayList<CommentImg>();
                            mediaList.clear();
                            for (int k = 0; k < media.size(); k++) {
                                mediaList.add(new CommentImg(media.get(k), false));
                            }
                            comment.setMediaList(mediaList);
                        }
                        if (tags != null && tags.size() > 0) {
                            List<CommentTag> tagList = new ArrayList<CommentTag>();
                            for (int k = 0; k < tags.size(); k++) {
                                tagList.add(new CommentTag(tags.get(k), false));
                            }
                            comment.setTagList(tagList);
                        }
                    }
                }
                list.addAll(comments);
            }
        }
        if (list.size() > 0) {
            ll_chargingdetail_pl.setVisibility(View.VISIBLE);
            commentDetailAdapter.notifyDataSetChanged();
        } else {
            ll_chargingdetail_pl.setVisibility(View.GONE);
        }
    }

    @Override
    public void commentListFail(int code, String msg) {
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
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe
    public void refresh(RefreshEvent data) {
        if (data != null && data.getRefreshIndex() == RefreshEvent.SAVE_CHARGE_COMMENT) {
            if (serchLat > 0 && serchLng > 0) {
                refresh();
            } else {
                mlocationClient.startLocation();
            }
        }
    }

    @Override
    public void OnBannerClick(int position) {
        RingLog.e(TAG, "position:" + position);
    }
}
