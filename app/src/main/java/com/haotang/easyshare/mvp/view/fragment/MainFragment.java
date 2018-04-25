package com.haotang.easyshare.mvp.view.fragment;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.roundview.RoundRelativeLayout;
import com.flyco.roundview.RoundTextView;
import com.haotang.easyshare.R;
import com.haotang.easyshare.mvp.model.entity.res.MainFragmentData;
import com.haotang.easyshare.mvp.view.adapter.MainLocalAdapter;
import com.haotang.easyshare.mvp.view.adapter.TakePhotoImgAdapter;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.ljy.devring.other.RingLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date zhoujunxia on 2018/4/14 20:34
 */
public class MainFragment extends BaseFragment implements AMap.OnMyLocationChangeListener {
    private final static String TAG = MainFragment.class.getSimpleName();
    @BindView(R.id.tmv_mainfrag_map)
    TextureMapView tmv_mainfrag_map;
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
    @BindView(R.id.rtv_mainfrag_local)
    RoundTextView rtvMainfragLocal;
    @BindView(R.id.iv_mainfrag_rmht1)
    ImageView ivMainfragRmht1;
    @BindView(R.id.iv_mainfrag_rmht2)
    ImageView ivMainfragRmht2;
    @BindView(R.id.iv_mainfrag_rmht3)
    ImageView ivMainfragRmht3;
    @BindView(R.id.ll_mainfrag_rmht)
    LinearLayout llMainfragRmht;
    @BindView(R.id.rl_mainfrag_localev_more)
    LinearLayout rlMainfragLocalevMore;
    @BindView(R.id.rl_mainfrag_localev)
    RelativeLayout rlMainfragLocalev;
    @BindView(R.id.rv_mainfrag_localev)
    RecyclerView rvMainfragLocalev;
    @BindView(R.id.iv_mainfrag_gj)
    ImageView ivMainfragGj;
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
    private AMap aMap;
    private UiSettings mUiSettings;
    private MyLocationStyle myLocationStyle;
    private int index;
    private List<MainFragmentData> list = new ArrayList<MainFragmentData>();
    private MainLocalAdapter mainLocalAdapter;

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
        tmv_mainfrag_map.onCreate(savedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = tmv_mainfrag_map.getMap();
            mUiSettings = aMap.getUiSettings();
            setUpMap();
        }
        ivMainfragGj.bringToFront();
        rtvMainfragLocal.bringToFront();
        rllMainfragSerch.bringToFront();
        index = 0;
        setTab();
        setAdapter();
    }

    private void setAdapter() {
        rvMainfragLocalev.setHasFixedSize(true);
        rvMainfragLocalev.setLayoutManager(new LinearLayoutManager(mActivity));
        mainLocalAdapter = new MainLocalAdapter(R.layout.item_takephoto_imginfo, list);
        mainLocalAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        rvMainfragLocalev.setAdapter(mainLocalAdapter);
    }

    private void setUpMap() {
        //定位
        // 如果要设置定位的默认状态，可以在此处进行设置
        myLocationStyle = new MyLocationStyle();
        aMap.setMyLocationStyle(myLocationStyle);
        mUiSettings.setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {
        //设置SDK 自带定位消息监听
        aMap.setOnMyLocationChangeListener(this);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        tmv_mainfrag_map.onResume();
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

    @OnClick({R.id.ll_mainfrag_city, R.id.rl_mainfrag_send, R.id.iv_mainfrag_rmht1, R.id.iv_mainfrag_rmht2,
            R.id.iv_mainfrag_rmht3, R.id.iv_mainfrag_gj, R.id.rl_mainfrag_localev, R.id.rl_mainfrag_localev_gg,
            R.id.rl_mainfrag_localev_gr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_mainfrag_city:
                break;
            case R.id.rl_mainfrag_send:
                break;
            case R.id.iv_mainfrag_rmht1:
                break;
            case R.id.iv_mainfrag_rmht2:
                break;
            case R.id.iv_mainfrag_rmht3:
                break;
            case R.id.iv_mainfrag_gj:
                break;
            case R.id.rl_mainfrag_localev:
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

    private void setTab() {
        if (index == 0) {
            tvMainfragLocalevGg.setTextColor(getResources().getColor(R.color.a0271F0));
            tvMainfragLocalevGr.setTextColor(getResources().getColor(R.color.a333333));
            vwMainfragLocalevGg.setVisibility(View.VISIBLE);
            vwMainfragLocalevGr.setVisibility(View.GONE);
        } else if (index == 1) {
            tvMainfragLocalevGg.setTextColor(getResources().getColor(R.color.a333333));
            tvMainfragLocalevGr.setTextColor(getResources().getColor(R.color.a0271F0));
            vwMainfragLocalevGg.setVisibility(View.GONE);
            vwMainfragLocalevGr.setVisibility(View.VISIBLE);
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
                /*
                errorCode
                errorInfo
                locationType
                */
                RingLog.d(TAG, "定位信息， code: " + errorCode + " errorInfo: " + errorInfo + " locationType: " + locationType);
            } else {
                RingLog.d(TAG, "定位信息， bundle is null ");
            }
        } else {
            RingLog.d(TAG, "定位失败");
        }
    }
}
