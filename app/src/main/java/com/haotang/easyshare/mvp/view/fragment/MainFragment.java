package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArraySet;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundLinearLayout;
import com.flyco.roundview.RoundRelativeLayout;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.fragment.DaggerMainFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.MainFragmentModule;
import com.haotang.easyshare.mvp.model.entity.res.MainFragChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.MainFragmentData;
import com.haotang.easyshare.mvp.model.entity.res.SerchResult;
import com.haotang.easyshare.mvp.presenter.MainFragmentPresenter;
import com.haotang.easyshare.mvp.view.activity.AddChargeActivity;
import com.haotang.easyshare.mvp.view.activity.ButlerActivity;
import com.haotang.easyshare.mvp.view.activity.ChargingPileDetailActivity;
import com.haotang.easyshare.mvp.view.activity.CommentDetailActivity;
import com.haotang.easyshare.mvp.view.activity.LocalChargingActivity;
import com.haotang.easyshare.mvp.view.activity.SwitchCityActivity;
import com.haotang.easyshare.mvp.view.adapter.MainLocalAdapter;
import com.haotang.easyshare.mvp.view.adapter.MainSerchResultAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IMainFragmentView;
import com.haotang.easyshare.mvp.view.viewholder.MainFragmenBoDa;
import com.haotang.easyshare.mvp.view.viewholder.MainFragmenHeader;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static cn.jpush.android.api.JPushInterface.a.p;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/4/14 20:34
 */
