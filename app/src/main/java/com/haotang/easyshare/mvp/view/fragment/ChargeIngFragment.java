package com.haotang.easyshare.mvp.view.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.constant.UrlConstants;
import com.haotang.easyshare.di.component.fragment.DaggerChargeIngFragmentCommponent;
import com.haotang.easyshare.di.module.fragment.ChargeIngFragmentModule;
import com.haotang.easyshare.mvp.model.entity.event.RefreshBalanceEvent;
import com.haotang.easyshare.mvp.model.entity.event.RefreshFragmentEvent;
import com.haotang.easyshare.mvp.model.entity.event.SelectCouponEvent;
import com.haotang.easyshare.mvp.model.entity.event.StartCodeChargeing;
import com.haotang.easyshare.mvp.model.entity.res.AddChargeBean;
import com.haotang.easyshare.mvp.model.entity.res.ChargeingBill;
import com.haotang.easyshare.mvp.model.entity.res.ChargeingState;
import com.haotang.easyshare.mvp.model.entity.res.HomeBean;
import com.haotang.easyshare.mvp.model.entity.res.StartChargeing;
import com.haotang.easyshare.mvp.presenter.ChargeIngFragmentPresenter;
import com.haotang.easyshare.mvp.view.activity.LoginActivity;
import com.haotang.easyshare.mvp.view.activity.RechargeActivity;
import com.haotang.easyshare.mvp.view.activity.RechargeRecordActivity;
import com.haotang.easyshare.mvp.view.activity.ScanCodeActivity;
import com.haotang.easyshare.mvp.view.activity.SelectCouponActivity;
import com.haotang.easyshare.mvp.view.fragment.base.BaseFragment;
import com.haotang.easyshare.mvp.view.iview.IChargeIngFragmentView;
import com.haotang.easyshare.mvp.view.services.ChargeBillService;
import com.haotang.easyshare.mvp.view.services.ChargeStateService;
import com.haotang.easyshare.mvp.view.widget.AlertDialogNavAndPost;
import com.haotang.easyshare.mvp.view.widget.LoadingProgressDailog;
import com.haotang.easyshare.mvp.view.widget.WaveProgressView;
import com.haotang.easyshare.util.ComputeUtil;
import com.haotang.easyshare.util.CountdownUtil;
import com.haotang.easyshare.util.PollingUtils;
import com.haotang.easyshare.util.SharedPreferenceUtil;
import com.haotang.easyshare.util.StringUtil;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * <p>Title:${type_name}</p>
 * <p>Description:充电主界面</p>
 * <p>Company:北京昊唐科技有限公司</p>
 *
 * @author 徐俊
 * @date XJ on 2018/7/20 14:36
 */
