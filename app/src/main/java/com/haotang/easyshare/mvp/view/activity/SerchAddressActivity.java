package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundRelativeLayout;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.activity.DaggerSerchAddressActivityCommponent;
import com.haotang.easyshare.di.module.activity.SerchAddressActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.SerchResult;
import com.haotang.easyshare.mvp.presenter.SerchAddressPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.MainSerchResultAdapter;
import com.haotang.easyshare.mvp.view.iview.ISerchAddressView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.SignUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 搜索地址页
 */
public class SerchAddressActivity extends BaseActivity<SerchAddressPresenter> implements ISerchAddressView, PoiSearch.OnPoiSearchListener, AMapLocationListener {
    protected final static String TAG = SerchAddressActivity.class.getSimpleName();
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.et_serchaddress)
    EditText etSerchaddress;
    @BindView(R.id.rll_serchaddress_serch)
    RoundRelativeLayout rllSerchaddressSerch;
    @BindView(R.id.rv_serchaddress_result)
    RecyclerView rvSerchaddressResult;
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private List<SerchResult> serchList = new ArrayList<SerchResult>();
    private MainSerchResultAdapter mainSerchResultAdapter;
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;
    private String cityCode;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_serch_address;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerSerchAddressActivityCommponent.builder().
                serchAddressActivityModule(new SerchAddressActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        rvSerchaddressResult.setHasFixedSize(true);
        rvSerchaddressResult.setLayoutManager(new LinearLayoutManager(SerchAddressActivity.this));
        mainSerchResultAdapter = new MainSerchResultAdapter(R.layout.item_mainserchresult, serchList);
        rvSerchaddressResult.setAdapter(mainSerchResultAdapter);
        rvSerchaddressResult.addItemDecoration(new DividerItemDecoration(SerchAddressActivity.this,
                DividerItemDecoration.VERTICAL));
        setLocation();
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
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        mainSerchResultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!serchList.get(position).isFake()) {
                    DevRing.busManager().postEvent(serchList.get(position));
                    finish();
                }
            }
        });
        etSerchaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                RingLog.d(TAG, "关键字 = " + StringUtil.checkEditText(etSerchaddress));
                query = new PoiSearch.Query(StringUtil.checkEditText(etSerchaddress), "", cityCode);// 第一个参数表示搜索字符串，
                // 第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
                query.setPageSize(100);// 设置每页最多返回多少条poiitem
                query.setPageNum(0);// 设置查第一页
                poiSearch = new PoiSearch(SerchAddressActivity.this, query);
                poiSearch.setOnPoiSearchListener(SerchAddressActivity.this);
                poiSearch.searchPOIAsyn();
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }

    @OnClick({R.id.tv_serchaddress_other, R.id.iv_serchaddress_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_serchaddress_other:
                finish();
                break;
            case R.id.iv_serchaddress_clear:
                etSerchaddress.setText("");
                rllSerchaddressSerch.setAnimation(SystemUtil.shakeAnimation(5));
                break;
        }
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                //if (result.getQuery().equals(query)) {// 是否是同一条
                // 取得搜索到的poiitems有多少页
                List<PoiItem> poiItems = result.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                if (poiItems != null && poiItems.size() > 0) {
                    RingLog.d(TAG, "poiItems.size() = " + poiItems.size());
                    RingLog.d(TAG, "poiItems = " + poiItems.toString());
                    serchList.clear();
                    serchList.add(new SerchResult("目的地", "", 0, 0, true));
                    for (int i = 0; i < poiItems.size(); i++) {
                        PoiItem poiItem = poiItems.get(i);
                        if (poiItem != null) {
                            RingLog.d(TAG, "poiItem.getTitle() = " + poiItem.getTitle());
                            RingLog.d(TAG, "poiItem.getAdName() = " + poiItem.getAdName());
                            RingLog.d(TAG, "poiItem.getLatLonPoint().getLatitude() = " + poiItem.getLatLonPoint().getLatitude());
                            RingLog.d(TAG, "poiItem.getLatLonPoint().getLongitude() = " + poiItem.getLatLonPoint().getLongitude());
                            serchList.add(new SerchResult(poiItem.getTitle(), poiItem.getAdName(),
                                    poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude()));
                        }
                    }
                    mainSerchResultAdapter.notifyDataSetChanged();
                } else {
                    RingToast.show(R.string.no_result);
                }
                //}
            } else {
                RingToast.show(R.string.no_result);
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

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
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                cityCode = amapLocation.getCityCode();
                RingLog.d(TAG, "定位成功");
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                RingLog.d(TAG, "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }
}