public class MainFragment extends BaseFragment<MainFragmentPresenter> implements
        AMapLocationListener,
        View.OnClickListener, IMainFragmentView, AMap.OnMarkerClickListener,
        AMap.OnMapLoadedListener, PoiSearch.OnPoiSearchListener, AMap.OnMyLocationChangeListener {
    private final static String TAG = MainFragment.class.getSimpleName();
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.iv_mainfrag_gj)
    ImageView ivMainfragGj;
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
    private AMap aMap;
    private UiSettings mUiSettings;
    private MyLocationStyle myLocationStyle;
    private int index;
    private List<MainFragChargeBean> list = new ArrayList<MainFragChargeBean>();
    private List<MainFragmentData.PersonalBean> personalList = new ArrayList<MainFragmentData.PersonalBean>();
    private MainLocalAdapter mainLocalAdapter;
    private MainFragmenHeader mainFragmenHeader;
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
        mainFragmenHeader.getTmv_mainfrag_map().onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mainFragmenHeader.getTmv_mainfrag_map().getMap();
        }
        mUiSettings = aMap.getUiSettings();
        setUpMap();
        ivMainfragGj.bringToFront();
        mainFragmenHeader.getRtvMainfragLocal().bringToFront();
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
        bitmapList.clear();
        for (int i = 0; i < list.size(); i++) {
            MainFragChargeBean stationsBean = list.get(i);
            if (stationsBean != null) {
                Glide.with(mActivity)
                        .load(stationsBean.getImg())
                        .asBitmap()
                        .placeholder(R.mipmap.ic_image_load_circle).dontAnimate()
                        .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                bitmapList.add(resource);
                                if (bitmapList.size() == list.size()) {
                                    addMarkers();
                                }
                            }
                        });
            }
        }
    }

    private void addMarkers() {
        for (int i = 0; i < list.size(); i++) {
            Bitmap bitmap = bitmapList.get(i);
            MainFragChargeBean stationsBean = list.get(i);
            if (stationsBean != null) {
                stationsBean.setBitmap(SystemUtil.toCircleBitmap(bitmap));
                View infoWindow = getLayoutInflater().inflate(
                        R.layout.map_custom_info_window, null);
                ImageView iv_map_custom_info = ((ImageView) infoWindow.findViewById(R.id.iv_map_custom_info));
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
        rvMainfragLocalev.setLayoutManager(new LinearLayoutManager(mActivity));
        mainLocalAdapter = new MainLocalAdapter(R.layout.item_mainlocal, list, false, city);
        View top = getLayoutInflater().inflate(R.layout.mainlocal_top_view, (ViewGroup) rvMainfragLocalev.getParent(), false);
        mainFragmenHeader = new MainFragmenHeader(top);
        mainLocalAdapter.addHeaderView(top);
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
    protected void initEvent() {
        aMap.setOnMyLocationChangeListener(this);
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        mainFragmenHeader.getIvMainfragRmht1().setOnClickListener(this);
        mainFragmenHeader.getIvMainfragRmht2().setOnClickListener(this);
        mainFragmenHeader.getIvMainfragRmht3().setOnClickListener(this);
        mainFragmenHeader.getRlMainfragLocalev().setOnClickListener(this);
        mainFragmenHeader.getRtvMainfragLocal().setOnClickListener(this);
        mainFragmenHeader.getRlMainfragLocalevGg().setOnClickListener(this);
        mainFragmenHeader.getRlMainfragLocalevGr().setOnClickListener(this);
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
                query = new PoiSearch.Query(StringUtil.checkEditText(etMainfragSerch), "", "027");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
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

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mainFragmenHeader.getTmv_mainfrag_map().onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mainFragmenHeader.getTmv_mainfrag_map().onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mainFragmenHeader.getTmv_mainfrag_map().onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainFragmenHeader.getTmv_mainfrag_map().onDestroy();
    }

    private void setTab() {
        list.clear();
        if (index == 0 && publishList.size() > 0) {
            for (int i = 0; i < publishList.size(); i++) {
                MainFragmentData.PublishBean publishBean = publishList.get(i);
                if (publishBean != null) {
                    list.add(new MainFragChargeBean(publishBean.getImg(), publishBean.getAddress(),
                            publishBean.getDistance()
                            , publishBean.getLng(), publishBean.getFastNum(), publishBean.getFreeNum(),
                            publishBean.getIsPrivate(),
                            publishBean.getTitle(), publishBean.getOpenTime(), publishBean.getUuid(),
                            publishBean.getSlowNum(), publishBean.getLat()));
                }
            }
            mainFragmenHeader.getTvMainfragLocalevGg().setTextColor(getResources().getColor(R.color.a0271F0));
            mainFragmenHeader.getTvMainfragLocalevGr().setTextColor(getResources().getColor(R.color.a333333));
            mainFragmenHeader.getVwMainfragLocalevGg().setVisibility(View.VISIBLE);
            mainFragmenHeader.getVwMainfragLocalevGr().setVisibility(View.GONE);
        } else if (index == 1 && personalList.size() > 0) {
            mainFragmenHeader.getTvMainfragLocalevGg().setTextColor(getResources().getColor(R.color.a333333));
            mainFragmenHeader.getTvMainfragLocalevGr().setTextColor(getResources().getColor(R.color.a0271F0));
            mainFragmenHeader.getVwMainfragLocalevGg().setVisibility(View.GONE);
            mainFragmenHeader.getVwMainfragLocalevGr().setVisibility(View.VISIBLE);
        }
        addMarkersToMap();// 往地图上添加marker
        mainLocalAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.iv_mainfrag_gj, R.id.ll_mainfrag_city, R.id.rl_mainfrag_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_mainfrag_gj:
                startActivity(new Intent(mActivity, ButlerActivity.class));
                break;
            case R.id.ll_mainfrag_city:
                startActivity(new Intent(mActivity, SwitchCityActivity.class));
                break;
            case R.id.rl_mainfrag_send:
                startActivity(new Intent(mActivity, AddChargeActivity.class));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.iv_mainfrag_rmht1:
                break;
            case R.id.iv_mainfrag_rmht2:
                break;
            case R.id.iv_mainfrag_rmht3:
                break;
            case R.id.rl_mainfrag_localev:
                startActivity(new Intent(mActivity, LocalChargingActivity.class).putExtra("city", city));
                break;
            case R.id.rl_mainfrag_localev_gg:
                index = 0;
                setTab();
                break;
            case R.id.rl_mainfrag_localev_gr:
                index = 1;
                setTab();
                break;
        }
    }

    @Override
    public void getMainFragmentSuccess(MainFragmentData mainFragmentData) {
        if (mainFragmentData != null) {
            personalList.clear();
            publishList.clear();
            List<MainFragmentData.AdsBean> ads = mainFragmentData.getAds();
            List<MainFragmentData.PersonalBean> personal = mainFragmentData.getPersonal();
            List<MainFragmentData.PublishBean> publish = mainFragmentData.getPublish();
            StringUtil.setText(mainFragmenHeader.getRtvMainfragLocal(), mainFragmentData.getDistanceTip(), "", View.VISIBLE, View.VISIBLE);
            if (ads != null && ads.size() > 0) {//广告

            }
            if (publish != null && publish.size() > 0) {//附近充电桩
                publishList.addAll(publish);
            }
            if (personal != null && personal.size() > 0) {//热门话题
                personalList.addAll(personal);
            }
            index = 0;
            setTab();
        }
    }

    @Override
    public void getMainFragmentFail(int status, String desc) {
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
                    startActivity(new Intent(mActivity, ChargingPileDetailActivity.class));
                }
            });
            mainFragmenBoDa.getLlMainbottomPl().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mActivity, CommentDetailActivity.class));
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
                        serchList.add(new SerchResult("目的地", "", 0, 0));
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
                                rll_mainfrag_serchresult.setVisibility(View.GONE);
                                if (serchList != null && serchList.size() > 0 && serchList.size() > position) {
                                    SerchResult serchResult = serchList.get(position);
                                    if (serchResult != null) {
                                        mPresenter.homeIndex(serchResult.getLng(), serchResult.getLat());
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
                    mPresenter.homeIndex(lng, lat);
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
