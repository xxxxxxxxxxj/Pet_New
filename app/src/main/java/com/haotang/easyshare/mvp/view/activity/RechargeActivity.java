package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.app.AppConfig;
import com.haotang.easyshare.di.component.activity.DaggerRechargeActivityCommponent;
import com.haotang.easyshare.di.module.activity.RechargeActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.ALiPayResult;
import com.haotang.easyshare.mvp.model.entity.res.RechargePayInfo;
import com.haotang.easyshare.mvp.model.entity.res.RechargeTemp;
import com.haotang.easyshare.mvp.presenter.RechargePresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.RechargeTempAdapter;
import com.haotang.easyshare.mvp.view.iview.IRechargeView;
import com.haotang.easyshare.mvp.view.widget.GridSpacingItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.haotang.easyshare.util.PayUtils;
import com.haotang.easyshare.util.SystemUtil;
import com.ljy.devring.other.RingLog;
import com.ljy.devring.util.RingToast;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 充值界面
 */
public class RechargeActivity extends BaseActivity<RechargePresenter> implements IRechargeView {
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_recharge_temp)
    RecyclerView rvRechargeTemp;
    @BindView(R.id.iv_recharge_wx)
    ImageView ivRechargeWx;
    @BindView(R.id.iv_recharge_zfb)
    ImageView ivRechargeZfb;
    private int payWay;
    private List<RechargeTemp.DataBean> rechargeTempList = new ArrayList<RechargeTemp.DataBean>();
    private RechargeTempAdapter rechargeTempAdapter;
    private int templateId;

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_recharge;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerRechargeActivityCommponent.builder().
                rechargeActivityModule(new RechargeActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("立即充值");
        rvRechargeTemp.setHasFixedSize(true);
        rvRechargeTemp.setNestedScrollingEnabled(false);
        NoScollFullGridLayoutManager noScollFullGridLayoutManager = new
                NoScollFullGridLayoutManager(rvRechargeTemp, this, 3, GridLayoutManager.VERTICAL, false);
        noScollFullGridLayoutManager.setScrollEnabled(false);
        rvRechargeTemp.setLayoutManager(noScollFullGridLayoutManager);
        rvRechargeTemp.addItemDecoration(new GridSpacingItemDecoration(3,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing),
                true));
        rechargeTempList.clear();
        rechargeTempAdapter = new RechargeTempAdapter(R.layout.item_recharge_temp
                , rechargeTempList);
        rvRechargeTemp.setAdapter(rechargeTempAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showDialog();
        mPresenter.list();
    }

    @Override
    protected void initEvent() {
        rechargeTempAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (rechargeTempList.size() > 0 && rechargeTempList.size() > position) {
                    for (int i = 0; i < rechargeTempList.size(); i++) {
                        RechargeTemp.DataBean rechargeTemp = rechargeTempList.get(i);
                        if (rechargeTemp != null && rechargeTemp.isSelect()) {
                            rechargeTemp.setSelect(false);
                        }
                    }
                    RechargeTemp.DataBean rechargeTemp = rechargeTempList.get(position);
                    if (rechargeTemp != null) {
                        rechargeTemp.setSelect(true);
                        templateId = rechargeTemp.getId();
                    }
                    rechargeTempAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }

    private void setPayWay(int flag) {
        if (flag == 1) {//微信
            payWay = 1;
            ivRechargeWx.setImageResource(R.mipmap.icon_addcharge_select);
            ivRechargeZfb.setImageResource(R.mipmap.icon_addcharge_unselect);
        } else if (flag == 2) {//支付宝
            payWay = 2;
            ivRechargeWx.setImageResource(R.mipmap.icon_addcharge_unselect);
            ivRechargeZfb.setImageResource(R.mipmap.icon_addcharge_select);
        }
    }

    @OnClick({R.id.iv_titlebar_back, R.id.rl_recharge_wx, R.id.rl_recharge_zfb, R.id.btn_recharge_ljcz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.rl_recharge_wx:
                setPayWay(1);
                break;
            case R.id.rl_recharge_zfb:
                setPayWay(2);
                break;
            case R.id.btn_recharge_ljcz:
                if (templateId <= 0) {
                    RingToast.show("请选择充值金额");
                    return;
                }
                if (payWay <= 0) {
                    RingToast.show("请选择支付方式");
                    return;
                }
                showDialog();
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                builder.addFormDataPart("templateId", String.valueOf(templateId));
                builder.addFormDataPart("payWay", String.valueOf(payWay));
                RequestBody body = builder.build();
                mPresenter.build(body);
                break;
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

    @Override
    public void listFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "saveFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }

    @Override
    public void listSuccess(List<RechargeTemp.DataBean> data) {
        disMissDialog();
        if (data != null && data.size() > 0) {
            rechargeTempList.clear();
            rechargeTempList.addAll(data);
            rechargeTempAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void buildSuccess(RechargePayInfo.DataBean data) {
        disMissDialog();
        if (data != null) {
            RechargePayInfo.DataBean.PayInfoBean payInfo = data.getPayInfo();
            if (payInfo != null) {
                if (payWay == 1) {//微信支付
                    PayUtils.weChatPayment(RechargeActivity.this, payInfo.getAppid(), payInfo.getPartnerid()
                            , payInfo.getPrepayid(), payInfo.getPackageX(), payInfo.getNoncestr(),
                            payInfo.getTimestamp(), payInfo.getSign(), dialog);
                } else if (payWay == 2) {//支付宝支付
                    PayUtils.payByAliPay(RechargeActivity.this, payInfo.getOrderStr(), mHandler);
                }
            }
        }
    }

    @Override
    public void buildFail(int code, String msg) {
        disMissDialog();
        RingLog.e(TAG, "saveFail() status = " + code + "---desc = " + msg);
        SystemUtil.Exit(this, code);
    }

    @Subscribe
    public void onWXPayResult(BaseResp baseResp) {
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            RingLog.d(TAG, "baseResp.errCode = " + baseResp.errCode);
            RingLog.d(TAG, "baseResp.errStr = " + baseResp.errStr);
            RingLog.d(TAG, "baseResp.transaction = " + baseResp.transaction);
            RingLog.d(TAG, "baseResp.openId = " + baseResp.openId);
            if (baseResp.errCode == 0) {
                RingLog.d(TAG, "支付成功");
                RingToast.show("支付成功");
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                RingLog.d(TAG, "支付失败");
                RingToast.show("支付失败");
            }
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AppConfig.ALI_SDK_PAY_FLAG: {
                    ALiPayResult payResult = new ALiPayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    RingLog.d(TAG, "resultInfo = " + resultInfo);
                    RingLog.d(TAG, "resultStatus = " + resultStatus);
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        RingLog.d(TAG, "支付成功");
                        RingToast.show("支付成功");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        RingLog.d(TAG, "支付失败");
                        RingToast.show("支付失败");
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
}
