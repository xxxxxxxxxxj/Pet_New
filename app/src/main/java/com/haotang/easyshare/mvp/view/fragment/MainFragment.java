package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.flyco.roundview.RoundTextView;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.fragment.DaggerMainFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.MainFragmentModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.model.entity.res.AdvertisementBean;
import com.haotang.easyshare.mvp.model.entity.res.BrandAreaBean;
import com.haotang.easyshare.mvp.model.entity.res.MainFragChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.MainFragmentData;
import com.haotang.easyshare.mvp.model.entity.res.SerchResult;
import com.haotang.easyshare.mvp.model.imageload.GlideImageLoader;
import com.haotang.easyshare.mvp.presenter.MainFragmentPresenter;
import com.haotang.easyshare.mvp.view.activity.AddChargeActivity;
import com.haotang.easyshare.mvp.view.activity.ButlerActivity;
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
import com.haotang.easyshare.mvp.view.widget.NoScollFullLinearLayoutManager;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.mvp.view.widget.SoftKeyBoardListener;
import com.haotang.easyshare.util.DensityUtil;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.ScreenUtil;
import com.haotang.easyshare.util.SharedPreferenceUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.haotang.easyshare.util.UmenUtil;
import com.ljy.devring.other.RingLog;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

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
        AMap.OnMapLoadedListener, PoiSearch.OnPoiSearchListener, AMap.OnMyLocationChangeListener, OnBannerListener {
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
    RelativeLayout rllMainfragSerch;
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
    @BindView(R.id.iv_mainfrag_map_loc)
    ImageView iv_mainfrag_map_loc;
    @BindView(R.id.iv_mainfrag_gj)
    ImageView ivMainfragGj;
    @BindView(R.id.rl_mainfrag_top_cancel)
    TextView rl_mainfrag_top_cancel;

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
    private PopupWindow pWin;
    private List<AdvertisementBean.DataBean> bannerList = new ArrayList<AdvertisementBean.DataBean>();
    private MainSerchResultAdapter mainSerchResultAdapter;
    private double serchLng;
    private double serchLat;

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
        iv_mainfrag_map_loc.bringToFront();
        ivMainfragGj.bringToFront();
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
        aMap.clear();
        for (int i = 0; i < list.size(); i++) {
            MainFragChargeBean stationsBean = list.get(i);
            if (stationsBean != null) {
                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_map_view))
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
        divider.setDrawable(ContextCompat.getDrawable(mActivity, R.drawable.divider_f8_5));
        rvMainfragLocalev.addItemDecoration(divider);

        serchList.clear();
        serchList.add(new SerchResult("目的地", "", 0, 0, true));
        rv_mainfrag_serchresult.setHasFixedSize(true);
        rv_mainfrag_serchresult.setLayoutManager(new LinearLayoutManager(mActivity));
        mainSerchResultAdapter = new MainSerchResultAdapter(R.layout.item_mainserchresult, serchList);
        rv_mainfrag_serchresult.setAdapter(mainSerchResultAdapter);
        rv_mainfrag_serchresult.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
    }

    private void setUpMap() {
        //定位
        // 如果要设置定位的默认状态，可以在此处进行设置
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        aMap.setMyLocationStyle(myLocationStyle);
        mUiSettings.setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        mUiSettings.setZoomControlsEnabled(false);
    }

    @Override
    protected void initData() {
        showDialog();
        MultipartBody body = new MultipartBody.Builder().setType(MultipartBody.ALTERNATIVE)
                .addFormDataPart("category", "5").build();
        mPresenter.list(UrlConstants.getMapHeader(mActivity),body);
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe
    public void RefreshFragment(RefreshFragmentEvent refreshFragmentEvent) {
        if (refreshFragmentEvent != null && refreshFragmentEvent.getRefreshIndex() ==
                RefreshFragmentEvent.REFRESH_MAINFRAGMET && mlocationClient != null) {
            RingLog.e("REFRESH_MAINFRAGMET");
            if (serchLat > 0 && serchLng > 0) {
                refresh(serchLat, serchLng);
            } else {
                //启动定位
                mlocationClient.startLocation();
            }
            UmenUtil.UmengEventStatistics(getActivity(), UmenUtil.yxzx1);
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
                UmenUtil.UmengEventStatistics(getActivity(), UmenUtil.yxzx13);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
        mainSerchResultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (serchList != null && serchList.size() > 0 && serchList.size() > position) {
                    SerchResult serchResult = serchList.get(position);
                    if (serchResult != null && !serchResult.isFake()) {
                        SharedPreferenceUtil.getInstance(mActivity).saveBoolean("isSerch", true);
                        etMainfragSerch.setText(serchResult.getName());
                        rll_mainfrag_serchresult.setVisibility(View.GONE);
                        lng = serchResult.getLng();
                        lat = serchResult.getLat();
                        serchLng = serchResult.getLng();
                        serchLat = serchResult.getLat();
                        refresh(serchLat, serchLng);
                    }
                }
            }
        });
        SoftKeyBoardListener.setListener(mActivity, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                RingLog.e("keyBoardShow height = " + height);
                rll_mainfrag_serchresult.setVisibility(View.VISIBLE);
                rll_mainfrag_serchresult.bringToFront();
                serchList.clear();
                serchList.add(new SerchResult("目的地", "", 0, 0, true));
                mainSerchResultAdapter.notifyDataSetChanged();
                rl_mainfrag_top_cancel.setVisibility(View.VISIBLE);
                rlMainfragSend.setVisibility(View.GONE);
            }

            @Override
            public void keyBoardHide(int height) {
                RingLog.e("keyBoardHide height = " + height);
                rll_mainfrag_serchresult.setVisibility(View.GONE);
                rl_mainfrag_top_cancel.setVisibility(View.GONE);
                rlMainfragSend.setVisibility(View.VISIBLE);
            }
        });
    }

    private void refresh(double localLat, double localLng) {
        adBean1 = null;
        adBean2 = null;
        adBean3 = null;
        showDialog();
        mPresenter.homeIndex(UrlConstants.getMapHeader(mActivity),localLng, localLat);
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
        mainLocalAdapter.setLatLng(lat, lng);
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
            R.id.rl_mainfrag_localev_gg, R.id.rl_mainfrag_localev_gr, R.id.iv_mainfrag_rmht1, R.id.iv_mainfrag_rmht2,
            R.id.iv_mainfrag_rmht3, R.id.iv_mainfrag_map_loc, R.id.iv_mainfrag_gj, R.id.rl_mainfrag_top_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_mainfrag_top_cancel:
                SystemUtil.goneJP(mActivity);
                break;
            case R.id.iv_mainfrag_gj:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, ButlerActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.iv_mainfrag_map_loc:
                if (lat > 0 && lng > 0) {
                    aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(new LatLng(lat, lng), 18, 0, 30)),
                            1000, null);
                }
                break;
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
        SystemUtil.Exit(mActivity, status);
    }

    @Override
    public void listSuccess(List<AdvertisementBean.DataBean> data) {
        if (data != null && data.size() > 0) {
            bannerList.clear();
            bannerList.addAll(data);
            showPopPhoto(data);
        }
    }

    private void showPopPhoto(List<AdvertisementBean.DataBean> data) {
        pWin = null;
        if (pWin == null) {
            final View view = View
                    .inflate(mActivity, R.layout.pw_main_ad, null);
            ImageButton ib_pw_main_close = (ImageButton) view
                    .findViewById(R.id.ib_pw_main_close);
            Banner banner_main_ad = (Banner) view
                    .findViewById(R.id.banner_main_ad);
            pWin = new PopupWindow(view,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT, true);
            pWin.setFocusable(true);
            pWin.setWidth(SystemUtil.getDisplayMetrics(mActivity)[0]);
            pWin.showAtLocation(view, Gravity.CENTER, 0, 0);
            List<String> list = new ArrayList<String>();
            list.clear();
            for (int i = 0; i < data.size(); i++) {
                list.add(data.get(i).getImg());
            }
            banner_main_ad.setImages(list)
                    .setImageLoader(new GlideImageLoader())
                    .setOnBannerListener(this)
                    .start();
            ib_pw_main_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    pWin.dismiss();
                    pWin = null;
                }
            });
        }
    }

    @Override
    public void listFail(int status, String desc) {
        RingLog.e(TAG, "listFail() status = " + status + "---desc = " + desc);
        SystemUtil.Exit(mActivity, status);
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
            float screenDensity = ScreenUtil.getScreenDensity(mActivity);
            Log.e("TAG", "screenDensity = " + screenDensity);
            pWinBottomDialog.setHeight(SystemUtil.getDisplayMetrics(mActivity)[1] - DensityUtil.dp2px(mActivity, 280) - DensityUtil.getStatusBarHeight(mActivity));
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
            StringUtil.setText(mainFragmenBoDa.getTvMainbottomKuaichongNum(), "快充" + stationsBean.getFastNum() + "个", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(mainFragmenBoDa.getTvMainbottomManchongNum(), "慢充" + stationsBean.getSlowNum() + "个", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(mainFragmenBoDa.getTvMainbottomKongxianNum(), "空闲" + stationsBean.getFreeNum() + "个", "", View.VISIBLE, View.VISIBLE);
            mainFragmenBoDa.getLl_mainbottom().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mainFragmenBoDa.getLlMainbottomDaohang().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SystemUtil.goNavigation(mActivity, stationsBean.getLat(), stationsBean.getLng(), "",
                            stationsBean.getAddress(), lat, lng, stationsBean.getUuid());
                }
            });
            mainFragmenBoDa.getLlMainbottomXq().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ChargingPileDetailActivity.class);
                    intent.putExtra("uuid", stationsBean.getUuid());
                    intent.putExtra("serchLat", lat);
                    intent.putExtra("serchLng", lng);
                    startActivity(intent);
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
                        mainSerchResultAdapter.notifyDataSetChanged();
                    }
                }
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
                serchLat = 0;
                serchLng = 0;
                //定位成功回调信息，设置相关消息
                lat = amapLocation.getLatitude();//获取纬度
                lng = amapLocation.getLongitude();//获取经度
                city = amapLocation.getCity();
                cityCode = amapLocation.getCityCode();
                amapLocation.getAddress();
                RingLog.d(TAG, "定位成功lat = "
                        + lat + ", lng = "
                        + lng + ",city = " + city + ",cityCode = " + cityCode + ",address = " + amapLocation.getAddress());
                if (lat > 0 && lng > 0) {
                    refresh(lat, lng);
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

    @Override
    public void OnBannerClick(int position) {
        RingLog.e(TAG, "position:" + position);
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
}
