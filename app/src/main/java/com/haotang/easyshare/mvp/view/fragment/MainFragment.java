package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundRelativeLayout;
import com.flyco.roundview.RoundTextView;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.fragment.DaggerMainFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.MainFragmentModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.model.entity.res.BrandAreaBean;
import com.haotang.easyshare.mvp.model.entity.res.MainFragChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.MainFragmentData;
import com.haotang.easyshare.mvp.model.entity.res.SerchResult;
import com.haotang.easyshare.mvp.presenter.MainFragmentPresenter;
import com.haotang.easyshare.mvp.view.activity.AddChargeActivity;
import com.haotang.easyshare.mvp.view.activity.ChargingPileDetailActivity;
import com.haotang.easyshare.mvp.view.activity.CommentDetailActivity;
import com.haotang.easyshare.mvp.view.activity.LocalChargingActivity;
import com.haotang.easyshare.mvp.view.activity.LoginActivity;
import com.haotang.easyshare.mvp.view.activity.WebViewActivity;
import com.haotang.easyshare.mvp.view.adapter.MainLocalAdapter;
import com.haotang.easyshare.mvp.view.adapter.MainSerchResultAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IMainFragmentView;
import com.haotang.easyshare.mvp.view.viewholder.MainFragmenBoDa;
import com.haotang.easyshare.mvp.view.widget.CircleImageView;
import com.haotang.easyshare.mvp.view.widget.NoScollFullLinearLayoutManager;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/4/14 20:34
 */
