package com.haotang.easyshare.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.activity.DaggerChargingPileDetailActivityCommponent;
import com.haotang.easyshare.di.module.activity.ChargingPileDetailActivityModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshEvent;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.ChargeDetailBean;
import com.haotang.easyshare.mvp.presenter.ChargingPileDetailPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.iview.IChargingPileDetailView;
import com.haotang.easyshare.mvp.view.widget.PermissionDialog;
import com.haotang.easyshare.mvp.view.widget.ShareBottomDialog;
import com.haotang.easyshare.util.GlideUtil;
import com.haotang.easyshare.util.SignUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.haotang.easyshare.verticalbanner.VerticalBannerView;
import com.ljy.devring.DevRing;
import com.ljy.devring.other.RingLog;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.haotang.easyshare.R.id.iv_chargingdetail_sc;

/**
 * 充电桩详情
 */
public class ChargingPileDetailActivity extends BaseActivity<ChargingPileDetailPresenter> implements IChargingPileDetailView, AMapLocationListener {
    private final static String TAG = ChargingPileDetailActivity.class.getSimpleName();
    @Inject
    PermissionDialog permissionDialog;
    @BindView(R.id.iv_chargingdetail_back)
    ImageView ivChargingdetailBack;
    @BindView(iv_chargingdetail_sc)
    ImageView ivChargingdetailSc;
    @BindView(R.id.iv_chargingdetail_share)
    ImageView ivChargingdetailShare;
    @BindView(R.id.ll_chargingdetail_barright)
    LinearLayout llChargingdetailBarright;
    @BindView(R.id.rl_chargingdetail_title)
    RelativeLayout rlChargingdetailTitle;
    @BindView(R.id.tv_chargingdetail_pl)
    TextView tvChargingdetailPl;
    @BindView(R.id.ll_chargingdetail_pl)
    LinearLayout llChargingdetailPl;
    @BindView(R.id.tv_chargingdetail_cdcs)
    TextView tvChargingdetailCdcs;
    @BindView(R.id.iv_chargingdetail_img)
    ImageView ivChargingdetailImg;
    @BindView(R.id.rl_chargingdetail_img)
    RelativeLayout rlChargingdetailImg;
    @BindView(R.id.iv_chargingdetail_lt)
    ImageView ivChargingdetailLt;
    @BindView(R.id.iv_chargingdetail_phone)
    ImageView ivChargingdetailPhone;
    @BindView(R.id.ll_chargingdetail_ltdh)
    LinearLayout llChargingdetailLtdh;
    @BindView(R.id.iv_chargingdetail_ggorgr)
    ImageView ivChargingdetailGgorgr;
    @BindView(R.id.tv_chargingdetail_name)
    TextView tvChargingdetailName;
    @BindView(R.id.tv_chargingdetail_cdf)
    TextView tvChargingdetailCdf;
    @BindView(R.id.tv_chargingdetail_fwf)
    TextView tvChargingdetailFwf;
    @BindView(R.id.tv_chargingdetail_kuaichong_num)
    TextView tvChargingdetailKuaichongNum;
    @BindView(R.id.ll_chargingdetail_kuaichong)
    LinearLayout llChargingdetailKuaichong;
    @BindView(R.id.tv_chargingdetail_manchong_num)
    TextView tvChargingdetailManchongNum;
    @BindView(R.id.ll_chargingdetail_manchong)
    LinearLayout llChargingdetailManchong;
    @BindView(R.id.tv_chargingdetail_kongxian_num)
    TextView tvChargingdetailKongxianNum;
    @BindView(R.id.ll_chargingdetail_kongxian)
    LinearLayout llChargingdetailKongxian;
    @BindView(R.id.vbv_chargingdetail)
    VerticalBannerView vbvChargingdetail;
    @BindView(R.id.tv_chargingdetail_juli)
    TextView tvChargingdetailJuli;
    @BindView(R.id.ll_chargingdetail_daohang)
    LinearLayout llChargingdetailDaohang;
    @BindView(R.id.tv_chargingdetail_address)
    TextView tvChargingdetailAddress;
    @BindView(R.id.tv_chargingdetail_yys)
    TextView tvChargingdetailYys;
    @BindView(R.id.tv_chargingdetail_kfsj)
    TextView tvChargingdetailKfsj;
    @BindView(R.id.tv_chargingdetail_zffs)
    TextView tvChargingdetailZffs;
    @BindView(R.id.tv_chargingdetail_tcf)
    TextView tvChargingdetailTcf;
    @BindView(R.id.tv_chargingdetail_zdbz)
    TextView tvChargingdetailZdbz;
    private String uuid;
    private String city;
    private double lat;
    private double lng;
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;
    private String address;
    private double chargeLng;
    private double chargeLat;
    private int is_collect;
    private Map<String, String> parmMap = new HashMap<String, String>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_charging_pile_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerChargingPileDetailActivityCommponent.builder().
                chargingPileDetailActivityModule(new ChargingPileDetailActivityModule(this, this)).build().inject(this);
        uuid = getIntent().getStringExtra("uuid");
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
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
                lat = amapLocation.getLatitude();//获取纬度
                lng = amapLocation.getLongitude();//获取经度
                city = amapLocation.getCity();
                amapLocation.getAddress();
                RingLog.d(TAG, "定位成功lat = "
                        + lat + ", lng = "
                        + lng + ",city = " + city + ",address = " + amapLocation.getAddress());
                if (lat > 0 && lng > 0) {
                    Map<String, String> mapHeader = UrlConstants.getMapHeader(ChargingPileDetailActivity.this);
                    mapHeader.put("lng", String.valueOf(lng));
                    mapHeader.put("lat", String.valueOf(lat));
                    mapHeader.put("key", AppConfig.SERVER_KEY);
                    mapHeader.put("uuid", uuid);
                    RingLog.d(TAG, "mapHeader =  " + mapHeader.toString());
                    String md5 = SignUtil.sign(mapHeader, "MD5");
                    RingLog.d(TAG, "md5 =  " + md5);
                    mPresenter.detail(lng, lat, uuid, md5);
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

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }

