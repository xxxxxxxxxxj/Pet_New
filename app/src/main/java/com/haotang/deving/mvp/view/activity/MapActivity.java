package com.haotang.deving.mvp.view.activity;

import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.haotang.deving.R;
import com.haotang.deving.mvp.view.activity.base.BaseActivity;
import com.ljy.devring.DevRing;

import butterknife.BindView;

/**
 * 地图界面
 */
public class MapActivity extends BaseActivity {
    protected final static String TAG = TestActivity.class.getSimpleName();
    @BindView(R.id.map)
    MapView mapView;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_map;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        AMap aMap = mapView.getMap();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }
}
