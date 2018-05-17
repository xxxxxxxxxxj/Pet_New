package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.di.component.activity.DaggerLoginActivityCommponent;
import com.haotang.easyshare.di.module.activity.LoginActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.LoginBean;
import com.haotang.easyshare.mvp.model.entity.res.SendVerifyCodeBean;
import com.haotang.easyshare.mvp.presenter.LoginPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.iview.ILoginView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.shareutil.LoginUtil;
import com.haotang.easyshare.shareutil.login.LoginListener;
import com.haotang.easyshare.shareutil.login.LoginPlatform;
import com.haotang.easyshare.shareutil.login.LoginResult;
import com.haotang.easyshare.shareutil.login.result.BaseToken;
import com.haotang.easyshare.util.CountdownUtil;
import com.haotang.easyshare.util.SharedPreferenceUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.RequestCallback;
import cn.jpush.im.android.api.model.DeviceInfo;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * 登录页
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginView, AMapLocationListener {
    private final static String TAG = LoginActivity.class.getSimpleName();
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.iv_titlebar_back)
    ImageView ivTitlebarBack;
    @BindView(R.id.tv_titlebar_other)
    TextView tvTitlebarOther;
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.tv_login_hqyzm)
    TextView tvLoginHqyzm;
    @BindView(R.id.et_login_yzm)
    EditText etLoginYzm;
    @BindView(R.id.iv_login_login)
    ImageView ivLoginLogin;
    @BindView(R.id.tv_login_qita)
    TextView tvLoginQita;
    @BindView(R.id.iv_login_wxlogin)
    ImageView ivLoginWxlogin;
    private String wxOpenId;
    private double lng;
    private double lat;
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DevRing.activityStackManager().pushOneActivity(this);
        DaggerLoginActivityCommponent.builder().loginActivityModule(new LoginActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("登陆");
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
        etLoginYzm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ivLoginLogin.setImageResource(R.mipmap.bg_login_no);
                ivLoginLogin.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String yzm = s.toString().trim();
                Log.e("TAG", "yzm = " + yzm);
                if (StringUtil.isNotEmpty(yzm) && StringUtil.isNotEmpty(etLoginPhone.getText().toString())
                        && etLoginPhone.getText().toString().trim().replace(" ", "").length() == 11) {
                    ivLoginLogin.setImageResource(R.mipmap.bg_login_yes);
                    ivLoginLogin.setEnabled(true);
                }
            }
        });
        etLoginPhone.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                ivLoginLogin.setEnabled(false);
                ivLoginLogin.setImageResource(R.mipmap.bg_login_no);
                if (s == null || s.length() == 0) return;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    etLoginPhone.setText(sb.toString());
                    etLoginPhone.setSelection(index);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String phone = s.toString().trim().replace(" ", "");
                Log.e("TAG", "phone = " + phone);
                if (phone.length() == 11 && StringUtil.isNotEmpty(etLoginYzm.getText().toString())) {
                    ivLoginLogin.setImageResource(R.mipmap.bg_login_yes);
                    ivLoginLogin.setEnabled(true);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mlocationClient.stopLocation();
        CountdownUtil.getInstance().cancel("LOGIN_TIMER");
        DevRing.activityStackManager().exitActivity(this); //退出activity
    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_login_hqyzm, R.id.iv_login_login, R.id.iv_login_wxlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_login_hqyzm:
                if (StringUtil.isEmpty(StringUtil.checkEditText(etLoginPhone))) {
                    etLoginPhone.setAnimation(SystemUtil.shakeAnimation(5));
                    return;
                }
                String replace = etLoginPhone.getText().toString().trim().replace(" ", "");
                if (replace.length() != 11) {
                    etLoginPhone.setAnimation(SystemUtil.shakeAnimation(5));
                    return;
                }
                mPresenter.sendVerifyCode(etLoginPhone.getText().toString().trim().replace(" ", ""));
                break;
            case R.id.iv_login_login:
                if (StringUtil.isEmpty(StringUtil.checkEditText(etLoginPhone))) {
                    etLoginPhone.setAnimation(SystemUtil.shakeAnimation(5));
                    return;
                }
                String replace1 = etLoginPhone.getText().toString().trim().replace(" ", "");
                if (replace1.length() != 11) {
                    etLoginPhone.setAnimation(SystemUtil.shakeAnimation(5));
                    return;
                }
                if (StringUtil.isEmpty(StringUtil.checkEditText(etLoginYzm))) {
                    etLoginYzm.setAnimation(SystemUtil.shakeAnimation(5));
                    return;
                }
                mPresenter.login(etLoginPhone.getText().toString().trim().replace(" ", ""), wxOpenId, lng, lat,
                        SharedPreferenceUtil.getInstance(LoginActivity.this).getString("jpush_id", ""),
                        etLoginYzm.getText().toString().trim().replace(" ", ""));
                break;
            case R.id.iv_login_wxlogin:
                LoginUtil.login(LoginActivity.this, LoginPlatform.WX, new LoginListener() {
                    @Override
                    public void loginSuccess(LoginResult result) {
                        RingLog.d(TAG, result.getUserInfo().getNickname());
                        RingLog.d(TAG, "登录成功");
                    }

                    @Override
                    public void beforeFetchUserInfo(BaseToken token) {
                        RingLog.d(TAG, "获取用户信息");
                    }

                    @Override
                    public void loginFailure(Exception e) {
                        e.printStackTrace();
                        RingLog.d(TAG, "登录失败e = " + e.toString());
                    }

                    @Override
                    public void loginCancel() {
                        RingLog.d(TAG, "登录取消");
                    }
                });
                break;
        }
    }

    @Override
    public void sendVerifyCodeSuccess(SendVerifyCodeBean data) {
        etLoginYzm.requestFocus();
        CountdownUtil.getInstance().newTimer(60000, 1000, new CountdownUtil.ICountDown() {
            @Override
            public void onTick(long millisUntilFinished) {
                tvLoginHqyzm.setBackgroundResource(R.mipmap.bg_sms_no);
                tvLoginHqyzm.setEnabled(false);
                tvLoginHqyzm.setText((millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                tvLoginHqyzm.setBackgroundResource(R.mipmap.bg_sms_yes);
                tvLoginHqyzm.setEnabled(true);
                tvLoginHqyzm.setText("重新获取");
            }
        }, "LOGIN_TIMER");
    }

    @Override
    public void sendVerifyCodeFail(int status, String desc) {
        RingLog.e(TAG, "LoginActivity sendVerifyCodeFail() status = " + status + "---desc = " + desc);
        RingToast.show("LoginActivity sendVerifyCodeFail() status = " + status + "---desc = " + desc);
    }

    @Override
    public void loginSuccess(LoginBean data) {
        RingLog.e(TAG, "loginSuccess");
        SharedPreferenceUtil.getInstance(LoginActivity.this).saveString("cellphone",
                etLoginPhone.getText().toString().trim().replace(" ", ""));
        if (data != null) {
            DevRing.busManager().postEvent(data);
        }
        finish();
    }

    @Override
    public void loginFail(int status, String desc) {
        RingLog.e(TAG, "LoginActivity loginFail() status = " + status + "---desc = " + desc);
        RingToast.show("LoginActivity loginFail() status = " + status + "---desc = " + desc);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                lat = amapLocation.getLatitude();//获取纬度
                lng = amapLocation.getLongitude();//获取经度
                RingLog.d(TAG, "定位成功lat = "
                        + lat + ", lng = "
                        + lng);
                if (lat > 0 && lng > 0) {
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
}
