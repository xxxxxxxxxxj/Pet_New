package com.haotang.easyshare.mvp.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.activity.DaggerLocalChargingActivityCommponent;
import com.haotang.easyshare.di.module.activity.LocalChargingActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.MainFragChargeBean;
import com.haotang.easyshare.mvp.presenter.LocalChargingPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.MainLocalAdapter;
import com.haotang.easyshare.mvp.view.iview.ILocalChargingView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.SignUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 附近充电桩界面
 */
public class LocalChargingActivity extends BaseActivity<LocalChargingPresenter>
        implements ILocalChargingView, AMapLocationListener {
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_local_charging)
    RecyclerView rvLocalCharging;
    @BindView(R.id.srl_local_charging)
    SwipeRefreshLayout srlLocalCharging;
    private List<MainFragChargeBean> list = new ArrayList<MainFragChargeBean>();
    private MainLocalAdapter mainLocalAdapter;
    private int mNextRequestPage = 1;
    private String city;
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;
    private int pageSize;
    private double serchLat;
    private double serchLng;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_local_charging;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerLocalChargingActivityCommponent.builder().localChargingActivityModule(new LocalChargingActivityModule(this, this)).build().inject(this);
        city = getIntent().getStringExtra("city");
        serchLat = getIntent().getDoubleExtra("serchLat", 0);
        serchLng = getIntent().getDoubleExtra("serchLng", 0);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("附近的充电桩");
        srlLocalCharging.setRefreshing(true);
        srlLocalCharging.setColorSchemeColors(Color.rgb(47, 223, 189));
        setAdapter();
        if (serchLat > 0 && serchLng > 0) {
            nearBy();
        } else {
            setLocation();
        }
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

    private void setAdapter() {
        rvLocalCharging.setHasFixedSize(true);
        rvLocalCharging.setLayoutManager(new LinearLayoutManager(this));
        mainLocalAdapter = new MainLocalAdapter(R.layout.item_mainlocal, list, true, city);
        rvLocalCharging.setAdapter(mainLocalAdapter);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_f8_5));
        rvLocalCharging.addItemDecoration(divider);
        mainLocalAdapter.setLatLng(serchLat, serchLng);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        mainLocalAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        });
        srlLocalCharging.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        mainLocalAdapter.setEnableLoadMore(false);
        srlLocalCharging.setRefreshing(true);
        mNextRequestPage = 1;
        nearBy();
    }

    private void loadMore() {
        nearBy();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }

    @OnClick(R.id.iv_titlebar_back)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                serchLat = amapLocation.getLatitude();//获取纬度
                serchLng = amapLocation.getLongitude();//获取经度
                amapLocation.getAddress();
                RingLog.d(TAG, "定位成功lat = "
                        + serchLat + ", lng = "
                        + serchLng + ",city = " + city + ",address = " + amapLocation.getAddress());
                if (serchLat > 0 && serchLng > 0) {
                    nearBy();
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

    private void nearBy() {
        showDialog();
        Map<String, String> mapHeader = UrlConstants.getMapHeader(LocalChargingActivity.this);
        mapHeader.put("lng", String.valueOf(serchLng));
        mapHeader.put("lat", String.valueOf(serchLat));
        mapHeader.put("page", String.valueOf(mNextRequestPage));
        mapHeader.put("key", AppConfig.SERVER_KEY);
        RingLog.d(TAG, "mapHeader =  " + mapHeader.toString());
        String md5 = SignUtil.sign(mapHeader, "MD5");
        RingLog.d(TAG, "md5 =  " + md5);
        mPresenter.nearby(serchLng, serchLat, mNextRequestPage, md5);
    }

    @Override
    public void nearbySuccess(List<MainFragChargeBean> data) {
        disMissDialog();
        if (mNextRequestPage == 1) {
            srlLocalCharging.setRefreshing(false);
            mainLocalAdapter.setEnableLoadMore(true);
            list.clear();
        }
        mainLocalAdapter.loadMoreComplete();
        if (data != null && data.size() > 0) {
            if (mNextRequestPage == 1) {
                pageSize = data.size();
            } else {
                if (data.size() < pageSize) {
                    mainLocalAdapter.loadMoreEnd(false);
                }
            }
            list.addAll(data);
            mNextRequestPage++;
        } else {
            if (mNextRequestPage == 1) {
                mainLocalAdapter.loadMoreEnd(true);
                mainLocalAdapter.setEmptyView(setEmptyViewBase(2, "暂无充电桩", R.mipmap.no_data, null));
            } else {
                mainLocalAdapter.loadMoreEnd(false);
            }
        }
        mainLocalAdapter.notifyDataSetChanged();
    }

    @Override
    public void nearbyFail(int code, String msg) {
        disMissDialog();
        if (mNextRequestPage == 1) {
            mainLocalAdapter.setEnableLoadMore(true);
            srlLocalCharging.setRefreshing(false);
        } else {
            mainLocalAdapter.loadMoreFail();
        }
        mainLocalAdapter.setEmptyView(setEmptyViewBase(1, msg, R.mipmap.no_net_orerror, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        }));
        RingLog.e(TAG, "nearbyFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this,code);
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
