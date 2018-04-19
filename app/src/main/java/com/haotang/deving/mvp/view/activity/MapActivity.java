package com.haotang.deving.mvp.view.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.Projection;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.VisibleRegion;
import com.haotang.deving.R;
import com.haotang.deving.mvp.view.activity.base.BaseActivity;
import com.ljy.devring.DevRing;
import com.ljy.devring.util.RingToast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 地图界面
 */
public class MapActivity extends BaseActivity implements
        AMap.CancelableCallback, AMap.OnMapClickListener,
        AMap.OnMapLongClickListener, AMap.OnCameraChangeListener, AMap.OnMapTouchListener,
        AMap.OnMapScreenShotListener, RadioGroup.OnCheckedChangeListener, LocationSource,
        AMapLocationListener, AMap.OnMarkerClickListener,
        AMap.OnInfoWindowClickListener, AMap.OnMarkerDragListener, AMap.OnMapLoadedListener, AMap.InfoWindowAdapter {
    protected final static String TAG = TestActivity.class.getSimpleName();
    @BindView(R.id.map)
    MapView mapView;
    private static final int SCROLL_BY_PX = 100;
    private AMap aMap;
    public static final LatLng FANGHENG = new LatLng(39.989614, 116.481763);// 方恒国际中心经纬度
    public static final LatLng ZHONGGUANCUN = new LatLng(39.983456, 116.3154950);// 北京市中关村经纬度
    public static final LatLng SHANGHAI = new LatLng(31.238068, 121.501654);// 上海市经纬度
    public static final LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度
    public static final LatLng XIAN = new LatLng(34.341568, 108.940174);// 西安市经纬度

    @BindView(R.id.tap_text)
    TextView mTapTextView;
    @BindView(R.id.camera_text)
    TextView mCameraTextView;
    @BindView(R.id.touch_text)
    TextView mTouchTextView;
    private UiSettings mUiSettings;
    private OnLocationChangedListener mListener;
    @BindView(R.id.ll_map_event)
    LinearLayout mLl_map_middle;
    @BindView(R.id.ll_map_camera)
    LinearLayout mLl_map_top;
    @BindView(R.id.sv_map_uisetting)
    ScrollView mSv_map;
    @BindView(R.id.logo_position)
    RadioGroup mLogo_position;

    private MarkerOptions markerOption;
    @BindView(R.id.mark_listenter_text)
    TextView markerText;
    @BindView(R.id.custom_info_window_options)
    RadioGroup radioOption;
    private Marker marker2;// 有跳动效果的marker对象
    private LatLng latlng = new LatLng(36.061, 103.834);
    @BindView(R.id.ll_map_markers)
    LinearLayout mLl_map_markers;

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
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
            setUpMap();
        }
        mLl_map_middle.bringToFront();
        mLl_map_top.bringToFront();
        mSv_map.bringToFront();
        mLl_map_markers.bringToFront();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        mLogo_position.setOnCheckedChangeListener(this);
    }

    /**
     * 往地图上添加一个marker
     */
    private void setUpMap() {
        aMap.setPointToCenter(0, 0);//设置地图以屏幕坐标(0,0)为中心进行旋转，默认是以屏幕中心旋转
        aMap.addMarker(new MarkerOptions()
                .perspective(true)
                .position(FANGHENG)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        aMap.setOnMapClickListener(this);// 对amap添加单击地图事件监听器
        aMap.setOnMapLongClickListener(this);// 对amap添加长按地图事件监听器
        aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器
        aMap.setOnMapTouchListener(this);// 对amap添加触摸地图事件监听器

        aMap.setOnMarkerDragListener(this);// 设置marker可拖拽事件监听器
        aMap.setOnMapLoadedListener(this);// 设置amap加载成功事件监听器
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        aMap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
        aMap.setInfoWindowAdapter(this);// 设置自定义InfoWindow样式
        addMarkersToMap();// 往地图上添加marker
    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap() {
        ArrayList<BitmapDescriptor> giflist = new ArrayList<BitmapDescriptor>();
        giflist.add(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        giflist.add(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED));
        giflist.add(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .position(CHENGDU).title("成都市")
                .snippet("成都市:30.679879, 104.064855").icons(giflist)
                .perspective(true).draggable(true).period(50));

        markerOption = new MarkerOptions();
        markerOption.position(XIAN);
        markerOption.title("西安市").snippet("西安市：34.341568, 108.940174");
        markerOption.perspective(true);
        markerOption.draggable(true);
        markerOption.icon(
//				BitmapDescriptorFactory
//				.fromResource(R.drawable.location_marker)
                BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.location_marker))
        );
        //将Marker设置为贴地显示，可以双指下拉看效果
        markerOption.setFlat(true);
        marker2 = aMap.addMarker(markerOption);
        drawMarkers();// 添加1个带有系统默认icon的marker
    }

    /**
     * 绘制系统默认的1种marker背景图片
     */
    public void drawMarkers() {
        Marker marker = aMap.addMarker(new MarkerOptions()
                .position(latlng)
                .title("好好学习")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .perspective(true).draggable(true));
        marker.setRotateAngle(90);// 设置marker旋转90度
        marker.showInfoWindow();// 设置默认显示一个infowinfow

    }

    /**
     * 对marker标注点点击响应事件
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {
        if (marker.equals(marker2)) {
            if (aMap != null) {
                jumpPoint(marker);
            }
        }
        markerText.setText("你点击的是" + marker.getTitle());
        return false;
    }

    /**
     * marker点击时跳动一下
     */
    public void jumpPoint(final Marker marker) {
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        Projection proj = aMap.getProjection();
        Point startPoint = proj.toScreenLocation(XIAN);
        startPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);
        final long duration = 1500;

        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed
                        / duration);
                double lng = t * XIAN.longitude + (1 - t)
                        * startLatLng.longitude;
                double lat = t * XIAN.latitude + (1 - t)
                        * startLatLng.latitude;
                marker.setPosition(new LatLng(lat, lng));
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    /**
     * 监听点击infowindow窗口事件回调
     */
    @Override
    public void onInfoWindowClick(Marker marker) {
        RingToast.show("你点击了infoWindow窗口" + marker.getTitle());
        RingToast.show("当前地图可视区域内Marker数量:"
                + aMap.getMapScreenMarkers().size());
    }

    /**
     * 监听拖动marker时事件回调
     */
    @Override
    public void onMarkerDrag(Marker marker) {
        String curDes = marker.getTitle() + "拖动时当前位置:(lat,lng)\n("
                + marker.getPosition().latitude + ","
                + marker.getPosition().longitude + ")";
        markerText.setText(curDes);
    }

    /**
     * 监听拖动marker结束事件回调
     */
    @Override
    public void onMarkerDragEnd(Marker marker) {
        markerText.setText(marker.getTitle() + "停止拖动");
    }

    /**
     * 监听开始拖动marker事件回调
     */
    @Override
    public void onMarkerDragStart(Marker marker) {
        markerText.setText(marker.getTitle() + "开始拖动");
    }

    /**
     * 监听amap地图加载成功事件回调
     */
    @Override
    public void onMapLoaded() {
        // 设置所有maker显示在当前可视区域地图中
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(XIAN).include(CHENGDU)
                .include(latlng).build();
        aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 150));
    }

    /**
     * 监听自定义infowindow窗口的infocontents事件回调
     */
    @Override
    public View getInfoContents(Marker marker) {
        if (radioOption.getCheckedRadioButtonId() != R.id.custom_info_contents) {
            return null;
        }
        View infoContent = getLayoutInflater().inflate(
                R.layout.custom_info_contents, null);
        render(marker, infoContent);
        return infoContent;
    }

    /**
     * 监听自定义infowindow窗口的infowindow事件回调
     */
    @Override
    public View getInfoWindow(Marker marker) {
        if (radioOption.getCheckedRadioButtonId() != R.id.custom_info_window) {
            return null;
        }
        View infoWindow = getLayoutInflater().inflate(
                R.layout.custom_info_window, null);
        render(marker, infoWindow);
        return infoWindow;
    }

    /**
     * 自定义infowinfow窗口
     */
    public void render(Marker marker, View view) {
        if (radioOption.getCheckedRadioButtonId() == R.id.custom_info_contents) {
            ((ImageView) view.findViewById(R.id.badge))
                    .setImageResource(R.mipmap.badge_sa);
        } else if (radioOption.getCheckedRadioButtonId() == R.id.custom_info_window) {
            ImageView imageView = (ImageView) view.findViewById(R.id.badge);
            imageView.setImageResource(R.mipmap.badge_wa);
        }
        String title = marker.getTitle();
        TextView titleUi = ((TextView) view.findViewById(R.id.title));
        if (title != null) {
            SpannableString titleText = new SpannableString(title);
            titleText.setSpan(new ForegroundColorSpan(Color.RED), 0,
                    titleText.length(), 0);
            titleUi.setTextSize(15);
            titleUi.setText(titleText);
        } else {
            titleUi.setText("");
        }
        String snippet = marker.getSnippet();
        TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
        if (snippet != null) {
            SpannableString snippetText = new SpannableString(snippet);
            snippetText.setSpan(new ForegroundColorSpan(Color.GREEN), 0,
                    snippetText.length(), 0);
            snippetUi.setTextSize(20);
            snippetUi.setText(snippetText);
        } else {
            snippetUi.setText("");
        }
    }

    /**
     * 对单击地图事件回调
     */
    @Override
    public void onMapClick(LatLng point) {
        mTapTextView.setText("tapped, point=" + point);
    }

    /**
     * 对长按地图事件回调
     */
    @Override
    public void onMapLongClick(LatLng point) {
        mTapTextView.setText("long pressed, point=" + point);
    }

    /**
     * 对正在移动地图事件回调
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        mCameraTextView.setText("onCameraChange:" + cameraPosition.toString());
    }

    /**
     * 对移动地图结束事件回调
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        mCameraTextView.setText("onCameraChangeFinish:"
                + cameraPosition.toString());
        VisibleRegion visibleRegion = aMap.getProjection().getVisibleRegion(); // 获取可视区域、
        LatLngBounds latLngBounds = visibleRegion.latLngBounds;// 获取可视区域的Bounds
        boolean isContain = latLngBounds.contains(SHANGHAI);// 判断上海经纬度是否包括在当前地图可见区域
        if (isContain) {
            RingToast.show("上海市在地图当前可见区域内");
        } else {
            RingToast.show("上海市超出地图当前可见区域");
        }
    }

    /**
     * 对触摸地图事件回调
     */
    @Override
    public void onTouch(MotionEvent event) {
        mTouchTextView.setText("触摸事件：屏幕位置" + event.getY() + " " + event.getY());
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

    /**
     * 地图动画效果终止回调方法
     */
    @Override
    public void onCancel() {
        RingToast.show("Animation to 陆家嘴 canceled");
    }

    /**
     * 地图动画效果完成回调方法
     */
    @Override
    public void onFinish() {
        RingToast.show("Animation to 陆家嘴 complete");
    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update, AMap.CancelableCallback callback) {
        boolean animated = ((CompoundButton) findViewById(R.id.animate))
                .isChecked();
        if (animated) {
            aMap.animateCamera(update, 1000, callback);
        } else {
            aMap.moveCamera(update);
        }
    }

    @OnClick({R.id.stop_animation, R.id.animate, R.id.Lujiazui, R.id.Zhongguancun, R.id.scroll_left,
            R.id.scroll_up, R.id.scroll_down, R.id.scroll_right, R.id.zoom_in, R.id.zoom_out, R.id.map_screenshot
            , R.id.buttonScale, R.id.scale_toggle, R.id.zoom_toggle, R.id.compass_toggle, R.id.mylocation_toggle,
            R.id.scroll_toggle, R.id.zoom_gestures_toggle, R.id.tilt_toggle, R.id.rotate_toggle, R.id.clearMap, R.id.resetMap})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            /**
             * 点击停止动画按钮响应事件
             */
            case R.id.stop_animation:
                aMap.stopAnimation();
                break;
            /**
             * 点击“去中关村”按钮响应事件
             */
            case R.id.Zhongguancun:
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                ZHONGGUANCUN, 18, 0, 30)), null);
                break;

            /**
             * 点击“去陆家嘴”按钮响应事件
             */
            case R.id.Lujiazui:
                changeCamera(
                        CameraUpdateFactory.newCameraPosition(new CameraPosition(
                                SHANGHAI, 18, 30, 0)), this);
                break;
            /**
             * 点击向左移动按钮响应事件，camera将向左边移动
             */
            case R.id.scroll_left:
                changeCamera(CameraUpdateFactory.scrollBy(-SCROLL_BY_PX, 0), null);
                break;
            /**
             * 点击向右移动按钮响应事件，camera将向右边移动
             */
            case R.id.scroll_right:
                changeCamera(CameraUpdateFactory.scrollBy(SCROLL_BY_PX, 0), null);
                break;
            /**
             * 点击向上移动按钮响应事件，camera将向上边移动
             */
            case R.id.scroll_up:
                changeCamera(CameraUpdateFactory.scrollBy(0, -SCROLL_BY_PX), null);
                break;
            /**
             * 点击向下移动按钮响应事件，camera将向下边移动
             */
            case R.id.scroll_down:
                changeCamera(CameraUpdateFactory.scrollBy(0, SCROLL_BY_PX), null);
                break;
            /**
             * 点击地图放大按钮响应事件
             */
            case R.id.zoom_in:
                changeCamera(CameraUpdateFactory.zoomIn(), null);
                break;
            /**
             * 点击地图缩小按钮响应事件
             */
            case R.id.zoom_out:
                changeCamera(CameraUpdateFactory.zoomOut(), null);
                break;
            case R.id.map_screenshot:
                aMap.getMapScreenShot(this);
                break;
            /**
             * 一像素代表多少米
             */
            case R.id.buttonScale:
                float scale = aMap.getScalePerPixel();
                RingToast.show("每像素代表" + scale + "米");
                break;
            /**
             * 设置地图默认的比例尺是否显示
             */
            case R.id.scale_toggle:
                mUiSettings.setScaleControlsEnabled(((CheckBox) view).isChecked());

                break;
            /**
             * 设置地图默认的缩放按钮是否显示
             */
            case R.id.zoom_toggle:
                mUiSettings.setZoomControlsEnabled(((CheckBox) view).isChecked());
                break;
            /**
             * 设置地图默认的指南针是否显示
             */
            case R.id.compass_toggle:
                mUiSettings.setCompassEnabled(((CheckBox) view).isChecked());
                break;
            /**
             * 设置地图默认的定位按钮是否显示
             */
            case R.id.mylocation_toggle:
                aMap.setLocationSource(this);// 设置定位监听
                mUiSettings.setMyLocationButtonEnabled(((CheckBox) view)
                        .isChecked()); // 是否显示默认的定位按钮
                aMap.setMyLocationEnabled(((CheckBox) view).isChecked());// 是否可触发定位并显示定位层
                break;
            /**
             * 设置地图是否可以手势滑动
             */
            case R.id.scroll_toggle:
                mUiSettings.setScrollGesturesEnabled(((CheckBox) view).isChecked());
                break;
            /**
             * 设置地图是否可以手势缩放大小
             */
            case R.id.zoom_gestures_toggle:
                mUiSettings.setZoomGesturesEnabled(((CheckBox) view).isChecked());
                break;
            /**
             * 设置地图是否可以倾斜
             */
            case R.id.tilt_toggle:
                mUiSettings.setTiltGesturesEnabled(((CheckBox) view).isChecked());
                break;
            /**
             * 设置地图是否可以旋转
             */
            case R.id.rotate_toggle:
                mUiSettings.setRotateGesturesEnabled(((CheckBox) view).isChecked());
                break;
            /**
             * 清空地图上所有已经标注的marker
             */
            case R.id.clearMap:
                if (aMap != null) {
                    aMap.clear();
                }
                break;
            /**
             * 重新标注所有的marker
             */
            case R.id.resetMap:
                if (aMap != null) {
                    aMap.clear();
                    addMarkersToMap();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onMapScreenShot(Bitmap bitmap) {

    }

    @Override
    public void onMapScreenShot(Bitmap bitmap, int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            FileOutputStream fos = new FileOutputStream(
                    Environment.getExternalStorageDirectory() + "/test_"
                            + sdf.format(new Date()) + ".png");
            boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            try {
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (b)
                RingToast.show("截屏成功");
            else {
                RingToast.show("截屏失败");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置logo位置，左下，底部居中，右下
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (aMap != null) {
            if (checkedId == R.id.bottom_left) {
                mUiSettings
                        .setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);// 设置地图logo显示在左下方
            } else if (checkedId == R.id.bottom_center) {
                mUiSettings
                        .setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_CENTER);// 设置地图logo显示在底部居中
            } else if (checkedId == R.id.bottom_right) {
                mUiSettings
                        .setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);// 设置地图logo显示在右下方
            }
        }

    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation aLocation) {
        if (mListener != null) {
            mListener.onLocationChanged(aLocation);// 显示系统小蓝点
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }
}
