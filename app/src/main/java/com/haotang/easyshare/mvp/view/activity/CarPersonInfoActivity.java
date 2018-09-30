package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.activity.DaggerCarPersonInfoActivityCommponent;
import com.haotang.easyshare.di.module.activity.CarPersonInfoActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.HotSpecialCarBean;
import com.haotang.easyshare.mvp.presenter.CarPersonInfoPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.iview.ICarPersonInfoView;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.haotang.easyshare.util.UmenUtil;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 填写信息页
 */
public class CarPersonInfoActivity extends BaseActivity<CarPersonInfoPresenter> implements ICarPersonInfoView, AMapLocationListener {
    private final static String TAG = CarPersonInfoActivity.class.getSimpleName();
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.tv_cardetail_person_submit)
    TextView tvCardetailPersonSubmit;
    @BindView(R.id.tv_cardetail_person_tag)
    TextView tvCardetailPersonTag;
    @BindView(R.id.tv_cardetail_person_price)
    TextView tvCardetailPersonPrice;
    @BindView(R.id.iv_cardetail_person_img)
    ImageView ivCardetailPersonImg;
    @BindView(R.id.tv_cardetail_person_name)
    TextView tvCardetailPersonName;
    @BindView(R.id.tv_cardetail_person_xuhang)
    TextView tvCardetailPersonXuhang;
    @BindView(R.id.tv_cardetail_person_dq_title)
    TextView tvCardetailPersonDqTitle;
    @BindView(R.id.iv_cardetail_person_dq)
    ImageView ivCardetailPersonDq;
    @BindView(R.id.tv_cardetail_person_dq)
    TextView tv_cardetail_person_dq;
    @BindView(R.id.rl_cardetail_person_dq)
    RelativeLayout rlCardetailPersonDq;
    @BindView(R.id.tv_cardetail_person_xm)
    TextView tvCardetailPersonXm;
    @BindView(R.id.et_cardetail_person_xm)
    EditText etCardetailPersonXm;
    @BindView(R.id.tv_cardetail_person_phone)
    TextView tvCardetailPersonPhone;
    @BindView(R.id.et_cardetail_person_phone)
    EditText etCardetailPersonPhone;
    private HotSpecialCarBean.DataBean carDetailData;
    private double lat;
    private double lng;
    private String city = "";
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;
    private String cityCode;
    private int id;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_car_person_info;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        carDetailData = (HotSpecialCarBean.DataBean) getIntent().getSerializableExtra("carDetailData");
        activityListManager.addActivity(this);
        DaggerCarPersonInfoActivityCommponent.builder().
                carPersonInfoActivityModule(new CarPersonInfoActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        setLocation();
        id = carDetailData.getId();
        tvTitlebarTitle.setText("填写个人信息");
        if (StringUtil.isNotEmpty(carDetailData.getLabel())) {
            if (carDetailData.getLabel().equals("0")) {
                tvCardetailPersonTag.setVisibility(View.GONE);
            } else {
                tvCardetailPersonTag.setVisibility(View.VISIBLE);
                tvCardetailPersonTag.bringToFront();
                if (carDetailData.getLabel().equals("1")) {
                    tvCardetailPersonTag.setText("最新");
                } else if (carDetailData.getLabel().equals("2")) {
                    tvCardetailPersonTag.setText("最热");
                } else if (carDetailData.getLabel().equals("3")) {
                    tvCardetailPersonTag.setText("推荐");
                }
            }
        } else {
            tvCardetailPersonTag.setVisibility(View.GONE);
        }
        GlideUtil.loadNetImg(this, carDetailData.getIcon(), ivCardetailPersonImg, R.mipmap.ic_image_load);
        StringUtil.setText(tvCardetailPersonName, carDetailData.getCar(), "", View.VISIBLE, View.VISIBLE);
        StringUtil.setText(tvCardetailPersonXuhang, carDetailData.getBatteryLife(), "", View.VISIBLE, View.VISIBLE);
        StringUtil.setText(tvCardetailPersonPrice, "¥" + carDetailData.getPrice(), "", View.VISIBLE, View.VISIBLE);
        UmenUtil.UmengEventStatistics(this,UmenUtil.yxzx11);
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
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
        SystemUtil.goneJP(this);
    }

    @Override
    protected void initEvent() {
        etCardetailPersonPhone.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
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
                    etCardetailPersonPhone.setText(sb.toString());
                    etCardetailPersonPhone.setSelection(index);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @OnClick({R.id.iv_titlebar_back, R.id.tv_cardetail_person_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.tv_cardetail_person_submit:
                if (StringUtil.isEmpty(StringUtil.checkEditText(etCardetailPersonXm))) {
                    RingToast.show("请输入姓名");
                    SystemUtil.goneJP(this);
                    return;
                }
                if (StringUtil.isEmpty(StringUtil.checkEditText(etCardetailPersonPhone))) {
                    RingToast.show("请输入手机号码");
                    SystemUtil.goneJP(this);
                    return;
                }
                String replace1 = etCardetailPersonPhone.getText().toString().trim().replace(" ", "");
                if (replace1.length() != 11) {
                    RingToast.show("请输入正确的手机号码");
                    SystemUtil.goneJP(this);
                    return;
                }
                showDialog();
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("carId", id + "");
                builder.addFormDataPart("linkman", etCardetailPersonXm.getText().toString().trim());
                builder.addFormDataPart("telephone", etCardetailPersonPhone.getText().toString().trim());
                builder.addFormDataPart("lat", lat + "");
                builder.addFormDataPart("lng", lng + "");
                RequestBody build = builder.build();
                mPresenter.save(UrlConstants.getMapHeader(this),build);
                break;
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                lat = amapLocation.getLatitude();//获取纬度
                lng = amapLocation.getLongitude();//获取经度
                city = amapLocation.getCity();
                tv_cardetail_person_dq.setText(city);
                cityCode = amapLocation.getCityCode();
                amapLocation.getAddress();
                RingLog.d(TAG, "定位成功lat = "
                        + lat + ", lng = "
                        + lng + ",city = " + city + ",cityCode = " + cityCode + ",address = " + amapLocation.getAddress());
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

    @Override
    public void saveFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "saveFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }

    @Override
    public void saveSuccess(AddChargeBean data) {
        finish();
        RingToast.show("预定成功");
    }
}