public class ChargeIngFragment extends BaseFragment<ChargeIngFragmentPresenter> implements IChargeIngFragmentView, AMapLocationListener {
    protected final static String TAG = ChargeIngFragment.class.getSimpleName();
    private static final int CHARING_TO_COUPON = 111;
    @BindView(R.id.tv_chargeing_titlebar_other)
    TextView tvChargeingTitlebarOther;
    @BindView(R.id.tv_chargeing_num)
    TextView tvChargeingNum;
    @BindView(R.id.tv_chargeing_money)
    TextView tvChargeingMoney;
    @BindView(R.id.tv_chargeing_ljcz)
    TextView tvChargeingLjcz;
    @BindView(R.id.ll_chargeing_charge_before)
    LinearLayout rlChargeingChargeBefore;
    @BindView(R.id.tv_chargeing_name)
    TextView tvChargeingName;
    @BindView(R.id.tv_chargeing_gzbx)
    TextView tvChargeingGzbx;
    @BindView(R.id.tv_chargeing_kwh)
    TextView tvChargeingKwh;
    @BindView(R.id.tv_chargeing_status)
    TextView tvChargeingStatus;
    @BindView(R.id.tv_chargeing_cdf)
    TextView tvChargeingCdf;
    @BindView(R.id.tv_chargeing_fwf)
    TextView tvChargeingFwf;
    @BindView(R.id.tv_chargeing_zfy)
    TextView tvChargeingZfy;
    @BindView(R.id.btn_chargeing_submit)
    Button btnChargeingSubmit;
    @BindView(R.id.ll_chargeing_charge_after)
    LinearLayout rlChargeingChargeAfter;
    @BindView(R.id.ll_chargeing_ing)
    LinearLayout ll_chargeing_ing;
    @BindView(R.id.ll_chargeing_jsm)
    LinearLayout ll_chargeing_jsm;
    @BindView(R.id.tv_chargeing_jsmname)
    TextView tv_chargeing_jsmname;
    @BindView(R.id.tv_chargeing_jsm)
    TextView tv_chargeing_jsm;
    @BindView(R.id.tv_chargeing_coupon)
    TextView tv_chargeing_coupon;
    @BindView(R.id.rl_chargeing_coupon)
    RelativeLayout rl_chargeing_coupon;
    @BindView(R.id.tv_chargeing_tishi)
    TextView tv_chargeing_tishi;
    @BindView(R.id.wpv_chargeing)
    WaveProgressView wpv_chargeing;
    @BindView(R.id.tv_chargeing_cdsj)
    CountdownView tv_chargeing_cdsj;
    private String phone;
    private int orderId;
    private String endCode;
    private String totalPrice;
    private int stateTimeOut = 5;
    private int billTimeOut = 5;
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;
    private int couponId;
    private double balance;
    private String totalServiceFee;
    private int state;
    private LoadingProgressDailog timeOutDialog;
    private LoadingProgressDailog.Builder timeOutBuilder;
    private int num;
    private int unit;
    private AlertDialogNavAndPost alertDialogTimeRecharge;
    private int timeOutRecharge;
    private String dialogStr = "";
    private int interval = 10;//单位秒
    private int providerId;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    PollingUtils.stopPollingService(getActivity(), ChargeStateService.class, ChargeStateService.ACTION);
                    PollingUtils.startPollingService(getActivity(), stateTimeOut, ChargeStateService.class, ChargeStateService.ACTION, orderId);
                    break;
            }
        }
    };

    @Override
    protected boolean isLazyLoad() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.chargeingfragment;
    }

    @Override
    protected void initView() {
        DaggerChargeIngFragmentCommponent.builder()
                .chargeIngFragmentModule(new ChargeIngFragmentModule(this, mActivity))
                .build()
                .inject(this);
        setLocation();
        rlChargeingChargeAfter.setVisibility(View.GONE);
        rlChargeingChargeBefore.setVisibility(View.VISIBLE);
        tvChargeingLjcz.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvChargeingLjcz.getPaint().setAntiAlias(true);//抗锯齿
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe
    public void getCoupon(SelectCouponEvent event) {//选择优惠券返回
        if (event != null) {
            int reduceType = event.getReduceType();
            couponId = event.getId();
            if (reduceType == 1) {//减免券
                tv_chargeing_coupon.setText("优惠券减免" + event.getAmount() + "元");
                totalPrice = String.valueOf(ComputeUtil.sub(Double.valueOf(totalPrice), event.getAmount()));
                StringUtil.setText(tvChargeingZfy, totalPrice + "", "", View.VISIBLE, View.VISIBLE);
            } else if (reduceType == 2) {//折扣券
                tv_chargeing_coupon.setText("服务费" + event.getAmount() + "折,优惠" + ComputeUtil.sub(Double.valueOf(totalServiceFee), ComputeUtil.mul(Double.valueOf(totalServiceFee), event.getAmount())) + "元");
                totalPrice = String.valueOf(ComputeUtil.sub(Double.valueOf(totalPrice), ComputeUtil.mul(Double.valueOf(totalServiceFee), event.getAmount())));
                StringUtil.setText(tvChargeingFwf, ComputeUtil.mul(Double.valueOf(totalServiceFee), event.getAmount()) + "", "", View.VISIBLE, View.VISIBLE);
                StringUtil.setText(tvChargeingZfy, totalPrice + "", "", View.VISIBLE, View.VISIBLE);
            }
        }
    }

    @Subscribe
    public void RefreshBalance(RefreshBalanceEvent event) {//充值返回
        if (event != null) {
            closeRechargeTimeOutDialog();
            showDialog();
            mPresenter.home(UrlConstants.getMapHeader(mActivity));
        }
    }

    @Subscribe
    public void RefreshFragment(RefreshFragmentEvent refreshFragmentEvent) {//切换地步tab
        if (refreshFragmentEvent != null && refreshFragmentEvent.getRefreshIndex() ==
                RefreshFragmentEvent.REFRESH_CHARGEINGFRAGMET && mPresenter != null) {
            RingLog.e("REFRESH_CHARGEINGFRAGMET");
            refresh();
        }
    }

    private void showTimeOutDialog(int timeOut) {
        if (timeOut > 0) {
            if (timeOutDialog == null) {
                timeOutBuilder = new LoadingProgressDailog.Builder(mActivity)
                        .setMessage("充电连接中..." + timeOut + "S\n请及时查看状态哦")
                        .setCancelable(false)
                        .setCancelOutside(false);
                timeOutDialog = timeOutBuilder.create();
            }
            if (timeOutDialog.isShowing()) {
                timeOutDialog.dismiss();
            }
            timeOutDialog.show();
            CountdownUtil.getInstance().newTimer(timeOut * 1000, 1000, new CountdownUtil.ICountDown() {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.e("TAG", "millisUntilFinished = " + (millisUntilFinished / 1000));
                    if (timeOutBuilder != null) {
                        Log.e("TAG", "充电连接中... = " + (millisUntilFinished / 1000));
                        timeOutBuilder.setMessage("充电连接中..." + (millisUntilFinished / 1000) + "S\n请及时查看状态哦");
                    }
                }

                @Override
                public void onFinish() {
                    closeTimeOutDialog();
                    num--;
                    if (num > 0) {
                        showTimeOutDialog(unit);
                    } else {
                        RingToast.show("连接超时,未能成功启动");
                        rlChargeingChargeAfter.setVisibility(View.GONE);
                        rlChargeingChargeBefore.setVisibility(View.VISIBLE);
                        tvChargeingLjcz.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                        tvChargeingLjcz.getPaint().setAntiAlias(true);//抗锯齿
                        //调取取消订单接口
                        showDialog();
                        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                        builder.addFormDataPart("orderId", orderId + "");
                        RequestBody build = builder.build();
                        mPresenter.cancelOrder(UrlConstants.getMapHeader(mActivity), build);
                    }
                }
            }, "CHARGEING_OUTTIME_TIMER");
        }
    }

    private void closeTimeOutDialog() {
        if (timeOutDialog != null) {
            timeOutDialog.dismiss();
        }
        CountdownUtil.getInstance().cancel("CHARGEING_OUTTIME_TIMER");
    }

    private void showRechargeTimeOutDialog(List<String> dialogTips, int stopTimeout) {
        if (dialogTips != null && dialogTips.size() > 0
                && (alertDialogTimeRecharge == null || (alertDialogTimeRecharge != null && !alertDialogTimeRecharge.isShowing()))) {
            timeOutRecharge = stopTimeout;
            dialogStr = "";
            for (int i = 0; i < dialogTips.size(); i++) {
                if (i == dialogTips.size() - 1) {
                    dialogStr = dialogStr + dialogTips.get(i);
                } else {
                    dialogStr = dialogStr + dialogTips.get(i) + "\n";
                }
            }
            if (alertDialogTimeRecharge == null) {
                alertDialogTimeRecharge = new AlertDialogNavAndPost(mActivity).builder().setTitle("")
                        .setMsg(dialogStr.replace("%s", timeOutRecharge + ""))
                        .setCancelable(false)
                        .setCancelOutside(false)
                        .setPositiveButtonVisible(true)
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(mActivity, RechargeActivity.class));
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
            }
            if (alertDialogTimeRecharge.isShowing()) {
                alertDialogTimeRecharge.dismiss();
            }
            alertDialogTimeRecharge.show();
            CountdownUtil.getInstance().newTimer(timeOutRecharge * 1000, 1000, new CountdownUtil.ICountDown() {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.e("TAG", "millisUntilFinished = " + (millisUntilFinished / 1000));
                    if (alertDialogTimeRecharge != null) {
                        Log.e("TAG", "充值倒计时中... = " + (millisUntilFinished / 1000));
                        alertDialogTimeRecharge.setMsg(dialogStr.replace("%s", (millisUntilFinished / 1000) + ""));
                    }
                }

                @Override
                public void onFinish() {
                    closeRechargeTimeOutDialog();
                    if (!StringUtil.isNotEmpty(endCode)) {
                        showDialog();
                        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                        builder.addFormDataPart("orderId", orderId + "");
                        RequestBody build = builder.build();
                        mPresenter.stop(UrlConstants.getMapHeader(mActivity), build);
                    }
                }
            }, "RECHARGE_OUTTIME_TIMER");
        }
    }

    private void closeRechargeTimeOutDialog() {
        if (alertDialogTimeRecharge != null) {
            alertDialogTimeRecharge.dismiss();
        }
        CountdownUtil.getInstance().cancel("RECHARGE_OUTTIME_TIMER");
    }

    @Subscribe
    public void getChargeData(StartCodeChargeing data) {//扫码返回
        if (data != null) {
            interval = data.getInterval();
            List<String> dialogTips = data.getDialogTips();
            if (dialogTips != null && dialogTips.size() > 0) {
                String dialogStr = "";
                for (int i = 0; i < dialogTips.size(); i++) {
                    if (i == dialogTips.size() - 1) {
                        dialogStr = dialogStr + dialogTips.get(i);
                    } else {
                        dialogStr = dialogStr + dialogTips.get(i) + "\n";
                    }
                }
                new AlertDialogNavAndPost(mActivity).builder().setTitle("")
                        .setMsg(dialogStr)
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(mActivity, RechargeActivity.class));
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
            } else {
                tv_chargeing_tishi.setVisibility(View.VISIBLE);
                unit = data.getUnit();
                //启动倒计时
                num = data.getTimeOut() % unit > 0 ? data.getTimeOut() / unit + 1 : data.getTimeOut() / unit;
                RingLog.e("num = " + num);
                if (data.getTimeOut() > 0 && num > 0) {
                    if (num > 1) {
                        showTimeOutDialog(data.getTimeOut() - (unit * (num - 1)));
                    } else {
                        showTimeOutDialog(data.getTimeOut());
                    }
                }
                orderId = data.getOrderId();
                StringUtil.setText(tvChargeingCdf, "0元", "", View.VISIBLE, View.VISIBLE);
                StringUtil.setText(tvChargeingFwf, "0元", "", View.VISIBLE, View.VISIBLE);
                StringUtil.setText(tvChargeingZfy, "0元", "", View.VISIBLE, View.VISIBLE);
                StringUtil.setText(tvChargeingKwh, "0.00KWH", "", View.VISIBLE, View.VISIBLE);
                rlChargeingChargeAfter.setVisibility(View.VISIBLE);
                rlChargeingChargeBefore.setVisibility(View.GONE);
                rl_chargeing_coupon.setVisibility(View.GONE);
                tvChargeingGzbx.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                tvChargeingGzbx.getPaint().setAntiAlias(true);//抗锯齿
                ll_chargeing_ing.bringToFront();
                StringUtil.setText(tvChargeingStatus, "连接中...", "", View.VISIBLE, View.VISIBLE);
                StringUtil.setText(btnChargeingSubmit, "充电连接中...", "", View.VISIBLE, View.VISIBLE);
                //handler.sendEmptyMessage(0);
                PollingUtils.stopPollingService(getActivity(), ChargeStateService.class, ChargeStateService.ACTION);
                PollingUtils.startPollingService(getActivity(), stateTimeOut, ChargeStateService.class, ChargeStateService.ACTION, orderId);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        PollingUtils.stopPollingService(getActivity(), ChargeBillService.class, ChargeBillService.ACTION);
        PollingUtils.stopPollingService(getActivity(), ChargeStateService.class, ChargeStateService.ACTION);
        CountdownUtil.getInstance().cancel("CHARGEING_OUTTIME_TIMER");
        CountdownUtil.getInstance().cancel("RECHARGE_OUTTIME_TIMER");
    }

    @Subscribe
    public void getChargeState(ChargeingState event) {//轮询获取充电状态结果
        if (event != null) {
            if (event.getCode() == 0) {
                ChargeingState.DataBean data = event.getData();
                if (data != null) {
                    tv_chargeing_cdsj.updateShow(Integer.parseInt(data.getTotalTime()) * 1000);
                    tv_chargeing_cdsj.pause();
                    rl_chargeing_coupon.setVisibility(View.GONE);
                    rlChargeingChargeAfter.setVisibility(View.VISIBLE);
                    rlChargeingChargeBefore.setVisibility(View.GONE);
                    tvChargeingGzbx.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                    tvChargeingGzbx.getPaint().setAntiAlias(true);//抗锯齿
                    ll_chargeing_ing.bringToFront();
                    String soc = data.getSoc();
                    providerId = data.getProviderId();
                    if (StringUtil.isNotEmpty(soc)) {
                        wpv_chargeing.setProgress(Integer.parseInt(soc.replace("%", "")));
                    }
                    if (data.getDialogTips() != null && data.getDialogTips().size() > 0) {
                        showRechargeTimeOutDialog(data.getDialogTips(), data.getStopTimeout());
                    } else {
                        state = data.getState();
                        StringUtil.setText(tvChargeingName, data.getProviderName(), "", View.VISIBLE, View.GONE);
                        totalPrice = data.getTotalPrice();
                        totalServiceFee = data.getTotalServiceFee();
                        endCode = data.getEndCode();
                        if (StringUtil.isNotEmpty(data.getTotalPower())) {
                            StringUtil.setText(tvChargeingKwh, data.getTotalPower() + "KWH", "", View.VISIBLE, View.VISIBLE);
                        } else {
                            StringUtil.setText(tvChargeingKwh, "0.00KWH", "", View.VISIBLE, View.VISIBLE);
                        }
                        StringUtil.setText(tvChargeingCdf, data.getTotalPowerPrice() + "", "", View.VISIBLE, View.VISIBLE);
                        StringUtil.setText(tvChargeingFwf, totalServiceFee + "", "", View.VISIBLE, View.VISIBLE);
                        StringUtil.setText(tvChargeingZfy, totalPrice + "", "", View.VISIBLE, View.VISIBLE);
                        if (state == 0) {//连接中,插枪未充电
                            tv_chargeing_tishi.setVisibility(View.VISIBLE);
                            ll_chargeing_jsm.setVisibility(View.GONE);
                            StringUtil.setText(tvChargeingStatus, "连接中...", "", View.VISIBLE, View.VISIBLE);
                            StringUtil.setText(btnChargeingSubmit, "充电连接中...", "", View.VISIBLE, View.VISIBLE);
                            //handler.sendEmptyMessageDelayed(0, (interval * 1000));
                            PollingUtils.stopPollingService(getActivity(), ChargeStateService.class, ChargeStateService.ACTION);
                            PollingUtils.startPollingService(getActivity(), stateTimeOut, ChargeStateService.class, ChargeStateService.ACTION, orderId);
                        } else if (state == 1) {//进行中,轮询查询充电状态接口
                            tv_chargeing_tishi.setVisibility(View.GONE);
                            closeTimeOutDialog();
                            if (StringUtil.isNotEmpty(endCode)) {
                                ll_chargeing_jsm.setVisibility(View.VISIBLE);
                                StringUtil.setText(tv_chargeing_jsmname, (data.getProviderName() == null ? "" : data.getProviderName()) + "结束验证码", "", View.VISIBLE, View.VISIBLE);
                                StringUtil.setText(tv_chargeing_jsm, data.getEndCode(), "", View.VISIBLE, View.VISIBLE);
                            } else {
                                ll_chargeing_jsm.setVisibility(View.GONE);
                            }
                            StringUtil.setText(tvChargeingStatus, "充电中...", "", View.VISIBLE, View.VISIBLE);
                            if (providerId == 2) {
                                StringUtil.setText(btnChargeingSubmit, "输入上方结束码", "", View.VISIBLE, View.VISIBLE);
                            } else {
                                StringUtil.setText(btnChargeingSubmit, "结束充电", "", View.VISIBLE, View.VISIBLE);
                            }
                            //handler.sendEmptyMessageDelayed(0, (interval * 1000));
                            PollingUtils.stopPollingService(getActivity(), ChargeStateService.class, ChargeStateService.ACTION);
                            PollingUtils.startPollingService(getActivity(), stateTimeOut, ChargeStateService.class, ChargeStateService.ACTION, orderId);
                        } else if (state == 2) {//结算中,轮询获取账单接口
                            tv_chargeing_tishi.setVisibility(View.GONE);
                            closeTimeOutDialog();
                            ll_chargeing_jsm.setVisibility(View.GONE);
                            StringUtil.setText(tvChargeingStatus, "获取账单中...", "", View.VISIBLE, View.VISIBLE);
                            StringUtil.setText(btnChargeingSubmit, "获取账单中...", "", View.VISIBLE, View.VISIBLE);
                            handler.removeMessages(0);
                            PollingUtils.stopPollingService(getActivity(), ChargeStateService.class, ChargeStateService.ACTION);
                            PollingUtils.stopPollingService(getActivity(), ChargeBillService.class, ChargeBillService.ACTION);
                            PollingUtils.startPollingService(getActivity(), billTimeOut, ChargeBillService.class, ChargeBillService.ACTION, orderId);
                        } else if (state == 3) {//待支付,轮询获取账单接口
                            tv_chargeing_tishi.setVisibility(View.GONE);
                            closeTimeOutDialog();
                            ll_chargeing_jsm.setVisibility(View.GONE);
                            StringUtil.setText(tvChargeingStatus, "获取账单中...", "", View.VISIBLE, View.VISIBLE);
                            StringUtil.setText(btnChargeingSubmit, "获取账单中...", "", View.VISIBLE, View.VISIBLE);
                            handler.removeMessages(0);
                            PollingUtils.stopPollingService(getActivity(), ChargeStateService.class, ChargeStateService.ACTION);
                            PollingUtils.stopPollingService(getActivity(), ChargeBillService.class, ChargeBillService.ACTION);
                            PollingUtils.startPollingService(getActivity(), billTimeOut, ChargeBillService.class, ChargeBillService.ACTION, orderId);
                        }
                    }
                }
            } else if (event.getCode() == 220007) {
                rl_chargeing_coupon.setVisibility(View.GONE);
                rlChargeingChargeAfter.setVisibility(View.VISIBLE);
                rlChargeingChargeBefore.setVisibility(View.GONE);
                tvChargeingGzbx.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                tvChargeingGzbx.getPaint().setAntiAlias(true);//抗锯齿
                ll_chargeing_ing.bringToFront();
                tv_chargeing_tishi.setVisibility(View.GONE);
                closeTimeOutDialog();
                ll_chargeing_jsm.setVisibility(View.GONE);
                StringUtil.setText(tvChargeingStatus, "获取账单中...", "", View.VISIBLE, View.VISIBLE);
                StringUtil.setText(btnChargeingSubmit, "获取账单中...", "", View.VISIBLE, View.VISIBLE);
                handler.removeMessages(0);
                PollingUtils.stopPollingService(getActivity(), ChargeStateService.class, ChargeStateService.ACTION);
                PollingUtils.stopPollingService(getActivity(), ChargeBillService.class, ChargeBillService.ACTION);
                PollingUtils.startPollingService(getActivity(), billTimeOut, ChargeBillService.class, ChargeBillService.ACTION, orderId);
            } else {
                closeTimeOutDialog();
                RingToast.show(event.getMsg());
                rlChargeingChargeAfter.setVisibility(View.GONE);
                rlChargeingChargeBefore.setVisibility(View.VISIBLE);
                tvChargeingLjcz.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                tvChargeingLjcz.getPaint().setAntiAlias(true);//抗锯齿
                //调取取消订单接口
                showDialog();
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("orderId", orderId + "");
                RequestBody build = builder.build();
                mPresenter.cancelOrder(UrlConstants.getMapHeader(mActivity), build);
            }
        }
    }

    @Subscribe
    public void getChargeBill(ChargeingBill event) {//轮询获取账单结果
        PollingUtils.stopPollingService(getActivity(), ChargeBillService.class, ChargeBillService.ACTION);
        PollingUtils.startPollingService(getActivity(), billTimeOut, ChargeBillService.class, ChargeBillService.ACTION, orderId);
        if (event != null) {
            if (event.getCode() == 0) {
                ChargeingBill.DataBean data = event.getData();
                if (data != null) {
                    tv_chargeing_tishi.setVisibility(View.GONE);
                    state = 4;
                    rl_chargeing_coupon.setVisibility(View.VISIBLE);
                    totalPrice = data.getTotalPrice();
                    totalServiceFee = data.getTotalServiceFee();
                    if (StringUtil.isNotEmpty(totalPrice)) {
                        PollingUtils.stopPollingService(getActivity(), ChargeBillService.class, ChargeBillService.ACTION);
                    }
                    StringUtil.setText(btnChargeingSubmit, "支付", "", View.VISIBLE, View.VISIBLE);
                    if (StringUtil.isNotEmpty(data.getTotalPower())) {
                        StringUtil.setText(tvChargeingKwh, data.getTotalPower() + "KWH", "", View.VISIBLE, View.VISIBLE);
                    } else {
                        StringUtil.setText(tvChargeingKwh, "0.00KWH", "", View.VISIBLE, View.VISIBLE);
                    }
                    StringUtil.setText(tvChargeingStatus, "充电结束", "", View.VISIBLE, View.VISIBLE);
                    StringUtil.setText(tvChargeingCdf, data.getTotalPowerPrice() + "", "", View.VISIBLE, View.VISIBLE);
                    StringUtil.setText(tvChargeingFwf, totalServiceFee + "", "", View.VISIBLE, View.VISIBLE);
                    StringUtil.setText(tvChargeingZfy, totalPrice + "", "", View.VISIBLE, View.VISIBLE);
                }
            } else {
                RingToast.show("账单在飞来的路上，请耐心等候哦");
            }
        }
    }

    private void refresh() {
        if (SystemUtil.checkLogin(mActivity)) {
            showDialog();
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            if (SharedPreferenceUtil.getInstance(getActivity()).getBoolean("is_open", false)) {
                builder.addFormDataPart("reset", "1");
            } else {
                builder.addFormDataPart("reset", "0");
            }
            RequestBody build = builder.build();
            mPresenter.ing(UrlConstants.getMapHeader(mActivity), build);
            mPresenter.home(UrlConstants.getMapHeader(mActivity));
            SharedPreferenceUtil.getInstance(getActivity()).saveBoolean("is_open", false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.home(UrlConstants.getMapHeader(mActivity));
        }
    }

    @Override
    protected void initData() {
        refresh();
    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void requestData() {

    }

    @OnClick({R.id.tv_chargeing_titlebar_other, R.id.rl_chargeing_start, R.id.tv_chargeing_ljcz,
            R.id.btn_chargeing_submit, R.id.tv_chargeing_gzbx, R.id.rl_chargeing_coupon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_chargeing_coupon:
                if (StringUtil.isNotEmpty(totalPrice)) {
                    startActivity(new Intent(mActivity, SelectCouponActivity.class).putExtra("price", Double.valueOf(totalPrice)));
                }
                break;
            case R.id.tv_chargeing_titlebar_other:
                SystemUtil.cellPhone(mActivity, phone);
                break;
            case R.id.rl_chargeing_start:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, ScanCodeActivity.class).putExtra("phone", phone));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.tv_chargeing_ljcz:
                if (SystemUtil.checkLogin(mActivity)) {
                    startActivity(new Intent(mActivity, RechargeActivity.class));
                } else {
                    startActivity(new Intent(mActivity, LoginActivity.class));
                }
                break;
            case R.id.btn_chargeing_submit:
                if (state == 0) {//连接中,插枪未充电
                } else if (state == 1) {//进行中,轮询查询充电状态接口
                    if (StringUtil.isNotEmpty(endCode)) {
                        RingToast.show("请在充电桩上输入结束码");
                    } else {
                        showDialog();
                        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                        builder.addFormDataPart("orderId", orderId + "");
                        RequestBody build = builder.build();
                        mPresenter.stop(UrlConstants.getMapHeader(mActivity), build);
                    }
                } else if (state == 2) {//结算中,轮询获取账单接口
                } else if (state == 3) {//待支付,轮询获取账单接口
                } else if (state == 4) {//获取到账单，调取支付接口
                    showDialog();
                    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    builder.addFormDataPart("orderId", orderId + "");
                    builder.addFormDataPart("price", totalPrice);
                    if (couponId > 0) {
                        builder.addFormDataPart("couponId", couponId + "");
                    }
                    RequestBody build = builder.build();
                    mPresenter.pay(UrlConstants.getMapHeader(mActivity), build);
                }
                break;
            case R.id.tv_chargeing_gzbx:
                //启动定位
                mlocationClient.startLocation();
                break;
        }
    }

    private void setLocation() {
        mlocationClient = new AMapLocationClient(getActivity());
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
    }

    @Override
    public void ingSuccess(StartChargeing.DataBean data) {//查询是否有进行中订单
        rlChargeingChargeAfter.setVisibility(View.GONE);
        rlChargeingChargeBefore.setVisibility(View.VISIBLE);
        tvChargeingLjcz.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvChargeingLjcz.getPaint().setAntiAlias(true);//抗锯齿
        disMissDialog();
        if (data != null) {
            interval = data.getInterval();
            providerId = data.getProviderId();
            ll_chargeing_ing.bringToFront();
            orderId = data.getOrderId();
            state = data.getState();
            StringUtil.setText(tvChargeingName, data.getProviderName(), "", View.VISIBLE, View.GONE);
            handler.removeMessages(0);
            if (state == 0) {//连接中,插枪未充电
                unit = Integer.parseInt(data.getUnit());
                //启动倒计时
                num = data.getTimeout() % unit > 0 ? data.getTimeout() / unit + 1 : data.getTimeout() / unit;
                RingLog.e("num = " + num);
                if (data.getTimeout() > 0 && num > 0) {
                    if (num > 1) {
                        showTimeOutDialog(data.getTimeout() - (unit * (num - 1)));
                    } else {
                        showTimeOutDialog(data.getTimeout());
                    }
                }
                StringUtil.setText(tvChargeingKwh, "0.00KWH", "", View.VISIBLE, View.VISIBLE);
                rlChargeingChargeAfter.setVisibility(View.VISIBLE);
                rlChargeingChargeBefore.setVisibility(View.GONE);
                tvChargeingGzbx.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                tvChargeingGzbx.getPaint().setAntiAlias(true);//抗锯齿
                ll_chargeing_ing.bringToFront();
                StringUtil.setText(tvChargeingStatus, "连接中...", "", View.VISIBLE, View.VISIBLE);
                StringUtil.setText(btnChargeingSubmit, "充电连接中...", "", View.VISIBLE, View.VISIBLE);
                PollingUtils.stopPollingService(getActivity(), ChargeStateService.class, ChargeStateService.ACTION);
                PollingUtils.startPollingService(getActivity(), stateTimeOut, ChargeStateService.class, ChargeStateService.ACTION, orderId);
            } else if (state == 1) {//进行中,轮询查询充电状态接口
                rlChargeingChargeAfter.setVisibility(View.VISIBLE);
                rlChargeingChargeBefore.setVisibility(View.GONE);
                tvChargeingGzbx.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                tvChargeingGzbx.getPaint().setAntiAlias(true);//抗锯齿
                ll_chargeing_ing.bringToFront();
                StringUtil.setText(tvChargeingStatus, "充电中...", "", View.VISIBLE, View.VISIBLE);
                if (providerId == 2) {
                    StringUtil.setText(btnChargeingSubmit, "输入上方结束码", "", View.VISIBLE, View.VISIBLE);
                } else {
                    StringUtil.setText(btnChargeingSubmit, "结束充电", "", View.VISIBLE, View.VISIBLE);
                }
                PollingUtils.stopPollingService(getActivity(), ChargeStateService.class, ChargeStateService.ACTION);
                PollingUtils.startPollingService(getActivity(), stateTimeOut, ChargeStateService.class, ChargeStateService.ACTION, orderId);
            } else if (state == 2) {//结算中,轮询获取账单接口
                rlChargeingChargeAfter.setVisibility(View.VISIBLE);
                rlChargeingChargeBefore.setVisibility(View.GONE);
                tvChargeingGzbx.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                tvChargeingGzbx.getPaint().setAntiAlias(true);//抗锯齿
                ll_chargeing_ing.bringToFront();
                StringUtil.setText(tvChargeingStatus, "获取账单中...", "", View.VISIBLE, View.VISIBLE);
                StringUtil.setText(btnChargeingSubmit, "获取账单中...", "", View.VISIBLE, View.VISIBLE);
                PollingUtils.stopPollingService(getActivity(), ChargeStateService.class, ChargeStateService.ACTION);
                PollingUtils.stopPollingService(getActivity(), ChargeBillService.class, ChargeBillService.ACTION);
                PollingUtils.startPollingService(getActivity(), billTimeOut, ChargeBillService.class, ChargeBillService.ACTION, orderId);
            } else if (state == 3) {//待支付,轮询获取账单接口
                rlChargeingChargeAfter.setVisibility(View.VISIBLE);
                rlChargeingChargeBefore.setVisibility(View.GONE);
                tvChargeingGzbx.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                tvChargeingGzbx.getPaint().setAntiAlias(true);//抗锯齿
                ll_chargeing_ing.bringToFront();
                StringUtil.setText(tvChargeingStatus, "获取账单中...", "", View.VISIBLE, View.VISIBLE);
                StringUtil.setText(btnChargeingSubmit, "获取账单中...", "", View.VISIBLE, View.VISIBLE);
                PollingUtils.stopPollingService(getActivity(), ChargeStateService.class, ChargeStateService.ACTION);
                PollingUtils.stopPollingService(getActivity(), ChargeBillService.class, ChargeBillService.ACTION);
                PollingUtils.startPollingService(getActivity(), billTimeOut, ChargeBillService.class, ChargeBillService.ACTION, orderId);
            }
        }
    }

    @Override
    public void ingFail(int code, String msg) {
        RingToast.show(msg);
        disMissDialog();
        RingLog.e(TAG, "specialFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
        rlChargeingChargeAfter.setVisibility(View.GONE);
        rlChargeingChargeBefore.setVisibility(View.VISIBLE);
        tvChargeingLjcz.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvChargeingLjcz.getPaint().setAntiAlias(true);//抗锯齿
    }

    @Override
    public void stopFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "specialFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
        RingToast.show(msg);
    }

    @Override
    public void stopSuccess(ChargeingState.DataBean data) {
        disMissDialog();
        StringUtil.setText(tvChargeingStatus, "获取账单中...", "", View.VISIBLE, View.VISIBLE);
        StringUtil.setText(btnChargeingSubmit, "获取账单中...", "", View.VISIBLE, View.VISIBLE);
        handler.removeMessages(0);
        PollingUtils.stopPollingService(getActivity(), ChargeStateService.class, ChargeStateService.ACTION);
        PollingUtils.stopPollingService(getActivity(), ChargeBillService.class, ChargeBillService.ACTION);
        PollingUtils.startPollingService(getActivity(), billTimeOut, ChargeBillService.class, ChargeBillService.ACTION, orderId);
    }

    @Override
    public void paySuccess(ChargeingState.DataBean data) {
        rlChargeingChargeAfter.setVisibility(View.GONE);
        rlChargeingChargeBefore.setVisibility(View.VISIBLE);
        tvChargeingLjcz.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvChargeingLjcz.getPaint().setAntiAlias(true);//抗锯齿
        handler.removeMessages(0);
        PollingUtils.stopPollingService(getActivity(), ChargeStateService.class, ChargeStateService.ACTION);
        PollingUtils.stopPollingService(getActivity(), ChargeBillService.class, ChargeBillService.ACTION);
        disMissDialog();
        startActivity(new Intent(getActivity(), RechargeRecordActivity.class).putExtra("flag", 1));
    }

    @Override
    public void payFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "specialFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
        RingToast.show(msg);
    }

    @Override
    public void saveSuccess(AddChargeBean data) {
        disMissDialog();
        RingToast.show("已上报");
    }

    @Override
    public void saveFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "saveFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
    }

    @Override
    public void homeSuccess(HomeBean data) {
        disMissDialog();
        if (data != null) {
            phone = data.getKf_phone();
            balance = data.getBalance();
            tvChargeingMoney.setText(balance + "");
        }
    }

    @Override
    public void homeFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "homeFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
    }

    @Override
    public void cancelOrderSuccess(AddChargeBean data) {
        disMissDialog();
        rlChargeingChargeAfter.setVisibility(View.GONE);
        rlChargeingChargeBefore.setVisibility(View.VISIBLE);
        tvChargeingLjcz.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvChargeingLjcz.getPaint().setAntiAlias(true);//抗锯齿
        handler.removeMessages(0);
        PollingUtils.stopPollingService(getActivity(), ChargeStateService.class, ChargeStateService.ACTION);
        PollingUtils.stopPollingService(getActivity(), ChargeBillService.class, ChargeBillService.ACTION);
    }

    @Override
    public void cancelOrderFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "homeFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(mActivity, code);
        RingToast.show(msg);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                double lat = amapLocation.getLatitude();//获取纬度
                double lng = amapLocation.getLongitude();//获取经度
                RingLog.d(TAG, "定位成功lat = "
                        + lat + ", lng = "
                        + lng);
                if (lat > 0 && lng > 0) {
                    MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                    builder.addFormDataPart("orderid", orderId + "");
                    builder.addFormDataPart("lng", lng + "");
                    builder.addFormDataPart("lat", lat + "");
                    RequestBody build = builder.build();
                    mPresenter.save(UrlConstants.getMapHeader(mActivity), build);
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
