package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerRechargeActivityCommponent;
import com.haotang.easyshare.di.module.activity.RechargeActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.RechargeTemp;
import com.haotang.easyshare.mvp.presenter.RechargePresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.RechargeTempAdapter;
import com.haotang.easyshare.mvp.view.iview.IRechargeView;
import com.haotang.easyshare.mvp.view.widget.GridSpacingItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.ljy.devring.util.RingToast;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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
    private List<RechargeTemp> rechargeTempList = new ArrayList<RechargeTemp>();
    private RechargeTempAdapter rechargeTempAdapter;

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
        for (int i = 0; i < 10; i++) {
            rechargeTempList.add(new RechargeTemp("充10000", false));
        }
        rechargeTempAdapter = new RechargeTempAdapter(R.layout.item_recharge_temp
                , rechargeTempList);
        rvRechargeTemp.setAdapter(rechargeTempAdapter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        rechargeTempAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (rechargeTempList.size() > 0 && rechargeTempList.size() > position) {
                    for (int i = 0; i < rechargeTempList.size(); i++) {
                        RechargeTemp rechargeTemp = rechargeTempList.get(i);
                        if (rechargeTemp != null && rechargeTemp.isSelect()) {
                            rechargeTemp.setSelect(false);
                        }
                    }
                    RechargeTemp rechargeTemp = rechargeTempList.get(position);
                    if (rechargeTemp != null) {
                        rechargeTemp.setSelect(true);
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
                if (payWay > 0) {

                } else {
                    RingToast.show("请选择支付方式");
                }
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
}
