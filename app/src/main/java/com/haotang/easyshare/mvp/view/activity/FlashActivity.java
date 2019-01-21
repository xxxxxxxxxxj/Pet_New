package com.haotang.easyshare.mvp.view.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.activity.DaggerFlashActivityCommponent;
import com.haotang.easyshare.di.module.activity.FlashActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.FlashBean;
import com.haotang.easyshare.mvp.presenter.FlashPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.iview.IFlashView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.util.CountdownUtil;
import com.haotang.easyshare.util.SharedPreferenceUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemTypeUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.other.permission.PermissionListener;
import com.ljy.devring.util.RingToast;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:欢迎页</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/4/11 17:47
 */
public class FlashActivity extends BaseActivity<FlashPresenter> implements IFlashView, AMapLocationListener {
    private final static String TAG = FlashActivity.class.getSimpleName();
    private String backup;
    private String imgUrl;
    private String jumpUrl;
    private int point;
    @Inject
    PermissionDialog permissionDialog;
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;

    @Override
    public void getFlashSuccess(FlashBean flashBean) {
        if (flashBean != null) {
            backup = flashBean.getBackup();
            imgUrl = flashBean.getImgUrl();
            jumpUrl = flashBean.getJumpUrl();
            point = flashBean.getPoint();
            if (StringUtil.isNotEmpty(imgUrl)) {
                initTimer(1);
            } else {
                initTimer(0);
            }
        } else {
            initTimer(0);
        }
    }

    @Override
    public void getFlashFail(int status, String desc) {
        initTimer(0);
        RingLog.e(TAG, "FlashActivity getFlashFail() status = " + status + "---desc = " + desc);
        SystemUtil.Exit(this, status);
    }

    @Override
    public void openAppCallbackSuccess(AddChargeBean data) {

    }

    @Override
    public void openAppCallbackFail(int status, String desc) {
        RingLog.e(TAG, "FlashActivity openAppCallbackFail() status = " + status + "---desc = " + desc);
        SystemUtil.Exit(this, status);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_flash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initWindows();
        setSwipeBackEnable(false);
        //使用Dagger2对本类中相关变量进行初始化
        DaggerFlashActivityCommponent.builder().flashActivityModule(new FlashActivityModule(this, this)).build().inject(this);
        activityListManager.addActivity(this);
        permissionDialog.setMessage("该功能需您授予\"获取手机信息和位置权限\"权限才可正常使用");
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        //SystemUtil.hideBottomUIMenu(this);
        SharedPreferenceUtil.getInstance(this).saveBoolean("is_open", true);
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
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                double lat = amapLocation.getLatitude();//获取纬度
                double lng = amapLocation.getLongitude();//获取经度
                amapLocation.getAddress();
                RingLog.d(TAG, "定位成功lat = "
                        + lat + ", lng = "
                        + lng + ",address = " + amapLocation.getAddress());
                if (lat > 0 && lng > 0) {
                    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    builder.addFormDataPart("lat", lat + "");
                    builder.addFormDataPart("lng", lng + "");
                    RequestBody build = builder.build();
                    mPresenter.openAppCallback(UrlConstants.getMapHeader(FlashActivity.this), build);
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
    protected void initData(Bundle savedInstanceState) {
        //申请必要权限
        DevRing.permissionManager().requestEachCombined(FlashActivity.this, new PermissionListener() {
            @Override
            public void onGranted(String permissionName) {
                //全部权限都被授予的话，则弹出底部选项
                if (SharedPreferenceUtil.getInstance(FlashActivity.this).getBoolean("guide", false)) {
                    mPresenter.startPageConfig(UrlConstants.getMapHeader(FlashActivity.this), FlashActivity.this);
                } else {
                    initTimer(0);
                }
            }

            @Override
            public void onDenied(String permissionName) {
                //如果用户拒绝了其中一个授权请求，则提醒用户
                RingToast.show("该功能需您授予\"获取手机信息和位置权限\"权限才可正常使用");
                if (SharedPreferenceUtil.getInstance(FlashActivity.this).getBoolean("guide", false)) {
                    mPresenter.startPageConfig(UrlConstants.getMapHeader(FlashActivity.this), FlashActivity.this);
                } else {
                    initTimer(0);
                }
            }

            @Override
            public void onDeniedWithNeverAsk(String permissionName) {
                //如果用户拒绝了其中一个授权请求，且勾选了不再提醒，则需要引导用户到权限管理页面开启
                permissionDialog.show();
            }
        }, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void initTimer(final int flag) {
        CountdownUtil.getInstance().newTimer(3000, 1000, new CountdownUtil.ICountDown() {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (SharedPreferenceUtil.getInstance(FlashActivity.this).getBoolean("guide", false)) {
                    if (flag == 1) {
                        goNext(StartPageActivity.class);
                    } else {
                        goNext(MainActivity.class);
                    }
                } else {
                    goNext(GuideActivity.class);
                }
            }
        }, "FLASH_TIMER");
    }

    private void goNext(Class clazz) {
        Intent intent = new Intent(this, clazz);
        intent.putExtra("img_url", imgUrl);
        intent.putExtra("jump_url", jumpUrl);
        intent.putExtra("backup", backup);
        intent.putExtra("point", point);
        startActivity(intent);
        finish();
        activityListManager.removeActivity(this); //退出activity
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CountdownUtil.getInstance().cancel("FLASH_TIMER");
        activityListManager.removeActivity(this); //退出activity
    }

    @Override
    protected void initEvent() {
        permissionDialog.setPositiveButton(R.string.permission_request_dialog_pos, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionDialog.dismiss();
                if (SharedPreferenceUtil.getInstance(FlashActivity.this).getBoolean("guide", false)) {
                    mPresenter.startPageConfig(UrlConstants.getMapHeader(FlashActivity.this), FlashActivity.this);
                } else {
                    initTimer(0);
                }
                SystemTypeUtil.goToPermissionManager(FlashActivity.this);
            }
        });
        permissionDialog.setNegativeButton(R.string.permission_request_dialog_nav, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionDialog.dismiss();
                if (SharedPreferenceUtil.getInstance(FlashActivity.this).getBoolean("guide", false)) {
                    mPresenter.startPageConfig(UrlConstants.getMapHeader(FlashActivity.this), FlashActivity.this);
                } else {
                    initTimer(0);
                }
            }
        });
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
