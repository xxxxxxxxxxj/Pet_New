package com.haotang.easyshare.mvp.view.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haotang.easyshare.R;
import com.haotang.easyshare.di.component.activity.DaggerRefundActivityCommponent;
import com.haotang.easyshare.di.module.activity.RefundActivityModule;
import com.haotang.easyshare.mvp.model.entity.res.RefundReson;
import com.haotang.easyshare.mvp.model.entity.res.RefundSm;
import com.haotang.easyshare.mvp.presenter.RefundPresenter;
import com.haotang.easyshare.mvp.view.activity.base.BaseActivity;
import com.haotang.easyshare.mvp.view.adapter.RefundResonAdapter;
import com.haotang.easyshare.mvp.view.adapter.RefundSmAdapter;
import com.haotang.easyshare.mvp.view.iview.IRefundView;
import com.haotang.easyshare.mvp.view.widget.GridSpacingItemDecoration;
import com.haotang.easyshare.mvp.view.widget.NoScollFullGridLayoutManager;
import com.haotang.easyshare.mvp.view.widget.NoScollFullLinearLayoutManager;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 退款页
 */
public class RefundActivity extends BaseActivity<RefundPresenter> implements IRefundView {
    @BindView(R.id.tv_titlebar_title)
    TextView tvTitlebarTitle;
    @BindView(R.id.rv_refund_reason)
    RecyclerView rvRefundReason;
    @BindView(R.id.rv_refund_sm)
    RecyclerView rvRefundSm;
    @BindView(R.id.btn_refund_submit)
    Button btnRefundSubmit;
    private List<RefundReson> resonList = new ArrayList<RefundReson>();
    private List<RefundSm> smList = new ArrayList<RefundSm>();
    private RefundResonAdapter refundResonAdapter;
    private RefundSmAdapter refundSmAdapter;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_refund;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        activityListManager.addActivity(this);
        DaggerRefundActivityCommponent.builder().
                refundActivityModule(new RefundActivityModule(this, this)).build().inject(this);
    }

    @Override
    protected void setView(Bundle savedInstanceState) {
        tvTitlebarTitle.setText("申请退款");

        rvRefundReason.setHasFixedSize(true);
        rvRefundReason.setNestedScrollingEnabled(false);
        NoScollFullGridLayoutManager noScollFullGridLayoutManager = new
                NoScollFullGridLayoutManager(rvRefundReason, this, 2, GridLayoutManager.VERTICAL, false);
        noScollFullGridLayoutManager.setScrollEnabled(false);
        rvRefundReason.setLayoutManager(noScollFullGridLayoutManager);
        rvRefundReason.addItemDecoration(new GridSpacingItemDecoration(2,
                getResources().getDimensionPixelSize(R.dimen.verticalSpacing10),
                getResources().getDimensionPixelSize(R.dimen.horizontalSpacing10),
                true));
        for (int i = 0; i < 10; i++) {
            resonList.add(new RefundReson("支持的桩较少", false));
        }
        refundResonAdapter = new RefundResonAdapter(R.layout.item_refund_reson
                , resonList);
        rvRefundReason.setAdapter(refundResonAdapter);

        rvRefundSm.setHasFixedSize(true);
        rvRefundSm.setNestedScrollingEnabled(false);
        NoScollFullLinearLayoutManager noScollFullLinearLayoutManager = new NoScollFullLinearLayoutManager(this);
        noScollFullLinearLayoutManager.setScrollEnabled(false);
        rvRefundSm.setLayoutManager(noScollFullLinearLayoutManager);
        for (int i = 0; i < 10; i++) {
            smList.add(new RefundSm((i + 1) + ".退款金额仅包含实际充值金额，有系统活动赠送的金额不可退"));
        }
        refundSmAdapter = new RefundSmAdapter(R.layout.item_refund_sm, smList);
        rvRefundSm.setAdapter(refundSmAdapter);
        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider_white_5));
        rvRefundSm.addItemDecoration(divider);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {
        refundResonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (resonList.size() > 0 && resonList.size() > position) {
                    for (int i = 0; i < resonList.size(); i++) {
                        RefundReson refundReson = resonList.get(i);
                        if (refundReson != null && refundReson.isSelect()) {
                            refundReson.setSelect(false);
                        }
                    }
                    RefundReson refundReson = resonList.get(position);
                    if (refundReson != null) {
                        refundReson.setSelect(true);
                    }
                    refundResonAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @OnClick({R.id.iv_titlebar_back, R.id.btn_refund_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_titlebar_back:
                finish();
                break;
            case R.id.btn_refund_submit:
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
    protected void onDestroy() {
        super.onDestroy();
        activityListManager.removeActivity(this); //退出activity
    }
}
