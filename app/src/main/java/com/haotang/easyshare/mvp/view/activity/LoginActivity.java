package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.activity.DaggerLoginActivityCommponent;
import com.haotang.easyshare.di.module.activity.LoginActivityModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.model.entity.res.LoginBean;
import com.haotang.easyshare.mvp.model.entity.res.SendVerifyCodeBean;
import com.haotang.easyshare.mvp.model.entity.res.WxLoginBean;
import com.haotang.easyshare.mvp.model.entity.res.WxUserInfoBean;
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
import com.haotang.easyshare.util.Global;
import com.haotang.easyshare.util.SharedPreferenceUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
    @BindView(R.id.ll_login_qita)
    LinearLayout ll_login_qita;
    private String userName;
    private String headImg;
    private String wxOpenId;
    private double lng;
    private double lat;
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;
    private int previous;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerLoginActivityCommponent.builder().loginActivityModule(new LoginActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        Intent intent = getIntent();
        previous = intent.getIntExtra("previous", 0);
        tvTitlebarTitle.setText("登录");
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
                try {
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
                } catch (Exception e) {
                    e.printStackTrace();
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
        SystemUtil.goneJP(this);
        CountdownUtil.getInstance().cancel("LOGIN_TIMER");
        activityListManager.removeActivity(this); //退出activity
    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_login_hqyzm, R.id.iv_login_login, R.id.iv_login_wxlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_login_hqyzm:
                if (StringUtil.isEmpty(StringUtil.checkEditText(etLoginPhone))) {
                    RingToast.show("请输入手机号码");
                    SystemUtil.goneJP(this);
                    return;
                }
                String replace = etLoginPhone.getText().toString().trim().replace(" ", "");
                if (replace.length() != 11) {
                    RingToast.show("请输入正确的手机号码");
                    SystemUtil.goneJP(this);
                    return;
                }
                showDialog();
                mPresenter.sendVerifyCode(UrlConstants.getMapHeader(this),etLoginPhone.getText().toString().trim().replace(" ", ""));
                break;
            case R.id.iv_login_login:
                if (StringUtil.isEmpty(StringUtil.checkEditText(etLoginPhone))) {
                    RingToast.show("请输入手机号码");
                    SystemUtil.goneJP(this);
                    return;
                }
                String replace1 = etLoginPhone.getText().toString().trim().replace(" ", "");
                if (replace1.length() != 11) {
                    RingToast.show("请输入正确的手机号码");
                    SystemUtil.goneJP(this);
                    return;
                }
                if (StringUtil.isEmpty(StringUtil.checkEditText(etLoginYzm))) {
                    RingToast.show("请输入验证码");
                    SystemUtil.goneJP(this);
                    return;
                }
                showDialog();
                mPresenter.login(UrlConstants.getMapHeader(this),etLoginPhone.getText().toString().trim().replace(" ", ""), wxOpenId, lng, lat,
                        SharedPreferenceUtil.getInstance(LoginActivity.this).getString("jpush_id", ""),
                        etLoginYzm.getText().toString().trim().replace(" ", ""), userName, headImg);
                break;
            case R.id.iv_login_wxlogin:
                LoginUtil.login(LoginActivity.this, LoginPlatform.WX, new LoginListener() {
                    @Override
                    public void loginSuccess(LoginResult result) {
                        RingLog.e(TAG, "LoginResult = " + result.toString());
                        RingLog.e(TAG, "登录成功");
                        RingToast.show("微信登录成功");
                        if (result != null) {
                            showDialog();
                            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                            builder.addFormDataPart("code", result.getmCcode());
                            RequestBody body = builder.build();
                            mPresenter.getWxOpenId(UrlConstants.getMapHeader(LoginActivity.this),body);
                        }
                    }

                    @Override
                    public void beforeFetchUserInfo(BaseToken token) {
                        RingLog.e(TAG, "获取用户信息");
                    }

                    @Override
                    public void loginFailure(Exception e) {
                        e.printStackTrace();
                        RingLog.e(TAG, "登录失败e = " + e.toString());
                    }

                    @Override
                    public void loginCancel() {
                        RingLog.e(TAG, "登录取消");
                    }
                });
                break;
        }
    }

    @Override
    public void sendVerifyCodeSuccess(SendVerifyCodeBean data) {
        disMissDialog();
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
        disMissDialog();
        RingToast.show(desc);
        RingLog.e(TAG, "LoginActivity sendVerifyCodeFail() status = " + status + "---desc = " + desc);
    }

    @Override
    public void loginSuccess(LoginBean data) {
        disMissDialog();
        RingLog.e(TAG, "loginSuccess");
        SharedPreferenceUtil.getInstance(LoginActivity.this).saveString("cellphone",
                etLoginPhone.getText().toString().trim().replace(" ", ""));
        if (data != null) {
            DevRing.busManager().postEvent(data);
        }
        if (previous == Global.H5_TO_LOGIN) {
            DevRing.busManager().postEvent(new RefreshFragmentEvent(RefreshFragmentEvent.REFRESH_WEBVIEW_LOGIN));
        }
        finish();
    }

    @Override
    public void loginFail(int status, String desc) {
        disMissDialog();
        RingToast.show(desc);
        RingLog.e(TAG, "LoginActivity loginFail() status = " + status + "---desc = " + desc);
    }

    @Override
    public void getWxOpenIdSuccess(WxLoginBean data) {
        disMissDialog();
        if (data != null && StringUtil.isNotEmpty(data.getOpenId())) {
            showDialog();
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            builder.addFormDataPart("wxOpenId", data.getOpenId());
            RequestBody body = builder.build();
            mPresenter.getWxUserInfo(UrlConstants.getMapHeader(this),body);
        }
    }

    @Override
    public void getWxOpenIdFail(int status, String desc) {
        disMissDialog();
        RingToast.show(desc);
        RingLog.e(TAG, "LoginActivity getWxOpenIdFail() status = " + status + "---desc = " + desc);
    }

    @Override
    public void getWxUserInfoFail(int status, String desc) {
        disMissDialog();
        RingToast.show(desc);
        RingLog.e(TAG, "LoginActivity getWxUserInfoFail() status = " + status + "---desc = " + desc);
    }

    @Override
    public void getWxUserInfoSuccess(WxUserInfoBean data) {
        disMissDialog();
        if (data != null) {
            ll_login_qita.setVisibility(View.GONE);
            userName = data.getNickname();
            headImg = data.getHeadimgurl();
            wxOpenId = data.getOpenid();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                lat = amapLocation.getLatitude();//获取纬度
                lng = amapLocation.getLongitude();//获取经度
                RingLog.e(TAG, "定位成功lat = "
                        + lat + ", lng = "
                        + lng);
                if (lat > 0 && lng > 0) {
                    mlocationClient.stopLocation();
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                RingLog.e(TAG, "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
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