public class MainFragment extends BaseFragment<MainFragmentPresenter> implements
        AMapLocationListener, IMainFragmentView, AMap.OnMarkerClickListener,
        AMap.OnMapLoadedListener, PoiSearch.OnPoiSearchListener, AMap.OnMyLocationChangeListener {
    private final static String TAG = MainFragment.class.getSimpleName();
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.rv_mainfrag_localev)
    RecyclerView rvMainfragLocalev;
    @BindView(R.id.tv_mainfrag_city)
    TextView tvMainfragCity;
    @BindView(R.id.iv_mainfrag_city)
    ImageView ivMainfragCity;
    @BindView(R.id.ll_mainfrag_city)
    LinearLayout llMainfragCity;
    @BindView(R.id.iv_mainfrag_send_redpoint)
    ImageView ivMainfragSendRedpoint;
    @BindView(R.id.rl_mainfrag_send)
    RelativeLayout rlMainfragSend;
    @BindView(R.id.et_mainfrag_serch)
    EditText etMainfragSerch;
    @BindView(R.id.rll_mainfrag_serch)
    RoundRelativeLayout rllMainfragSerch;
    @BindView(R.id.rll_mainfrag_serchresult)
    RoundLinearLayout rll_mainfrag_serchresult;
    @BindView(R.id.rv_mainfrag_serchresult)
    RecyclerView rv_mainfrag_serchresult;

    @BindView(R.id.rtv_mainfrag_local)
    RoundTextView rtvMainfragLocal;
    @BindView(R.id.tmv_mainfrag_map)
    MapView tmv_mainfrag_map;
    @BindView(R.id.ll_mainfrag_rmht)
    LinearLayout llMainfragRmht;
    @BindView(R.id.rl_mainfrag_localev_more)
    LinearLayout rlMainfragLocalevMore;
    @BindView(R.id.rl_mainfrag_localev)
    RelativeLayout rlMainfragLocalev;
    @BindView(R.id.tv_mainfrag_localev_gg)
    TextView tvMainfragLocalevGg;
    @BindView(R.id.vw_mainfrag_localev_gg)
    View vwMainfragLocalevGg;
    @BindView(R.id.rl_mainfrag_localev_gg)
    RelativeLayout rlMainfragLocalevGg;
    @BindView(R.id.tv_mainfrag_localev_gr)
    TextView tvMainfragLocalevGr;
    @BindView(R.id.vw_mainfrag_localev_gr)
    View vwMainfragLocalevGr;
    @BindView(R.id.rl_mainfrag_localev_gr)
    RelativeLayout rlMainfragLocalevGr;
    @BindView(R.id.iv_mainfrag_rmht1)
    ImageView iv_mainfrag_rmht1;
    @BindView(R.id.iv_mainfrag_rmht2)
    ImageView iv_mainfrag_rmht2;
    @BindView(R.id.iv_mainfrag_rmht3)
    ImageView iv_mainfrag_rmht3;

    private AMap aMap;
    private UiSettings mUiSettings;
    private MyLocationStyle myLocationStyle;
    private int index;
    private List<MainFragChargeBean> list = new ArrayList<MainFragChargeBean>();
    private List<MainFragmentData.PersonalBean> personalList = new ArrayList<MainFragmentData.PersonalBean>();
    private MainLocalAdapter mainLocalAdapter;
    private PopupWindow pWinBottomDialog;
    private MainFragmenBoDa mainFragmenBoDa;
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private List<SerchResult> serchList = new ArrayList<SerchResult>();
    private List<Bitmap> bitmapList = new ArrayList<Bitmap>();
    private double lat;
    private double lng;
    private String city = "";
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;
    private String cityCode;
    private List<MainFragmentData.PublishBean> publishList = new ArrayList<MainFragmentData.PublishBean>();
    private List<BrandAreaBean.AdBean> adList = new ArrayList<BrandAreaBean.AdBean>();
    private BrandAreaBean.AdBean adBean1;
    private BrandAreaBean.AdBean adBean2;
    private BrandAreaBean.AdBean adBean3;

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.mainfragment;
    }

    @Override
    protected void initView() {
        //使用Dagger2对本类中相关变量进行初始化
        DaggerMainFragmentCommponent.builder()
                .mainFragmentModule(new MainFragmentModule(this, mActivity))
                .build()
                .inject(this);
        setAdapter();
        RingLog.d(TAG, "savedInstanceState = " + savedInstanceState);
        tmv_mainfrag_map.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = tmv_mainfrag_map.getMap();
        }
        mUiSettings = aMap.getUiSettings();
        setUpMap();
        rtvMainfragLocal.bringToFront();
        rllMainfragSerch.bringToFront();
        setLocation();
    }

    private void setLocation() {
        mlocationClient = new AMapLocationClient(mActivity);
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

    private void addMarkersToMap() {
        RingLog.d(TAG, "list.size() = " + list.size());
        aMap.clear();
        bitmapList.clear();
        /*for (int i = 0; i < list.size(); i++) {
            MainFragChargeBean stationsBean = list.get(i);
            if (stationsBean != null) {
                Glide.with(mActivity)
                        .load(stationsBean.getImg())
                        .asBitmap()
                        .placeholder(R.mipmap.ic_image_load_circle).dontAnimate()
                        .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                RingLog.d(TAG, "onResourceReady bitmapList.size() = " + bitmapList.size());
                                bitmapList.add(resource);
                                if (bitmapList.size() == list.size()) {
                                    addMarkers();
                                }
                            }
                        });
            }
        }*/
        for (int i = 0; i < list.size(); i++) {
            //Bitmap bitmap = bitmapList.get(i);
            MainFragChargeBean stationsBean = list.get(i);
            if (stationsBean != null) {
                //stationsBean.setBitmap(bitmap);
                View infoWindow = getLayoutInflater().inflate(
                        R.layout.map_custom_info_window, null);
                CircleImageView iv_map_custom_info = ((CircleImageView) infoWindow.findViewById(R.id.iv_map_custom_info));
                //iv_map_custom_info.setImageBitmap(stationsBean.getBitmap());
                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromView(infoWindow))
                        .position(new LatLng(stationsBean.getLat(), stationsBean.getLng()))
                        .draggable(true);
                Marker marker = aMap.addMarker(markerOptions);
                marker.setObject(i);
            }
        }
    }

    private void addMarkers() {
        RingLog.d(TAG, "list.size() = " + list.size());
        RingLog.d(TAG, "bitmapList.size() = " + bitmapList.size());
        for (int i = 0; i < list.size(); i++) {
            Bitmap bitmap = bitmapList.get(i);
            MainFragChargeBean stationsBean = list.get(i);
            if (stationsBean != null) {
                stationsBean.setBitmap(bitmap);
                View infoWindow = getLayoutInflater().inflate(
                        R.layout.map_custom_info_window, null);
                CircleImageView iv_map_custom_info = ((CircleImageView) infoWindow.findViewById(R.id.iv_map_custom_info));
                iv_map_custom_info.setImageBitmap(stationsBean.getBitmap());
                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromView(infoWindow))
                        .position(new LatLng(stationsBean.getLat(), stationsBean.getLng()))
                        .draggable(true);
                Marker marker = aMap.addMarker(markerOptions);
                marker.setObject(i);
            }
        }
    }

    private void setAdapter() {
        rvMainfragLocalev.setHasFixedSize(true);
        rvMainfragLocalev.setNestedScrollingEnabled(false);
        NoScollFullLinearLayoutManager noScollFullLinearLayoutManager = new NoScollFullLinearLayoutManager(mActivity);
        noScollFullLinearLayoutManager.setScrollEnabled(false);
        rvMainfragLocalev.setLayoutManager(noScollFullLinearLayoutManager);
        mainLocalAdapter = new MainLocalAdapter(R.layout.item_mainlocal, list, true, city);
        rvMainfragLocalev.setAdapter(mainLocalAdapter);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(mActivity, R.drawable.divider_f8_15));
        rvMainfragLocalev.addItemDecoration(divider);
    }

    private void setUpMap() {
        //定位
        // 如果要设置定位的默认状态，可以在此处进行设置
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        aMap.setMyLocationStyle(myLocationStyle);
        mUiSettings.setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(false);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mUiSettings.setZoomControlsEnabled(false);
    }

    @Override
    protected void initData() {
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe
    public void RefreshFragment(RefreshFragmentEvent refreshFragmentEvent) {
        if (refreshFragmentEvent != null && refreshFragmentEvent.getRefreshIndex() == RefreshFragmentEvent.REFRESH_MAINFRAGMET) {
            RingLog.e("REFRESH_MAINFRAGMET");
            //启动定位
            mlocationClient.startLocation();
        }
    }

    @Override
    protected void initEvent() {
        aMap.setOnMyLocationChangeListener(this);
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        //解决上下滑动冲突问题
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    rvMainfragLocalev.requestDisallowInterceptTouchEvent(false);
                } else {
                    rvMainfragLocalev.requestDisallowInterceptTouchEvent(true);
                }
            }
        });
        etMainfragSerch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                query = new PoiSearch.Query(StringUtil.checkEditText(etMainfragSerch), "", cityCode);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
                query.setPageSize(100);// 设置每页最多返回多少条poiitem
                query.setPageNum(0);// 设置查第一页
                poiSearch = new PoiSearch(mActivity, query);
                poiSearch.setOnPoiSearchListener(MainFragment.this);
                poiSearch.searchPOIAsyn();
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                if (StringUtil.isEmpty(etMainfragSerch.getText().toString())) {
                    rll_mainfrag_serchresult.setVisibility(View.GONE);
                }
            }
        });
    }

    private void refresh() {
        adBean1 = null;
        adBean2 = null;
        adBean3 = null;
        showDialog();
        mPresenter.homeIndex(lng, lat);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        tmv_mainfrag_map.onResume();
    }

    @Override
    public void requestData() {

    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        tmv_mainfrag_map.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        tmv_mainfrag_map.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tmv_mainfrag_map.onDestroy();
    }

    private void setTab() {
        list.clear();
        if (index == 0) {
            tvMainfragLocalevGg.setTextColor(getResources().getColor(R.color.a0271F0));
            tvMainfragLocalevGr.setTextColor(getResources().getColor(R.color.a333333));
            vwMainfragLocalevGg.setVisibility(View.VISIBLE);
            vwMainfragLocalevGr.setVisibility(View.GONE);
            if (publishList.size() > 0) {
                for (int i = 0; i < publishList.size(); i++) {
                    MainFragmentData.PublishBean publishBean = publishList.get(i);
                    if (publishBean != null) {
                        list.add(new MainFragChargeBean(publishBean.getImg(), publishBean.getAddress(),
                                publishBean.getDistance()
                                , publishBean.getLng(), publishBean.getFastNum(), publishBean.getFreeNum(),
                                publishBean.getIsPrivate(),
                                publishBean.getTitle(), publishBean.getOpenTime(), publishBean.getUuid(),
                                publishBean.getSlowNum(), publishBean.getLat(), publishBean.getParkingPrice(), publishBean.getPayWay(), publishBean.getProvider()));
                    }
                }
            }
        } else if (index == 1) {
            tvMainfragLocalevGg.setTextColor(getResources().getColor(R.color.a333333));
            tvMainfragLocalevGr.setTextColor(getResources().getColor(R.color.a0271F0));
            vwMainfragLocalevGg.setVisibility(View.GONE);
            vwMainfragLocalevGr.setVisibility(View.VISIBLE);
            if (personalList.size() > 0) {
                for (int i = 0; i < personalList.size(); i++) {
                    MainFragmentData.PersonalBean publishBean = personalList.get(i);
                    if (publishBean != null) {
                        list.add(new MainFragChargeBean(publishBean.getImg(), publishBean.getAddress(),
                                publishBean.getDistance()
                                , publishBean.getLng(), publishBean.getFastNum(), publishBean.getFreeNum(),
                                publishBean.getIsPrivate(),
                                publishBean.getTitle(), publishBean.getOpenTime(), publishBean.getUuid(),
                                publishBean.getSlowNum(), publishBean.getLat(), publishBean.getParkingPrice(), publishBean.getPayWay(), publishBean.getProvider()));
                    }
                }
            }
        }
        mainLocalAdapter.notifyDataSetChanged();
        addMarkersToMap();// 往地图上添加marker
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();//存放所有点的经纬度
        for (int i = 0; i < list.size(); i++) {
            MainFragChargeBean stationsBean = list.get(i);
            if (stationsBean != null) {
                boundsBuilder.include(new LatLng(stationsBean.getLat(), stationsBean.getLng()));//把所有点都include进去（LatLng类型）
            }
        }
        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 150));//第二个参数为四周留空宽度
    }

    @OnClick({R.id.ll_mainfrag_city, R.id.rl_mainfrag_send, R.id.rtv_mainfrag_local, R.id.rl_mainfrag_localev,
            R.id.rl_mainfrag_localev_gg, R.id.rl_mainfrag_localev_gr, R.id.iv_mainfrag_rmht1, R.id.iv_mainfrag_rmht2, R.id.iv_mainfrag_rmht3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_mainfrag_city:
                break;
            case R.id.rl_mainfrag_send:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, AddChargeActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.rtv_mainfrag_local:
                LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();//存放所有点的经纬度
                for (int i = 0; i < list.size(); i++) {
                    MainFragChargeBean stationsBean = list.get(i);
                    if (stationsBean != null) {
                        boundsBuilder.include(new LatLng(stationsBean.getLat(), stationsBean.getLng()));//把所有点都include进去（LatLng类型）
                    }
                }
                aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 150));//第二个参数为四周留空宽度
                break;
            case R.id.rl_mainfrag_localev:
                Intent intent = new Intent(mActivity, LocalChargingActivity.class);
                intent.putExtra("city", city);
                intent.putExtra("serchLat", lat);
                intent.putExtra("serchLng", lng);
                startActivity(intent);
                break;
            case R.id.rl_mainfrag_localev_gg:
                index = 0;
                setTab();
                break;
            case R.id.rl_mainfrag_localev_gr:
                index = 1;
                setTab();
                break;
            case R.id.iv_mainfrag_rmht1:
                if (adBean1 != null) {
                    if (adBean1.getDisplay() == 1) {//原生

                    } else if (adBean1.getDisplay() == 2) {//H5
                        mActivity.startActivity(new Intent(mActivity, WebViewActivity.class).
                                putExtra(WebViewActivity.URL_KEY, adBean1.getDestination()));
                    }
                }
                break;
            case R.id.iv_mainfrag_rmht2:
                if (adBean2 != null) {
                    if (adBean2.getDisplay() == 1) {//原生

                    } else if (adBean2.getDisplay() == 2) {//H5
                        mActivity.startActivity(new Intent(mActivity, WebViewActivity.class).
                                putExtra(WebViewActivity.URL_KEY, adBean2.getDestination()));
                    }
                }
                break;
            case R.id.iv_mainfrag_rmht3:
                if (adBean3 != null) {
                    if (adBean3.getDisplay() == 1) {//原生

                    } else if (adBean3.getDisplay() == 2) {//H5
                        mActivity.startActivity(new Intent(mActivity, WebViewActivity.class).
                                putExtra(WebViewActivity.URL_KEY, adBean3.getDestination()));
                    }
                }
                break;
        }
    }

    @Override
    public void getMainFragmentSuccess(MainFragmentData mainFragmentData) {
        disMissDialog();
        if (mainFragmentData != null) {
            personalList.clear();
            publishList.clear();
            List<MainFragmentData.AdsBean> ads = mainFragmentData.getAds();
            List<MainFragmentData.PersonalBean> personal = mainFragmentData.getPersonal();
            List<MainFragmentData.PublishBean> publish = mainFragmentData.getPublish();
            StringUtil.setText(rtvMainfragLocal, mainFragmentData.getDistanceTip(), "", View.VISIBLE, View.VISIBLE);
            if (ads != null && ads.size() > 0) {//广告
                adList.clear();
                llMainfragRmht.setVisibility(View.VISIBLE);
                for (int i = 0; i < ads.size(); i++) {
                    MainFragmentData.AdsBean adsBean = ads.get(i);
                    if (adsBean != null) {
                        adList.add(new BrandAreaBean.AdBean(adsBean.getImg(), adsBean.getDisplay(), adsBean.getDestination()));
                    }
                }
                for (int i = 0; i < adList.size(); i++) {
                    BrandAreaBean.AdBean adBean = adList.get(i);
                    if (adBean != null) {
                        if (i == 0) {
                            adBean1 = adBean;
                        } else if (i == 1) {
                            adBean2 = adBean;
                        } else if (i == 2) {
                            adBean3 = adBean;
                        }
                    }
                }
                if (adBean1 != null) {
                    iv_mainfrag_rmht1.setVisibility(View.VISIBLE);
                    GlideUtil.loadNetImg(mActivity, adBean1.getImg(), iv_mainfrag_rmht1, R.mipmap.ic_image_load);
                } else {
                    iv_mainfrag_rmht1.setVisibility(View.INVISIBLE);
                }
                if (adBean2 != null) {
                    iv_mainfrag_rmht2.setVisibility(View.VISIBLE);
                    GlideUtil.loadNetImg(mActivity, adBean2.getImg(), iv_mainfrag_rmht2, R.mipmap.ic_image_load);
                } else {
                    iv_mainfrag_rmht2.setVisibility(View.INVISIBLE);
                }
                if (adBean3 != null) {
                    iv_mainfrag_rmht3.setVisibility(View.VISIBLE);
                    GlideUtil.loadNetImg(mActivity, adBean3.getImg(), iv_mainfrag_rmht3, R.mipmap.ic_image_load);
                } else {
                    iv_mainfrag_rmht3.setVisibility(View.INVISIBLE);
                }
            } else {
                llMainfragRmht.setVisibility(View.GONE);
            }
            if (publish != null && publish.size() > 0) {//公共充电桩
                publishList.addAll(publish);
            }
            if (personal != null && personal.size() > 0) {//私人充电桩
                personalList.addAll(personal);
            }
            index = 0;
            setTab();
        }
    }

    @Override
    public void getMainFragmentFail(int status, String desc) {
        disMissDialog();
        RingLog.e(TAG, "getMainFragmentFail() status = " + status + "---desc = " + desc);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(marker.getPosition(), 18, 0, 30)),
                1000, null);
        int position = (int) marker.getObject();
        showBottomDialog(position);
        return true;
    }

    private void showBottomDialog(int position) {
        pWinBottomDialog = null;
        if (pWinBottomDialog == null) {
            final MainFragChargeBean stationsBean = list.get(position);
            ViewGroup customView = (ViewGroup) View.inflate(mActivity, R.layout.mainfrag_bottom_dialog, null);
            mainFragmenBoDa = new MainFragmenBoDa(customView);
            pWinBottomDialog = new PopupWindow(customView,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, true);
            pWinBottomDialog.setFocusable(true);// 取得焦点
            //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
            pWinBottomDialog.setBackgroundDrawable(new BitmapDrawable());
            //点击外部消失
            pWinBottomDialog.setOutsideTouchable(true);
            //设置可以点击
            pWinBottomDialog.setTouchable(true);
            //进入退出的动画
            pWinBottomDialog.setAnimationStyle(R.style.mypopwindow_anim_style);
            pWinBottomDialog.setWidth(SystemUtil.getDisplayMetrics(mActivity)[0]);
            pWinBottomDialog.showAtLocation(customView, Gravity.BOTTOM, 0, 0);
            mainFragmenBoDa.getLl_mainbottom().bringToFront();
            StringUtil.setText(mainFragmenBoDa.getTvMainbottomName(), stationsBean.getTitle(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(mainFragmenBoDa.getTvMainbottomJuli(), stationsBean.getDistance(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(mainFragmenBoDa.getTvMainbottomXxdz(), stationsBean.getAddress(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(mainFragmenBoDa.getTvMainbottomKfsj(), stationsBean.getOpenTime(), "", View.VISIBLE, View.VISIBLE);

            StringUtil.setText(mainFragmenBoDa.getTvMainbottomYys(), stationsBean.getProvider(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(mainFragmenBoDa.getTvMainbottomZffs(), stationsBean.getPayWay(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(mainFragmenBoDa.getTvMainbottomTcf(), stationsBean.getParkingPrice(), "", View.VISIBLE, View.VISIBLE);
            if (stationsBean.getIsPrivate() == 0) {//公共
                mainFragmenBoDa.getIvMainbottomGgorgr().setImageResource(R.mipmap.icon_gg);
            } else if (stationsBean.getIsPrivate() == 1) {//个人
                mainFragmenBoDa.getIvMainbottomGgorgr().setImageResource(R.mipmap.icon_gr);
            }
            if (stationsBean.getFastNum() > 0) {
                StringUtil.setText(mainFragmenBoDa.getTvMainbottomKuaichongNum(), "快充" + stationsBean.getFastNum() + "个", "", View.VISIBLE, View.VISIBLE);
                mainFragmenBoDa.getLlMainbottomKuaichong().setVisibility(View.VISIBLE);
            } else {
                mainFragmenBoDa.getLlMainbottomKuaichong().setVisibility(View.GONE);
            }
            if (stationsBean.getSlowNum() > 0) {
                StringUtil.setText(mainFragmenBoDa.getTvMainbottomManchongNum(), "慢充" + stationsBean.getSlowNum() + "个", "", View.VISIBLE, View.VISIBLE);
                mainFragmenBoDa.getLlMainbottomManchong().setVisibility(View.VISIBLE);
            } else {
                mainFragmenBoDa.getLlMainbottomManchong().setVisibility(View.GONE);
            }
            if (stationsBean.getFreeNum() > 0) {
                StringUtil.setText(mainFragmenBoDa.getTvMainbottomKongxianNum(), "空闲" + stationsBean.getFreeNum() + "个", "", View.VISIBLE, View.VISIBLE);
                mainFragmenBoDa.getLlMainbottomKongxian().setVisibility(View.VISIBLE);
            } else {
                mainFragmenBoDa.getLlMainbottomKongxian().setVisibility(View.GONE);
            }
            mainFragmenBoDa.getIvMainbottomBg().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pWinBottomDialog.dismiss();
                }
            });
            mainFragmenBoDa.getLl_mainbottom().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mainFragmenBoDa.getLlMainbottomDaohang().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SystemUtil.goNavigation(mActivity, stationsBean.getLat(), stationsBean.getLng(), "我的位置",
                            stationsBean.getAddress(), city);
                }
            });
            mainFragmenBoDa.getLlMainbottomXq().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mActivity, ChargingPileDetailActivity.class).putExtra("uuid", stationsBean.getUuid()));
                }
            });
            mainFragmenBoDa.getLlMainbottomPl().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mActivity, CommentDetailActivity.class).putExtra("uuid", stationsBean.getUuid()));
                }
            });
            pWinBottomDialog.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                }
            });
        }
    }

    /**
     * 监听amap地图加载成功事件回调
     */
    @Override
    public void onMapLoaded() {
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();//存放所有点的经纬度
        for (int i = 0; i < list.size(); i++) {
            MainFragChargeBean stationsBean = list.get(i);
            if (stationsBean != null) {
                boundsBuilder.include(new LatLng(stationsBean.getLat(), stationsBean.getLng()));//把所有点都include进去（LatLng类型）
            }
        }
        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 150));//第二个参数为四周留空宽度
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    if (poiItems != null && poiItems.size() > 0) {
                        serchList.clear();
                        serchList.add(new SerchResult("目的地", "", 0, 0, true));
                        for (int i = 0; i < poiItems.size(); i++) {
                            PoiItem poiItem = poiItems.get(i);
                            if (poiItem != null) {
                                serchList.add(new SerchResult(poiItem.getTitle(), poiItem.getAdName(),
                                        poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude()));
                            }
                        }
                        rll_mainfrag_serchresult.bringToFront();
                        rll_mainfrag_serchresult.setVisibility(View.VISIBLE);
                        rv_mainfrag_serchresult.setHasFixedSize(true);
                        rv_mainfrag_serchresult.setLayoutManager(new LinearLayoutManager(mActivity));
                        MainSerchResultAdapter mainSerchResultAdapter = new MainSerchResultAdapter(R.layout.item_mainserchresult, serchList);
                        rv_mainfrag_serchresult.setAdapter(mainSerchResultAdapter);
                        rv_mainfrag_serchresult.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
                        mainSerchResultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                if (serchList != null && serchList.size() > 0 && serchList.size() > position) {
                                    SerchResult serchResult = serchList.get(position);
                                    if (serchResult != null && !serchResult.isFake()) {
                                        rll_mainfrag_serchresult.setVisibility(View.GONE);
                                        lng = serchResult.getLng();
                                        lat = serchResult.getLat();
                                        refresh();
                                    }
                                }
                            }
                        });
                    } else {
                        rll_mainfrag_serchresult.setVisibility(View.GONE);
                        RingToast.show(R.string.no_result);
                    }
                }
            } else {
                rll_mainfrag_serchresult.setVisibility(View.GONE);
                RingToast.show(R.string.no_result);
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                lat = amapLocation.getLatitude();//获取纬度
                lng = amapLocation.getLongitude();//获取经度
                city = amapLocation.getCity();
                tvMainfragCity.setText(city);
                cityCode = amapLocation.getCityCode();
                amapLocation.getAddress();
                RingLog.d(TAG, "定位成功lat = "
                        + lat + ", lng = "
                        + lng + ",city = " + city + ",cityCode = " + cityCode + ",address = " + amapLocation.getAddress());
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

    @Override
    public void onMyLocationChange(Location location) {
        // 定位回调监听
        if (location != null) {
            RingLog.d(TAG, "onMyLocationChange 定位成功， lat: " + location.getLatitude() + " lon: " + location.getLongitude());
            Bundle bundle = location.getExtras();
            if (bundle != null) {
                int errorCode = bundle.getInt(MyLocationStyle.ERROR_CODE);
                String errorInfo = bundle.getString(MyLocationStyle.ERROR_INFO);
                // 定位类型，可能为GPS WIFI等，具体可以参考官网的定位SDK介绍
                int locationType = bundle.getInt(MyLocationStyle.LOCATION_TYPE);
                RingLog.d(TAG, "定位信息， code: " + errorCode + " errorInfo: " + errorInfo + " locationType: " + locationType);
            } else {
                RingLog.d(TAG, "定位信息， bundle is null ");
            }
        } else {
            RingLog.d(TAG, "定位失败");
        }
    }

}