    @OnClick({R.id.iv_chargingdetail_back, iv_chargingdetail_sc, R.id.iv_chargingdetail_share, R.id.ll_chargingdetail_pl, R.id.iv_chargingdetail_lt, R.id.iv_chargingdetail_phone, R.id.ll_chargingdetail_daohang})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_chargingdetail_back:
                finish();
                break;
            case iv_chargingdetail_sc:
                if (SystemUtil.checkLogin(this)) {
                    parmMap.clear();
                    parmMap.put("uuid", uuid);
                    if (is_collect == 0) {//是否已收藏(0:否、1:是)
                        mPresenter.follow(parmMap);
                    } else if (is_collect == 1) {
                        mPresenter.cancel(parmMap);
                    }
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.iv_chargingdetail_share:
                ShareBottomDialog dialog = new ShareBottomDialog();
                dialog.setShareInfo("测试", "测试",
                        "https://www.duba.com","http://img.sayiyinxiang.com/api/brand/imgs/15246549042921928075.jpg");
                dialog.show(getSupportFragmentManager());
                break;
            case R.id.ll_chargingdetail_pl:
                startActivity(new Intent(ChargingPileDetailActivity.this, CommentDetailActivity.class).putExtra("uuid", uuid));
                break;
            case R.id.iv_chargingdetail_lt:
                break;
            case R.id.iv_chargingdetail_phone:
                SystemUtil.cellPhone(ChargingPileDetailActivity.this, "");
                break;
            case R.id.ll_chargingdetail_daohang:
                SystemUtil.goNavigation(this, chargeLat, chargeLat, "我的位置", address, city);
                break;
        }
    }

    @Override
    public void detailSuccess(ChargeDetailBean data) {
        if (data != null) {
            is_collect = data.getIsCollect();
            uuid = data.getUuid();
            address = data.getAddress();
            chargeLat = data.getLat();
            chargeLng = data.getLng();
            StringUtil.setText(tvChargingdetailName, data.getTitle(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailJuli, data.getDistance(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailAddress, data.getAddress(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailKfsj, "开放时间：" + data.getOpenTime(), "", View.VISIBLE, View.VISIBLE);
            tvChargingdetailCdcs.bringToFront();
            StringUtil.setText(tvChargingdetailCdcs, "充电" + data.getTimes() + "次", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailCdf, "充电费：" + data.getElectricityPrice() + "元/度", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailFwf, "服务费：" + data.getServiceFee() + "元/度", "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailYys, data.getProvider(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailZffs, data.getPayWay(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailTcf, data.getParkingPrice(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailZdbz, data.getRemark(), "", View.VISIBLE, View.VISIBLE);
            StringUtil.setText(tvChargingdetailPl, "评论(" + data.getCommentTotal() + ")", "", View.VISIBLE, View.VISIBLE);
            GlideUtil.loadNetImg(this, data.getHeadImg(), ivChargingdetailImg, R.mipmap.ic_image_load);
            if (is_collect == 0) {//是否已收藏(0:否、1:是)
                ivChargingdetailSc.setImageResource(R.mipmap.sc_not);
            } else if (is_collect == 1) {
                ivChargingdetailSc.setImageResource(R.mipmap.sc);
            }
            if (data.getIsPrivate() == 0) {//公共
                ivChargingdetailLt.setVisibility(View.GONE);
                ivChargingdetailGgorgr.setImageResource(R.mipmap.icon_gg);
            } else if (data.getIsPrivate() == 1) {//个人
                ivChargingdetailLt.setVisibility(View.VISIBLE);
                ivChargingdetailGgorgr.setImageResource(R.mipmap.icon_gr);
            }
            if (data.getFastNum() > 0) {
                StringUtil.setText(tvChargingdetailKuaichongNum, "快充" + data.getFastNum() + "个", "", View.VISIBLE, View.VISIBLE);
                llChargingdetailKuaichong.setVisibility(View.VISIBLE);
            } else {
                llChargingdetailKuaichong.setVisibility(View.GONE);
            }
            if (data.getSlowNum() > 0) {
                StringUtil.setText(tvChargingdetailManchongNum, "慢充" + data.getSlowNum() + "个", "", View.VISIBLE, View.VISIBLE);
                llChargingdetailManchong.setVisibility(View.VISIBLE);
            } else {
                llChargingdetailManchong.setVisibility(View.GONE);
            }
            if (data.getFreeNum() > 0) {
                StringUtil.setText(tvChargingdetailKongxianNum, "空闲" + data.getFreeNum() + "个", "", View.VISIBLE, View.VISIBLE);
                llChargingdetailKongxian.setVisibility(View.VISIBLE);
            } else {
                llChargingdetailKongxian.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void detailFail(int code, String msg) {
        RingLog.e(TAG, "detailFail() status = " + code + "---desc = " + msg);
    }

    @Override
    public void followSuccess(AddChargeBean data) {
        is_collect = 1;
        ivChargingdetailSc.setImageResource(R.mipmap.sc);
        DevRing.busManager().postEvent(new RefreshEvent(RefreshEvent.COLLECT_OR_CANCEL_CHARGE));
    }

    @Override
    public void followFail(int code, String msg) {
        RingLog.e(TAG, "detailFail() status = " + code + "---desc = " + msg);
    }

    @Override
    public void cancelSuccess(AddChargeBean data) {
        is_collect = 0;
        ivChargingdetailSc.setImageResource(R.mipmap.sc_not);
        DevRing.busManager().postEvent(new RefreshEvent(RefreshEvent.COLLECT_OR_CANCEL_CHARGE));
    }

    @Override
    public void cancelFail(int code, String msg) {
        RingLog.e(TAG, "detailFail() status = " + code + "---desc = " + msg);
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
